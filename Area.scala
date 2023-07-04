
import scala.collection.mutable.Map


class Area(var name: String, var description: String):

  private val neighbors = Map[String, Area]()
  private val items = Map[String, Item]()
  private val people = Map[String, Friend]()


  def neighbor(direction: String) = this.neighbors.get(direction)


  def setNeighbor(direction: String, neighbor: Area) =
    this.neighbors += direction -> neighbor


  def setNeighbors(exits: Vector[(String, Area)]) =
    this.neighbors ++= exits


  def fullDescription =
    val exitList = "\n\nExits available: " + this.neighbors.keys.mkString(" ")
    if items.nonEmpty || people.nonEmpty then
      val itemList = "\nYou see here: " + items.keys.mkString(" ") + people.keys.mkString(" ")
      this.description + itemList + exitList
    else
      this.description + exitList


  def speak(someone: String) =  this.people(someone).line


  def addItem(item: Item) = this.items += item.name -> item


  def addPerson(friend: Friend) = this.people += friend.name -> friend


  def contains(name: String) = items.contains(name) || people.contains(name)


  def removeItem(itemName: String) =
    if items.contains(itemName) then
      val removed = items(itemName)
      items -= itemName
      Some(removed)
    else None


  override def toString = this.name + ": " + this.description.replaceAll("\n", " ").take(150)


end Area

