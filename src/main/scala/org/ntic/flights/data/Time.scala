package org.ntic.flights.data

case class Time(hours: Int, minutes: Int) extends Ordered[Time] {
  require(hours >= 0 && hours <= 23, "hours must be within 0 and 23")
  require(minutes >= 0 && minutes <= 59, "minutes must be within 0 and 59")
  val asMinutes: Int = hours * 60 + minutes
  override lazy val toString: String = f"$hours%02d:$minutes%02d"

  def minus(that: Time): Int =
    this.asMinutes - that.asMinutes

  def -(that: Time): Int =
    minus(that)

  override def compare(that: Time): Int =
    this - that
}

object Time {

  private val totalMinutesInADay = 1440

  /**
   * This function is used to create a Time object from a string
   * @param timeStr: String
   * @return Time
   */
  def fromString(timeStr: String): Time = {
    require(0 < timeStr.length && timeStr.length <5, "timeStr must have at least 1 digit and up to 4 digits")
    val formatted: String = f"${timeStr.toInt}%04d"

    val inputHours: Int = formatted.substring(0, 2).toInt
    val hours: Int = if (inputHours/24 > 0) inputHours - 24*(inputHours/24) else inputHours

    val inputMinutes: Int = formatted.substring(2).toInt
    val minutes: Int = if (inputMinutes/60 > 0) inputMinutes - 60*(inputMinutes/60) else inputMinutes
    Time(hours, minutes)
  }

  def fromMinutes(minutes: Int): Time = {
    val normalized = if (minutes < 0) 0 else minutes % totalMinutesInADay
    Time(normalized / 60, normalized % 60)
  }
}