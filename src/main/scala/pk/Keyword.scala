package pk

trait Keyword[V] extends (Map[String, Any] => V) { self =>
  val key: String
  val aClass: Class[V]

  trait TestFailure

  def get(candidate: Map[String, Any]): Option[V] = extractValue(candidate).right.toOption

  def apply(candidate: Map[String, Any]): V = extractValue(candidate).fold(ex => throw ex, identity)


  private def extractValue(candidate: Map[String, Any]): Either[RuntimeException,V] = {

    def cast(value: Any): Either[RuntimeException, V] = {
      if (equivalent(aClass, value.getClass))
        Right(value.asInstanceOf[V])
      else
        Left(wrongTypeError(value))
    }

    def equivalent(to: Class[_], from: Class[_]): Boolean = {
      Keyword.equivalentTypes.contains(from -> to) || to.isAssignableFrom(from)
    }

    def noElementError: RuntimeException = {
      new NoSuchElementException(s"Keyword $key does not exist in ${candidate.toString}")
    }

    def wrongTypeError(value: Any): RuntimeException = {
      new ClassCastException(s"Keyword '$key' of type ${aClass.getCanonicalName} " +
        s"cannot be used to access value of type ${value.getClass.getCanonicalName} in ${candidate.toString}")
    }

    for {
      rawValue <- candidate.get(key).toRight(noElementError).right
      castValue <- cast(rawValue).right
    } yield castValue
  }
}

object Keyword {

  def apply[V: Manifest](name: String) = new Keyword[V] {
    val key = name
    val aClass = implicitly[Manifest[V]].runtimeClass.asInstanceOf[Class[V]]
  }

  private val equivalentTypes = Seq(
    classOf[java.lang.Byte] -> classOf[scala.Byte],
    classOf[java.lang.Character] -> classOf[scala.Char],
    classOf[java.lang.Boolean] -> classOf[scala.Boolean],
    classOf[java.lang.Short] -> classOf[scala.Short],
    classOf[java.lang.Integer] -> classOf[scala.Int],
    classOf[java.lang.Long] -> classOf[scala.Long],
    classOf[java.lang.Float] -> classOf[scala.Float],
    classOf[java.lang.Double] -> classOf[scala.Double]
  )
}
