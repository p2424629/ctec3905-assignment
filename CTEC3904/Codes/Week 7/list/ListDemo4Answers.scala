package list

object ListDemo4Answers {
  val wordList: List[String] = List("The", "quick", "brown", "fox", "jumped", "over", "the", "lazy", "dog")
  val aardvark: List[Char] = "aardvark".toList
  val numList: List[Int] = List(3, 1, 4, 1, 5, 9, 2, 6, 5)
  val bitList: List[Int] = List(1, 0, 1, 1, 0, 1, 1, 0)
  /*
   * EXERCISE ListDemo4.1
   * Using only foldRight compute each of the following:
   * (a) the sum of the numbers in numList. Should be 36
   * (b) the product of the numbers in numList. Should be 32400
   * (c) the largest number in numlist. Should be 9
   * (d) The smallest number in numlist. Should be 1
   * (e) All the words in wordList flattened into a string: "Thequickbrownfoxjumpedoverthelazydog"
   * (f) The length of bitList. Should be 8
   * (g) The parity (number of 1 bits) of bitList (0 if even, 1 if odd). Should be 1
   * (h) The number of zeros and ones in bitList returned as a pair of Int values. Should be (3, 5)
   * (i) The longest word in wordList. Should be "jumped"
   * (j) The list of words from wordList with each word reversed
   * (k) The list of words from wordList containing the letter 'o'
   */

  def exerciseListDemo4_1() {
    val a = numList.foldRight(0)(_ + _)
    val b = numList.foldRight(1)(_ * _)
    val c = numList.foldRight(1)(_ max _)
    val d = numList.foldRight(1)(_ min _)
    val e = wordList.foldRight("")(_ + _) // Here + is string concatenation
    val f = bitList.foldRight(0)((b, n) => n + 1)
    val g = bitList.foldRight(0)((b, p) => if (b == 1) 1 - p else p)
    val h = bitList.foldRight((0, 0)) { case (b, (z, o)) => (z + 1 - b, o + b) }
    val i = wordList.foldRight("")((w1, w2) => if (w1.length > w2.length) w1 else w2)
    val j = wordList.foldRight(List[String]())((w, ws) => w.reverse :: ws)
    val k = wordList.foldRight(List[String]())((w, ws) => if (w.contains('o')) w :: ws else ws)
    println(s"$a\n$b\n$c\n$d\n$e\n$f\n$g\n$h\n$i\n$j\n$k")
  }
  /*
   * EXERCISE ListDemo4.2
   * Using only foldLeft compute each of the following:
   * (a) the sum of the numbers in numList. Should be 36
   * (b) the product of the numbers in numList. Should be 32400
   * (c) the largest number in numlist. Should be 9
   * (d) The smallest number in numlist. Should be 1
   * (e) All the words in wordList flattened into a string: "Thequickbrownfoxjumpedoverthelazydog"
   * (f) The length of bitList. Should be 8
   * (g) The parity (number of 1 bits) of bitList (0 if even, 1 if odd). Should be 1
   * (h) The number of zeros and ones in bitList returned as a pair of Int values. Should be (3, 5)
   * (i) Convert the bitList to an unsigned decimal integer. Should be 182
   */

  def exerciseListDemo4_2() {
    val a = numList.foldLeft(0)(_ + _)
    val b = numList.foldLeft(1)(_ * _)
    val c = numList.foldLeft(1)(_ max _)
    val d = numList.foldLeft(1)(_ min _)
    val e = wordList.foldLeft("")(_ + _) // Here + is string concatenation
    val f = bitList.foldLeft(0)((n, b) => n + 1)
    val g = bitList.foldLeft(0)((p, b) => if (b == 1) 1 - p else p)
    val h = bitList.foldLeft((0, 0)) { case ((z, o), b) => (z + 1 - b, o + b) }
    val i = bitList.foldLeft(0)((d, b) => d * 2 + b)
    println(s"$a\n$b\n$c\n$d\n$e\n$f\n$g\n$h\n$i")
  }
  /*
   * In many of the cases in ListDemo4.2 you found that substituting foldLeft in
   * place of foldRight with the same initial value and same function (operator)
   * delivered the same results. In other cases, however, you had to adapt the
   * order of the parameters to the function (operator). This recognises that the
   * accumulating parameter is on right (or the left) of the function (operator)
   * respectively.
   *
   * You can use a foldLeft or a foldRight interchangeably, i.e.
   *
   *    xs.foldLeft(z)(op) == xs.foldRight(z)(op)
   *
   * under the following conditions:
   *
   * 		z op x == x                     // z is a left identity of op
   *  and
   *    x op z == x                     // z is a right identity of op
   *  and
   *    (a op b) op c == a op (b op c)  // op is an associative operator
   *
   * Mathematicians say that a tuple (z, op) that satisfies these properties
   * is a monoid.
   *
   * Thus, the following examples for z and op will permit interchangeability:
   * 	  (+, 0)  // numeric addition and zero			 0+x==x,  x+0==x,  (a+b)+c==a+(b+c)
   * 		(*, 1)	// numeric multiplication and one  1*x==x,  x*1==x,  (a*b)*c==a*(b*c)
   *    (+, "") // string concatenation and empty  ""+s==s, s+""==s, (s+t)+u==s+(t+u)
   *    (&&, true)  // conjunction and true      true&&b==b, b&&true==b,  && is associative
   *    (||, false) // disjunction and false    false||b==b, b||false==b, || is associative
   * and there are lots of other examples.
   * But,
   *    (-, 0) does not work because 0-x does not equal x, and - is not associative
   *           e.g.  5-0==5, 0-5==-5,  (3-4)-5==-6, 3-(4-5)==4
   *    (/, 1) does not work
   * and you can think of lots of other examples like this, too.
   */

  /*
   * EXERCISE ListDemo4.3
   *
   * When you might use foldRight instead of foldLeft...
   * (a) When the conditions for interchangeability are satisfied
   * (b) When the cost of evaluating op increases as its left argument does
   * 		 (e.g. list concatenation xs:::ys which is proportional to the
   *     length of xs. Thus (xs:::ys):::zs takes longer to evaluate than
   *     xs:::(ys:::zs). If each of these lists is of length N (say), then
   *     the first case takes N+2N = 3N steps whereas the second case takes
   *     N+N = 2N steps. This may not seem like a big difference, but over
   *     a list of lists the difference is compounded.)
   *
   * Run the following experiment and note the results. Try increasing the
   * size of the longList to get a more dramatic difference. Start with
   * 25000 and try a few other values until you appreciate the scale of it.
   * It is the difference between O(N) and O(N^2) for very large N.
   */
  def exerciseListDemo4_3() {
    val longList: List[Int] = (1 to 20000).toList
    val longListList: List[List[Int]] = longList.map(List[Int](_))

    def foldExperiment() {
      println(longListList take 10)
      val t0: Long = System.nanoTime()
      val fl: List[Int] = longListList.foldLeft(List[Int]())(_ ::: _)
      val t1: Long = System.nanoTime()
      println(s"Left folding:  Display first ten:\n${fl.take(10)}...\nTook ${(t1 - t0) / 1000000L} milliseconds")
      val t2: Long = System.nanoTime()
      val fr: List[Int] = longListList.foldRight(List[Int]())(_ ::: _)
      val t3: Long = System.nanoTime()
      println(s"Right folding: Display first ten:\n${fr.take(10)}...\nTook ${(t3 - t2) / 1000000L} milliseconds")
    }

    foldExperiment()
  }
  /*
   * Variations: We leave you to look up the following variations on folding:
   * (1) reduceLeft         -	what happens if the list is empty?
   * (2) reduceLeftOption   - what happens if the list is empty?
   * (3) reduceRight        - what happens if the list is empty?
   * (4) reduceRightOption  - what happens if the list is empty?
   * (5) fold - why is this non-deterministic version is useful?
   */

  /*
   * EXERCISE ListDemo4.4
   *
   * NB: ListDemo4.1(f),(j) and (k) are examples of using foldRight to calculate the
   * length of a list and to perform a map and a filter (respectively). These can be
   * generalised - see the functions lengthFun, mapFun, filterFun below.
   *
   * In a similar way, write functions to perform the following operations using only
   * foldLeft or foldRight:
   *
   * (a) flattenFun: flattens a list of lists into a list
   * (b) reverseFun: reverses a list
   * (c) sequenceFun: applies a list of functions in turn:
   *     e.g. sequenceFun(List(f, g, h))(x) returns h(g(f(x)))
   */
  def exerciseListDemo4_4() {
    def lengthFun[A](xs: List[A]): Int =
      xs.foldRight(0)((b, n) => n + 1)

    def mapFun[A, B](xs: List[A], f: A => B): List[B] =
      xs.foldRight(List.empty: List[B])((y, ys) => f(y) :: ys)

    def filterFun[A](xs: List[A], p: A => Boolean): List[A] =
      xs.foldRight(List.empty: List[A])((y, ys) => if (p(y)) y :: ys else ys)

    def flattenFun[A](xss: List[List[A]]): List[A] =
      xss.foldRight(List[A]())(_ ::: _)

    def reverseFun[A](xs: List[A]): List[A] =
      xs.foldLeft(List[A]())((xs, x) => x :: xs)

    def sequenceFun[A](fs: List[A => A])(x: A): A =
      fs.foldRight(identity[A] _)(_ andThen _)(x)

    println(mapFun(numList, (_: Int) * 2))
    println(mapFun(wordList, (_: String).toUpperCase))
    println(filterFun(numList, (_: Int) % 3 == 0))
    println(filterFun(wordList, (_: String).contains('o')))
    println(flattenFun(List(List(1, 2), List(3), List(4, 5))))
    println(reverseFun(wordList))
    println(reverseFun(mapFun(wordList, (_: String).reverse)))
    println(sequenceFun(List[Int => Int](_ + 1, _ * 2, x => x * x, _ / 3))(3))
    println(sequenceFun(
      List[String => String](
        _.toUpperCase,
        _.reverse,
        "<tag>" + _,
        _ + "</tag>"))("snoitcnuf gnicneuqes"))
  }
  /*
   * EXERCISE ListDemo4.5
   * Sometimes it is required to keep all of the partial results of
   * a fold. For this purpose we can use scan.  For example:
   *
   * List(1,2,3).foldLeft(0)(_+_)  = 6
   *  ((0 + 1) + 2) + 3
   *
   * List(1,2,3).foldRight(0)(_+_) = 6
   *   1 + (2 + (3 + 0))
   *
   * List(1,2,3).scanLeft(0)(_+_)  = List(0, 1, 3, 6)
   *   List( 0,  0 + 1,  (0 + 1) + 2,  ((0 + 1) + 2) + 3 )
   *
   * List(1,2,3).scanRight(0)(_+_) = List(6, 5, 3, 0)
   *   List(  1 + (2 + (3 + 0)),  2 + (3 + 0),  3 + 0,  0 )
   *
   * Here are some examples with different types:
   *   aardvark.scanRight(List[Char]())(_::_)
   * = List(List(a, a, r, d, v, a, r, k),
   *        List(a, r, d, v, a, r, k),
   *        List(r, d, v, a, r, k),
   *        List(d, v, a, r, k),
   *        List(v, a, r, k),
   *        List(a, r, k),
   *        List(r, k),
   *        List(k),
   *        List())
   *
   *   List[Int=>Int](_+1, _*2, x => x*x, _/3).scanLeft(identity[Int] _)(_ andThen _)
   * = List($$Lambda$13366/985613394@453b614,
   *        scala.Function1$$Lambda$13368/1605515070@440eff18,
   *        scala.Function1$$Lambda$13368/1605515070@77fddc0a,
   *        scala.Function1$$Lambda$13368/1605515070@39b80e0c,
   *        scala.Function1$$Lambda$13368/1605515070@20c2ec39)
   *   Scala can't print the functions it generated very nicely, but this list of
   *   functions they represent:
   *   List(id,
   *        id andThen (_+1),
   *        (id andThen (_+1)) andThen (_*2),
   *        ((id andThen (_+1)) andThen (_*2)) andThen (x => x*x),
   *        (((id andThen (_+1)) andThen (_*2)) andThen (x => x*x)) andThen (_/3))
   *   The example is more interesting if we apply each of these functions to a value:
   *   List[Int=>Int](_+1, _*2, x => x*x, _/3).scanLeft(identity[Int] _)(_ andThen _) map (_(3))
   * = List(3, 4, 8, 64, 21)
   *
   * Using scanLeft or scanRight produce the following lists:
   *
   * (a) The list of factorials from 0 factorial to 9 factorial:
   *     List(1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880)
   *     Use (1 to 9).toList.scanLeft...
   *
   * (b) All the suffixes of the string "0123":
   *     List(0123, 123, 23, 3, "")
   *     Use "0123".toString.scanRight...
   *
   * (c) All the prefixes of the string "0123"
   *     List("", 0, 01, 012, 0123)
   *
   * (d) The integer representations of each of the prefixes of bitList.
   *     i.e. given that bitList represents 10110110 then the result has the
   *     decimal equivalents of
   *     (0, 1,  10, 101, 1011, 10110, 101101, 1011011, 10110110)
   *     The first element represents the base case (an empty byte)
   *     List(0, 1, 2, 5, 11, 22, 45, 91, 182)
   *     Use bitList.scanLeft...
   */
  def exerciseListDemo4_5() {
    // Examples
    println(List(1, 2, 3).foldLeft(0)(_ + _))
    println(List(1, 2, 3).foldRight(0)(_ + _))
    println(List(1, 2, 3).scanLeft(0)(_ + _))
    println(List(1, 2, 3).scanRight(0)(_ + _))
    println(aardvark.scanLeft(List[Char]())((a, b) => b :: a))
    println(aardvark.scanRight(List[Char]())(_ :: _))
    println(List[Int => Int](_ + 1, _ * 2, x => x * x, _ / 3).scanLeft(identity[Int] _)(_ andThen _))
    println(List[Int => Int](_ + 1, _ * 2, x => x * x, _ / 3).scanLeft(identity[Int] _)(_ andThen _) map (_ (3)))
    //Exercises
    val a = (1 to 9).toList.scanLeft(1)(_ * _)
    val b = "0123".toList.scanRight("")(_ + _)
    val c = "0123".toList.scanLeft("")(_ + _)
    val d = bitList.scanLeft(0)(_ * 2 + _)
    println(s"$a\n$b\n$c\n$d")
  }
  def main(args: Array[String]): Unit = {
    exerciseListDemo4_1()
    exerciseListDemo4_2()
    exerciseListDemo4_3()
    exerciseListDemo4_4()
    exerciseListDemo4_5()

  }
}
