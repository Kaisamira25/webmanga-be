package com.alpha.lainovo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(contact = @Contact(name = "Swe", email = "abc@gmail.com", url = "https://swe.vn/"), description = "Docs API Swe", title = "Docs API Swe", version = "1.0.0", license = @License(name = "abc name", url = "https://swe.vn/"), termsOfService = "terms of service"), servers = {
		@Server(description = "localhost dev", url = "http://localhost:8080") })

@SecurityScheme(name = "bearerAuth", description = "JWT auth description", scheme = "bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
public class ConfigSwagger {
}
