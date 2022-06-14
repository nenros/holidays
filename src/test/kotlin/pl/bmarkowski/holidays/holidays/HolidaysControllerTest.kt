package pl.bmarkowski.holidays.holidays

import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import pl.bmarkowski.holidays.holidays_api.Holidays
import pl.bmarkowski.holidays.holidays_api.HolidaysApiService
import java.time.LocalDate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class HolidaysControllerTest {
    @MockkBean
    private lateinit var holidaysApiService: HolidaysApiService

    @Test
    fun `successfull call and responds with found holidays`(@Autowired  webClient: WebTestClient) {
        coEvery { holidaysApiService.getHolidaysForCountryAfterDate(country1, dateToAsk) } returns listOf( 
            Holidays(country1HolidayName, date = nearestHolidayDate, country = country1),
            Holidays("Not Test Country 1", date = dateToAsk.plusDays(3), country = country1)
        )
        coEvery { holidaysApiService.getHolidaysForCountryAfterDate(country2, dateToAsk) } returns listOf(
            Holidays(country2HolidayName, date = nearestHolidayDate, country = country2),
            Holidays(country2AnotherHolidayName, date = nearestHolidayDate, country = country2),
            Holidays("Not Test Country 2", date = dateToAsk.plusDays(3), country = country2)
        )  


        webClient.get().uri {
            it.path("/holidays")
            it.queryParam("country1", country1)
            it.queryParam("country2", country2)
            it.queryParam("date", dateToAsk)

            it.build()
        }.exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.date").isEqualTo(nearestHolidayDate.toString())
            .jsonPath("$.name1").isEqualTo(country1HolidayName)
            .jsonPath("$.name2").isEqualTo("${country2HolidayName},${country2AnotherHolidayName}")
    }
    
    @Test
    fun `respond with 404 on not found holiday`(@Autowired  webClient: WebTestClient){
        coEvery { holidaysApiService.getHolidaysForCountryAfterDate(country1, dateToAsk) } returns listOf(
            Holidays(country1HolidayName, date = nearestHolidayDate, country = country1),
        )
        coEvery { holidaysApiService.getHolidaysForCountryAfterDate(country2, dateToAsk) } returns emptyList()
        webClient.get().uri {
            it.path("/holidays")
            it.queryParam("country1", country1)
            it.queryParam("country2", country2)
            it.queryParam("date", dateToAsk)

            it.build()
        }.exchange()
            .expectStatus().isNotFound
            .expectBody().isEmpty
    }
    
    companion object {
        val dateToAsk: LocalDate = LocalDate.now()
        val nearestHolidayDate: LocalDate = dateToAsk.plusDays(2)
        const val country1HolidayName = "Test Country 1"
        const val country2HolidayName = "Test Country 2"
        const val country2AnotherHolidayName = "Test Country 2.1"
        const val country1 = "PL"
        const val country2 = "NO"
    }
}