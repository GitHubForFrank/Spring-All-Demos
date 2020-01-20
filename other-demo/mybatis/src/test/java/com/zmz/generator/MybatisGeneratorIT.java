package com.zmz.generator;

import org.apache.commons.io.IOUtils;
import org.apache.ibatis.io.Resources;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ASNPHDG
 * @create 2019-07-14 13:03
 */
public class MybatisGeneratorIT {

    /**
     * getResource方法中的 / 是必须要的
     * C:\temp
     * @throws Exception
     */
    @Test
    public void test_sqlServer_way01() throws Exception {
        URL url = MybatisGeneratorIT.class.getResource("/generator/generatorConfig_sqlServer.xml");
        File configFile = new File(url.getFile());
        List<String> warnings = new ArrayList<>();
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }

    /**
     * 如下是通过替换字符串的方法，实现动态替换xml中参数
     * @throws Exception
     */
    @Test
    public void test_sqlServer_way02() throws Exception {
        List<String> warnings = new ArrayList<>();
        String  mybatisConfigFilePath = "\\generator\\generatorConfig_sqlServer.xml";
        InputStream configFile = getInputStream(mybatisConfigFilePath);
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);

    }

    private InputStream getInputStream(String mybatisConfigFilePath) throws Exception {
        InputStream inputStream = Resources.getResourceAsStream(mybatisConfigFilePath);
        StringWriter writer = new StringWriter();
        IOUtils.copy(inputStream, writer, "UTF-8");
        String mybatisConfigContent =  writer.toString();
        System.out.println(mybatisConfigContent);
        mybatisConfigContent = mybatisConfigContent.replace("@username@","");
        return new ByteArrayInputStream(mybatisConfigContent.getBytes());
    }


}
