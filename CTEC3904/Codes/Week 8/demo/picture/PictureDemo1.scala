package demo.picture

import lib.picture.Picture
import lib.picture.Picture._
import lib.picture.Timetable

object PictureDemo1 {

  /*
   * EXERCISE PictureDemo1.1
   * Run the following demonstration and make notes of the results.
   * Ensure you can understand what each output is generating.
   */
  def pictureDemo1_1(): Unit = {
    val p = Picture("Studying functional\nprogramming")
    val q = Picture("is a rewarding\nand enjoyable\nexperience")
//    println(p)
//    println(p.border('*'))
//    println(p.frame)
//    println(p.reflectH)
//    println(p.reflectV)
//    println(p + (q, BOT))
//    println(p.frame + (q.frame, BOT))
//    println(p.frame ^ (q.frame, RGT))
  }



  /*
   * EXERCISE PictureDemo1.2
   * Run the following demonstration and make notes of the results.
   * Note that the granularity of the spacing parameter – as a percentage – is quite
   * crude and not terribly useful for small pictures. However, the predefined TOP (0%),
   * MID(50%), and BOT (100%) are generally more useful and predictable, even for small
   * pictures. Try adjusting these or add some of your own similar experiments to check
   * that you understand how spread is working.
   */
  def pictureDemo1_2(): Unit = {
    val r = Picture("********\n********")
    val s = Picture("+++\n+++\n+++\n+++\n+++\n+++")
    val t = Picture("o\no\no\no")
    val u = Picture("%%%%%%%%%%%%")
    val ps = List(r,s,t,u)
//    println(spread(ps, TOP).frame)
//    println(spread(ps, MID).frame)
//    println(spread(ps, BOT).frame)
//    println(spread(ps, 40).frame)
  }

  /*
   * EXERCISE PictureDemo1.3
   * Using the picture combinators construct a picture that generates the following
   * output when printed. (You may wish to do this in stages using intermediate
   * pictures)

    ----------------------------
    |                          |
    | Functional    lanoitcnuF |
    | Programming  gnimmargorP |
    |                          |
    |                          |
    | Programming  gnimmargorP |
    | Functional    lanoitcnuF |
    |                          |
    ----------------------------

   */
  def pictureDemo1_3(): Unit = {

  }


  /*
   * We introduce iterate that applies a function n times to its starting value.
   * Thus, iterate(4, f, x) = List( x, f(x), f(f(x)), f(f(f(x))), f(f(f(f(x)))) )
   * Etc.
   */
  def iterate(n: Int, f: Picture => Picture, x: Picture): List[Picture] = n match {
    case 0 => List.empty
    case _ => x :: iterate(n-1, f, f(x))
  }

  /*
   * EXERCISE PictureDemo1.4
   * Firstly, run the experiment below. Then add more code to create some different
   * patterns using iterate
   */
  def pictureDemo1_4(): Unit = {
    val w = Picture("*").frame
    val ws: List[Picture] = iterate(3, _.border('*').frame, w)
    val z = spread(ws, TOP)
    println(z + (z.reflectV, TOP))
  }


  /*
   * EXERCISE PictureDemo1.5
   * Study the program below – particularly the use of the rotation within iterate.
   * Can you construct a larger example that behaves in a similar way?
   */
  def pictureDemo1_5(): Unit = {
    val a = Picture("123\n8 4\n765").border(' ')
    val as = iterate(8, _.rotate(1), a)
    val b = spread(as, MID)
    println(b)
  }


  /*
   * EXERCISE PictureDemo1.6
   * This exercise uses the library lib.picture.Timetable. This library must be installed in the
   * correct place for this to work. You will also need to ensure that the input data file is
   * copied into the folder  dat/tt01.txt
   *
   * The contents of the tt01.txt file are given at the bottom of this file for ease of cutting
   * and pasting.
   * In the file tt01.txt the commas separate the fields. The first field is the day (1=Monday), the
   * second is the hour, and the remaining fields are the separate lines of the activity in that slot.
   * Feel free to add some more activities to the data file.
   *
   * The example program below constructs two instances of the Timetable class, each with different
   * settings. In particular, tt1 stacks activities that occur in the same slot (clashes), whereas
   * tt1 puts them side by side. In each case reference is made to a data file (text) which contains
   * the activities.
   *
   * Run the program and observe the output. Change the input data to create different timetables.
   * This program is a large example and would benefit from detailed study. Do this carefully in
   * your own time (but feel free to contact the module deliverers for advice on anything you do not
   * understand.) You might consider that this is rather a short program for generating a relatively
   * sophisticated output. Clearly it benefits from the methods in the Picture DSL and the Scala List
   * library.
   */
  def pictureDemo1_6(): Unit = {
    val tt1 = new Timetable(slotWidth=14, alsoBeside=false, lastDay=3)
    val tt2 = new Timetable(slotWidth=14, alsoBeside=true)
    //println(tt1.makeTimetable("dat/tt01.txt"))
//    println(tt2.makeTimetable("dat/tt01.txt"))
  }





  def main(args: Array[String]): Unit = {
    pictureDemo1_1()
    pictureDemo1_2()
    pictureDemo1_3()
    pictureDemo1_4()
    pictureDemo1_5()
    pictureDemo1_6()
  }
}

/* The contents of the file dat/tt01.txt is contained below (excluding the close comment line at the end).
1,10,CTEC1901,Computational,Modelling,Lecture,GH3.13
1,15,CTEC1901,Computational,Modelling,Lecture,GH3.13
1,15,Optional,Industrial,Placement,Seminar,Q1.12
1,15,Optional,Student Voice,Focus Group,Campus Centre
1,10,CTEC1901,Computational,Modelling,Seminar,GH3.54
3,10,CTEC1901,Computational,Modelling,Seminar,GH3.51
1,11,CTEC1401,Computer,Programming,Lecture,VP4.05
4,9,CTEC1401,Computer,Programming,Lecture,VP4.05
3,11,CTEC1401,Computer,Programming,Lab,GH6.83
4,14,CTEC1412,Ethics Law,and Portfolio,Lecture,CL2.13
5,9,CTEC1412,Ethics Law,and Portfolio,Lecture,CL2.13
5,14,CTEC1412,Ethics Law,and Portfolio,Seminar,Q0.10
2,11,CTEC1801,Computational,Modelling,Lecture,HB0.45
2,16,CTEC1801,Computational,Modelling,Lecture,HB0.45
5,16,CTEC1801,Computational,Modelling,Lab,GH6.52
3,11,Careers,Seminar,Week 6 Only,Q0.11
4,14,CV writing,workshop,Week 6 Only,GH4.71
5,15,Employability,Lecture,BI0.05
2,10,Guest Speaker,Week 6 Only,CL2.13
*/