package lib.editor.mutable

/**
  * An UndoEditor implements a stack of basic editors thus enabling a history
  * to be maintained and operations to be undone. The top of the stack is the
  * most recent state.
  *
  * @author David Smallwood
  */
class UndoEditorA extends UndoEditor {

  /**
    * Copy the current state and push it onto the stack. This adds to history.
    * This method is not part of the public API but may be overridden by any
    * subclass. The underlying character sequence is cloned to avoid structure
    * sharing. Although this is a deeper copy than [[UndoEditor]], structure
    * sharing of the underlying character elements remains.
    */
  override protected def pushCurrentState(): Unit = {
    history.insert(0, text.clone())
  }
}
