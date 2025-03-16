package com.openclassrooms.mddapi.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuration class for Swagger/OpenAPI documentation.
 * Sets up the OpenAPI specification for the API documentation.
 * 
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */

@Configuration
public class SwaggerConfig {

        /**
         * Creates and configures an OpenAPI bean for API documentation.
         * Defines metadata such as API title, version, contact information,
         * license, and server URLs.
         * 
         * @return A configured OpenAPI instance
         */

        @Bean
        public OpenAPI myOpenAPI() {
                Server devServer = new Server();
                devServer.setUrl("http://localhost:8080");
                devServer.setDescription("Server URL in Development environment");

                Contact contact = new Contact();
                contact.setName("MDD API Support");
                contact.setEmail("support@mddapi.com");

                License mitLicense = new License()
                                .name("MIT License")
                                .url("https://choosealicense.com/licenses/mit/");

                Info info = new Info()
                                .title("MDD API Documentation")
                                .version("1.0")
                                .contact(contact)
                                .description("This API exposes endpoints for the MDD application.")
                                .license(mitLicense);

                return new OpenAPI()
                                .info(info)
                                .servers(List.of(devServer));
        }
}