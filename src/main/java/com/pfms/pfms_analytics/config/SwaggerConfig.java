package com.pfms.pfms_analytics.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springSwaggerExample() {
        // Can be accessed through http://localhost:8080/pfms-analytics/swagger-ui/index.html
        return new OpenAPI()
                .info(new Info().title("Financial Reports and Analytics API")
                        .description("The Financial Reports and Analytics API provides the ability to generate financial reports for monthly, quarterly, and yearly periods. It also offers data visualization features, presenting charts and graphs to display income, expenses, and budgets.")
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}