package org.ntic.flights.data

import org.ntic.flights.{FlightsLoaderConfig, TestData}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

import scala.util.Try

class FlightsFileReportTest extends AnyFlatSpec with Matchers {

  "A FlightFileReport" should "be correctly initialized from rows" in {
    val fileLines: Seq[String] = TestData.fileLines
    val rows: Seq[Try[Row]] = FileUtils.loadFromFileLines(fileLines)
    val flightReport: FlightsFileReport = FlightsFileReport.fromRows(rows)

    val expectedValidRows = List(
      Row("7/1/2023 12:00:00 AM", 10135, "ABE", "Allentown/Bethlehem/Easton, PA", "PA", 11057, "CLT", "Charlotte, NC", "NC", "606", 0.00, "739", 0.00),
      Row("7/1/2023 12:00:00 AM", 10135, "ABE", "Allentown/Bethlehem/Easton, PA", "PA", 14761, "SFB", "Sanford, FL", "FL", "551", -9.00, "808", -14.00),
      Row("7/1/2023 12:00:00 AM", 10136, "ABI", "Abilene, TX", "TX", 11298, "DFW", "Dallas/Fort Worth, TX", "TX", "1443", 45.00, "1606", 68.00),
      Row("7/1/2023 12:00:00 AM", 10140, "ABQ", "Albuquerque, NM", "NM", 10397, "ATL", "Atlanta, GA", "GA", "1640", 0.00, "2211", 0.00)
    )
    val expectedInvalidRows = List(
      "ArrayIndexOutOfBoundsException: 5",
      "ArrayIndexOutOfBoundsException: 7",
      "NumberFormatException: empty String"
    )
    val expectedFlights = expectedValidRows.map { row => Flight.fromRow(row) }

    flightReport.validRows should contain theSameElementsAs expectedValidRows
    flightReport.invalidRows should contain theSameElementsAs expectedInvalidRows
    flightReport.flights should contain theSameElementsAs expectedFlights
  }

  "A FlightFileReport" should "be correctly represented as a String" in {
    val fileLines: Seq[String] = TestData.fileLines
    val rows: Seq[Try[Row]] = FileUtils.loadFromFileLines(fileLines)
    val flightReport: FlightsFileReport = FlightsFileReport.fromRows(rows)

    val expected = s"""
    |FlightsReport:
    | - 4 valid rows.
    | - 3 invalid rows.
    |Error summary:
    |<ArrayIndexOutOfBoundsException: 5>: 1
    |<NumberFormatException: empty String>: 1
    |<ArrayIndexOutOfBoundsException: 7>: 1
    |""".stripMargin

    flightReport.toString shouldEqual expected
  }

}
