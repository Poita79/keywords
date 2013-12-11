package pk

trait Keyword[V] extends (Map[String, Any] => V) { self =>
  val key: String
  val aClass: Class[V]

  trait TestFailure

  private def noElementError(candidate: Map[String, Any]): RuntimeException = {
    new NoSuchElementException(s"Keyword $key does not exist in ${candidate.toString}")
  }

  private def wrongTypeError(candidate: Map[String, Any], value: Any): RuntimeException = {
    new ClassCastException(s"Keyword '$key' of type ${aClass.getCanonicalName} " +
      s"cannot be used to access value of type ${value.getClass.getCanonicalName} in ${candidate.toString}")
  }

  private def test(candidate: Map[String, Any]): Either[RuntimeException,V] = {
    candidate.get(key).toRight(noElementError(candidate)).right flatMap { v =>
      if (aClass.isAssignableFrom(v.getClass)) Right(v.asInstanceOf[V])
      else Left(wrongTypeError(candidate, v))
    }
  }

  def get(candidate: Map[String, Any]): Option[V] = test(candidate).right.toOption

  def apply(candidate: Map[String, Any]): V = test(candidate).fold(ex => throw ex, identity)

}

object Keyword {

  def apply[V: Manifest](name: String) = new Keyword[V] {
    val key = name
    val aClass = implicitly[Manifest[V]].runtimeClass.asInstanceOf[Class[V]]
  }
}
