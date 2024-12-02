package org.ntic.flights.data

import com.sun.media.sound.InvalidFormatException

import java.time.LocalDate

/**
 * This class is used to represent a date of a flight
 * @param day: Int
 * @param month: Int
 * @param year: Int
 */
case class FlightDate(day: Int,
                      month: Int,
                      year: Int) {
  lazy private val cachedStr = f"$day%02d/$month%02d/$year"
  override def toString: String = cachedStr
}

object FlightDate {
  /**
   * This function validate that a date is correct by inspecting the values of day, month and year
   * @param month: Int
   * @param day: Int
   * @param year: Int
   * @return Boolean
   */
  private def validateDate(month: Int, day: Int, year: Int): Boolean = {
    (1 <= month && month <= 12) && (1 <= day && day <= 31) && (1987 < year && year <= LocalDate.now().getYear)
  }

  /**
   * This function is used to convert a string to a FlightDate
   * @param date: String
   * @return FlightDate
   */
  def fromString(date: String): FlightDate = {
    require(date.contains("/"), "A valid input should contains '/' character")
    val currentYear: Int = LocalDate.now().getYear
    date.split(" ").head.split("/").map(x => x.toInt).toList match {
      case List(month, day, year) if validateDate(month, day, year)=> FlightDate(day, month, year)
      case List(month, day, year) if
        month < 1 || 12 < month => throw new AssertionError(s"Invalid date $month/$day/$year: Month is not between 1 and 12")
      case List(month, day, year) if
        day < 1 || 31 < day => throw new AssertionError(s"Invalid date $month/$day/$year: Day is not between 1 and 31")
      case List(month, day, year) if
        year < 1987 || currentYear < year => throw new AssertionError(s"Invalid date $month/$day/$year: Year is not between 1987 and $currentYear")
      case _ => throw new InvalidFormatException(s"$date has invalid format")
    }
  }
}
