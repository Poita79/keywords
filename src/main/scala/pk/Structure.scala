package pk

case class Structure(key: String, keywords: Keyword[_]*) extends Keyword[Map[String, Any]] {
  val clazz = classOf[Map[String, Any]]

  override def explainFailure(candidate: Map[String, Any]): String = {
    keywords.map{_.explainFailure(candidate)}.filter(!_.isEmpty).mkString("; ")
  }

  def conforms(candidate: Map[String, Any]): Boolean = keywords forall {_.get(candidate).isDefined}

  def cast(candidate: Map[String, Any]): Option[Map[String, Any]] = if (conforms(candidate)) Some(candidate) else None
}
