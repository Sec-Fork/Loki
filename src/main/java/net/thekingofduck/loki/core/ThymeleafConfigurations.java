package net.thekingofduck.loki.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.io.File;
import java.io.FileNotFoundException;

@Configuration
public class ThymeleafConfigurations {

    @Value("${loki.templates}")
    private String templates;

    @Bean
    public SpringResourceTemplateResolver firstTemplateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        //templateResolver.setPrefix("classpath:/templates2/");

        File path = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String outside_templates= String.format("%s%s",System.getProperty("user.dir"),templates).replaceAll("\\\\","/");
        templateResolver.setPrefix("file:"+outside_templates);

        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCheckExistence(true);

        //Spring Boot中Thymeleaf引擎动态刷新
        templateResolver.setCacheable(false);
        return templateResolver;
    }

}
