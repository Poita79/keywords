package com.github.petekneller.keywords

import Keyword.StringyMap

case class Structure(key: String, keywords: Keyword[_]*) extends Keyword[StringyMap] {
  val clazz = classOf[StringyMap]

  override def explainFailure(candidate: StringyMap): String = {
    keywords.map{_.explainFailure(candidate)}.filter(!_.isEmpty).mkString("; ")
  }

  def conforms(candidate: StringyMap): Boolean = keywords forall {_.get(candidate).isDefined}

  def cast(candidate: StringyMap): Option[StringyMap] = if (conforms(candidate)) Some(candidate) else None
}

// TODO implicit conversion from StringyMap to a class that has the Structure API?
