package pl.bmarkowski.holidays.holidays

import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.bmarkowski.holidays.holidays.exceptions.HolidayNotFoundException
import pl.bmarkowski.holidays.holidays.response.HolidayResponse
import java.time.LocalDate

@RestController
@RequestMapping("/holidays")
class HolidaysController(private val holidaysService: HolidaysService) {

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getNearestHolidays(
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam  date: LocalDate,
        @RequestParam country1: String,
        @RequestParam country2: String
    ) : HolidayResponse = holidaysService.getNearestSameHolidayForCountriesAfterDate(date, country1, country2).toHolidayResponse()

    @ExceptionHandler(HolidayNotFoundException::class)
    fun handleNotFoundHoliday(exception: HolidayNotFoundException) : ResponseEntity<Unit>{
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }
}

private fun  Holiday.toHolidayResponse(): HolidayResponse {
 return HolidayResponse(this.date, this.country1Names.joinToString(","), this.country2Names.joinToString(","))
}
