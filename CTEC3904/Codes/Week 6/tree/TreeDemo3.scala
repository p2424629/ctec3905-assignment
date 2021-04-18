package tree

object TreeDemo3 {
  /*
   * When we need to compare elements we will assume we can do so using the comparison
   * methods in Ordering: lt, gt, lteq, gteq, equiv, etc.
   */

  import scala.math.Ordering._

  /*
   * ABSTRACT TREES
   * A binary search tree. Smaller items go on the left, and larger items on the right.
   * Duplicates are not stored.
   */
  sealed abstract class BST[+A] {
    /*
     * Returns true if a BST is Empty and false if it is a Fork instance.
     */
    def isEmpty: Boolean
    /*
     * Return the left side of a BST
     */
    def left: BST[A]
    /*
     * Return the right side of a BST
     */
    def right: BST[A]
    /*
     * Return the element at the root of a BST
     */
    def root: A
    /*
     * Splits the tree by removing the least element and returning a pair containing
     * (the least element, the BST without the least element)
     */
    def split: (A, BST[A])
    /*
     * Joins two BSTs by creating a new BST with the smallest element of the 2nd tree
     * acting as the root of the newly created tree.
     */
    def join[B >: A](that: BST[B]): BST[B] = that match {
      case Empty => this
      case _ =>
        val (h, t) = that.split
        Fork(this, h, t)
    }
    /*
     * Insert a new item into the BST
     */
    def insert[B >: A](x: B)(implicit ord: Ordering[B]): BST[B]
    /*
     * Remove an item from the BST. If it is not in the tree then return the original tree.
     */
    def delete[B >: A](x: B)(implicit ord: Ordering[B]): BST[B]
    /*
     * Perform an inorder traversal of the BST generating a List of items in order.
     */
    def flatten: List[A] =
      if (this.isEmpty)
        List.empty
      else
        this.left.flatten ::: this.root :: this.right.flatten
    /*
     * Pretty-print the tree
     */
    def show: String = BST.shows(this)._3.mkString("\n")
    /*
     * Exercise TreeDemo3.1
     * CALCULATE THE DEPTH OF THE TREE (LENGTH OF LONGEST PATH FROM THE ROOT)
     */
    def depth: Int = ???
    /*
     * Exercise TreeDemo3.2
     * LOOK FOR ITEM x IN THE TREE. IF IT IS THERE RETURN true, ELSE false.
     */
    def contains[B >: A](x: B)(implicit ord: Ordering[B]): Boolean = ???
    /*
     * Exercise TreeDemo3.3
     * WRITE A PREORDER TREE TRAVERSAL. The traversed items are returned in a list.
     */
    def preorder: List[A] = ???
  }

  /*
   * The companion object to the class BST. A collection of useful functions
   * for processing BSTs is provided here.  These functions must be called
   * like this:  BST.tsort(xs).  If the BST object and its contents have been
   * imported into the user's scope, then the BST prefix is not required:
   * import BST._
   * tsort(xs)
   */
  object BST {
    /*
     * Construct a BST from a list of items.
     */
    def build[A](ys: List[A])(implicit ord: Ordering[A]): BST[A] = ys match {
      case List() => Empty
      case x :: xs => {
        val (lft, rgt) = xs.partition(ord.lteq(_, x))
        Fork(build(lft), x, build(rgt))
      }
    }
    /*
     * Insert a list of items into a BST.
     */
    def inserts[A](bst: BST[A], xs: List[A])(implicit ord: Ordering[A]): BST[A] =
      xs.foldRight(bst)((x: A, t: BST[A]) => t.insert(x))
    /*
     * Sort a list of items using a BST as an intermediate structure.
     */
    def tsort[A](xs: List[A])(implicit ord: Ordering[A]): List[A] = build(xs).flatten
    /*
     * Pair the elements of two lists and then apply the given function to each pair
     * resulting in a new list. Such a function (in Haskell) is called zipWith. The
     * zipWith function is used in the shows function that follows.
     */
    def zipWith[A, B, C](f: (A, B) => C)(xs: List[A])(ys: List[B]): List[C] =
      for ((x, y) <- xs zip ys) yield f(x, y)
    /*
     * This binary-tree pretty-printing algorithm is adapted from Mark Jones' version
     * distributed with examples of using the gofer/hugs languages (c. early 1990s).
     */
    def shows[A](t: BST[A]): (Int, Int, List[String]) = t match {
      case Empty => (1, 1, List(""))
      case Fork(l, n, r) => {
        val (hl, bl, pl) = shows(l)
        val (hr, br, pr) = shows(r)
        val joinStrings = (a: String, b: String) => a + b
        val top = zipWith(joinStrings)(List.fill(bl - 1)("     ") ::: List("   ,-") ::: List.fill(hl - bl)("   | ")) _
        val bot = zipWith(joinStrings)(List.fill(br - 1)("   | ") ::: List("   '-") ::: List.fill(hr - br)("     ")) _
        val mid = List(s"--$n ")
        (hl + hr + 1, hl + 1, top(pl) ::: mid ::: bot(pr))
      }
    }
  }

  /*
   * EMPTY TREES
   */
  case object Empty extends BST[Nothing] {
    override def isEmpty: Boolean = true
    override def left: BST[Nothing] = throw new Exception("Empty.left")
    override def right: BST[Nothing] = throw new Exception("Empty.right")
    override def root: Nothing = throw new Exception("Empty.root")
    override def split: (Nothing, BST[Nothing]) = throw new Exception("Empty.split")
    override def insert[B >: Nothing](x: B)(implicit ord: Ordering[B]): BST[B] =
      Fork(Empty, x, Empty)
    override def delete[B >: Nothing](x: B)(implicit ord: Ordering[B]): BST[B] =
      this
  }

  /*
   * NON-EMPTY TREES
   */
  case class Fork[+A](l: BST[A], n: A, r: BST[A]) extends BST[A] {
    override def isEmpty: Boolean = false
    override def left: BST[A] = l
    override def right: BST[A] = r
    override def root: A = n
    override def split: (A, BST[A]) = l match {
      case Empty => (n, r)
      case _ => {
        val (x, ll) = l.split
        (x, Fork(ll, n, r))
      }
    }
    override def insert[B >: A](x: B)(implicit ord: Ordering[B]): BST[B] =
      if (ord.lt(x, n))
        Fork(l.insert(x), n, r)
      else if (ord.gt(x, n))
        Fork(l, n, r.insert(x))
      else
        this
    override def delete[B >: A](x: B)(implicit ord: Ordering[B]): BST[B] =
      if (ord.lt(x, n))
        Fork(l.delete(x), n, r)
      else if (ord.gt(x, n))
        Fork(l, n, r.delete(x))
      else
        l join r
  }

  def main(args: Array[String]): Unit = {
    val xs: List[Int] = List(50, 25, 75, 20, 30, 70, 80, 27, 77, 28, 40, 42, 44, 47)
    val t: BST[Int] = BST.build(xs)
    println(t)
    println(t.show)

    // Exercise TreeDemo3.1
    //    println(t.depth)                // Should be 7
    //    println(t.left.depth)           // Should be 6
    //    println(t.left.left.depth)      // Should be 1
    //    println(t.left.right.depth)     // Should be 5
    //    println(t.left.left.left.depth) // Should be 0

    // Exercise TreeDemo3.2
    //    println(xs map (t contains _))    // All of these must be true
    //    println(xs forall (t contains _)) // Another way of checking the above
    //    println(t contains 99)            // Should be false

    // Exercise TreeDemo3.3
    //    println(t.flatten)                // Should be an ordered list (i.e. xs in order)
    //    println(t.preorder)               // Preorder travsersal: 50, 25, 20, 30, 27, ...
    //    println(BST.tsort(xs))            // Treesort should generate the same as t.flatten
  }
}
