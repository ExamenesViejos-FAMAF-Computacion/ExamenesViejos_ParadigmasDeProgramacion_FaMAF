import com.sun.net.httpserver.{HttpExchange, HttpHandler, HttpServer}
import java.net.InetSocketAddress
import java.nio.file.{Files, Paths}
import scala.util.{Failure, Success, Try, Using}

object MockRedditApi {
  class JsonHandler(filePath: String) extends HttpHandler {
    override def handle(exchange: HttpExchange): Unit = {
      val fileAttempt = Try(Files.readAllBytes(Paths.get(filePath)))
      val (statusCode, payload) = fileAttempt match {
        case Success(bytes) => 
          (200, bytes)
        case Failure(_) => 
          (404, s"""{"error": "404", "message": "Subreddit not found"}""".getBytes)
      }

      exchange.getResponseHeaders.set("Content-Type", "application/json")
      exchange.sendResponseHeaders(statusCode, payload.length.toLong)

      Using(exchange.getResponseBody) { os =>
        os.write(payload)
      }
    }
  }

  def main(args: Array[String]): Unit = {
    val port = 8123
    val server = HttpServer.create(new InetSocketAddress(port), 0)

    val subredditsDir = Paths.get("subreddits")
    Using(Files.list(subredditsDir)) { stream =>
      stream
        .filter(path => path.getFileName.toString.endsWith(".json"))
        .forEach { path =>
          val subredditName = path.getFileName.toString.replace(".json", "")
          server.createContext(s"/r/$subredditName/.json", new JsonHandler(path.toString))
        }
    }

    server.setExecutor(null)
    server.start()

    println(s"Fake Reddit API running on http://localhost:$port")
    println("Press Ctrl+C (or stop the process) to shut down.")
    Try(scala.io.StdIn.readLine()) match {
      case Failure(_: InterruptedException) =>
        println("\nInterrupt received. Initiating graceful shutdown...")
      case Failure(exception) =>
        println(s"\nUnexpected input error: ${exception.getMessage}")
      case Success(_) =>
    }
    println("Stopping HttpServer...")
    server.stop(0) 
    println("Server successfully stopped.")
  }
}