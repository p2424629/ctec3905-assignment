package lib.editor.immutable

/**
  * An UndoEditor implements a stack of basic editors thus enabling a history
  * to be maintained and operations to be undone. The top of the stack is the
  * most recent state.
  *
  * @author David Smallwood
  */
class UndoEditor(history: List[BasicEditor] = List(new BasicEditor)) {

  private def saveThen(op: BasicEditor => BasicEditor): UndoEditor =
    new UndoEditor(op(history.head) :: history)

  private def justApply(op: BasicEditor => BasicEditor): UndoEditor =
    new UndoEditor(op(history.head) :: history.tail)

  def undo: UndoEditor =
    if (history.length > 1)
      new UndoEditor(history.tail)
    else
      this

  def left: UndoEditor = this.justApply(_.left)

  def right: UndoEditor = this.justApply(_.right)

  def insert(c: Char): UndoEditor = this.saveThen(_.insert(c))

  def delete: UndoEditor = this.saveThen(_.delete)

  def backspace: UndoEditor = this.saveThen(_.backspace)

  def modify(c: Char): UndoEditor = this.saveThen(_.modify(c))

  override def toString: String = history.toString

  def show: UndoEditor = {
    println(this.toString)
    this
  }
}
