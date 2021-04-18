package lib.editor.immutable

/**
  * A basic editor implements the sequence of characters using an immutable
  * List.
  *
  * @author David Smallwood
  */
class BasicEditor(cursor: Int = 0, size: Int = 0, text: List[CharElement] = List()) {

  final def left: BasicEditor =
    if (cursor > 0)
      new BasicEditor(cursor - 1, size, text)
    else
      this

  final def right: BasicEditor =
    if (cursor < size)
      new BasicEditor(cursor + 1, size, text)
    else
      this

  final def insert(c: Char): BasicEditor = {
    val (l, r) = text.splitAt(cursor)
    new BasicEditor(cursor + 1, size + 1, l ++ (CharElement(c) :: r))
  }

  final def delete: BasicEditor =
    if (cursor < size) {
      val (l, r) = text.splitAt(cursor)
      new BasicEditor(cursor, size - 1, l ++ r.tail)
    }
    else
      this

  final def backspace: BasicEditor =
    if (cursor > 0)
      this.left.delete
    else
      this

  final def modify(c: Char): BasicEditor =
    if (cursor < size)
      this.delete.insert(c).left
    else
      this

  override def toString: String = {
    val (l, r) = text.map(_.char).splitAt(cursor)
    s"($cursor,$size)[${l.mkString}|${r.mkString}]"
  }

  final def show: BasicEditor = {
    println(this.toString)
    this
  }
}
