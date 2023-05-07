package com.example.loanservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        servers = {
                @Server(
                        description = "dev version of loan-order service",
                        url = "http://localhost:8081"
                )
        }
)
@Configuration
public class SpringDocConf {

    @Bean
    public OpenAPI baseOpenAPI() {

        Contact contact = new Contact();
        contact.setName("Vrzesinsky");
        contact.setUrl("https://t.me/puuuccik");

        return new OpenAPI().info(new Info()
                .title("Loan-order MTS")
                .version("1.0.0")
                .description("Documentation to loan-order service")
                .contact(contact));
    }
}
