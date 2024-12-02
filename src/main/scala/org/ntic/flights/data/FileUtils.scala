package org.ntic.flights.data

import org.ntic.flights.FlightsLoaderConfig

import java.io.{File, FileInputStream, FileOutputStream, ObjectInputStream, ObjectOutputStream}
import scala.io.{BufferedSource, Source}
import scala.util.Try

object FileUtils {

  /**
   * This function is used to check if the line is valid or not
   *
   * @param s: String
   * @return Boolean: true if the line is invalid, false otherwise
   */
  def isInvalidLine(s: String): Boolean = {
    val delimiter: String = FlightsLoaderConfig.delimiter
    val expectedLength: Int = FlightsLoaderConfig.headersLength
    val values: List[String] = s.split(delimiter).toList
    values.length != expectedLength
  }

  /**
   * This function is used to read the file located in the path `filePath` and return a list of lines of the file
   *
   * @param filePath: String
   * @return List[String]
   */
  def getLinesFromFile(filePath: String): List[String] = {
    val inputFile = new File(filePath)
    require(inputFile.exists(), "File must exists")
    val source: BufferedSource = Source.fromFile(filePath)
    val lines: List[String] = source.getLines().toList

    source.close()
    lines
  }

  /**
   * This function is used to load the rows from the file lines
   *
   * @param fileLines: Seq[String]
   * @return Seq\[Try\[Row\]\]
   */
  def loadFromFileLines(fileLines: Seq[String]): Seq[Try[Row]] = {
    require(fileLines.nonEmpty, "File must not be empty")

    val delimiter: String = FlightsLoaderConfig.delimiter
    val targetLines: Seq[String] = if (FlightsLoaderConfig.hasHeaders) fileLines.tail else fileLines
    require(targetLines.nonEmpty, "File must have data after headers")

    val rows: Seq[Try[Row]] = targetLines.map(_.split(delimiter)).map(Row.fromStringList(_)).toList
    rows
  }

  def writeFile(flights: Seq[Flight], outputFilePath: String): Unit = {
    val file = new File(outputFilePath)
    file.getParentFile.mkdirs()

    val out = new ObjectOutputStream(new FileOutputStream(outputFilePath))
    try {
      out.writeObject(flights)
    } finally {
      out.close()
    }
  }

  def loadFromFile(filePath: String): Seq[Flight] = {
    val inputFile = new File(filePath)
    require(inputFile.exists(), "Input file must exists")
    val input = new ObjectInputStream(new FileInputStream(filePath))
    try {
      input.readObject().asInstanceOf[Seq[Flight]]
    } finally {
      input.close()
    }
  }

}