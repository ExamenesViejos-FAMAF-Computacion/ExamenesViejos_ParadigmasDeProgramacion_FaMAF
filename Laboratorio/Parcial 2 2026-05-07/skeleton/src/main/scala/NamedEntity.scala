/**
 * Clase base abstracta para todas las entidades nombradas.
 *
 * Una entidad nombrada es una expresión del texto que refiere a un objeto
 * del mundo real (persona, lugar, organización, tecnología, etc.).
 *
 * @param text el texto tal como aparece en el corpus
 */
abstract class NamedEntity(val text: String) {

  /**
   * Retorna el tipo de la entidad como String.
   */
  def entityType: String

  /**
   * Retorna una línea de descripción de la entidad para el informe.
   */
  def describe: String = s"[$entityType] $text"
}

class Person(text: String) extends NamedEntity(text) {
  def entityType: String = "Person"
}

class Organization(text: String) extends NamedEntity(text) {
  def entityType: String = "Organization"
}

class University(text: String) extends Organization(text) {
  override def entityType: String = "University"
}

class Place(text: String) extends NamedEntity(text) {
  def entityType: String = "Place"
}

class Technology(text: String) extends NamedEntity(text) {
  def entityType: String = "Technology"
}

class ProgrammingLanguage(text: String) extends Technology(text) {
  override def entityType: String = "ProgrammingLanguage"
}
