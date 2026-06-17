import org.json4s._
import org.json4s.jackson.JsonMethods._
import scala.io.Source


object Main {
  // Define `Subscription` as a simple tuple type alias
  type Subscription = (String, String)

    // Pure function to read subscriptions from a JSON file
  def readSubscriptions(path: String): List[Subscription] = {
    val source = Source.fromFile(path)
    val jsonString = source.mkString
    implicit val formats: Formats = DefaultFormats

    val json = parse(jsonString)
    val subscriptions = json.extract[List[Map[String, String]]].map { subscriptionMap =>
      (subscriptionMap("name"), subscriptionMap("url"))
    }
    subscriptions
  }

  def readPosts(url: String): List[String] = {
    val source = Source.fromURL(url)
    val jsonContent = source.mkString
    implicit val formats: Formats = DefaultFormats

    val json = parse(jsonContent)
    val children = (json \ "data" \ "children").extract[List[JValue]]

    children.map { child =>
      val data = child \ "data"
      val title = (data \ "title").extract[String]
      title
    }
  }

  // FUNCIONES AUXILIARES PARA IMPRIMIR UN POST Y UN SUBSCRIPTION
  // PUEDEN SER MODIFICADAS PARA AGREGAR OPTION Y COMPLETAR EL EJERCICIO 3
  // def printPost(post: Post): Unit = {
  //   val content = post._2
  //   val truncatedContent = if (content.length > 80) content.take(80) else content
  //   println(s"${post._1} by **${post._3}**")
  //   println(truncatedContent)
  //   println("-----------------------")
  // }

  // def printSubscription(allPosts: (String, List[Post])): Unit = {
  //   val url = posts._1
  //   println(s"Posts from: $url")
  //   posts._2.map(printPost)
  // }

  // Main function to run
  def main(args: Array[String]): Unit = {
    val header = s"Reddit Post Parser\n${"=" * 40}"

    println("=======================")
    println("EJ1: LEER SUSCRIPCIONES")
    val subscriptions: List[Subscription] = readSubscriptions("subscriptions.json")

    // Print subscriptions read - We can use imperative for I/O
    for (subscription <- subscriptions) {
      println(subscription)
    }

    println("=======================")
    println("")
    println("=======================")
    println("EJ2: DESCARGAR POSTS")

    // Descargar y parsear los posts
    var allPosts: List[String] = List.empty
    for (subscription <- subscriptions) {
      var postTitles = readPosts(subscription._2)
      allPosts = allPosts ++ postTitles
    }

    println("=======================")
    println("")
    println("=======================")
    println("EJ3: IMPRIMIR POSTS Y CONTEO DE PALABRAS CENSURADAS")

    // Print final results
    //allPosts.map(printSubscription)
    println("=======================")
  }
}
