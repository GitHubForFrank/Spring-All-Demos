package com.zmz.core.config;

//import com.github.mkopylec.charon.configuration.CharonConfigurer;
//import com.github.mkopylec.charon.configuration.RequestMappingConfigurer;
//import com.github.mkopylec.charon.forwarding.RestTemplateConfigurer;
//import com.github.mkopylec.charon.forwarding.interceptors.rewrite.RequestServerNameRewriterConfigurer;
//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//
//import java.time.Duration;
//
//import static com.github.mkopylec.charon.forwarding.TimeoutConfigurer.timeout;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/4/29 9:10
 */
@Configuration
public class CharonConfiguration {

    // todo 还未调试成功，以后再试， https://blog.csdn.net/jimo_lonely/article/details/106006432

//    @Bean
//    public CharonConfigurer charonConfigurer() {
//        return CharonConfigurer.charonConfiguration()
////                .set(customInterceptorConfigurer())
//                .set(RestTemplateConfigurer.restTemplate()
//                        .set(timeout().connection(Duration.ofSeconds(1))
//                                .read(Duration.ofSeconds(100)).write(Duration.ofSeconds(100))))
//                .add(RequestMappingConfigurer.requestMapping("api")
//                        .set(RequestServerNameRewriterConfigurer
//                                .requestServerNameRewriter()
//                                .outgoingServers("localhost:10991/app01")
//                        )
//                        .pathRegex("/api/auth/.*")
//                ).add(RequestMappingConfigurer.requestMapping("sdk")
//                        // .set(rateLimiter().configuration(custom().timeoutDuration(Duration.ofSeconds(100))))
//                        .set(RequestServerNameRewriterConfigurer
//                                .requestServerNameRewriter()
//                                .outgoingServers("localhost:10991/app01")
//                        )
//                        .pathRegex("/api/sdk/.*")
//                );
//    }
}
