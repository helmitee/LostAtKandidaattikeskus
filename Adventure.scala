
import java.util.{Timer, TimerTask}

class Time():
  var clocktime = 0 //clock in seconds
  var timer = Timer()
  val task = new TimerTask(){
    def run() =
      clocktime += 1}

  def startTimer() =
    timer.schedule(task, 0, 1000) //every second clock +1

class Adventure:

  // the name of the game
  val title = "Lost At Kandidaattikeskus"

  private val main1 = Area("Main Hall 1", "")
  private val main2 = Area("Main Hall 2", "")
  private val alvari = Area("Alvari", "This student restaurant is serving meatballs today. Smells delicious...\nType 'eat' is you are hungry.")
  private val y1 = Area("Y-wing, 1st Floor", "")
  private val u1 = Area("U-wing, 1st Floor", "")
  private val m1 = Area("M-wing, 1st Floor", "")
  private val a1 = Area("A-wing, 1st Floor", "Here you can see a beautiful exhibition of projects of students.")
  private val lobby1 = Area("Lobby 1", "")
  private val lobby2 = Area("Lobby 2", "")
  private val y2 = Area("Y-wing, 2nd Floor", "")
  private val u2 = Area("U-wing, 2nd Floor", "")
  private val m2 = Area("M-wing, 2nd Floor", "")
  private val a2 = Area("A-wing, 2nd Floor", "")
  private val u3 = Area("U-wing, 3rd Floor", "")
  private val y3 = Area("Y-wing, 3rd Floor", "")
  private val m3 = Area("M-wing, 3rd Floor", "")

  private val destination = u3

  main1.setNeighbors(Vector("east" -> main2, "north" -> y1, "south" -> alvari, "up" -> lobby1))
  main2.setNeighbors(Vector("west" -> main1, "north" -> m1, "up" -> lobby2))
  alvari.setNeighbors(Vector("north" -> main1))
  y1.setNeighbors(Vector("east" -> m1, "south" -> main1, "north" -> u1, "up" -> y2))
  u1.setNeighbors(Vector("south" -> y1, "up" -> u2))
  m1.setNeighbors(Vector("west" -> y1, "east" -> a1, "south" -> main2, "up" -> m2))
  a1.setNeighbors(Vector("west" -> m1, "up" -> a2))
  lobby1.setNeighbors(Vector("north" -> y2, "east" -> lobby2, "down" -> main1))
  lobby2.setNeighbors(Vector("west" -> lobby1, "north" -> m2, "down" -> main2))
  y2.setNeighbors(Vector("south" -> lobby1, "north" -> u2, "down" -> y1, "up" -> y3))
  u2.setNeighbors(Vector("south" -> y2, "down" -> u1, "up" -> u3))
  m2.setNeighbors(Vector("south" -> lobby2, "down" -> m1, "up" -> m3))
  a2.setNeighbors(Vector("down" -> a1))
  u3.setNeighbors(Vector("down" -> u2))
  y3.setNeighbors(Vector("down" -> y2))
  m3.setNeighbors(Vector("down" -> m2))

  // Adding items to the game
  m2.addItem(Item("access token", "It looks like an old HSL card but it's much more useful.\nMaybe you should pick it up..."))
  main2.addItem(Item("compass", "This might be helpful"))
  u1.addPerson(Friend("friend", "I think that we are at the right wing!"))
  a1.addPerson(Friend("stranger", "You should go to the U-wing!"))
  a2.addPerson(Friend("someone", "You should go to the third floor!"))
  y1.addItem(Item("map", ""))


  val timeLimit = 5*60 // 5 mins
  val time = Time()
  val player = Player(main1, time)


  def isComplete = this.player.location == this.destination


  def isOver = this.isComplete || this.player.hasQuit || time.clocktime == this.timeLimit


  def welcomeMessage = "\n" + title + "\n\nIt's your first week at school.\nYou are trying to find your way to an excercise session.\n\nAll the information you have is that the session is held in this massive labyrinth-like building called Undergraduate Center.\nYou will start at the Main Hall and will have 5 minutes left until the session starts.\nType 'help' to get information about the commands."


  def goodbyeMessage =
    if this.isComplete then
      "You found the correct classroom! Well done!"
    else if time.clocktime == this.timeLimit then
      "Oh no!! You are late, the excercise session just started. No points for you.\nGame over!"
    else  // game over due to player quitting
      "Qame over!"


  def playTurn(command: String) =
    val action = Action(command)
    val outcomeReport = action.execute(this.player)
    outcomeReport.getOrElse(s"Unknown command: \"$command\".")


end Adventure

