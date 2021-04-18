package demo.editor

object MutableEditorDemo8 {

  import lib.editor.mutable._

  def main(args: Array[String]): Unit = {

    val ed: UndoEditorB = new UndoEditorB
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
     * Move to the left and replace the b with an X
     */
    ed.left()
    ed.left()
    ed.show()

    ed.modify('X')
    ed.show()

    ed.insert('Y')
    ed.show()
    /*
     * Now undo the last two actions so we should see "abc" again
     */
    ed.undo()
    ed.show()

    ed.undo()
    ed.show()
  }
}
