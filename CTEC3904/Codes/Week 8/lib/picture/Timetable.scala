package lib.picture

import lib.picture.Picture._
import scala.io.Source

class Timetable(
    firstDay:   Int     =  1,   // 1 is Monday
    lastDay:    Int     =  5,   // 5 is Friday
    firstTime:  Int     =  9,   // 09:00
    lastTime:   Int     = 17,   // 17:00
    slotWidth:  Int     =  9,   // Minimum column width (< 9 will truncate headers)
    alsoBeside: Boolean = false // Show clashes vertically (true) horizontally (false)
  ) {
  private val nbrOfDays = lastDay - firstDay + 1
  private val days = (firstDay to lastDay).toList
  private val dayNames = List("MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY","SUNDAY")
  private val dayLabels = dayNames drop (firstDay - 1) take nbrOfDays
  private val header = dayLabels map (Picture(_) fixWidth slotWidth)
  private val times = (firstTime to lastTime).toList
  private val timeLabels = "Time " :: (times map (t => s"$t:00"))
  private val timeWidth = 5
  private val margin = timeLabels map (Picture(_) fixWidth(timeWidth,RGT))
  
  private case class Slot(day: Int, time: Int)

  private val slots =
    for (d <- days; t <- times) yield Slot(d,t)

  private val alsoP: Picture =
    if (alsoBeside)
      Picture("ALSO").border(' ')
    else
      Picture("ALSO").borderT(' ').borderB(' ').fixWidth(slotWidth)

  private def also(p1: Picture, p2: Picture): Picture =
    if (alsoBeside)
      p1 + alsoP + p2
    else
      p1 ^ alsoP ^ p2
  
  private case class Activity(slot: Slot, picture: Picture)

  private def parseLine(line: String): Activity = {
    val xs: Array[String] = line.split(",")
    Activity(Slot(xs(0).toInt, xs(1).toInt), Picture(xs.drop(2)).fixWidth(slotWidth))
  }
  
//  def makeTimetable(dataFilename: String): Picture = {
//    val lines = Source.fromFile(dataFilename).getLines.toList
//    val activities: List[Activity] = lines map parseLine
//    val grouped: Map[Slot,Picture] =
//      activities.groupBy(_.slot).mapValues(_.map(_.picture).reduceLeft(also))
//
//    val body = times map (t => days map (d => grouped.getOrElse(Slot(d,t),Picture(' '))))
//
//    makeTableFromRows( (margin, header :: body).zipped.map(_::_) )
//  }
}
