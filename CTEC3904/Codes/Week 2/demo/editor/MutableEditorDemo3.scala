package demo.editor

object MutableEditorDemo3 {

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
     * Now undo these actions
     */
    ed.undo()
    ed.show()
    ed.undo()
    ed.show()
    ed.undo()
    ed.show()
  }
}
