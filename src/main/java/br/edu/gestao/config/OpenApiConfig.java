package br.edu.gestao.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.*;

@Configuration
public class OpenApiConfig {
    @Bean
    OpenAPI api() {
        return new OpenAPI().info(new Info()
                .title("API Gestão Comercial")
                .description("API didática de clientes, produtos, serviços e vendas para consumo com Flutter.")
                .version("1.0.0"));
    }
}
