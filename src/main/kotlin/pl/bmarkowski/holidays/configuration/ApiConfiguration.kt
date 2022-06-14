package pl.bmarkowski.holidays.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("holidays")
data class ApiConfiguration(val apiKey: String)
