package pl.bmarkowski.holidays.holidays

import kotlinx.coroutines.flow.*
import org.springframework.stereotype.Service
import pl.bmarkowski.holidays.holidays.exceptions.HolidayNotFoundException
import pl.bmarkowski.holidays.holidays_api.HolidaysApiService
import java.time.LocalDate

@Service
class HolidaysService(private val holidaysApiService: HolidaysApiService) {

    suspend fun getNearestSameHolidayForCountriesAfterDate(
        date: LocalDate,
        country1: String,
        country2: String
    ): Holiday {
        return flowOf(country1, country2)
            .flatMapMerge(2) { country -> holidaysApiService.getHolidaysForCountryAfterDate(country, date).asFlow() }
            .toList()
            .filter { it.date.isAfter(date) }
            .groupBy { it.date }
            .filter { it.value.size > 1 }
            .mapValues {
                it.value
                    .map { holiday -> Pair(holiday.country, holiday.name) }
                    .groupBy { holiday -> holiday.first }
                    .mapValues { holidays -> holidays.value.map { holiday -> holiday.second } }
            }
            .filter { it.value.keys.size > 1 }
            .minWithOrNull(compareBy { it.key })?.let {
                Holiday(
                    date = it.key,
                    country1 = country1,
                    country1Names = it.value.getOrDefault(country1, emptyList()),
                    country2 = country2,
                    country2Names = it.value.getOrDefault(country2, emptyList())
                )
            } ?: throw HolidayNotFoundException()
    }
}