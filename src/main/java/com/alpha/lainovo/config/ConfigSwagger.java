package com.alpha.lainovo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

//@Configuration
@OpenAPIDefinition(info = @Info(contact = @Contact(name = "Lainovo", email = "thienthan726@gmail.com"), description = "Docs API Lainovo", title = "Docs API Lainovo", version = "1.0.0", license = @License(name = "Lainovo"), termsOfService = "terms of service"), servers = {
        @Server(description = "localhost dev", url = "http://localhost:8080") })
@SecurityScheme(name = "bearerAuth", description = "JWT auth description", scheme = "bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
public class ConfigSwagger {
}
