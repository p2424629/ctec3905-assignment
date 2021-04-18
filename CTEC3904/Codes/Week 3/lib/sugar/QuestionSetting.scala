package lib.sugar

object QuestionSetting {

  /* The itShould method is just a syntactic device we invented to make it easier
   * to describe the functions below. By using it we provide a default definition
   * for each of the uncompleted functions which type-checks and compiles. However,
   * at run-time an exception is thrown if you try to use an unimplemented
   * function.
   */
  def itShould[A](s: String): A = throw new Exception("Not implemented: " + s)

}
