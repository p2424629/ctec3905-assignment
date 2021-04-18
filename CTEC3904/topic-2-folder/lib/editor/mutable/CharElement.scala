package lib.editor.mutable

/**
  * A representation of characters in an editor. In its basic form an instance of
  * this class is simply a box for a Char. However, there is scope to add other
  * attributes to represent decoration such as boldface, italic, colour, etc. This
  * has not been done here, but the data structure enables this as a future
  * extension.
  *
  * @author David Smallwood
  * @param initChar The initial character assigned to the new instance.
  */
class CharElement(initChar: Char) {

  private var c: Char = initChar

  /*
   * Other attributes could be added here to represent properties of the current
   * character: e.g. bold typeface, italics, underline, strikethrough, colours, etc.
   * For example, an Int could be used or (better) a [[collection.mutable.BitSet]].
   * var attributes: Int = ...
   * var attributes: collection.mutable.BitSet = ...
   */

  def setChar(c: Char): Unit =
    this.c = c

  def getChar: Char = c

  override def toString: String = s"$c"
}
