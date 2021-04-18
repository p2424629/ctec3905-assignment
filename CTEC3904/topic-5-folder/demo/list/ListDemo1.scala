package demo.list

object ListDemo1 {

  /*
   * These exercises have been grouped by question so that in main() it is possible to un-comment
   * one question at a time. The experiements and the display of their results are contained within
   * individual methods for each question. This makes it easier to manage a large number of
   * individual definitions and to introduce them incrementally.
   */

  val wordList: List[String] = List("The", "quick", "brown", "fox", "jumped", "over", "the", "lazy", "dog")
  val charList: List[Char] = ('a' to 'z').toList
  val aardvark: List[Char] = "aardvark".toList
  val intList: List[Int] = (0 to 9).toList

  /*
   * EXERCISE ListDemo1.1
   * Using only head and tail extract the following words from wordList:
   * (a) The
   * (b) quick
   * (c) dog
   * Then extract the following words from wordList using only init and last:
   * (d) The
   * (e) lazy
   * (f) dog
   */

  def exerciseListDemo1_1() {
//    val a = ???
//    val b = ???
//    val c = ???
//    val d = ???
//    val e = ???
//    val f = ???
//    println(a)
//    println(b)
//    println(c)
//    println(d)
//    println(e)
//    println(f)
  }


  /*
   * EXERCISE ListDemo1.2
   * Using charList, intList, and wordList, respectively, and at most the operators
   * take, drop, and ::: build the following lists:
   * (a) List(x, y, z, a, b, c, d)
   * (b) List(9, 0)
   * (c) List(over, the, lazy, dog, jumped, The, quick, brown, fox)
   * Use the method splitAt to divide the intList in each of the following ways:
   * (d) (List(0, 1, 2, 3), List(4, 5, 6, 7, 8, 9))
   * (e) (List(0, 1, 2, 3, 4, 5, 6, 7, 8), List(9))
   * (f) (List(), List(0, 1, 2, 3, 4, 5, 6, 7, 8, 9))
   */

  def exerciseListDemo1_2() {
//    val a = ???
//    val b = ???
//    val c = ???
//    val d = ???
//    val e = ???
//    val f = ???
//    println(a)
//    println(b)
//    println(c)
//    println(d)
//    println(e)
//    println(f)
  }

  /*
   * EXERCISE ListDemo1.3
   * Construct appropriate (function) arguments ... for map such that intList.map(...) generates
   * each of the following lists:
   * (a) List(0, 2, 4, 6, 8, 10, 12, 14, 16, 18)
   * (b) List(1, 3, 5, 7, 9, 11, 13, 15, 17, 19)
   * (c) List(0, 1, 2, 3, 4, 5, 5, 5, 5, 5)
   * (d) List(9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
   * (e) List(a, b, c, d, e, f, g, h, i, j)
   * (f) List(The, quick, brown, fox, jumped, over, the, lazy, dog, The)
   * (g) List(false, false, true, true, false, true, false, false, true)
   * (h) List(THE, QUICK, BROWN, FOX, JUMPED, OVER, THE, LAZY, DOG)
   * (i) List(List(T, h, e), List(q, u, i, c, k), List(b, r, o, w, n), List(f, o, x),
   *          List(j, u, m, p, e, d), List(o, v, e, r), List(t, h, e), List(l, a, z, y),
   *          List(d, o, g))
   */
  def exerciseListDemo1_3() {
//    val a = ???
//    val b = ???
//    val c = ???
//    val d = ???
//    val e = ???
//    val f = ???
//    val g = ???
//    val h = ???
//    val i = ???
//    println(a)
//    println(b)
//    println(c)
//    println(d)
//    println(e)
//    println(f)
//    println(g)
//    println(h)
//    println(i)
  }


  /*
   * EXERCISE ListDemo1.4
   * Construct appropriate (function) arguments ... and use with filter, takeWhile, or
   * dropWhile, and the relevant list such that (e.g.) intList.filter(...), wordList.takeWhile(...),
   * etc., generates each of the following lists:
   * (a) List(a, a)
   * (b) List(r, d, v, a, r, k)
   * (c) List(a, a, a)
   * (d) List(0, 3, 6, 9)
   * (e) List(a, e, i, o, u)
   * (f) List(The, fox, the, dog)
   * (g) List(The, quick, brown, fox)
   * (h) List(the, lazy, dog)
   * (i) List(x, y, z)
   * (j) List(0, 2, 3, 4, 6, 8, 9)
   */
  def exerciseListDemo1_4() {
//    val a = ???
//    val b = ???
//    val c = ???
//    val d = ???
//    val e = ???
//    val f = ???
//    val g = ???
//    val h = ???
//    val i = ???
//    val j = ???
//    println(a)
//    println(b)
//    println(c)
//    println(d)
//    println(e)
//    println(f)
//    println(g)
//    println(h)
//    println(i)
//    println(j)
  }

  /*
   * EXERCISE ListDemo1.5
   * Write a function, swapAround, that takes a list and returns it with the first half
   * swapped with the second half.  Thus, e.g.,
   *
   * swapAround(List("The", "horse", "the", "cart")) = List(the, cart, the, horse)
   *
   * Try your function out with a few lists (of different element types) to show it
   * in action.
   */

  def swapAround[A](xs: List[A]): List[A] = ???

  def exerciseListDemo1_5(): Unit = {
//    println(swapAround(List("The", "horse", "the", "cart")))
//    println(swapAround(List(1, 2, 3, 4, 5)))
//    println(swapAround(List.empty))
  }

  /*
   * EXERCISE ListDemo1.6
   * Write a function, groupInto, that takes a list and splits it into packets of a given
   * size. Here are some examples:
   *
   * groupInto(4, "abcdefghijkl".toList) =
   *     List(List(a, b, c, d), List(e, f, g, h), List(i, j, k, l))
   *
   * groupInto(3, List(0, 1, 2, 3, 4, 5, 6, 7, 8, 9) =
   *    List(List(0, 1, 2), List(3, 4, 5), List(6, 7, 8), List(9))
   *
   * groupInto(1, wordList) =
   *    List(List(The), List(quick), List(brown), List(fox), List(jumped), List(over),
   *         List(the), List(lazy), List(dog))
   *
   * groupInto(8, aardvark)) =
   *     List(List(a, a, r, d, v, a, r, k))
   *
   * The final list in the result may have fewer elements if there are insufficient
   * elements to make up the packet size.
   */

  def groupInto[A](size: Int, xs: List[A]): List[List[A]] = ???

  def exerciseListDemo1_6() {
//    println(groupInto(4, "abcdefghijkl".toList))
//    println(groupInto(3, List(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)))
//    println(groupInto(1, wordList))
//    println(groupInto(8, aardvark))
  }


  /*
   * EXERCISE ListDemo1.7
   * This exercise builds upon your previous answer and assumes that you have developed
   * a working version of the groupInto function.
   *
   * (a) Write a function, packetReverse, that splits a list into packets, reverses each
   *     packet, and flattens the result. For example:
   *
   *     packetReverse(3, intList) = List( 2, 1, 0, 5, 4, 3, 8, 7, 6, 9)
   *     packetReverse(2, wordList) = List(quick, The, fox, brown, over, jumped, `lazy`, the, dog)
   *     packetReverse(8, aardvark) = List(k, r, a, v, d, r, a, a)
   *
   *     You should avoid inserting a reverse method into a copy of your groupInto method.
   *     Rather, you should combine your groupInto method, with reverse and flatMap to achieve
   *     the result.
   *
   * (b) Write a function, tabulate, that takes a list of numbers and formats it as an HTML
   *     table with the given width. Thus, e.g.,
   *
   *     tabulate(2, List(1, 2, 3, 4) =
   *         <table><tr><td>1</td><td>2</td></tr><tr><td>3</td><td>4</td></tr></table>
   *
   *     tabulate(List(1, List(1, 2)) =
   *         <table><tr><td>1</td></tr><tr><td>2</td></tr></table>
   *
   *     This can be solved using groupInto and map methods (although we do not want to restrict
   *     you to these if you wish to explore alternatives.) However, you will find the following
   *     method useful, too:  mkString.  The standard Scala method mkString compresses a list of
   *     strings into a single string.  E.g. List("abc", "def", "gh").mkString = "abcdefgh". (In
   *     fact, mkString is much more general than this - but we leave you to look it up if you
   *     want to learn more about it.)
   */

  def packetReverse[A](size: Int, xs: List[A]): List[A] = ???

  def tabulate(width: Int, xs: List[Int]): String = ???

  def exerciseListDemo1_7() {
//    println(packetReverse(4, "abcdefghijkl".toList))
//    println(packetReverse(3, intList))
//    println(packetReverse(2, wordList))
//    println(packetReverse(8, aardvark))
//
//    println(tabulate(2, List(1, 2, 3, 4)))
//    println(tabulate(1, List(1, 2)))
  }

  /*
  * EXERCISE ListDemo1.8
  * Tony Hoare's famous quicksort algorithm can be written recursively. See below. It uses
  * the head of the list as the pivot element which does not work well for nearly or fully
  * ordered data (O(n^2)). But for reasonably random data this version will have the n(log n)
  * performance normally associated with a good implementation of quicksort. The version
  * below is notable for the simplicity of its expression in a functional style.
  *
  * The function random(k) generates a list of k random characters drawn from the digits
  * 0-9 and the upper and lower case letters.
  *
  * Your task is to write the merge sort function that works by splitting the list into
  * two halves, recursively sorting each half, and then merging the resulting ordered
  * lists back together. Demonstrate your function in action.
  *
  */

  def qsort(xs: List[Char]): List[Char] = xs match {
    case List() => List()
    case y :: ys =>
      val (less, more) = ys.partition(_ <= y)
      qsort(less) ::: y :: qsort(more)
  }

  /**
    * Generates a list of k random alphanumeric characters
    */
  def random(k: Int): List[Char] = scala.util.Random.alphanumeric.take(k).toList

  /*
   * merge assumes that each of its arguments are in ascending order. The method
   * merges the two lists into one which is also in ascending order.
   */
  def merge(as: List[Char], bs: List[Char]): List[Char] = ???

  def msort(xs: List[Char]): List[Char] = ???
  // Will need to call merge

  def exerciseListDemo1_8() {
    val rs = random(100)
    println(rs.mkString)
    println(qsort(rs).mkString)
//    println(msort(rs).mkString)
  }

  def main(args: Array[String]): Unit = {
    println("Un-comment the exercise methods in main() one at a time.")
    println("Complete the individual methods as you attempt each question.")
    println("You may wish to keep notes as you go along for future reference.")
//    exerciseListDemo1_1()
//    exerciseListDemo1_2()
//    exerciseListDemo1_3()
//    exerciseListDemo1_4()
//    exerciseListDemo1_5()
//    exerciseListDemo1_6()
//    exerciseListDemo1_7()
//    exerciseListDemo1_8()
  }
}
