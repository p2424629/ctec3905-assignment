package demo.editor

object ImmutableEditorDemo3 {

  import lib.editor.immutable._

  def main(args: Array[String]): Unit = {

    val ed0: BasicEditor = new BasicEditor

    val ed1 = ed0.insert('a')
    val ed2 = ed1.insert('b')
    val ed3 = ed2.insert('c')
    /*
     * Each of these is distinct
     */
    ed0.show
    ed1.show
    ed2.show
    ed3.show
    /*
     * Thus we have named the intermediate states. This makes them
     * persistent. Each one is always available and is immutable.
     * The following are based on ed2 but they each create a new
     * editor based on applying some operation to the one and only,
     * immutable, ed2.
     */
    ed2.insert('Z').show
    ed2.backspace.show
    /*
     * You see, ed2 is the same as ever!
     */
    ed2.show
  }
}
