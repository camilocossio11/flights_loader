package org.ntic.flights

import com.typesafe.config.{ConfigFactory, Config}
import scala.jdk.CollectionConverters._

object FlightsLoaderConfig {
  private var config: Config = ConfigFactory.load()

  def loadTestingConfig(): Unit = {
    config = ConfigFactory.load("application-test.conf")
  }

  def filePath: String = config.getString("flightsLoader.filePath")
  def hasHeaders: Boolean = config.getBoolean("flightsLoader.hasHeaders")
  def headersLength: Int = config.getInt("flightsLoader.headersLength")
  def delimiter: String = config.getString("flightsLoader.delimiter")
  def outputDir: String = config.getString("flightsLoader.outputDir")
  def headers: List[String] = config.getStringList("flightsLoader.headers").asScala.toList
  def columnIndexMap: Map[String, Int] = headers.map(x => (x, headers.indexOf(x))).toMap
  def filteredOrigin: List[String] = config.getStringList("flightsLoader.filteredOrigin").asScala.toList
}
