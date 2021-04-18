package demo.editor

object MutableEditorDemo4 {

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

    ed.left()
    ed.left()
    ed.left()
    ed.show()
    /*
     * Replace the first character with an X
     */
    ed.modify('X')
    ed.show()
    /*
     * Now undo this action
     */
    ed.undo()
    ed.show()
  }
}
