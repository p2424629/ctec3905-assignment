package answers.function

/*
 * In this demo we look at recursion
 */
object FunDemo3Answers {

  /*
   * Consider the following function.  Note that its return type is Unit which means that
   * it returns nothing of interest: the type Unit only has one value (). It is like
   * writing a void method in Java or C. This function is written for its side-effect: it
   * will cause the numbers between lo and hi to be printed on the output stream in
   * ascending order. Call the function from main with some different values for lo and
   * hi and observe its behaviour.
   */
  def ascending(lo: Int, hi: Int): Unit = {
    if (lo <= hi) {
      println(lo)
      ascending(lo + 1, hi)
    }
  }

  /*
   * EXERCISE FunDemo3.1
   * WRITE THE FUNCTION descending WHICH IS LIKE ascending BUT OUTPUTS THE VALUES IN
   * DESCENDING ORDER.
   */
  def descending(lo: Int, hi: Int): Unit = {
    if (lo <= hi) {
      descending(lo + 1, hi)
      println(lo)
    }
  }

  /*
   * EXERCISE FunDemo3.2
   * USING RECURSION WRITE THE FUNCTION sumOdds:
   */
  def sumOdds(a: Int, b: Int): Int = {
    def addOdds(o: Int): Int =
      if (o > b)
        0
      else
        o + addOdds(o + 2)

    if (a > b)
      0
    else if (a % 2 == 1)
      addOdds(a)
    else
      addOdds(a + 1)
  }

  /*
   * Consider the following function isPrime that tests if a given integer is a prime number.
   * The algorithm is very simple and checks each number between 2 and the square root of n
   * to see if it divides exactly into n. If none does then the number is prime.
   */
  def isPrime(n: Int): Boolean = {
    if (n < 2)
      false
    else {
      val rootn: Int = Math.sqrt(n).round.toInt
      for (i <- 2 to rootn)
        if (n % i == 0)
          return false
      true
    }
  }

  /*
   * EXERCISE FunDemo3.3
   * COMPLETE THE RECURSIVE FUNCTION:
   */
  def sumFromTo(a: Int, b: Int): Int =
    if (a>b)
      0
    else
      a + sumFromTo(a+1, b)
  /*
   * If a>b then the result is zero. So, for example,
   *   sumFromTo(5,4)  =   0
   *   sumFromTo(7,7)  =   7
   *   sumFromTo(2,5)  =  14
   * In the main function write a variety of different experiments using sumFromTo to demonstrate it
   * in action. At least include the experiments above.
   */



  /*
   * EXERCISE FunDemo3.4
   * REWRITE THE isPrime FUNCTION AS isPrime2 USING RECURSION INSTEAD OF A LOOP.
   */
  def isPrime2(n: Int): Boolean = {
    val rootn: Int = Math.sqrt(n).round.toInt

    def check(k: Int): Boolean =
      if (k > rootn)
        true
      else if (n % k == 0)
        false
      else check(k + 1)

    if (n < 2)
      false
    else
      check(2)
  }




  /*
   * Study the Fibonacci function below and read the notes that accompany it. If you are unfamiliar
   * with the Fibonacci sequence you may wish to visit https://en.wikipedia.org/wiki/Fibonacci_number
   */
  def fibonacci(n: BigInt): BigInt = {
    def fib(a: BigInt, b: BigInt, n: BigInt): BigInt =
      if (n == 0) a + b
      else fib(b, a + b, n - 1)

    if (n == 0) 0
    else if (n == 1) 1
    else fib(0, 1, n - 2)
  }

  /*
   * There are some important points to note about this function definition:
   * 1. Notice the use of the type BigInt. This type represents unbounded integers which cannot overflow.
   *    They are built on top of Java's BigInteger type but look a lot prettier in Scala. Because the
   *    Fibonacci sequence generates very big numbers very quickly it is useful to use the BigInt data
   *    type in this instance.
   * 2. The function fibonacci is NOT recursive. It deals with the initial cases of the zero-th and first
   *    Fibonacci numnbers. However, for the nth Fibonacci number (where n>1), a call is made to an
   *    auxialliary function called fib.
   * 3. The auxilliary function uses recursion and takes three parameters. The first two parameters carry
   *    the previous two values in the sequence. This is why, when it is called initally from fibonacci,
   *    the initial values of a and b are 0 and 1 respectively. The third parameter is a counter
   *    representing which Fibonacci number is required. Initially it is the value originally requested.
   *    However, with each recursive call it is reduced by one until it reaches the base value of two.
   * 4. The reason for coding the function using an auxilliary is because we do not want to clutter up
   *    the main fibonacci function with parameters carrying the previous two values. This is really
   *    internal information that the client does not want to see. Therefore, the main function only
   *    takes as input the index, n, that the client wishes to provide. The auxilliary function then does
   *    the bulk of the work.
   * Let us trace an evaluation to see how it works:
   *
   *    fibonacci(8)
   *  = fib(0,1,8)
   *  = fib(1,1,7)
   *  = fib(1,2,6)
   *  = fib(2,3,5)
   *  = fib(3,5,4)
   *  = fib(5,8,3)
   *  = fib(8,13,2)
   *  = 8+13
   *  = 21
   */

  /*
   * EXERCISE FunDemo3.5
   * WRITE ANOTHER VERSION OF THE fibonacci FUNCTION IN WHICH THE RECURSION IS REMOVED AND REPLACED
   * BY AN ALGORITHM THAT USES A LOOP. In this exercise you are deconstructing a recursive algorhtim
   * and we are encouraging you to compare the methdos by designing the more traditional equivalent.
   */

  def fibonacci2(n: BigInt): BigInt =
    if (n == 0)
      0
    else if (n == 1)
      1
    else {
      var prevPrev: BigInt = 0
      var prev: BigInt = 1
      var accumulator: BigInt = 0
      for (i <- (2:BigInt) to n) {
        accumulator = prevPrev + prev
        prevPrev = prev
        prev = accumulator
      }
      accumulator
    }


  def main(args: Array[String]): Unit = {
    println(fibonacci(0), fibonacci2(0))
    println(fibonacci(1), fibonacci2(1))
    println(fibonacci(2), fibonacci2(2))
    println(fibonacci(3), fibonacci2(3))
    println(fibonacci(10), fibonacci2(10))
    println(fibonacci(50), fibonacci2(50))
    //    println(isPrime(113))
    //    println(sumOdds(0, 9))
    //    println(sumOdds(1, 9))
    //    println(sumOdds(1, 1))
    //    println(sumOdds(2, 2))
    //    ascending(1, 9)
    //    for (i <- 0 to 20) println(i, isPrime(i), isPrime2(i))
    //    println(sumFromTo(7, 7))
    //    println(sumFromTo(-2, 2))
    //    println(sumFromTo(2, 5))

  }
}
