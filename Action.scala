class Action(input: String):

  private val commandText = input.trim.toLowerCase
  private val verb        = commandText.takeWhile( _ != ' ' )
  private val modifiers   = commandText.drop(verb.length).trim


  def execute(actor: Player) = this.verb match
    case "go"        => Some(actor.go(this.modifiers))
    case "quit"      => Some(actor.quit())
    case "get"       => Some(actor.get(this.modifiers))
    case "inventory" => Some(actor.inventory)
    case "drop"      => Some(actor.drop(this.modifiers))
    case "help"      => Some(actor.help())
    case "show"      => Some(actor.showThis(this.modifiers))
    case "talk"      => Some(actor.talkTo(this.modifiers))
    case "use"       => Some(actor.use())
    case "time"      => Some(actor.clock())
    case "eat"       => Some(actor.eat())
    case other       => None


  override def toString = s"$verb (modifiers: $modifiers)"


end Action

