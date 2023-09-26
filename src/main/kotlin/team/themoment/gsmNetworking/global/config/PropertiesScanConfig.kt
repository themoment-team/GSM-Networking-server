package team.themoment.gsmNetworking.global.config

import team.themoment.gsmNetworking.global.security.encrypt.properties.EncryptProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Configuration
import team.themoment.gsmNetworking.global.security.jwt.properties.JwtExpTimeProperties
import team.themoment.gsmNetworking.global.security.jwt.properties.JwtProperties
import team.themoment.gsmNetworking.global.security.oauth.properties.Oauth2Properties

@Configuration
@ConfigurationPropertiesScan(
    basePackageClasses = [
        Oauth2Properties::class,
        JwtExpTimeProperties::class,
        JwtProperties::class,
        EncryptProperties::class
    ]
)
class PropertiesScanConfig