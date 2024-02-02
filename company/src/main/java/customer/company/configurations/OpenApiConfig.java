package customer.company.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info=@Info(
                contact=@Contact(
                        name="Dhanush",
                        email="dhanushindrakumar123@gmail.com"
                ),
                description="Open Api documentation",
                title="Customer Operations",
                version="1.0",
                termsOfService = "Terms of service"

        ),
        servers={
                @Server(
                        description="Dev",
                        url="http://localhost:2000"
                ),
                @Server(
                        description="Test",
                        url="http://localhost:2000"
                )
        },
        security = {
                @SecurityRequirement(name="bearerAuth")
        }


)
@SecurityScheme(
        name="bearerAuth",
        description="JWT auth description",
        scheme="bearer",
        type= SecuritySchemeType.HTTP,
        bearerFormat ="JWT",
        in= SecuritySchemeIn.HEADER

)









public class OpenApiConfig{

}





