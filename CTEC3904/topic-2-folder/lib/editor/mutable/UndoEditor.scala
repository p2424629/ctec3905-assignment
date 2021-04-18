package lib.editor.mutable

/**
  * An UndoEditor implements a stack of basic editors thus enabling a history
  * to be maintained and operations to be undone. The top of the stack is the
  * most recent state.
  *
  * @author David Smallwood
  */
class UndoEditor extends BasicEditor {

  import collection.mutable.ListBuffer

  /*
   * The history is maintained using a list of buffers. Each buffer stores the
   * the sequence of characters. Each buffer in history represents a snapshot of the
   * editor state saved before an operation to facilitate an undo. The edits can
   * be undone all the way back to creation. NB - the cursor position is not saved
   * as it is inefficient and unnecessary to recall every cursor movement.
   */
  protected val history = new collection.mutable.ListBuffer[ListBuffer[CharElement]]

  /**
    * Copy the current state and push it onto the stack. This adds to history.
    * This method is not part of the public API but may be overridden by any
    * subclass. The character sequence is not actually copied - only its
    * reference is duplicated. This is a shallow copy.
    */
  protected def pushCurrentState(): Unit = {
    history.insert(0, text)
  }

  /**
    * Restores the previous state by popping the stack. This undoes the most
    * recent edit operation.
    *
    * @note Cannot undo beyond the initial editor state.
    */
  def undo(): Unit = {
    if (history.nonEmpty) {
      size = history.head.size
      cursor = scala.math.min(size, cursor)
      text = history.head
      history.remove(0)
    }
  }

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
  override protected def insertCharacter(position: Int, ch: CharElement): Unit = {
    pushCurrentState()
    super.insertCharacter(position, ch)
  }

  /**
    * Deletes the [[CharElement]] at the current position. This is achieved
    * by moving all characters to the right of the cursor position down one
    * place.
    *
    * @note Precondition: 0 <= position < size
    * @param position Specifies the position to delete in the sequence.
    */
  override protected def deleteCharacter(position: Int): Unit = {
    pushCurrentState()
    super.deleteCharacter(position)
  }

  /**
    * Applies a method to the [[CharElement]] at the current position.
    *
    * @note Precondition: 0 <= position < size
    * @param position The position to modify within the sequence.
    * @param f        The method to apply to the current [[CharElement]]
    */
  override protected def modifyCharacter(position: Int, f: CharElement => Unit): Unit = {
    pushCurrentState()
    super.modifyCharacter(position, f)
  }

  override def toString: String = {
    f"${super.toString}%-20s" +
      history.toString.replaceAll("ListBuffer", "")
  }
}
