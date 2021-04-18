package demo.editor

object ImmutableEditorDemo1 {

  import lib.editor.immutable._

  def main(args: Array[String]): Unit = {

    val ed: BasicEditor = new BasicEditor
    ed.show
    /*
     * Insert the letters "abc"
     */
    ed.
      insert('a').
      show.
      insert('b').
      show.
      insert('c').
      show
    ed.show

  }
}
