import scala.swing._

class UI(val board: Board) extends MainFrame {

  private def restrictHeight(s: Component) {
    s.maximumSize = new Dimension(Short.MaxValue, s.preferredSize.height)
  }

  title = "Snake"

  val canvas = new Canvas(board)
  val newGameButton = Button("New Game") { newGame() }
  val quitButton = Button("Quit") { sys.exit(0) }
  val buttonLine = new BoxPanel(Orientation.Horizontal) {
    contents += newGameButton
    contents += Swing.HGlue
    contents += quitButton
  }

  // make sure that resizing only changes the TicTacToeDisplay
  restrictHeight(buttonLine)

  contents = new BoxPanel(Orientation.Vertical) {
    contents += canvas
    contents += Swing.VStrut(10)
    contents += buttonLine
    border = Swing.EmptyBorder(10, 10, 10, 10)
  }



  val gtimer = Timer(1000/60) { tick() }
  val timer = Timer(200) { gameplay() }

  def newGame() {
    timer.stop()
    board.newGame()
    timer.start()
  }

  def tick() {
    // this method is called every 200 milliseconds
    canvas.repaint()
  }

  def gameplay(): Unit = {
    if(
      board.snake.points.head.x < 0
        || board.snake.points.head.x > 40
        || board.snake.points.head.y < 0
        || board.snake.points.head.y > 40
        || board.snake.points.tail.contains(board.snake.points.head)
    ) newGame()
    board.step()
  }
}

object Timer {
  def apply(interval: Int, repeats: Boolean = true)(op: => Unit) = {
    val timeOut = new javax.swing.AbstractAction() {
      def actionPerformed(e : java.awt.event.ActionEvent) = op
    }
    val t = new javax.swing.Timer(interval, timeOut)
    t.setRepeats(repeats)
    t.start()
    t
  }
}

object Game {
  def main(args: Array[String]): Unit = {
    val board = new Board
    val ui = new UI(board)
    ui.centerOnScreen()
    ui.visible = true

    println("End of main function")

  }
}
