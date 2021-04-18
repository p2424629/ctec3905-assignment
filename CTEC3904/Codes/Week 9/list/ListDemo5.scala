package list

import scala.collection.immutable.LazyList._
import scala.math.BigInt

object ListDemo5 {

  /*
   * EXERCISE ListDemo5.1
   * LAZY (call-by-name) parameters
   *
   * (a) What happens when you call the function foo with each of
   *     the following?
   *     foo(1, true)
   *     foo(1, false)
   *     foo(exception, true)
   *     foo(exception, false)
   *     bar(1, true)
   *     bar(1, false)
   *     bar(exception, true)
   *     bar(exception, false)
   *
   *     We have commented out the experiments so you can uncomment them one at a time.
   *     Can you explain the behaviour in each case? Check carefully!
   */
  def exerciseListDemo5_1() {

    def exception: Int = throw new Exception // the undefined value as an exception

    def foo(x: Int, flag: Boolean): Int = if (flag) 0 else x
    def bar(x: => Int, flag: Boolean): Int = if (flag) 0 else x

//    println(foo(1, true))
//    println(foo(1, false))
//    println(foo(exception, true))
//    println(foo(exception, false))
//    println(bar(1, true))
//    println(bar(1, false))
//    println(bar(exception, true))
//    println(bar(exception, false))
  }


  /*
   * The display methods below are designed to print prefixes of infinite lists. The displayApproximations
   * method can be used to show a sequence of approximations (of partial lists) whose limit is an infinite list.
   * These methods are used in the subsequent exercises.
   */

  def display[A](n: Int)(ll: LazyList[A]): Unit = {
    val (l, r) = ll.splitAt(n)
    l.foreach(x => print(s"$x "))
    println(r)
  }

  def displayApproximations[A](n: Int)(ll: LazyList[A]): Unit =
    for (k <- 0 to n) yield display(k)(ll)

  /*
   * EXERCISE ListDemo5.2
   * INFINITE LISTS as the limit of a sequence of partial lists.
   *
   * Uncomment one at a time each of the displayApproximations experiments. Then construct your own
   * infinite lists and print them out using displayApproximations.  The infinite lists you should
   * construct are:
   * (a) All the odd numbers starting at 1:  1, 3, 5, 7, ...
   * (b) The squares of all the postive integers: 0, 1, 4, 9, ...
   * (c) All the negative numbers starting with -1:  -1, -2, -3, -4, ...
   * (d) All the positive integers without any multiples of 5:  1, 2, 3, 4, 6, 7, 8, 9, 11, ...
   * (e) The endless cycle: 1, 2, 3, 1, 2, 3, ...
   * (f) The infinite sequence of 'a' prefixes:  a, aa, aaa, aaaa, aaaa, aaaaa, ...
   * (g) The infinite list:  1, List(1), List(List(1)), List(List(List(1))), ...
   * (h) The infinite list of binary numbers as strings, starting with the single bit numbers,
   *     followed by the two-bit numbers, then the three-bit numbers, etc. forever...
   *     i.e.  0 1 00 01 10 11 000 001 010 011 100 101 110 111 0000 0001 0010 ...
   *     [This last experiment is quite difficult!]
   *
   */
  def exerciseListDemo5_2(): Unit = {

    val ones: LazyList[Int] = LazyList.continually(1)
    val nats: LazyList[Int] = LazyList.from(0, 1)
    val evens: LazyList[Int] = nats map (_ * 2)

    displayApproximations(20)(ones)
    displayApproximations(20)(nats)
    displayApproximations(20)(evens)

    /*
     * The use of lazy val ... in the following (as opposed to just writing val ...) is to delay
     * the evaluation of the value until it is actually needed. If we did not do this then definitions
     * of the form val something = ??? would throw an exception whether or not something is used.
     * By writing lazy val something = ??? the exception is only thrown if you try to evaluate (use) it.
     * This device enables us to write the questions using ??? and for you to complete them incrementally
     * without having to comment them out.
     */

    lazy val odds: LazyList[Int] = ???
    lazy val squares: LazyList[Int] = ???
    lazy val negatives: LazyList[Int] = ???
    lazy val not5s: LazyList[Int] = ???
    lazy val threeCycle: LazyList[Int] = ???
    lazy val aaas: LazyList[String] = ???
    lazy val deeper: LazyList[Any] = ???
    lazy val binary: LazyList[String] = ???

//    displayApproximations(20)(odds)
//    displayApproximations(20)(squares)
//    displayApproximations(20)(negatives)
//    displayApproximations(20)(not5s)
//    displayApproximations(20)(threeCycle)
//    displayApproximations(20)(aaas)
//    displayApproximations(20)(deeper)
//    displayApproximations(20)(binary)
  }

  /*
   * EXERCISE ListDemo5.3
   * The following experiment prints out two different but well-known sequences:
   *     the Fibonacci numbers (each number is the sum of the previous two)
   *     the prime numbers (each number has no factors other than itself and 1)
   */
  def exerciseListDemo5_3(): Unit = {

    def isPrime(n: Long): Boolean = {
      if (n < 2)
        false
      else {
        val rootn: Long = Math.sqrt(n).round
        for (i <- 2L to rootn)
          if (n % i == 0)
            return false
        true
      }
    }

    val nats: LazyList[Long] = LazyList.iterate(0L)(_ + 1L)
    val primes: LazyList[Long] = nats filter isPrime
    lazy val fibs: LazyList[Long] = 0L #:: 1L #:: fibs.zip(fibs.tail).map { n => n._1 + n._2 }

    display(20)(primes)
    display(20)(fibs)

    // (a) Display the the 21st to the 40th prime numbers

    // (b) Display all the Fibbonacci numbers that are less than 1000000

    // (c) Display the first 20 prime Fibonacci numbers


    /*
     * The following represents the infinite list of powers of two:  1, 2, 4, 8, 16, ...
     */
    def twoTo(n: Long): Long = Math.pow(2.0, n.toDouble).toLong

    /*
     * In the following we use the notation 2^p to represent "two to the power of p".
     * Euclid (3rd C. BCE) showed that 2^(p - 1) * (2^p - 1) is an even perfect number provided that
     * (2^p - 1) is prime. For (2^p - 1) to be prime then p itself must be prime (Mersenne, 16th C.)
     * Display the first eight even perfect numbers. You will get
     * 6 28 496 8128 33550336 8589869056 137438691328 2305843008139952128
     * See https://en.wikipedia.org/wiki/List_of_perfect_numbers
     */

    // (d) Display the first eight even perfect numbers (see discussion above)

    lazy val perfects: LazyList[Long] = ???

//    display(8)(perfects)
  }

  /*
   * EXERCISE ListDemo5.4
   * (This example is taken from the lecture slides)
   *
   * The exponential function, e^x, in mathematics can be represented as the following Maclaurin
   * series:
   *   exp(x) = sum(x^k / k!)  for k=0..infinity
   *
   * Any finite number of terms from this series is an approximation of exp(x). Thus, if we take
   *
   * one term:    x^0 / 0!
   *            = 1
   * two terms:   1 + x^1.1!
   *            = 1 + x
   * three terms: 1 + x + x^2/2!
   *
   * four terms:  1 + x + x^2/2! + x^3/3!
   *
   * and so on. The more terms we take, the better the approximation to exp(x). If we use Math.exp(x)
   * to calculate e^x then the result is a value of type Double so its accuracy is limited by that of
   * the underlying Double data type.  It will be interesting to use the Maclarurin series expansion
   * to calculate e^x to the same accuracy as Math.exp(x).
   */

  def exerciseListDemo5_4(): Unit = {
    val nats: LazyList[Long] = LazyList.iterate(0L)(_ + 1L)

    def e(x: Double)(t: Int): Double = {
      val factorials: LazyList[Long] = nats.tail.scanLeft(1L)(_ * _)
      val powersOfX: LazyList[Double] = LazyList.iterate(1.0)(_ * x)
      val terms: LazyList[Double] = powersOfX lazyZip (factorials map (_.toDouble)) map (_/_)
      terms.take(t).sum
    }

   /*
    * Construct the list of approximations of e,  e(1.0)(k),  by taking values of k=1, 2, ...
    * Then display all the approximations from this list until the accuracy is within 0.000000001
    * (You can determine the accuracy of an approximation by comparing it with Math.exp(1.0)).
    */
    lazy val approximations: LazyList[Double] = ???
  }


  /*
   * EXERCISE ListDemo5.5
   * This exercise is about generating Pascale's Triangle. You may like to look this up if you are
   * not familiar with it:  https://en.wikipedia.org/wiki/Pascal%27s_triangle
   */
  def exerciseListDemo5_5(): Unit = {
    /*
     * Each row of the triangle is represented as a list. And the triangle is a list of rows. Thus
     *               1
     *            1     1
     *         1     2     1
     *      1     3     3     1
     *   1     4     6     4     1
     * etc.
     *
     * Since the triangle is infinite, we will represent it as an infinite list of rows. Each row
     * is finite. However, by representing rows as LazyLists we will only need to compute as much
     * of them as necessary - as we will see.
     */
    def pascalesTriangle: LazyList[LazyList[Long]] = {
      /*
       * nextRow generates the next row from the previous row. For example:
       * If previousRow = [1, 3, 3, 1]
       * Then the next row is calculated thus:
       *   [1, 3, 3, 1].zip[0, 1, 3, 3, 1].map(_+_) ::: [1]
       * = [(1,0),(3,1),(3,3),(1,3)].map(_+_) ::: [1]
       * = [ 1, 4, 6, 4 ] ::: [1]
       * = [ 1, 4, 6, 4, 1 ]
       */
      def nextRow(previousRow: LazyList[Long]): LazyList[Long] =
        previousRow.zip(0L #:: previousRow).map(p => p._1 + p._2) #::: LazyList(1L)

      /*
       * Starting with the first row [1], the infinite list of rows is generated by iterating the
       * function nextRow:
       * [ [1],  nextRow([1]),  nextRow(nextRow([1])), nextRow(nextRow(nextRow([1]))), ... ]
       */
      LazyList.iterate(LazyList(1L))(nextRow)
    }

    /*
     * Uncomment these print statements one at a time and observe the behaviour carefully.
     * Consider the experiements (a), (b), ... Once you have completed each experiment,
     * leave it: do not turn previous experiments back into comments again. The results are
     * cumulative.
     */

    /*
     * (a) Let us print the triangle. It's not looking much like a triangle!
     *     Can you explain what you see?
     */
//    println(pascalesTriangle)

    /*
     * (b) Given that the triangle is a list of lists we can index any particular value using
     *     a pair of integers. Thus pascalesTriangle(r)(k) will return the value on row r and
     *     in position k. Remember, the numbering starts at zero becuase this are list indexes.
     *
     *     Thus, pascalesTriangle(0)(0) will give the (only) value on the top row. Check that
     *     this is so, and that the value 1 is printed.
     */
//    println(pascalesTriangle(0)(0))

    /*
     * (c) Now print out the triangle for a second time.  Can you explain what has happened?
     */
//    println(pascalesTriangle)

    /*
     * (d) Now print out the value on row 7 at position 2.  (It should be 21)
     */
//    println(pascalesTriangle(7)(2))

    /*
     * (e) Now print out the triangle for a third time. We use a foreach to lay it out more nicely.
     *     What do you make of this?
     */
//    pascalesTriangle.take(8).foreach(println)

    /*
     * (f) Now print out the value on row 4 at position 3.  (It should be 4)
     */
//    println(pascalesTriangle(4)(3))

    /*
     * (g) Now print out the triangle for a fourth time.  What difference to you see now
     *     between this version and the version printed in experiment (e)?  Can you
     *     explain this?
     */
//    pascalesTriangle.take(8).foreach(println)


    /*
     * (h) What happens when you change the definition of pascalesTriangle from a val to
     *     a def.  That is:
     *     def pascalesTriangle: LazyList[LazyList[Long]] = ...
     *
     *     When all the experiments are re-run, what difference do you notice this time?
     *     Why is this?
     */
  }


  def main(args: Array[String]): Unit = {

    exerciseListDemo5_1()
    exerciseListDemo5_2()
    exerciseListDemo5_3()
    exerciseListDemo5_4()
    exerciseListDemo5_5()

  }
}
