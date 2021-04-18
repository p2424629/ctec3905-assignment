package tree

import scala.language.implicitConversions

object TreeDemo1Answers {

  /*
   * We introduce the syntax of propositional logic. An proposition is represented by
   * the abstract class Prop. Actual propositions will be constructed from its
   * subclasses.  Note the following points about Prop:
   *
   * 1) Five methods have been provided as "syntactic sugar" to facilitate writing
   *    propositions. For example, instead of writing And(Not(P),Or(Not(Q), Not(R)))
   *    we can write ~P * (~Q + ~R). This is much less error-prone, and is easier to
   *    read (and write).
   * 2) We have used the symbol ~ for Not. Scala allows us to write this unary operator
   *    in a prefix position. For logical negation ~P  is more natural to write than
   *    P.~ (the latter is not legal syntax becuase ~ is prefix).
   * 3) We have used the symbols *, +, >>, and == for conjunction, disjunction,
   *    impication, and equivalence, respectively. These were chosen becuase Scala has
   *    a builit-in operator precedence table that allows these operators to have a
   *    precedence of * (highest) down to == (lowest).  Thus, e.g., the proposition
   *    P * Q >> P == P + ~P
   *    would be parsed as
   *    ((P * Q) >> P) == (P + (~P))
   * 4) A toString method has been provided to print out propositions using the same
   *    infix symbols (rather than defaulting to the prefix And(Not(P), Q) style.
   *    Parentheses are added around every binary operation to reflect the structure
   *    of the proposition. Some of these may be unnecessary - but none will be wrong.
   */
  sealed abstract class Prop {
    def unary_~ : Not = Not(this)

    def *(that: Prop): And = And(this, that)

    def +(that: Prop): Or = Or(this, that)

    def >>(that: Prop): Implies = Implies(this, that)

    def ==(that: Prop): Equiv = Equiv(this, that)

    override def toString: String = this match {
      case True => "True"
      case False => "False"
      case P => "P"
      case Q => "Q"
      case R => "R"
      case Not(Not(p)) => s"~(${Not(p)})"
      case Not(p) => s"~$p"
      case And(p, q) => s"($p * $q)"
      case Or(p, q) => s"($p + $q)"
      case Implies(p, q) => s"($p >> $q)"
      case Equiv(p, q) => s"($p == $q)"
    }
  }

  /*
   * The subclasses of Prop represent the different types of proposition available.
   * These include:
   * - Constants:  True and False
   * - Variables:  P, Q, and R  (You could add more, but these are all for now)
   * - Negation:   Not(_)
   * - Conjunction: And(_, _)
   * - Disjunction: Or(_,_)
   * - Implication: Implies(_,_)
   * - Equivalende: Equiv(_,_)
   */

  sealed abstract class Constant extends Prop

  case object True extends Constant

  case object False extends Constant

  sealed abstract class Variable extends Prop

  case object P extends Variable

  case object Q extends Variable

  case object R extends Variable

  case class Not(p: Prop) extends Prop

  case class And(p: Prop, q: Prop) extends Prop

  case class Or(p: Prop, q: Prop) extends Prop

  case class Implies(p: Prop, q: Prop) extends Prop

  case class Equiv(p: Prop, q: Prop) extends Prop

  /*
   * Scala allows implicit functions. This is an advanced feature and its use
   * is flagged by the import at the top of this file. Whenever the compiler
   * "sees" a proposition True (or False) in a position where a Scala Boolean
   * is expected then this function is called by the compiler automatically.
   * For example, we can write
   * if (eval(p)) ...
   * and the compiler will convert this to
   * if (constantToBoolean(eval(p))) ...
   * The purpose of this is to avoid clutter in the code when the intention
   * is clear. Implicit conversions need to be used with care becuase the
   * notational simplicity they offer can be outweighed by semantic complexity.
   * The more the compiler does secretly behind the scenes, the less obvious
   * this may be when trying to track down errors in the code. However, we
   * conclude that it is a benefit in this case. It means we can use the
   * result of evaluating a proposition to determine the behaviour of other
   * functions that are testing for (Boolean) true/false.
   */
  implicit def constantToBoolean(c: Constant): Boolean = c match {
    case True => true
    case False => false
  }

  /*
   * object Prop is the "companion object" to class Prop. Scala allows any
   * class to have a companion object which must have the same name as its
   * class. Inside the companion object it is possible to write useful functions
   * and other definitions that are part of the library associated with the
   * class but which are not written as instance methods. The analogue in Java
   * is that the definitions in the companion object are like the static
   * definitions in a Java class. (Scala does not support static definitions).
   * You have seen these before. For example, the Scala class List has the
   * instance method length. So, for some list xs, you call the method using
   * the standard method notation xs.length. The companion object List also
   * has definitions such as "range" and you call this using the function
   * notation: List.range(x, y). If you import everything in the companion
   * object, e.g.
   *   import List._
   * then you don't need to qualify the functions: just write: range(x, y).
   *
   * A moot point is what to do about definitions that take an instance of
   * Prop as a parameter. For example, the evaluation function (see below).
   * We have added eval to the companion object. To evaluate a proposition
   * we will write: Prop.eval(p)(values)
   * However, we could have written this as an instance method within the
   * class Prop in which case we would call it like this:
   * p.eval(values)
   * We decided to put it into the companion object because we wanted it to
   * be used in prefix form: eval(p)(...). However, compare this with the
   * toString method which is defined by cases as an instance method within
   * the class Prop. In this situation it is appropriate to do this because
   * toString is always used in the postfix position, p.toString.
   */
  object Prop {

    /*
     * Evaluating a proposition is defined by case analysis of the proposition
     * itself. The constants True and False evaluate to themselves. The unary
     * operator Not evalutes its argument and then inverts the result. The And
     * operator is defined by evaluating its first argument and then, if it
     * returns False, returning False. But if the first argument evaluates to
     * True then evaluate the second argument. The operators Or, Implies, and
     * Equivalence are all derived operators (they can be written in terms of
     * other operators - namely And and Not). So these operators are evaluated
     * by delegation. Finally, consider the variables P, Q and R. Their values
     * need to be looked up in a state. The state is passed as a parameter to
     * the eval function and contains mappings of the propositional variables
     * to their corresponding propositional constant values. We have used a Map
     * collection for representing the state.
     */
    def eval(p: Prop, state: Map[Variable, Constant] = Map()): Constant = p match {
      case True => True
      case False => False
      case P => state(P)
      case Q => state(Q)
      case R => state(R)
      case Not(q) => eval(q, state) match {
        case True => False
        case False => True
      }
      case And(q, r) => eval(q, state) match {
        case False => False
        case True => eval(r, state)
      }
      case Or(q, r) => eval(~(~q * ~r), state)
      case Implies(q, r) => eval(~q + r, state)
      case Equiv(q, r) => eval((q >> r) * (r >> q), state)
    }

    /*
     * A function to return the list of variables used in a proposition. It is
     * defined by case analysis over the structure of the proposition p. The
     * constants True and False contain no variables. Each variable contains
     * itself. The operators collect the variables defined by their arguments
     * and join these together.
     */
    def getVars(p: Prop): List[Variable] = (p match {
      case x: Constant => List()
      case v: Variable => List(v)
      case Not(q) => getVars(q)
      case And(q, r) => getVars(q) ::: getVars(r)
      case Or(q, r) => getVars(q) ::: getVars(r)
      case Implies(q, r) => getVars(q) ::: getVars(r)
      case Equiv(q, r) => getVars(q) ::: getVars(r)
    }).distinct

    /*
     * A function that returns all the combinations of state mappings of the
     * given propositional variables to the constants True and False. In other
     * words, combinations returns a truth table with columns for each variable.
     * So, given the list of variables List(P, Q) then the result would be:
     * List( Map(P -> False, Q -> False),
     *       Map(P -> False, Q -> True),
     *       Map(P -> True,  Q -> False),
     *       Map(P -> True,  Q -> True)
     *     )
     * The values are generated by using the underlying bit-patterns associated
     * with the numbers 0 - N where N = 2^(number of variables) - 1
     * Thus, for the List(P, Q), N = 2^2 - 1 = 3. So the numbers 0..3 are
     * generated. The underlying bit patterns are
     * 00000000 00000001 00000010 00000011
     * The mappings are formed by testing the corresponding bits and matching
     * them with the variables.
     */
    def combinations(vs: List[Variable]): List[Map[Variable, Constant]] = {
      val numVars = vs.length
      val combs = List.range(0, math.pow(2, numVars).toInt) map (_.toByte)

      def testIndex(b: Byte, i: Long): Constant = if ((b >>> i) % 2 == 1) True else False

      val mappings = combs map (b => (for ((v, i) <- vs.zipWithIndex) yield v -> testIndex(b, i)).toMap)
      mappings
    }

    /*
     * A proposition is satisfiable if there exists (at least) one way of
     * assigning the values True/False to the variables such that the proposition
     * evalauates to True. That is, at least one row of the truth table returns
     * True. To discover whether a proposition p is satisfiable the following
     * steps are taken:
     * - get all the combinations of mappings of variables to values in p
     * - evaluate p with each of these combinations in turn
     * - return a combination that satisfies the proposition as soon as it is found.
     *   Otherwise indicate that there is no such combination by returning None.
     */
    def satisfiable(p: Prop): Option[Map[Variable, Constant]] =
      combinations(getVars(p)) find (eval(p, _))

    /*
     * A proposition is valid if it returns True when evaluated with every
     * combination of assginments of True/False to its variables. That is, every
     * row of the truth table returns True. Therefore, generate all of these
     * assignment combinations and check that evaluating p for every one of
     * these states returns True.  Note taht the forall method expects a function
     * argument that returns true/false (Boolean). The eval function returns
     * True/False (Constant). This is a situation when the compiler will convert
     * implicitly a True/False to a true/false by using the implicit function
     * definition constantToBoolean (see earlier in this file).
     */
    def valid(p: Prop): Boolean =
      combinations(getVars(p)) forall (eval(p, _))


    /*
     * Exercise TreeDemo1.1  WRITE A FUNCTION THAT REPLACES ALL DISJUNCTIONS
     * (a + b) BY CONJUNCTIONS (using De Morgan's law): a + b  =  ~(~a * ~b)
     * Thus  P + Q            becomes  ~(~P * ~Q)
     * and   P + (Q * R + P)  becomes  ~(~P * ~(~(~(Q * R) * ~P)))
     */

    def changeOrToAnd(p: Prop): Prop = p match {
      case Or(q, r) => Not(And(Not(changeOrToAnd(q)), Not(changeOrToAnd(r))))
      case And(q, r) => And(changeOrToAnd(q), changeOrToAnd(r))
      case Implies(q, r) => Implies(changeOrToAnd(q), changeOrToAnd(r))
      case Equiv(q, r) => Equiv(changeOrToAnd(q), changeOrToAnd(r))
      case Not(q) => Not(changeOrToAnd(q))
      case x: Constant => x
      case v: Variable => v
    }


    /*
     * Exercise TreeDemo1.2  WRITE A FUNCTION THAT REMOVES DOUBLE NEGATIONS
     * FROM A PROPOSITION. This uses the involution law:  ~(~a) = a
     * Thus  ~(~P) >> ~(~(~Q))            becomes  P >> ~Q
     * and   ~(~(~(~(~P)) + ~Q * ~(~R)))  becomes ~P + (~Q * R)
     */

    def removeDoubleNegations(p: Prop): Prop = p match {
      case x: Constant => x
      case v: Variable => v
      case Not(Not(q)) => removeDoubleNegations(q)
      case Not(q) => Not(removeDoubleNegations(q))
      case And(q, r) => And(removeDoubleNegations(q), removeDoubleNegations(r))
      case Or(q, r) => Or(removeDoubleNegations(q), removeDoubleNegations(r))
      case Implies(q, r) => Implies(removeDoubleNegations(q), removeDoubleNegations(r))
      case Equiv(q, r) => Equiv(removeDoubleNegations(q), removeDoubleNegations(r))
    }

    /*
     * Exercise TreeDemo1.3  WRITE A FUNCTION THAT SUBSTITUTES ALL OCCURRENCES
     * OF VARIABLE x FOR PROPOSITION y WITHIN A PROPOSITION p. The algorithm
     * should perform a prefix traversal of p.
     * Thus  substitute(P + Q * P, P, R)           becomes  R + (Q * R)
     * and   substitute(P + Q * P, P, ~P)          becomes  ~P + (Q * ~P)
     * and   substitute(P + Q * P, P, ~(P >> ~Q))  becomes  ~(P >> ~Q) + (Q * ~(P >> ~Q))
     */
    def substitute(p: Prop, x: Variable, y: Prop): Prop = p match {
      case c: Constant => c
      case v: Variable => if (v == x) y else v
      case Not(q) => Not(substitute(q, x, y))
      case And(q, r) => And(substitute(q, x, y), substitute(r, x, y))
      case Or(q, r) => Or(substitute(q, x, y), substitute(r, x, y))
      case Implies(q, r) => Implies(substitute(q, x, y), substitute(r, x, y))
      case Equiv(q, r) => Equiv(substitute(q, x, y), substitute(r, x, y))
    }

    /*
     * Exercise TreeDemo1.4  WRITE A FUNCTION THAT DETERMINES WHETHER A PROPOSITION
     * IS NOT VALID. The function should return ANY state (bindings of variables to
     * True/False) that falsifies the proposition if it is not valid. Otherwise it
     * should return None (i.e. there are no bindings that falsify it).
     */
    def notValid(p: Prop): Option[Map[Variable, Constant]] =
      combinations(getVars(p)) find (eval(~p, _))

    /*
     * Exercise TreeDemo1.5  WRITE A FUNCTION THAT DETERMINES WHETHER A PROPOSITION
     * IS NOT VALID. The function should return ALL states (bindings of variables to
     * True/False) that falsifies the proposition if it is not valid. Otherwise it
     * should return an empty list (i.e. there are no bindings that falsify it).
     */
    def notValidAll(p: Prop): List[Map[Variable, Constant]] =
      combinations(getVars(p)) filter (eval(~p, _))

  }

  def main(args: Array[String]): Unit = {
    import Prop._

    println(eval(True))
    println(eval(False >> True))

    val x: Prop = ~(~P) + Q + R * (~Q * ~R * ~P + ~R * ~(P + Q))
    println(x)
    println(getVars(x))

    println(combinations(List(P, Q, R)))
    println(satisfiable(x))
    println(valid(x))

    // Exercise TreeDemo1.1
    println(changeOrToAnd(x))
    println(removeDoubleNegations(changeOrToAnd(x)))
    println(P + (Q * R + P))
    println(changeOrToAnd(P + (Q * R + P)))

    // Exercise TreeDemo1.2
    val y = ~(~P) >> ~(~(~Q))
    println(y)
    println(removeDoubleNegations(y))

    // Exercise TreeDemo1.3
    println(substitute(y, P, R))
    println(substitute(y, R, P))
    println(substitute(P + Q * P, P, ~(P >> ~Q)))

    // Exercise TreeDemo1.4
    println(notValid(y))
    println(notValid(P + ~P))

    // Exercise TreeDemo1.5
    println(notValidAll(y))
    println(notValidAll(P == P + P))

    println(changeOrToAnd(P + ~(P + Q)))
  }
}
