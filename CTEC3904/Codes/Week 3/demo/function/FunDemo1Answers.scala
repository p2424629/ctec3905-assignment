package answers.function

/*
 * In this demo we look at "pure" functions and cover:
 *   function declaration
 *   curried functions and partial application
 *   function composition
 * Some of the functions below (smaller, twice, add) are inspired by the examples in Introduction
 * to Functional Programming in Haskell by Richard Bird, Prentice-Hall International, 1998, Chapter 1).
 */

object FunDemo1Answers {

  /*
   * Below are examples of simple integer functions. Each has a name,
   * a function type (Int => Int), and a definition (lambda expression).
   */

  val inc: Int => Int = x => 1 + x
  val dec: Int => Int = x => x - 1
  val dbl: Int => Int = x => 2 * x
  val sqr: Int => Int = x => x * x

  /*
   * EXERCISE FunDemo1.1
   * COMPLETE EACH OF THE FOLLOWING FUNCTION DEFINITIONS:
   */

  val half: Int => Int = x => x / 2
  val cube: Int => Int = x => x * x * x
  val negate: Int => Int = x => -x
  val minutes: Int => Int = x => x / 60

  /*
   * Below are examples of functions that take two arguments written in
   * curried form. We interpret the type of add as Int => (Int => Int)
   * which shows that add takes an arguement and returns a function
   * (Int => Int). The => operator is right-associative which means that
   * the definition of add below is parsed as:
   *   add: Int => (Int => Int) = x => (y => x+y)
   * However, the unnecessary parentheses are often omitted because they
   * cause clutter.
   * What happens when we apply add to an input value - e.g. to 1?
   *   add(1)                  This is how we pass an argument to add
   * = (x => (y => x+y))(1)    Substituting add by its definition
   * = (y => 1+y)              The value (1) replaces the parameter(x) - this is beta reduction
   *   which is the function that adds one. However, we can continue
   * = (x => 1+x)              We can change the name of the parameter - this is eta reduction
   * = inc                     Which is equivalent to the inc function! (as expected)
   *
   * What happens if we apply add to two values?
   *   add(1)(3)
   * = (x => (y => x+y))(1)(3) Definition of add
   * = (y => 1+y)(3)           The first argument (1) is passed - beta reduction
   * = 1+3                     The second argument (3) is passed - beta reduction
   * = 4                       Arithmetic
   *
   * Since we know (from above) that the partial application add(1) is equivalent to inc:
   *   add(1)(3)
   * = inc(3)                  As shown earlier: add(1) = inc
   * = (x => 1+x)(3)           Definition of inc
   * = 1+3                     Beta reduction
   * = 4                       Arithmetic
   */

  val add: Int => Int => Int = x => y => x + y
  val sub: Int => Int => Int = x => y => x - y
  val mul: Int => Int => Int = x => y => x * y
  val div: Int => Int => Int = x => y => x / y
  val divBy: Int => Int => Int = d => n => n / d

  /*
   * EXERCISE FunDemo1.2
   * COMPLETE EACH OF THE FOLLOWING FUNCTION DEFINITIONS:
   */

  val mod: Int => Int => Int = x => y => x % y
  val flipSub: Int => Int => Int = x => y => y - x
  val smaller: Int => Int => Int = x => y => if (x < y) x else y
  val larger: Int => Int => Int = x => y => if (x > y) x else y
  val balance: Int => Int => Int = x => y => if (x < y) -1 else if (x > y) 1 else 0
  val gcd: Int => Int => Int = x => y => {
    def hcf(a: Int, b: Int): Int =
      if (a < b)
        hcf(a, b - a)
      else if (a > b)
        hcf(a - b, b)
      else
        a

    hcf(x, y)
  }
  val lcm: Int => Int => Int = x => y => x * y / gcd(x)(y)

  /*
   * EXERCISE FunDemo1.3
   * COMPLETE EACH OF THE FOLLOWING FUNCTIONS USING
   * COMBINATIONS OF THE FUNCTIONS ABOVE AS MUCH AS POSSIBLE.
   *
   * The point of these exercises is to practice combining existing
   * functions to make new ones. Thus
   *
   * val dblThenInc: Int => Int = itShould("double its argument then add one")
   *
   * could be solved like this:
   *
   *     val dblThenInc: Int => Int = x => 2*x + 1
   *
   * but we would prefer it if you defined it reusing the earlier functions:
   *
   *     val dblThenInc: Int => Int = dbl andThen inc
   *
   * or as we have defined it (inc compose dbl), or possibly like this
   * (mul(2) andThen inc), etc. You choose!
   *
   * The first two are already completed as examples
   */

  val dblThenInc: Int => Int = inc compose dbl
  val sqrThenTriple: Int => Int = sqr andThen mul(3)
  val quadruple: Int => Int = dbl compose dbl
  val pwr4: Int => Int = sqr compose sqr
  val oneTenthAfterSub2: Int => Int = flipSub(2) andThen divBy(10)
  val threeQuarters: Int => Int = mul(3) andThen divBy(4)
  val bound: Int => Int => Int => Int = a => b => c => smaller(larger(a)(b))(larger(smaller(a)(b))(c))
  // It is not easy to construct bound using compose (or at least it would be hard to read). But
  // we can make it look nicer by using some local values:
  val bound2: Int => Int => Int => Int = a => b => {
    val l = larger(a)(b)
    val s = smaller(a)(b)
    smaller(l) compose larger(s)
  }


  /*
   * Consider the following function:
   */
  val twice: (Int => Int) => (Int => Int) = f => x => f(f(x))
  /*
   * twice takes two paramters, f then x. f is a function (Int => Int) and x is an Int.
   * The effect of twice is to apply a function twice to its argument: e.g.
   *   twice(dbl)(3)
   * = (f => x => f(f(x)))(dbl)(3)    Definition of twice
   * = (x => dbl(dbl(x)))(3)          Beta reduction
   * = dbl(dbl(3))                    Beta reduction
   * = dbl((x => 2*x)(3))             Definition of dbl
   * = dbl(2*3)                       Beta reduction
   * = dbl(6)                         Arithmetic
   * = (x => 2*x)(6)                  Definition of dbl
   * = 2*6                            Beta reduction
   * = 12                             Arithmetic
   *
   * Another way of writing twice is given below (we've called it twice2).
   * It works because (f compose f)(x) = f(f(x)) for all values of x.
   *
   */
  val twice2: (Int => Int) => (Int => Int) = f => f compose f

  /*
  * EXERCISE FunDemo1.4
  * IN A SIMILAR WAY TO TWICE AND TWICE2 (RESPECTIVELY) DEFINE EACH OF THE
  * FOLLOWING
  */

  val thrice: (Int => Int) => (Int => Int) = f => x => f(f(f(x)))
  val thrice2: (Int => Int) => (Int => Int) = f => f compose f compose f
  val octo: (Int => Int) => (Int => Int) = f => x => f(f(f(f(f(f(f(f(x))))))))
  val octo2: (Int => Int) => (Int => Int) = {
    val quad = twice2 compose twice2
    quad compose quad
  }

  /*
   * Consider the following function:
   */
  val flip: (Int => Int => Int) => Int => Int => Int = f => a => b => f(b)(a)
  /*
   * The purpose of flip is to transform a function f so that instead of calculating
   * f(a)(b) it calculates f(b)(a). Essentially, flip(f) behaves like f only with
   * the two arguments swapped over.  This can be useful when reusing existing
   * functions to define new ones:
   */

  val subtract10: Int => Int = flip(sub)(10)
  /*
   * How does this work? Let's try it with 17:
   *   subtract10(17)
   * = flip(sub)(10)(17)                      Definition of subtract10
   * = (f => a => b => f(b)(a))(sub)(10)(17)  Definition of flip
   * = (a => b => sub(b)(a))(10)(17)          Beta reduction
   * = (b => sub(b)(10))(17)                  Beta reduction
   * = sub(17)(10)                            Beta reduction
   * = (x => y => x-y)(17)(10)                Definition of sub
   * = (y => 17-y)(10)                        Beta reduction
   * = 17-10                                  Beta reduction
   * = 7                                      Arithmetic
   */

  /*
  * EXERCISE FunDemo1.5
  * WORK OUT THE ANSWERS TO EACH OF THE FOLLOWING USING PEN AND PAPER
  * BEFORE PRINTING THEM OUT TO SEE IF YOU WERE RIGHT
  */

  val fd1_5_i: Int = flip(div)(7)(flip(sub)(37)(100))
  /*
   * = flip(div)(7)(sub(100)(37))
   * = flip(div)(7)(63)
   * = div(63)(7)
   * = 9
   */

  val fd1_5_ii: Int = (flip(sub)(2) compose flip(div)(10)) (200)
  /*
   * = (flip(sub)(2))(flip(div)(10)(200))
   * = (flip(sub)(2))(div(200)(10))
   * = (flip(sub)(2))(20)
   * = sub(20)(2)
   * = 18
   */

  val fd1_5_iii: Int = flip(flip(sub))(32)(17)
  /*
   * = flip(sub)(17)(32)
   * = sub(32)(17)
   * = 15
   *
   * Thus flip undoes flip.  In fact (flip compose flip) is the identity function
   * i.e. flip compose flip = id
   * where id = x => x
   * as the next example demonstrates.
   */

  val fd1_5_iv: Int = (flip compose flip) (sub)(99)(44)
  /*
   * = id(sub)(99)(44)
   * = sub(99)(44)
   * = 55
   */

  val fd1_5_v: Int = twice(flip(div)(10))(500)
  /*
   * = (flip(div)(10)) (flip(div(10)) (500))    Definition of twice
   * = (flip(div)(10)) (div(500)(10))
   * = (flip(div)(10)) (50)
   * = div(50)(10)
   * = 5
   */


  def main(args: Array[String]): Unit = {

    /* YOU SHOULD TEST EACH OF YOUR ANSWERS BY CONSTRUCTING
     * SOME EXPERIMENTS AND PRINTING THEM OUT.
     */

    println(fd1_5_i)
    println(fd1_5_ii)
    println(fd1_5_iii)
    println(fd1_5_iv)
    println(fd1_5_v)

  }
}
