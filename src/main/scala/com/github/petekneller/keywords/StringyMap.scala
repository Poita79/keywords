package com.github.petekneller.keywords


object StringyMap {

  trait StringyKey[-K] {
    def stringKeyOf(k: K): String
  }

  def apply[K: StringyKey](elems: (K, Any)*): Keyword.StringyMap = {
    def toStringyPair(p: (K, Any)): (String, Any) = p match {
      case (k, v) => implicitly[StringyKey[K]].stringKeyOf(k) -> v
    }

    Map[String, Any](elems.map(toStringyPair): _*)
  }

  object implicits {
    implicit val stringyString = new StringyKey[String] { def stringKeyOf(k: String) = k }
    implicit val stringyKeyword = new StringyKey[Keyword[Any]] { def stringKeyOf(k: Keyword[Any]) = k.key }
  }

}


