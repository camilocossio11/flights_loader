package org.ntic.flights.data

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

class TimeTest extends AnyFlatSpec with Matchers {
  "A Time" should "be correctly initialized from string" in {
    val timeStr1 = "650"
    val timeStr2 = "1440"
    val timeStr3 = "3274"
    val expected1 = Time(6, 50)
    val expected2 = Time(14, 40)
    val expected3 = Time(8, 14)

    val result1 = Time.fromString(timeStr1)
    val result2 = Time.fromString(timeStr2)
    val result3 = Time.fromString(timeStr3)
    result1 shouldEqual expected1
    result2 shouldEqual expected2
    result3 shouldEqual expected3
  }

  "A time" should "be correctly initialized from negative minutes" in {
    val result = Time.fromMinutes(-30)
    val expected = Time(0, 0)
    result shouldEqual expected
  }

  "A time" should "be correctly initialized minutes greater than the maximum minutes in a day" in {
    val result = Time.fromMinutes(1540)
    val expected = Time(1, 40)
    result shouldEqual expected
  }

  "A time with empty value for input param" should "raise an Exception because of incorrect formatting" in {
    val timeStr = ""
    an [IllegalArgumentException] should be thrownBy Time.fromString(timeStr)
  }

  "A time with a more than 4-digit value for input param" should "raise an Exception because of incorrect formatting" in {
    val timeStr1 = "12345"
    val timeStr2 = "1234567"
    val timeStr3 = "123456789"

    an [IllegalArgumentException] should be thrownBy Time.fromString(timeStr1)
    an [IllegalArgumentException] should be thrownBy Time.fromString(timeStr2)
    an [IllegalArgumentException] should be thrownBy Time.fromString(timeStr3)
  }

  "A time with hours parameter greater than 23 or less than 0" should "raise an Exception because of incorrect formatting" in {
    an [IllegalArgumentException] should be thrownBy Time(-1, 30)
    an [IllegalArgumentException] should be thrownBy Time(24, 15)
  }

  "A time with minutes parameter greater than 59 or less than 0" should "raise an Exception because of incorrect formatting" in {
    an [IllegalArgumentException] should be thrownBy Time(6, -30)
    an [IllegalArgumentException] should be thrownBy Time(15, 60)
  }

  "A time" should "be correctly computed as minutes" in {
    val result1 = Time(2, 43).asMinutes
    val expected1 = 163

    val result2 = Time.fromString("2315").asMinutes
    val expected2 = 1395

    val result3 = Time.fromMinutes(385).asMinutes
    val expected3 = 385

    result1 shouldEqual expected1
    result2 shouldEqual expected2
    result3 shouldEqual expected3
  }

  "A time" should "be correctly represented as a string" in {
    val result1 = Time.fromMinutes(1540).toString
    val expected1 = "01:40"

    val result2 = Time(16, 35).toString
    val expected2 = "16:35"

    result1 shouldEqual expected1
    result2 shouldEqual expected2
  }

  "Compare" should "return 0 for equal times" in {
    val time1 = Time(4, 15)
    val time2 = Time(4, 15)

    time1.compare(time2) shouldBe 0
  }

  it should "return a negative number when the first time is earlier" in {
    val time1 = Time(1, 20)
    val time2 = Time(4, 15)

    time1.compare(time2) should be < 0
  }

  it should "return a positive number when the first time is later" in {
    val time1 = Time(6, 20)
    val time2 = Time(4, 15)

    time1.compare(time2) should be > 0
  }

}

