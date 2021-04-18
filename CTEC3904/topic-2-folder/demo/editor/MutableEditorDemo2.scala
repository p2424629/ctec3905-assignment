package demo.editor

object MutableEditorDemo2 {

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
     * Move the cursor to the left twice
     */
    ed.left()
    ed.left()
    ed.show()
    /*
     * Modify the character at the current cursor position
     */
    ed.modify('X')
    ed.show()
    /*
     * Backspace
     */
    ed.backspace()
    ed.show()
    /*
     * Delete
     */
    ed.delete()
    ed.show()
    /*
     * Cursor right
     */
    ed.right()
    ed.show()
    /*
     * Backspace
     */
    ed.backspace()
    ed.show()
  }
}