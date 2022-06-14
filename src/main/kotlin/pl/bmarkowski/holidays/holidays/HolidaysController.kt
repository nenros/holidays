package pl.bmarkowski.holidays.holidays

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import pl.bmarkowski.holidays.holidays.request.HolidayRequest

@Component
class HolidaysHandler(private val holidaysService: HolidaysService) {

    suspend fun getNearestHolidays(request: ServerRequest): ServerResponse {
        val date = request.queryParamOrNull("date")
        val lang1 = request.queryParamOrNull("lang1")
        val lang2 = request.queryParamOrNull("lang2")
        
        val holiday = holidaysService.getNearestSameHolidayForCountriesAfterDate(requestBody.date, requestBody.lang1, requestBody.lang2)
        
        return ServerResponse.ok()
            .json()
            .bodyValueAndAwait(holiday)
    }
}