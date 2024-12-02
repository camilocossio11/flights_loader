package org.ntic.flights

import org.ntic.flights.data.{FileUtils, Flight, Row}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.io.File

class FileUtilsTest extends AnyFlatSpec with Matchers {
  "A line from a file" should "be invalid if there are empty columns" in {
    val singleRow =  """7/1/2023 12:00:00 AM;10257;ALB;Albany, NY;NY;11278;DCA;Washington, DC;VA;;;;"""
    FileUtils.isInvalidLine(singleRow) shouldBe true
  }

  "A line from a file" should "be valid if there are not any empty columns" in {
    val singleRow = """7/1/2023 12:00:00 AM;10257;ALB;Albany, NY;NY;11057;CLT;Charlotte, NC;NC;1128;-9.00;1320;-25.00"""
    FileUtils.isInvalidLine(singleRow) shouldBe false
  }

  "A line from a file" should "be invalid if there are more columns than expected" in {
    val singleRow = """7/1/2023 12:00:00 AM;10257;ALB;Albany, NY;NY;11057;CLT;Charlotte, NC;NC;1128;-9.00;1320;-25.00;extra"""
    FileUtils.isInvalidLine(singleRow) shouldBe true
  }

  "A line from a file" should "be invalid if there are less columns than expected" in {
    val singleRow = """7/1/2023 12:00:00 AM;10257;ALB;Albany, NY;NY;11057;CLT;Charlotte, NC;NC;1128;-9.00"""
    FileUtils.isInvalidLine(singleRow) shouldBe true
  }

  "Lines from file" should "be read correctly" in {
    val file = "src/test/resources/test_data.csv"
    val expected = List(
      "FL_DATE;ORIGIN_AIRPORT_ID;ORIGIN;ORIGIN_CITY_NAME;ORIGIN_STATE_ABR;DEST_AIRPORT_ID;DEST;DEST_CITY_NAME;DEST_STATE_ABR;DEP_TIME;DEP_DELAY;ARR_TIME;ARR_DELAY",
      "7/1/2023 12:00:00 AM;10135;ABE;Allentown/Bethlehem/Easton, PA;PA;10397;ATL;Atlanta, GA;GA;650;-4.00;847;-17.00",
      "7/1/2023 12:00:00 AM;10135;ABE;Allentown/Bethlehem/Easton, PA;PA;11057;CLT;Charlotte, NC;NC;606;-9.00;739;-26.00",
      "7/1/2023 12:00:00 AM;10135;ABE;Allentown/Bethlehem/Easton, PA;PA;13577;MYR;Myrtle Beach, SC;SC;710;-20.00;833;-31.00",
      "7/1/2023 12:00:00 AM;10135;ABE;Allentown/Bethlehem/Easton, PA;PA;13930;ORD;Chicago, IL;IL;641;11.00;819;31.00",
      "7/1/2023 12:00:00 AM;10135;ABE;Allentown/Bethlehem/Easton, PA;PA;14082;PGD;Punta Gorda, FL;FL;1950;-9.00;2215;-26.00",
      "7/1/2023 12:00:00 AM;10135;ABE;Allentown/Bethlehem/Easton, PA;PA;14112;PIE;St. Petersburg, FL;FL;625;-5.00;859;-6.00",
      "7/1/2023 12:00:00 AM;10135;ABE;Allentown/Bethlehem/Easton, PA;PA;14761;SFB;Sanford, FL;FL;551;-9.00;808;-14.00",
      "7/1/2023 12:00:00 AM;10136;ABI;Abilene, TX;TX;11298;DFW;Dallas/Fort Worth, TX;TX;1443;45.00;1606;68.00",
      "7/1/2023 12:00:00 AM;10140;ABQ;Albuquerque, NM;NM;10397;ATL;Atlanta, GA;GA;1640;-1.00;2211;21.00",
      "7/1/2023 12:00:00 AM;10140;ABQ;Albuquerque, NM;NM;10423;AUS;Austin, TX;TX;1139;4.00;1409;-11.00",
      "7/1/2023 12:00:00 AM;10140;ABQ;Albuquerque, NM;NM;10800;BUR;Burbank, CA;CA;1559;9.00;1645;0.00",
      "7/1/2023 12:00:00 AM;10140;ABQ;Albuquerque, NM;NM;10821;BWI;Baltimore, MD;MD;1427;17.00;2011;16.00",
      "7/1/2023 12:00:00 AM;10140;ABQ;Albuquerque, NM;NM;11259;DAL;Dallas, TX;TX;514;-1.00;748;-12.00",
      "7/1/2023 12:00:00 AM;10140;ABQ;Albuquerque, NM;NM;11292;DEN;Denver, CO;CO;544;-1.00;654;-11.00"
    )
    val result = FileUtils.getLinesFromFile(file)
    result shouldEqual expected
  }

  "File lines" should "be loaded correctly" in {
    val fileLines = List(
      "FL_DATE;ORIGIN_AIRPORT_ID;ORIGIN;ORIGIN_CITY_NAME;ORIGIN_STATE_ABR;DEST_AIRPORT_ID;DEST;DEST_CITY_NAME;DEST_STATE_ABR;DEP_TIME;DEP_DELAY;ARR_TIME;ARR_DELAY",
      "7/1/2023 12:00:00 AM;10135;ABE;Allentown/Bethlehem/Easton, PA;PA;10397;ATL;Atlanta, GA;GA;650;-4.00;847;-17.00",
      "7/1/2023 12:00:00 AM;10135;ABE;Allentown/Bethlehem/Easton, PA;PA;11057;CLT;Charlotte, NC;NC;606;-9.00;739;-26.00",
      "7/1/2023 12:00:00 AM;10135;ABE;Allentown/Bethlehem/Easton, PA;PA;13577;MYR;Myrtle Beach, SC;SC;710;-20.00;833;-31.00",
      "7/1/2023 12:00:00 AM;10135;ABE;Allentown/Bethlehem/Easton, PA;PA;13930;ORD;Chicago, IL;IL;641;11.00;819;31.00",
      "7/1/2023 12:00:00 AM;10135;ABE;Allentown/Bethlehem/Easton, PA;PA;14082;PGD;Punta Gorda, FL;FL;1950;-9.00;2215;-26.00",
      "7/1/2023 12:00:00 AM;10135;ABE;Allentown/Bethlehem/Easton, PA;PA;14112;PIE;St. Petersburg, FL;FL;625;-5.00;859;-6.00",
      "7/1/2023 12:00:00 AM;10135;ABE;Allentown/Bethlehem/Easton, PA;PA;14761;SFB;Sanford, FL;FL;551;-9.00;808;-14.00",
      "7/1/2023 12:00:00 AM;10136;ABI;Abilene, TX;TX;11298;DFW;Dallas/Fort Worth, TX;TX;1443;45.00;1606;68.00",
      "7/1/2023 12:00:00 AM;10140;ABQ;Albuquerque, NM;NM;10397;ATL;Atlanta, GA;GA;1640;-1.00;2211;21.00",
      "7/1/2023 12:00:00 AM;10140;ABQ;Albuquerque, NM;NM;10423;AUS;Austin, TX;TX;1139;4.00;1409;-11.00",
      "7/1/2023 12:00:00 AM;10140;ABQ;Albuquerque, NM;NM;10800;BUR;Burbank, CA;CA;1559;9.00;1645;0.00",
      "7/1/2023 12:00:00 AM;10140;ABQ;Albuquerque, NM;NM;10821;BWI;Baltimore, MD;MD;1427;17.00;2011;16.00",
      "7/1/2023 12:00:00 AM;10140;ABQ;Albuquerque, NM;NM;11259;DAL;Dallas, TX;TX;514;-1.00;748;-12.00",
      "7/1/2023 12:00:00 AM;10140;ABQ;Albuquerque, NM;NM;11292;DEN;Denver, CO;CO;544;-1.00;654;-11.00"
    )
    val result = FileUtils.loadFromFileLines(fileLines)
    result.length shouldBe 14
  }

  "Lines from empty file" should "be read correctly" in {
    val file = "src/test/resources/test_data_empty.csv"
    val expected = Nil
    val result = FileUtils.getLinesFromFile(file)
    result shouldEqual expected
  }

  "Empty list of file lines" should "raise an Exception because of missing information" in {
    val fileLines = Nil
    an [IllegalArgumentException] should be thrownBy FileUtils.loadFromFileLines(fileLines)
  }

  "File without data after headers" should "raise an Exception because of missing information" in {
    val fileLines = List(
      "FL_DATE;ORIGIN_AIRPORT_ID;ORIGIN;ORIGIN_CITY_NAME;ORIGIN_STATE_ABR;DEST_AIRPORT_ID;DEST;DEST_CITY_NAME;DEST_STATE_ABR;DEP_TIME;DEP_DELAY;ARR_TIME;ARR_DELAY"
    )
    an [IllegalArgumentException] should be thrownBy FileUtils.loadFromFileLines(fileLines)
  }

  "FileUtils" should "successfully create a .obj file with flight data" in {
    FlightsLoaderConfig.loadTestingConfig()
    val row = Row("7/1/2023 12:00:00 AM", 10135, "ABE", "Allentown/Bethlehem/Easton, PA", "PA", 11057, "CLT", "Charlotte, NC", "NC", "606", -9.00, "739", -26.00)
    val flights = List(Flight.fromRow(row))
    val filePath = s"${FlightsLoaderConfig.outputDir}/testCreation.obj"
    FileUtils.writeFile(flights, filePath)

    val file = new File(filePath)
    file.exists() shouldBe true
    file.delete()
  }

  "FileUtils" should "successfully create a .obj file with empty data" in {
    FlightsLoaderConfig.loadTestingConfig()
    val flights = Nil
    val filePath = s"${FlightsLoaderConfig.outputDir}/testCreationEmpty.obj"
    FileUtils.writeFile(flights, filePath)

    val file = new File(filePath)
    file.exists() shouldBe true
    file.delete()
  }

  "FileUtils" should "successfully read a .obj file with flight data and valid path" in {
    FlightsLoaderConfig.loadTestingConfig()
    val row = Row("7/1/2023 12:00:00 AM", 10135, "ABE", "Allentown/Bethlehem/Easton, PA", "PA", 11057, "CLT", "Charlotte, NC", "NC", "606", -9.00, "739", -26.00)
    val flights = List(Flight.fromRow(row))
    val filePath = s"${FlightsLoaderConfig.outputDir}/testRead.obj"
    FileUtils.writeFile(flights, filePath)
    val loadedFlights = FileUtils.loadFromFile(filePath)

    loadedFlights shouldEqual flights
    val file = new File(filePath)
    file.delete()
  }

  "FileUtils" should "raise an Exception because of invalid path" in {
    val filePath = "invalid/path"

    an [IllegalArgumentException] should be thrownBy FileUtils.getLinesFromFile(filePath)
  }

  "FileUtils read operation" should "raise an Exception because of invalid path" in {
    val filePath = "invalid/path"

    an [IllegalArgumentException] should be thrownBy FileUtils.loadFromFile(filePath)
  }

}
