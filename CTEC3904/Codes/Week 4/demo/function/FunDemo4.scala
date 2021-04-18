package demo.function

/*
 * In this demo we use a recursive data structure and pattern matching
 */
object FunDemo4 {

  import lib.sugar.QuestionSetting.itShould

  /*
   * We introduce a simple recursive data structure to represent whole numbers. The abstract class
   * Num represents these numbers which can be of one of two forms:
   * Zero    represents the least whole number
   * Succ(n) represents the successor of n where n is a Num
   * The intention is that
   * 0  is represented by Zero
   * 1  is represented by Succ(Zero)
   * 2  is represented by Succ(Succ(Zero))
   * etc.
   * Thus a whole number is represented by the number of instances of the Succ constructor.
   *
   * This is modelled in Scala below.
   */

  sealed abstract class Num // sealed means that Num cannot be subclassed outside this object

  case object Zero extends Num // the base case of the recursion - a Num representing 0

  case class Succ(n: Num) extends Num // the recursive case - a Num representing the successor of another Num


  /*
   * One of the simplest operations on a Num is to add one: i.e. an increment function. To increase a
   * Num by one it is necessary to add and extra Succ constructor around it:
   */
  def incr(n: Num): Num = Succ(n)

  /*
   * What about decrement? How do we reduce the value of a Num by one? This would require removing
   * one of the Succ constructors.  We also need to deal with what happens at zero.  Rather than
   * throw an exception, we will specify that if we decrement zero we return zero. For any other
   * number we just need to remove the first Succ constructor - this can be achieved by pattern
   * matching.
   */
  def decr(n: Num): Num = n match {
    case Zero => Zero
    case Succ(k) => k
  }

  /*
   * We define some useful Num instances:
   */
  val zero: Num = Zero
  val one: Num = Succ(Zero)
  val two: Num = Succ(Succ(Zero))
  val three: Num = Succ(Succ(Succ(Zero)))
  val ten: Num = Succ(Succ(Succ(Succ(Succ(Succ(Succ(Succ(Succ(Succ(Zero))))))))))

  /*
   * EXERCISE FunDemo4.1
   * In main USE THE incr AND decr FUNCTIONS ON EACH OF THE EXAMPLE Num INSTANCES zero, one,
   * two, etc. AND OBSERVE THE OUTPUT IN EACH CASE.
   */

  /*
   * Writing out such instances for each whole number is unwieldy, and impossible.
   * However, we can write a conversion function from Int => Num. This makes it much easier to
   * create arbitrary Nums.
   */
  def toNum(i: Int): Num =
    if (i < 0)
      throw new ArithmeticException("Trying to convert a -ve Int to a Num")
    else if (i == 0)
      Zero
    else
      incr(toNum(i - 1))

  /*
   * And it is possible to go the other way: to convert a Num into an Int. This is easy to
   * achieve with pattern matching.
   */
  def toInt(n: Num): Int = n match {
    case Zero => 0
    case Succ(n) => 1 + toInt(n)
  }

  /*
   * EXERCISE FunDemo4.2
   * REDO THE PREVIOUS EXERCISE (4.1) BUT INSTEAD OF USING THE READY-MADE NUM VALUES ZERO, ONE, TWO
   * ETC., USE TONUM TO CONSTRUCT THEM. Extend the experiment so that you try some larger numbers as
   * well. You should also practice with toInt to check that the numbers convert correctly back to
   * their corresponding Int values.
   */

  /*
   * How can we add two Num values together?  Clearly, if we want to add
   * Succ(Succ(Zero)) and Succ(Succ(Succ(Zero))) we should get Succ(Succ(Succ(Succ(Succ(Zero))))).
   * That is, 2+3=5. The following function achieves this by using recursion on the structure of
   * the first number.
   */
  def add(m: Num)(n: Num): Num = m match {
    case Zero => n
    case Succ(k) => Succ(add(k)(n))
  }

  /*
   * Let us trace this using our example. See how the first argument (m) successively reduces in
   * size until it reaches the base case - Zero. At this point the recursion ends and the answer
   * is delivered.
   *   add (Succ(Succ(Zero))) (Succ(Succ(Succ(Zero))))
   * = Succ( add (Succ(Zero)) (Succ(Succ(Succ(Zero)))) )
   * = Succ(Succ( add (Zero) (Succ(Succ(Succ(Zero)))) ))
   * = Succ(Succ(Succ(Succ(Succ(Zero)))))
   */

  /*
  * EXERCISE FunDemo4.3
  * EXPERIMENT WITH THE add FUNCTION TO CHECK THAT YOU UNDERSTAND HOW IT IS WORKING. In main you
  * should add plenty of experiments and print out the answers until you are satisfied that you
  * understand how this is working.
  */

  /*
   * EXERCISE FunDemo4.4
   * IMPLEMENT EACH OF THE FOLLOWING FUNCTIONS USING PATTERN MATCHING AND RECURSION AND
   * BY REUSING EXISTING Num OPERATORS AS YOU WISH.
   */

  def sub(m: Num)(n: Num): Num = itShould("compute m-n. If m<n then the result is Zero")

  def mul(m: Num)(n: Num): Num = itShould("compute m*n")

  def pwr(n: Num)(p: Num): Num = itShould("compute m^p")

  def div(m: Num)(n: Num): Num = itShould("compute m/n. This is integer division so ignore any remainder")

  def mod(m: Num)(n: Num): Num = itShould("compute m%n. This is the remainder after the division m/n")
  // If you prefer you can write mod using only combinations of the earlier operators


  def main(args: Array[String]): Unit = {

    println(one)
    println(ten)

  }
}
