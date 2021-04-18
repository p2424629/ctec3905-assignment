package lib.editor.immutable

/**
  * A representation of characters in an editor. In its basic form an instance of
  * this class is simply a box for a Char. However, there is scope to add other
  * attributes to represent decoration such as boldface, italic, colour, etc. This
  * has not been done here, but could be achieved by adding more parameters to the
  * case class.
  *
  * @author David Smallwood
  * @param char The character assigned to the new instance.
  */
case class CharElement(char: Char)
