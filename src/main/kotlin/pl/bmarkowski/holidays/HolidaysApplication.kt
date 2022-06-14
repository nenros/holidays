package pl.bmarkowski.holidays

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import pl.bmarkowski.holidays.configuration.ApiConfiguration

@SpringBootApplication
@EnableConfigurationProperties(ApiConfiguration::class)
class HolidaysApplication 

fun main(args: Array<String>) {
    runApplication<HolidaysApplication>(*args)
}
