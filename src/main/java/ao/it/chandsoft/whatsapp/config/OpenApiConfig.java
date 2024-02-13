package ao.it.chandsoft.whatsapp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .version("1.0.0")
                        .title("WhatsApp API")
                        .description("It allows you send whatsapp message via WhatsApp API")
                        .contact(new Contact().name("Nelson Chandimba da Silva")
                                .email("dismao16@gmail.com")
                        )
                        .termsOfService("Terms Of Service")
                        /*.license(new License()
                                .name("MIT Licence")
                                .url("https://opensource.org/license/mit/")
                                .identifier("7c2f3780-a579-4b68-8ede-4c9662cd1e9d")
                        )*/
                );
    }

}
