package list

object ListDemo2Answers {

  /*
   * In this set of exercises you are going to build your own list data type. This is to give
   * you an insight into how the methods are written. You will not need to use this data type
   * again since the standard Scala collections have been optimised and provide a much larger
   * range of methods.
   *
   * The definition is taken from the notes that accompany this topic. However, the type and
   * its constructors have been written in upper case (i.e. LIST, NIL, CONS) to avoid any
   * namespace clashes with the Scala immutable List class that is imported by default.
   *
   * You will see that the methods have been declared within the abstract superclass and
   * overridden in each of the two subclasses, NIL, and CONS. The below methods have been
   * provided as examples of the coding style, and the remainder are left for you to implement.
   *
   *  apply   // i.e. xs(k) to look up the k-th value. This is the same as xs.apply(k)
   *  length
   *  head
   *  init
   *  filter
   *  :::     // the append operator
   *
   * Your task is to complete the following methods by adding their definitions to the data
   * structure and writing some demonstration outputs to show each one in action.
   *
   *  tail
   *  last
   *  take
   *  drop
   *  takeWhile
   *  dropWhile
   *  map
   */

  sealed abstract class LIST[+A] {
    def ::[B >: A](h: B): LIST[B] = CONS(h, this)

    def apply(k: Int): A

    def length: Int

    def head: A

    def init: LIST[A]

    def filter(p: A => Boolean): LIST[A]

    def :::[B >: A](that: LIST[B]): LIST[B] = that match {
      case NIL => this
      case CONS(h, t) => CONS(h, t ::: this)
    }

    def tail: LIST[A]

    def last: A

    def take(k: Int): LIST[A]

    def drop(k: Int): LIST[A]

    def takeWhile(p: A => Boolean): LIST[A]

    def dropWhile(p: A => Boolean): LIST[A]

    def map[B](f: A => B): LIST[B]
  }

  object LIST {
    /*
     * This method in the compnaion object allows us to convert between List
     * and LIST. Thus, LIST(List(1, 2, 3)) = 1 :: 2 :: 3 :: NIL
     */
    def apply[A](xs: List[A]): LIST[A] = xs.toList.foldRight(NIL: LIST[A])(_ :: _)
  }

  case object NIL extends LIST[Nothing] {
    override def apply(k: Int): Nothing = throw new Exception(s"NIL.apply($k)")

    override def length: Int = 0

    override def head: Nothing = throw new Exception("NIL.head")

    override def init: LIST[Nothing] = throw new Exception("NIL.init")

    override def filter(p: Nothing => Boolean): LIST[Nothing] = NIL

    /////

    override def tail: LIST[Nothing] = throw new Exception("NIL.tail")

    override def last: Nothing = throw new Exception("NIL.last")

    override def take(k: Int): LIST[Nothing] = NIL

    override def drop(k: Int): LIST[Nothing] = NIL

    override def takeWhile(p: Nothing => Boolean): LIST[Nothing] = NIL

    override def dropWhile(p: Nothing => Boolean): LIST[Nothing] = NIL

    override def map[B](f: Nothing => B): LIST[B] = NIL

  }

  case class CONS[+A](h: A, t: LIST[A]) extends LIST[A] {
    override def toString: String = s"$h :: $t"

    override def apply(k: Int): A =
      if (k == 0)
        h
      else
        t(k - 1)

    override def length: Int = 1 + t.length

    override def head: A = h

    override def init: LIST[A] = t match {
      case NIL => NIL
      case CONS(_, NIL) => CONS(h, NIL)
      case _ => CONS(h, t.init)
    }

    override def filter(p: A => Boolean): LIST[A] =
      if (p(h))
        CONS(h, t.filter(p))
      else
        t.filter(p)

    /////

    override def tail: LIST[A] = t

    override def last: A = t match {
      case NIL => h
      case _ => t.last
    }

    override def take(k: Int): LIST[A] =
      if (k==0) NIL else CONS(h, t.take(k-1))

    override def drop(k: Int): LIST[A] =
      if (k==0) this else t.drop(k-1)

    override def takeWhile(p: A => Boolean): LIST[A] =
      if (p(h)) CONS(h, t.takeWhile(p)) else NIL

    override def dropWhile(p: A => Boolean): LIST[A] =
      if (p(h)) t.dropWhile(p) else this

    override def map[B](f: A => B): LIST[B] =
      CONS( f(h), t.map(f))
  }

  def main(args: Array[String]): Unit = {

    val xs: LIST[Int] = 1 :: 2 :: 3 :: 4 :: 5 :: 6 :: 7 :: 8 :: 9 :: NIL
    val ys: LIST[Char] = LIST("onetwo".toList)
    val zs: LIST[Char] = LIST("threefourfive".toList)

    println(xs)
    println(xs.head)
    println(xs.length)
    println(xs.init)
    println(xs.filter(_ % 2 == 0))
    println(ys)
    println(zs)
    println(ys ::: zs)

    println(zs take 0)
    println(zs drop 0)
    println(zs take 4)
    println(zs drop 4)
    println(zs take 50)
    println(zs drop 50)

    println(zs takeWhile (_ != 'e'))
    println(zs dropWhile (_ != 'e'))

    println(zs map (_.toUpper))
    println(xs map (zs(_)))

  }
}
