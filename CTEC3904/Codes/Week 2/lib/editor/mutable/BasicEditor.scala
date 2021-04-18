package lib.editor.mutable

import scala.collection.mutable

/**
  * A basic editor implements the sequence of characters using a mutable
  * ListBuffer.
  *
  * @author David Smallwood
  */
class BasicEditor {

  import collection.mutable.ListBuffer

  /** The text is stored as a mutable sequence of character elements */
  protected var text: ListBuffer[CharElement] = new ListBuffer[CharElement]

  /** The number of characters currently on the line */
  protected var size: Int = 0

  /** The cursor position which can range between 0 and size */
  protected var cursor: Int = 0

  /**
    * Inserts a new [[CharElement]] at the current position. Room is made by
    * shunting characters from the current cursor position up one place. The
    * newly created character is a new instance of [[CharElement]] with its
    * default initial values. If the specified position == size then the new
    * character is created at the end of the sequence.
    *
    * @note Precondition: 0 <= position <= size
    * @param position The position to insert in the sequence.
    * @param ch       The character to insert.
    */
  protected def insertCharacter(position: Int, ch: CharElement): Unit = {
    text.insert(position, ch)
  }

  /**
    * Deletes the [[CharElement]] at the current position. This is achieved
    * by moving all characters to the right of the cursor position down one
    * place.
    *
    * @note Precondition: 0 <= position < size
    * @param position Specifies the position to delete in the sequence.
    */
  protected def deleteCharacter(position: Int): Unit = {
    text.remove(position)
  }

  /**
    * Applies a method to the [[CharElement]] at the current position.
    *
    * @note Precondition: 0 <= position < size
    * @param position The position to modify within the sequence.
    * @param f        The method to apply to the current [[CharElement]]
    */
  protected def modifyCharacter(position: Int, f: CharElement => Unit): Unit = {
    f(text(position))
  }

  /** **************************************************************************
    * Public interface:
    * ************************************************************************ */

  /**
    * Moves the cursor one place to the left if possible.
    *
    * @note Postcondition: If cursor == 0 before the method is called then
    *       cursor remains unchanged. Otherwise it is reduced by one.
    * @note Postcondition: The size does not change.
    */
  final def left(): Unit = {
    if (cursor > 0) cursor = cursor - 1
  }

  /**
    * Moves the cursor one place to the right if possible.
    *
    * @note Postcondition: If cursor == size before the method is called
    *       then cursor remains unchanged. Otherwise it is reduced by one.
    * @note Postcondition: The size does not change.
    */
  final def right(): Unit = {
    if (cursor < size) cursor = cursor + 1
  }

  /**
    * Inserts a new character at the current cursor position. The attribute
    * value associated with the new character will be the default initial
    * attribute value defined in [[CharElement]].
    *
    * @note Postcondition: The cursor is incremented by one so that it lies
    *       just after the inserted character and the size is incremented by one.
    * @param c The character to be inserted.
    */
  final def insert(c: Char): Unit = {
    insertCharacter(cursor, new CharElement(c))
    cursor = cursor + 1
    size = size + 1
  }

  /**
    * Deletes the character to the right of the current cursor position. This
    * is sometimes characterised as a forward-delete and is usually represented
    * by the Delete (or DEL) key on a keyboard.
    *
    * @note If cursor is equal to size then nothing happens - the cursor
    *       is beyond the string and thus not referencing any character.
    * @note Postcondition: The cursor position remains unchanged. If the cursor
    *       is less than size before the method is called then the size is
    *       reduced by one. Otherwise size is unchanged.
    */
  final def delete(): Unit = {
    if (cursor < size) {
      deleteCharacter(cursor)
      size = size - 1
    }
  }

  /**
    * Performs a backspace. This is sometimes characterised as a left-delete and
    * is usually represented by the Backspace key on a keyboard. It is just the
    * composition of a [[left]] cursor movement followed by a [[delete]].
    *
    * @note If cursor == 0 then nothing happens. There is no character to the
    *       left of position zero.
    * @note Postcondition: If cursor > 0 before the method is called then it
    *       is reduced by one afterwards and size is also reduced by one.
    *       Otherwise cursor and size remain unchanged.
    */
  final def backspace(): Unit = {
    if (cursor > 0) {
      left()
      delete()
    }
  }

  /**
    * Overwrites the character value to the right of the current cursor position.
    * @param c The new character to replace the current one.
    */
  final def modify(c: Char): Unit = {
    if (cursor < size)
      modifyCharacter(cursor, _.setChar(c))
  }

  final def show(): Unit = {
    println(this.toString)
  }

  override def toString: String = {
    val mutableString: mutable.StringBuilder = new mutable.StringBuilder
    mutableString.append(s"($cursor,$size)[")
    for (i <- 0 until cursor)
      mutableString.append(text(i).getChar)
    mutableString.append('|')
    for (i <- cursor until size)
      mutableString.append(text(i).getChar)
    mutableString.append(']')
    mutableString.toString
  }
}
