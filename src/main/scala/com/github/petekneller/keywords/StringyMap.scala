package com.github.petekneller.keywords


object StringyMap {

  def apply(elems: (String, Any)*): Keyword.StringyMap = Map[String, Any](elems:_*)

}
