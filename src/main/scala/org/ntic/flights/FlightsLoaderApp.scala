package org.ntic.flights

import org.ntic.flights.data.{FileUtils, Flight, FlightsFileReport, Row}

import scala.util.Try

object FlightsLoaderApp extends App {
  val fileLines: Seq[String] = FileUtils.getLinesFromFile(FlightsLoaderConfig.filePath)
  val rows: Seq[Try[Row]] = FileUtils.loadFromFileLines(fileLines)
  val flightReport: FlightsFileReport = FlightsFileReport.fromRows(rows)
  val flights: Seq[Flight] = flightReport.flights

  val flightsByOrigin: Map[String, Seq[Flight]] = flights.groupBy(_.origin.code)

  FlightsLoaderConfig.filteredOrigin.foreach { origin =>
    require(flightsByOrigin.contains(origin), s"Origin $origin is not available. Please provide a value in ${flightsByOrigin.keys}")
    val delayedFlights: List[Flight] = flightsByOrigin(origin).filter(_.isDelayed).sorted.toList
    val notDelayedFlights: List[Flight] = flightsByOrigin(origin).filter(!_.isDelayed).sorted.toList
    FileUtils.writeFile(delayedFlights, s"${FlightsLoaderConfig.outputDir}/${origin}_delayed.obj")
    FileUtils.writeFile(notDelayedFlights, s"${FlightsLoaderConfig.outputDir}/$origin.obj")
  }
  println(flightReport)
}
