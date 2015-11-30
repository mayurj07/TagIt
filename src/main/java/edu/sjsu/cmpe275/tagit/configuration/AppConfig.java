package edu.sjsu.cmpe275.tagit.configuration;

import edu.sjsu.cmpe275.tagit.AOP.TagitLogin;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by mjain on 11/23/15.
 */

@EnableAutoConfiguration
@ComponentScan(basePackages = "edu.sjsu.cmpe275.tagit")
@Configuration
@EnableTransactionManagement
public class AppConfig {
    @Bean
    public TagitLogin getTagitLogin(){
        return new TagitLogin();
    }
}
