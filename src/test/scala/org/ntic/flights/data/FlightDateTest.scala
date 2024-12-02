package org.ntic.flights.data

import com.sun.media.sound.InvalidFormatException
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

class FlightDateTest extends AnyFlatSpec with Matchers {
  "A FlightDate" should "be correctly initialized from string" in {
    val dateStr = "7/1/2023 12:00:00 AM"
    val expected = FlightDate(day = 1, month = 7, year = 2023)
    val result = FlightDate.fromString(dateStr)
    result shouldEqual expected
  }

  "A FlightDate" should "raise an Exception because of wrong string format" in {
    val dateStr = "7/1/2023/3 12:00:00 AM"
    an [InvalidFormatException] should be thrownBy FlightDate.fromString(dateStr)
  }

  "A FlightDate" should "print its value in corrected format: DD/MM/YYYY" in {
    val dateStr = "7/1/2023 12:00:00 AM"
    val flightDate = FlightDate.fromString(dateStr)
    val expected = "01/07/2023"
    val result = flightDate.toString
    result shouldEqual expected
  }

  "A FlightDate" should "raise an Exception because of wrong day format" in {
    val dateStr1 = "7/41/2023 12:00:00 AM"
    val dateStr2 = "7/-15/2023 12:00:00 AM"

    an [AssertionError] should be thrownBy FlightDate.fromString(dateStr1)
    an [AssertionError] should be thrownBy FlightDate.fromString(dateStr2)
  }

  "A FlightDate" should "raise an Exception because of wrong month format" in {
    val dateStr1 = "15/3/2023 12:00:00 AM"
    val dateStr2 = "0/3/2023 12:00:00 AM"

    an [AssertionError] should be thrownBy FlightDate.fromString(dateStr1)
    an [AssertionError] should be thrownBy FlightDate.fromString(dateStr2)
  }

  "A FlightDate" should "raise an Exception because of wrong year format" in {
    val dateStr1 = "10/3/1970 12:00:00 AM"
    val dateStr2 = "10/3/2030 12:00:00 AM"

    an [AssertionError] should be thrownBy FlightDate.fromString(dateStr1)
    an [AssertionError] should be thrownBy FlightDate.fromString(dateStr2)
  }

  "A FlightDate" should "correctly initialized from a sting without spaces" in {
    val dateStr = "7/1/2023"
    val expected = FlightDate(day = 1, month = 7, year = 2023)
    val result = FlightDate.fromString(dateStr)
    result shouldEqual expected
  }

  "A FlightDate" should "raise an Exception because of invalid inputs" in {
    val dateStr1 = ""
    val dateStr2 = "x"
    val dateStr3 = "123"

    an [IllegalArgumentException] should be thrownBy FlightDate.fromString(dateStr1)
    an [IllegalArgumentException] should be thrownBy FlightDate.fromString(dateStr2)
    an [IllegalArgumentException] should be thrownBy FlightDate.fromString(dateStr3)
  }

}
