package org.ntic.flights.data

import scala.util.Try

/**
 * This class is used to represent a row of the flights data. It contains the following fields:
 * @param flDate: String
 * @param originAirportId: Long
 * @param origin: String
 * @param originCityName: String
 * @param originStateAbr: String
 * @param destAirportId: Long
 * @param dest: String
 * @param destCityName: String
 * @param destStateAbr: String
 * @param depTime: String
 * @param depDelay: Double
 * @param arrTime: String
 * @param arrDelay: Double
 */
case class Row (
                 flDate: String,
                 originAirportId: Long,
                 origin: String,
                 originCityName: String,
                 originStateAbr: String,
                 destAirportId: Long,
                 dest: String,
                 destCityName: String,
                 destStateAbr: String,
                 depTime: String,
                 depDelay: Double,
                 arrTime: String,
                 arrDelay: Double
               )

object Row {
  /**
   * This method is used to create a Row object from a list of tokens. It returns a Try[Row] object.
   * If the list of tokens is not valid or any of the token is invalid, it returns a Failure object. Otherwise, it
   * returns a Success object.
   *
   * @param tokens: Seq[String]
   * @return Try[Row]
   */
  def fromStringList(tokens: Seq[String]): Try[Row] = {
    val result: Try[Row] = Try {
      Row(
        flDate = tokens.head,
        originAirportId = tokens(1).toLong,
        origin = tokens(2),
        originCityName = tokens(3),
        originStateAbr = tokens(4),
        destAirportId = tokens(5).toLong,
        dest = tokens(6),
        destCityName = tokens(7),
        destStateAbr = tokens(8),
        depTime = tokens(9),
        depDelay = tokens(10).toDouble,
        arrTime = tokens(11),
        arrDelay = tokens(12).toDouble
      )
    }
    result
  }
}