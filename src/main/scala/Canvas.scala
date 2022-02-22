import scala.swing._
import java.awt.{Color,Graphics2D,BasicStroke}
import scala.swing.event._

case class TicTacToeEvent(x: Int, y: Int) extends Event

class Canvas(val board: Board) extends Component {
  preferredSize = new Dimension(320, 320)
  focusable = true
  listenTo(keys)
  reactions += {
    case KeyPressed(_, c, _, _) =>
      c match {
        case Key.Up if (board.direction != Point(0, 1))=> board.direction = Point(0, -1)
        case Key.Left if (board.direction != Point(1, 0)) => board.direction = Point(-1, 0)
        case Key.Down if (board.direction != Point(0, -1)) => board.direction = Point(0, 1)
        case Key.Right if (board.direction != Point(-1, 0)) => board.direction = Point(1, 0)
        case _ => ()
      }
  }

  override def paintComponent(g : Graphics2D) {
    val d = size

    g.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON)
    g.setStroke(new BasicStroke(3f))
    g.setColor(Color.WHITE)
    g.fillRect(0,0, d.width, d.height)

    val squareSide = d.height min d.width
    val wid = squareSide / 40
    g.setColor(Color.BLACK)
    g.drawRect(0,0, squareSide, squareSide)
    board.objects().foreach { o =>
      g.setColor(o.color)
      o.points.foreach(p => g.drawRect(p.x*wid, p.y*wid, wid, wid))
    }
  }
}