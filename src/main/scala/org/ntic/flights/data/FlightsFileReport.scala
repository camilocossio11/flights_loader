package org.ntic.flights.data

import scala.util.{Failure, Try}

/**
 * This class is used to represent a report of the flights file with the valid rows, invalid rows and the flights
 * extracted from the valid rows.
 * @param validRows: Seq[Row]
 * @param invalidRows: Seq[String]
 * @param flights: Seq[Flight]
 */
case class FlightsFileReport(
  validRows: Seq[Row],
  invalidRows: Seq[String],
  flights: Seq[Flight]
) {

  override val toString: String = {
    val errorSummary: Map[String, Int] = invalidRows.groupBy(identity).view.mapValues(_.size).toMap
    val errorStrings: List[String] = errorSummary.map {
      case (error, occurrences) => s"<$error>: $occurrences"
    }.toList
    s"""
      |FlightsReport:
      | - ${validRows.length} valid rows.
      | - ${invalidRows.length} invalid rows.
      |Error summary:
      |${errorStrings.mkString("\n")}
      |""".stripMargin
  }
}

object FlightsFileReport {
  /**
   * This function is used to create a FlightsFileReport from a list of Try[Row] objects where each Try[Row] represents a row
   * loaded from the file. If the row is valid, it is added to the validRows list, otherwise the error message is added to
   * the invalidRows list. Finally, the valid rows are converted to Flight objects and added to the flights list.
   *
   * @param rows: Seq\[Try[Row]\]
   * @return FlightsFileReport
   */
  def fromRows(rows: Seq[Try[Row]]): FlightsFileReport = {
    val validRows: List[Row] = rows.filter(_.isSuccess).map(_.get).toList
    val invalidRows: List[String] = rows.filter(_.isFailure).map(
      (x: Try[Row]) => x match {
        case Failure(exception) => s"${exception.getClass.getSimpleName}: ${exception.getMessage}"
      }
    ).toList
    val validRowsToFlights: List[Try[Flight]] = validRows.map((row: Row) => Try(Flight.fromRow(row)))

    val flights: List[Flight] = validRowsToFlights.filter(_.isSuccess).map(_.get)
    val invalidFlights: List[String] = validRowsToFlights.filter(_.isFailure).map(
      {
        case Failure(exception) => s"${exception.getClass.getSimpleName}: ${exception.getMessage}"
      }
    )
    val invalidObjects: List[String] = invalidRows ++ invalidFlights
    FlightsFileReport(validRows, invalidObjects, flights)
  }
}
