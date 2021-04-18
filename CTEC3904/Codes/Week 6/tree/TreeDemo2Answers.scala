package tree

object TreeDemo2Answers {

  /*
   * We represent an organisation of employees. Some employees act as
   * managers to other employees. This gives a tree structure.  The data
   * structure is given below.  The following points are to note:
   *
   * 1) The constructor is called Team so that any part of the tree
   *    (hierarchy) can be considered as a team or subteam. The employees
   *    on the bottom tier do not line manage anyone and for the purpose
   *    of this data structure we assume that these employees head a
   *    team of one (themselves).
   *
   * 2) It is a case class which means that it will be possible to
   *    pattern match over instances of this class.
   *
   * 3) The second parameter to the constructor (reports) is a list of
   *    the employee's direct reports. That is, this employee is their
   *    line manager.
   *
   * 4) The reports parameter has a default value (empty list). This means
   *    that an employee can be constructed with a name only:
   *    Team("Freda"), for example. In this case the compiler
   *    automatically adds an empty list for the reports parameter. This
   *    makes it easy to define employees who are not line managers - i.e.
   *    there is no need to provide an empty list each time such an employee
   *    is created.
   *
   * 5) The overridden toString method constructs a view that shows the
   *    employee and their reports using indentation to reveal the
   *    hierarchy. It makes a call to a preorder traversal which is defined
   *    below.
   */

  case class Team(leader: String, reports: List[Team] = List.empty) {
    override def toString: String = preorder(this).map(_ + "\n").mkString
  }


  /*
   * A preorder traversal that constructs a list of strings representing
   * the hierarchy beginning with the given employee. Indentation is used
   * to indicate the manager/report relationships. The function is recursive
   * and uses a tab of four spaces at each level. Note that the tab
   * parameter has a default of zero. This means that the perorder function
   * can be called initially without a tab parameter (see how this occurs
   * in the toString method above): initially the tab value will be zero.
   */
  def preorder(team: Team, tab: Int = 0): List[String] =
    (" "*tab + team.leader) :: team.reports.flatMap(preorder(_, tab+4))


  /*
   * The groupInto takes a list of values and groups those values using
   * the given (group) size parameter. This function was an exercise in
   * a previous lab sheet. It is used in this demo to simplify the
   * construction of organisation hierarchy - see how it is used in main.
   */
  def groupInto[A](size: Int, xs: List[A]): List[List[A]] = {
    val blocks = (xs.indices by size).toList
    blocks.map(k => xs.drop(k)).map(_.take(size))
  }

  /*
   * Count the number of levels in the team. A team leader with no reports
   * is a single level.
   */
  def maxLevels(team: Team): Int =
    if (team.reports.isEmpty)
      1
    else
      1 + (team.reports map maxLevels).max


  /*
   * Add a new subteam to the current team.
   */
  def addNewSubteam(team: Team, newSubTeam: Team): Team =
    Team( team.leader, newSubTeam :: team.reports)

  /*
   * Exercise TreeDemo2.1  WRITE A FUNCTION THAT RETURNS THE NUMBER OF
   * EMPLOYEES IN THE GIVEN TEAM (INCLUDING THE TEAM LEADER).
   */
  def countEmployees(team: Team): Int =
    1 + (team.reports map countEmployees).sum

  /*
   * Exercise TreeDemo2.2  WRITE A FUNCTION THAT RETURNS TRUE IF THE
   * GIVEN NAME IS IN THE TEAM HEADED BY THE GIVEN TEAM LEADER. (The
   * name may be that of the team leader themselves.) Otherwise returns
   * false.
   */
  def personIsInTeam(team: Team, person: String): Boolean =
    team.leader == person ||
      (team.reports exists(personIsInTeam(_, person)))

  /*
   * Exercise TreeDemo2.3  WRITE A FUNCTION THAT CHANGES THE NAMES OF
   * EVERYONE IN THE GIVEN TEAM (including the team leader) to upper
   * case.
   */
  def capitaliseNames(team: Team): Team =
    Team(team.leader.toUpperCase, team.reports map capitaliseNames)

  /*
   * Exercise TreeDemo2.4  WRITE A FUNCTION TO PRODUCE AN ALPHABETICAL LIST
   * OF ALL THE PEOPLE WHO REPORT (DIRECTLY OR INDIRECTLY) TO THE GIVEN
   * TEAM LEADER.
   */
  def listOfReports(team: Team): List[String] = {

    def getAllNames(subteam: Team): List[String] =
      subteam.leader :: (subteam.reports flatMap getAllNames)

    getAllNames(team).tail.sorted
  }

  /*
   * Exercise TreeDemo2.5  WRITE A FUNCTION TO RETURN THE NAME OF THE
   * DIRECT LINE MANAGER OF A PERSON WITHIN A TEAM. You may assume that
   * no name occurs more than once within any team.
   */
  def findLineManager(team: Team, person: String): Option[String] =
    if ((team.reports map (_.leader)) contains person)
      Some(team.leader)
  else
      (team.reports map (findLineManager(_, person))).find(_.isDefined).flatten

  /*
   * Exercise TreeDemo2.6  WRITE A FUNCTION TO REMOVE THE GIVEN PERSON
   * FROM THE GIVEN TEAM. Note that the team leader themselves cannot be
   * removed (since this would leave the team with no-one in charge). If
   * the removed person was a line manager then their teams are added to
   * the removed person's manager's list of reports. You may assume that
   * no names appear twice in the organisation.
   */
  def removeEmployee(team: Team, person: String): Team = {

    def removeFrom(teamMembers: List[Team]): List[Team] = {
      val (xs, ys) = teamMembers.partition(_.leader == person)
      if (xs.nonEmpty)
        ys ::: xs.head.reports
      else
        teamMembers map (e => Team(e.leader, removeFrom(e.reports)))
    }

    if (team.leader == person)
      team // cannot remove the overall team leader
    else
      Team(team.leader, removeFrom(team.reports))
  }


  def main(args: Array[String]): Unit = {

    /*
     * This example of constructing an organisation hierarchy demonstrates
     * the use of higher order functions on lists (i.e. map), first order
     * functions on lists (i.e. zip), and list comprehension syntax:
     * for ( pattern <- generator) yield expression
     * We will look at list comprehensions in more detail in a subsequent
     * session. For now, just note this example and try and follow it
     * intuitively.
     */

    /*
     * The example given here gets you started and builds an organisation.
     * Feel free to add/remove names and to adapt the hierarchy as you like.
     * Try making some of the fourth level employees managers (so creating
     * a fifth level). But you may wish to go with the given example until
     * you are confident using the data structure.
     */

    /*
     * We begin with a list of employees who do not manage anyone.
     * It is easy to write the names in a string and then split the
     * string up into a list of names. Finally, we map the Team(_)
     * constructor over the list of names to generate a list of
     * employees. Remember that the function (Team(_)) is another
     * way of writing, e.g., (name => Team(name)).  Also note that
     * the second parameter (reports) is defaulted (not mentioned
     * explicitly) and therefore will become an empty list for each of
     * these employees. This is correct becuase none has any reports.
     */
    val fourthLevel: List[Team] =
      """Ade Benito Carolyn Mitesh Frank Harry Pietro Hongji
        |Meghan Geraldine Yorick Salimah Mikel Pascale Ali
        |Vinni Esther Chetna Zebedee Jatinder Tomasz Kazim
        |Nicola Dafydd Lucy Walter Zain
      """.stripMargin.split("\\s+").toList map (Team(_))

    /*
      * Next we construct the following lists of names:
      * - the level three managers' names,
      * - the level two managers' names
     */
    val levelThreeManagers: List[String] =
      "Alan Suffiyyah Katie Muhanad Marie Dotun Rhiannon Isse Luke".split("\\s+").toList

    val levelTwoManagers: List[String] =
      "Shushma George Ada".split(" ").toList

    /*
     * Finally, we construct the organisation hierarchy.
     * This is achieved by grouping the fourthLevel into teams of three and then
     * joining each team with one of the levelThreeManagers. We achieve this by
     * using the groupInto function (see above) to create the teams and then pairing
     * each team with a level three manager using the function zip. Next, we take
     * each of these pairings in turn and use them to construct the Team instance.
     * NB: Regarging the use of "for" in the below: these are not for loops - these
     * are list comprehensions: for ... yield.
     */

    val thirdLevel =
      for ( (mgr, team) <- levelThreeManagers zip groupInto(3, fourthLevel))
        yield Team(mgr, team)

    val secondLevel =
      for ( (mgr, team) <- levelTwoManagers zip groupInto(3, thirdLevel))
        yield Team(mgr, team)

    /*
     * Finally, the overall manager of the organisation is an employee whose
     * reports are all the second level managers.
     */
    val topLevel = Team("Oprah", secondLevel)

    /*
     * The organisation can be printed immediately. The toString method will be
     * called automatically which, in turn, will perform the preorder traversal
     * to show the hierarchy on the output terminal.
     */
    println(topLevel)

    println(maxLevels(topLevel))                            // Should be 4
    println(maxLevels(topLevel.reports.head))               // Should be 3
    println(maxLevels(topLevel.reports.head.reports.last))  // Should be 2

    // Exercise TreeDemo2.1
    println(countEmployees(topLevel))                           // Should be 40
    println(countEmployees(topLevel.reports.head))              // Should be 13 (Shushma's team)
    println(countEmployees(topLevel.reports.head.reports.last)) // Should be  4 (Katie's team)
    println(countEmployees(topLevel.reports.head.reports.last.reports.head)) // Should be 1 (Pietro alone)

    // Exercise TreeDemo2.2
    println(personIsInTeam(topLevel, "Tomasz"))   // Yes
    println(personIsInTeam(topLevel, "Dotun"))    // Yes
    println(personIsInTeam(topLevel, "Shushma"))  // Yes
    println(personIsInTeam(topLevel, "Oprah"))    // Yes - the CEO is in their own team
    println(personIsInTeam(topLevel, "Maria"))    // No, Maria is not part of the organisation
    println(personIsInTeam(topLevel.reports.last, "Chetna"))  // No, Chetna is not in Ada's team
    println(personIsInTeam(topLevel.reports.head.reports.last, "Hongji"))  // Yes, Hongji is in Shushma's team

    // Exercise TreeDemo2.3
    println(capitaliseNames(topLevel))

    // Exercise TreeDemo2.4
    println(listOfReports(topLevel))                        // Everyone in the organisation
    println(listOfReports(topLevel.reports.last))           // These are Ada's reports

    // Exercise TreeDemo2.5
    println(findLineManager(topLevel, "Nicola"))    // Should be Some(Isse)
    println(findLineManager(topLevel, "Luke"))      // Should be Some(Ada)
    println(findLineManager(topLevel, "Shushma"))   // Should be Some(Oprah)
    println(findLineManager(topLevel, "Oprah"))     // Should be None - the CEO has no manager
    println(findLineManager(topLevel, "No-one"))    // Should be None - name not found

    // Exercise TreeDemo2.6
    println(removeEmployee(topLevel, "Pietro"))
    println(removeEmployee(topLevel, "Katie"))
    println(removeEmployee(topLevel, "Ada"))
    println(removeEmployee(topLevel, "Oprah")) // Should not remove the head of the organisation
  }
}
