package demo.editor

object MutableEditorDemo5 {

  import lib.editor.mutable._

  def main(args: Array[String]): Unit = {

    val ed: UndoEditor = new UndoEditor
    ed.show()
    /*
     * Insert the letters "abc"
     */
    ed.insert('a')
    ed.show()

    ed.insert('b')
    ed.show()

    ed.insert('c')
    ed.show()
    /*
     * Delete the last character that was inserted
     */
    ed.backspace()
    ed.show()
    /*
     * Now undo this action
     */
    ed.undo()
    ed.show()
  }
}
