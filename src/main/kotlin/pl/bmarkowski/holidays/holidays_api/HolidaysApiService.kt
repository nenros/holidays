package pl.bmarkowski.holidays.holidays_api

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitExchange
import pl.bmarkowski.holidays.configuration.ApiConfiguration
import java.time.LocalDate

@Service
class HolidaysApiService(private val properties: ApiConfiguration) {
    private val webClient = WebClient.builder()
        .baseUrl("https://holidayapi.com/")
    .build()

    suspend fun getHolidaysForCountryAfterDate(country: String, date: LocalDate): List<Holidays> {
        return webClient.get().uri {
            it.path("/v1/holidays")
            it.queryParam("key", properties.apiKey)
            it.queryParam("country", country)
            it.queryParam("year", date.year)
            it.build()
        }
            .awaitExchange {
                it.awaitBody<HolidayApiResponse>().holidays
            }
    }
}