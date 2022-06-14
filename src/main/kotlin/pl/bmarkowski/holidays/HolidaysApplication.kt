package pl.bmarkowski.holidays

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HolidaysApplication

fun main(args: Array<String>) {
    runApplication<HolidaysApplication>(*args)
}
