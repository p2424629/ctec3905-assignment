package demo.function

/*
 * In this demo we look at:
 *  the difference between methods and functions
 *  the use of _ to create anonymous functions
 *  some functions using types other than Int
 *
 * It is strongly recommended that you read the short tutorial by Alvin Alexander
 * in which he explains the difference between functions and methods.
 *   https://alvinalexander.com/scala/fp-book/how-to-use-scala-methods-like-functions/
 */

object FunDemo2 {

  import lib.sugar.QuestionSetting.itShould

  /*
   * Previously we defined inc, dec, etc. using the function syntax:
   *   val inc: Int => Int = x => 1 + x
   *
   * The ability to write functions like this is very important, as is the ability to
   * define
   *
   * However, most programmers prefer to use a more familiar notation:
   */
  def inc(x: Int): Int = 1 + x

  def dec(x: Int): Int = x - 1

  def dbl(x: Int): Int = 2 * x

  def sqr(x: Int): Int = x * x

  /*
   * However, despite looking like functions, they are not functions. They are
   * methods. The use of the keyword def denotes a method. Remember that Scala
   * is also an objecct-oriented language.
   *
   * In most circumstances you will be able to use a (compatible) method where
   * a function is expected - the compiler can convert between these two types
   * automatically. However, there are times when you will need to make the
   * converstion explicit.  This is done by appending an underscore to the method:
   */

  val incFun1 = inc _
  /* It is an error to write
   * val incFun1 = inc
   * (The compiler will complain that inc has missing arguments)
   *
   * However, if you remove the type inference and make the type of incFun
   * explicit:
   */
  val incFun2: Int => Int = inc
  /*
   * then the compiler can automatically convert the method inc to an Int => Int
   * function. If you get an error telling you that a method which you have used
   * as a function is missing its argument list then it means that the automatic
   * conversion is not possible and you will need to make this explicit by adding
   * a trailing underscore _.
   *
   * NB: In the latest version of Scala (Scala 3) it seems that this restriction
   * is to be deprecated. This should make these confusing situations simpler. To
   * read more about this have a look at:
   * https://dotty.epfl.ch/docs/reference/changed-features/eta-expansion-spec.html
   */

  /*
   * EXERCISE FunDemo2.1
   * RE-WRITE THE FOLLOWING FROM FunDemo1.1 AS METHODS (using def).
   *
   * half
   * cube
   * negate
   * minutes
   */

  /*
   * Next we turn our attention to multi-parameter methods. Consider the
   * previous definitions of add, etc.
   * val add: Int => Int => Int = x => y => x + y
   *
   * These can also be written as methods using currying, viz:
   */
  def addc(x: Int)(y: Int): Int = x + y

  def subc(x: Int)(y: Int): Int = x - y

  def mulc(x: Int)(y: Int): Int = x * y

  def divc(x: Int)(y: Int): Int = x / y

  /*
   * Note the use of multiple parameter lists in the above examples. This uses
   * currying. Do have a look at
   * https://docs.scala-lang.org/tour/multiple-parameter-lists.html
   *
   * It is also possible to write these functions as methods in a much more
   * conventional (uncurried) way. In each of the following we pass a single
   * argument list rather than multiple ones.
   */
  def add(x: Int, y: Int): Int = x + y

  def sub(x: Int, y: Int): Int = x - y

  def mul(x: Int, y: Int): Int = x * y

  def div(x: Int, y: Int): Int = x / y

  /*
   * Now suppose that we wish to construct the function that triples its
   * argument by partially applying add.
   */
  val triple2: Int => Int = mul(3, _)
  /*
   * Notice how we can use the _ placeholder to specify where the missing
   * parameter should go. In fact, we are creating a lambda expression
   * implicitly:
   */
  val triple3: Int => Int = y => mul(3, y)
  /*
   * However, it is much more convenient to have the compiler construct the
   * lambda function behind the scenes, so the triple2 definition would be
   * preferred.
   */

  /*
   * EXERCISE FunDemo2.2
   * RE-WRITE THE FOLLOWING AS METHODS (FROM FunDemo1.2). YOU SHOULD IMPLEMENT
   * EACH ONE AS AN UNCURRIED METHOD.
   *
   * mod
   * flipSub
   * smaller
   * larger
   * balance
   * gcd
   * lcm
   */


  /*
   * In fact, although we wrote methods to mirror each of the arithmetic
   * operators +, -, *, /, etc. we need not have done so. Scala already
   * has them built in - and a lot more besides. In fact, these methods
   * have the same symbolic names. We can use them like traditional method
   * calls
   */
  val twentyOne: Int = 2.*(10).+(1)
  /*
   * or we can elide the dots and parentheses because the compiler knows
   * these can be used as infix operators:
   */
  val twentyTwo: Int = 2 * 10 + 2
  /*
   * In fact you have seen this already. The method compose was placed
   * between two functions:  f compose g. This could also be written as
   * f.compose(g).  The same goes for andThen and, in fact, any other
   * method that takes a parameter list that looks like (a, b). There is
   * nothing special about +, *, etc. because Scala allows sybmolic names
   * to be used as identifiers.
   */

  /*
   * It is also useful to use the underscore to create functions with
   * these operators:
   */
  val triple4: Int => Int = 3 * _
  val divByTen: Int => Int = _ / 10
  val negate: Int => Int = -_


  /*
   * Nearly all of our methods so far have been single-line. (Some of the
   * answers to FunDemo1 contained multiple-line functions). When you are
   * writing functions "on the fly" this is often the case: for example,
   * you may wish to construct the function that adds ten so that you can
   * pass it to some other method or function:  x => x+10, or in this case
   * you could write (_ + 10).
   *
   * However, there will be many occasions when your methods (or functions)
   * are defined over many lines. The following exercises ask you to write
   * such methods to give you practice at developing some larger code
   * examples.
   */

  /*
  * EXERCISE FunDemo2.3
  * WRITE EACH OF THE FOLLOWING FUNCTIONS
  */
  
  // val roots: Double => Double => Double => (Double, Double) = ???
  // returns the roots of a quadratic equation
  // Use the formula: roots(a)(b)(c) =
  //     (-b + sqrt(b*b - 4*a*c)/(2*a), b - sqrt(b*b - 4*a*c)/(2*a))
  // Use local definitions to avoid repeating calculations

  def clock(seconds: Int): (Int, Int, Int) =
    itShould("convert seconds into hours, minutes, and seconds")

  def main(args: Array[String]): Unit = {

  }
}
