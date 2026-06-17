import scala.io.Source
import org.json4s._
import org.json4s.jackson.JsonMethods._

/**
 * Responsable de todas las operaciones de entrada/salida:
 * descarga de feeds, lectura de archivos locales y extracción de texto.
 *
 * Este objeto no necesita ser modificado para completar los ejercicios.
 */
object FileIO {

  implicit val formats: DefaultFormats.type = DefaultFormats

  /** Retorna las URLs de los subreddits a analizar. */
  def readSubscriptions(): List[String] = {
    List(
      "http://localhost:8123/r/scala/.json",
      "http://localhost:8123/r/programming/.json"
    )
  }

  /** Descarga el contenido de una URL y lo retorna como String. */
  def downloadFeed(url: String): String = {
    val source = Source.fromURL(url)
    val content = source.mkString
    source.close()
    content
  }

  /**
   * Extrae los títulos de posts del JSON de Reddit.
   *
   * @param json respuesta JSON de la API de Reddit
   * @return lista de títulos de posts
   */
  def extractPostTitles(json: String): List[String] = {
    val parsed = parse(json)
    (parsed \\ "title").children.collect {
      case JString(title) => title
    }
  }

  /**
   * Lee un archivo de texto línea por línea.
   * Ignora líneas vacías y líneas que comienzan con '#' (comentarios).
   *
   * @param filePath ruta al archivo
   * @return lista de líneas no vacías
   */
  def readLines(filePath: String): List[String] = {
    val source = Source.fromFile(filePath)
    val lines = source.getLines()
      .filter(_.nonEmpty)
      .filterNot(_.startsWith("#"))
      .toList
    source.close()
    lines
  }
}
