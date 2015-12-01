package edu.sjsu.cmpe275.tagit;


import edu.sjsu.cmpe275.tagit.AOP.AOP;
import edu.sjsu.cmpe275.tagit.Utils.EmailNotification;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by mjain on 11/23/15.
 */

@EnableAutoConfiguration
@ComponentScan(basePackages = "edu.sjsu.cmpe275.tagit")
@Configuration
@EnableTransactionManagement
public class AppConfig extends WebMvcConfigurerAdapter {
    @Bean
    public AOP getTagitLogin(){
        return new AOP();
    }

    @Bean
    public EmailNotification getEmailNotification(){
        return new EmailNotification();
    }

}
