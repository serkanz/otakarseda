package zengin.serkan.countrydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableConfigurationProperties
public class CountrydemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(CountrydemoApplication.class, args);
    }
}
