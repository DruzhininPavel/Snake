import java.awt.Color
import scala.collection.mutable.ListBuffer
import scala.util.Random

case class Point(x: Int, y: Int)
case class GameObject(var points: ListBuffer[Point], color: Color)

class Board {
  val apple = GameObject(ListBuffer(Point(20, 20)), Color.RED)
  val snake = GameObject(ListBuffer(Point(20, 37),Point(20, 38),Point(20, 39)), new Color(0, 160, 0))
  var direction = Point(0, -1)

  def objects(): List[GameObject] = List(apple, snake)

  def newGame() = {
    apple.points = ListBuffer(Point(20, 20))
    snake.points = ListBuffer(Point(20, 37),Point(20, 38),Point(20, 39))
    direction = Point(0, -1)
  }

  def step() {
    if(snake.points.head == apple.points.head) {
      apple.points = ListBuffer(Point(Random.between(1, 39), Random.between(1, 39)))
      snake.points = Point(snake.points.head.x + direction.x, snake.points.head.y + direction.y) +: snake.points
    } else {
      snake.points = Point(snake.points.head.x + direction.x, snake.points.head.y + direction.y) +: snake.points.dropRight(1)
    }
  }
}