package com.zmz.core.config;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.zmz.core.api.dto.Response;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import springfox.documentation.RequestHandler;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static springfox.documentation.schema.AlternateTypeRules.newRule;

/**
 * @author ASNPHDG
 * @create 2020-01-26 21:31
 */
@Slf4j
@Configuration
@PropertySource(value = "classpath:swagger2.properties", ignoreResourceNotFound = true, encoding = "UTF-8")
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.enable}")
    private boolean swaggerEnable;

    @Value("${swagger2.basePackage}")
    private String basePackage;
    @Value("${swagger2.title}")
    private String title;
    @Value("${swagger2.description}")
    private String description;
    @Value("${swagger2.termsOfServiceUrl}")
    private String termsOfServiceUrl;
    @Value("${swagger2.version}")
    private String version;
    @Value("${swagger2.contact.name}")
    private String contactName;
    @Value("${swagger2.contact.url}")
    private String contactUrl;
    @Value("${swagger2.contact.email}")
    private String contactEmail;

    @Autowired
    private TypeResolver typeResolver;
    @Autowired
    private SwaggerHttpHeader swaggerHttpHeader;


    /**
     * 开发和测试环境下可以开启swagger辅助进行调试,而生产环境下可以关闭或者进行相应的权限控制，防止接口信息泄露
     * @return
     */
    @Bean
    public Docket createDocket4AllApis(){
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerEnable)
                .groupName("All APIs")
                .apiInfo(apiInfo(title))
                .select()
                .apis(getPredicate(basePackage))
                .paths(Predicates.not(PathSelectors.regex("/error.*|/actuator.*")))
                .build()
                .alternateTypeRules(getRule())
                .globalOperationParameters(this.getHeaders())
                .forCodeGeneration(true);
    }
    @Bean
    public String createDocketByController(){
        Map<String, Object> mapOfAllControl = SpringContextUtil.getApplicationContext().getBeansWithAnnotation(Api.class);
        if(mapOfAllControl!=null) {
            for (Map.Entry<String, Object> entry : mapOfAllControl.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                Class<?> declaringClass = value.getClass();

                String packageStr = declaringClass.getPackage().getName();
                ApiIgnore apiIgnore = declaringClass.getAnnotation(ApiIgnore.class);
                if (!packageStr.startsWith(basePackage) || apiIgnore != null) {
                    continue;
                }

                //sometime declaringClass.getName() contains $ , for example : com.zmz.testlog.api.controller.DictionaryController$$EnhancerBySpringCGLIB$$2f138dac
                String controllerName = declaringClass.getName().contains("$") ?
                        declaringClass.getName().substring(0, declaringClass.getName().indexOf('$')) : declaringClass.getName();

                Api api = declaringClass.getAnnotation(Api.class);
                String apiValue = api.value();

                Docket obj = new Docket(DocumentationType.SWAGGER_2)
                        .enable(swaggerEnable)
                        .groupName(apiValue)
                        .select()
                        .apis(getPredicate(controllerName))
                        .paths(PathSelectors.any())
                        .build()
                        .apiInfo(apiInfo(title))
                        .alternateTypeRules(getRule())
                        .globalOperationParameters(this.getHeaders())
                        .forCodeGeneration(true);

                registerBean(key, obj);
            }
        }
        return "createDocketByController";
    }
    @Bean
    public String createDocketByPackage(){
        Set<String> packageList = getPackageList();
        if (!CollectionUtils.isEmpty(packageList)){
            packageList.forEach(packages -> {
                Docket obj = new Docket(DocumentationType.SWAGGER_2)
                        .enable(swaggerEnable)
                        .groupName(packages)
                        .apiInfo(apiInfo(packages))
                        .select()
                        .apis(RequestHandlerSelectors.basePackage(packages))
                        .paths(PathSelectors.any())
                        .build()
                        .globalOperationParameters(this.getHeaders());
                registerBean(packages,obj);
            });
        }
        return "createDocketByPackage";
    }

    private ApiInfo apiInfo(String title) {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .termsOfServiceUrl(termsOfServiceUrl)
                .contact(new Contact(contactName, contactUrl, contactEmail))
                .version(version)
                .build();
    }
    private Predicate<RequestHandler> getPredicate(String splitCharacter){
        return new Predicate<RequestHandler>() {
            @Override
            public boolean apply(RequestHandler input) {
                boolean tempInd = false;
                Class<?> declaringClass = input.declaringClass();
                if(declaringClass.isAnnotationPresent(Api.class)) {
                    String conName = declaringClass.getName().contains("$")?
                            declaringClass.getName().substring(0,declaringClass.getName().indexOf('$')):declaringClass.getName();
                    if(conName.startsWith(splitCharacter)){
                        tempInd = true;
                    }
                }
                return tempInd;
            }
        };
    }
    private AlternateTypeRule getRule() {
        return newRule(typeResolver.resolve(Response.class, typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                typeResolver.resolve(WildcardType.class));
    }
    private List<Parameter> getHeaders(){
        List<Parameter> parameters = new ArrayList<>();
        for(int iLoop=0; iLoop<swaggerHttpHeader.getName().size(); iLoop++){
            ParameterBuilder parameterBuilder = new ParameterBuilder();
            parameterBuilder
                    // Parameter type support : header, cookie, body, query etc
                    .parameterType("header")
                    .name(swaggerHttpHeader.getName().get(iLoop))
                    .defaultValue(swaggerHttpHeader.getDefaultValue().get(iLoop))
                    .description(swaggerHttpHeader.getDescription().get(iLoop))
                    .modelRef(new ModelRef(swaggerHttpHeader.getModelRef().get(iLoop)))
                    .required(swaggerHttpHeader.getRequired().get(iLoop)).build();
            parameters.add(parameterBuilder.build());
        }
        return parameters;
    }
    private Set<String> getPackageList(){

        String SPRING_FOX_PACKAGE = "springfox.documentation";
        String SPRING_PACKAGE = "org.springframework";
        String SWAGGER_BOOTSTRAP_UI_PACKAGE="com.github.xiaoymin.swaggerbootstrapui.web";
        String CORE_PACKAGE="com.zmz.core";
        String CORE_DEMO_PACKAGE="com.zmz.demo";

        Set<String> packageSet = new HashSet<>();
        Map<String, HandlerMapping> handlerMappingMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(SpringContextUtil.getApplicationContext() , HandlerMapping.class);
        for (HandlerMapping handlerMapping : handlerMappingMap.values()){
            if (handlerMapping instanceof RequestMappingHandlerMapping){
                RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) handlerMapping;
                Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
                for (Map.Entry<RequestMappingInfo, HandlerMethod> requestMappingInfoHandlerMethodEntry : handlerMethods.entrySet()){
                    HandlerMethod mappingInfoValue = requestMappingInfoHandlerMethodEntry.getValue();
                    String packageName = mappingInfoValue.getBeanType().getPackage().getName();
                    if (!packageName.contains(SPRING_FOX_PACKAGE) && !packageName.contains(SPRING_PACKAGE) && !packageName.contains(SWAGGER_BOOTSTRAP_UI_PACKAGE) && !packageName.contains(CORE_PACKAGE) && !packageName.contains(CORE_DEMO_PACKAGE)){
                        packageSet.add(packageName);
                    }
                }
            }
        }
        return packageSet;
    }
    private void registerBean(String key,Docket obj){
        log.info("registerBean - key: {}",key);
        ApplicationContext context =  SpringContextUtil.getApplicationContext();
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)context.getAutowireCapableBeanFactory();
        String beanName = "SwaggerDocketBean_"+key;
        BeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClassName(obj.getClass().getName());
        beanDefinition.setScope("prototype");
        ((GenericBeanDefinition) beanDefinition).setSource(obj);
        ((GenericBeanDefinition) beanDefinition).setBeanClass(Docket.class);
        beanFactory.registerBeanDefinition(beanName, beanDefinition);
        context.getAutowireCapableBeanFactory().applyBeanPostProcessorsAfterInitialization(obj, beanName);
        beanFactory.registerSingleton(beanName, obj);
    }

}
