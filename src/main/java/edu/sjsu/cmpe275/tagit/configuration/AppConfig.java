package edu.sjsu.cmpe275.tagit.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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

}
