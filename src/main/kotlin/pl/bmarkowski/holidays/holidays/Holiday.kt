package pl.bmarkowski.holidays.holidays

import java.time.LocalDate

data class Holiday(val date: LocalDate, val country1: String, val country1Names: List<String>, val country2: String, val country2Names: List<String>)
