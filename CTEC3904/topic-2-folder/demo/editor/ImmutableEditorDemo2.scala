package demo.editor

object ImmutableEditorDemo2 {

  import lib.editor.immutable._

  def main(args: Array[String]): Unit = {

    val ed: UndoEditor = new UndoEditor

    ed.
      insert('a').
      insert('b').
      insert('c').
      show.
      left.
      left.
      show.
      modify('X').
      show.
      insert('Y').
      show.
      undo.
      show.
      undo.
      show

    /*
     * What happens if we show ed again?
     */
    ed.show
    /*
     * In fact, ed hasn't changed since it was declared!
     */
  }
}
