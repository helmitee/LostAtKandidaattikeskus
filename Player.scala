
import scala.collection.mutable.Map
import o1.*
import java.security.KeyStore.TrustedCertificateEntry


class Player(startingArea: Area, time: Time):

  private var currentLocation = startingArea        // gatherer: changes in relation to the previous location
  private var quitCommandGiven = false              // one-way flag
  private var eatCommandGiven = false
  private val timeLimit = 5*60
  private val backpack = Map[String, Item]()


  def hasQuit = this.quitCommandGiven || eatCommandGiven


  def location = this.currentLocation


  def showThis(name: String): String =
    if backpack.contains(name) then
      if name == "compass" then
        Pic("compass.png").show()
      else
        Pic("map.png").scaleBy(0.7).show()
      s"wow nice $name!"
    else s"You don't have any $name!"


  def use() =
    if backpack.contains("access token") then
      this.currentLocation = this.location.neighbor("up").getOrElse(this.currentLocation) //this works because only the top floor is locked
      "You used the access token and it worked!"
    else "This door is still locked. Where is your access token?"


  def eat() =
    if this.currentLocation.name == "Alvari" then
      eatCommandGiven = true
      "mmm...Time flies while eating..."
    else
      "No food nearby."


  def go(direction: String) =
    val destination = this.location.neighbor(direction)
    if destination.get.name == "U-wing, 3rd Floor" || destination.get.name == "M-wing, 3rd Floor" || destination.get.name == "Y-wing, 3rd Floor" then
      "This door is locked."
    else
      this.currentLocation = destination.getOrElse(this.currentLocation)
      if destination.isDefined then "You go " + direction + "." else "You can't go " + direction + "."


  def get(itemName: String) =
    if location.contains(itemName) then
      backpack += itemName -> this.location.removeItem(itemName).get
      "You pick up the " + itemName
    else "There is no " + itemName + " here to pick up."


  def drop(itemName: String) =
    if backpack.contains(itemName) then
      location.addItem(backpack(itemName))
      backpack -= itemName
      "You drop the " + itemName
    else "You don't have that!"


  def help() =
    "\n" + " -" * 12 + "\n\n" +
    "All the possible commands:\n" +
      "'go' + possible exit: Move between different rooms.\n" +
      "'get' + item name: Pick up things you can see.\n" +
      "'show'  + item name: Examine a thing you have.\n" +
      "'talk' + person name: Talk to a person you see.\n" +
      "'use' + item name: Use the item for something useful.\n" +
      "'time': Tells the remaining time.\n" +
      "'inventory': a list of objects you are holding.\n" +
      "'quit': Quit game.\n\n" + " -" * 12


  def has(itemName: String) = backpack.contains(itemName)


  def inventory =
    if backpack.isEmpty then "You are empty-handed."
    else "You are carrying:\n" + backpack.keys.mkString("\n")


  def talkTo(someone: String) =
    if location.contains(someone) then
      s"You: Hello $someone ! I think im lost. Do you have any idea how could I get to my excercise session??" + "\nA Friend: " + this.location.speak(someone)
    else "Why are you yelling?"


  def clock() =
    val mins: Int = (timeLimit - time.clocktime) / 60
    val secs: Int = (timeLimit - time.clocktime) % 60
    s"You have $mins minutes and $secs seconds left. Better hurry!"



  def quit() =
    this.quitCommandGiven = true
    ""


  override def toString = "Now at: " + this.location.name


end Player

