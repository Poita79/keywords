package pk

import org.scalatest.{Matchers, FunSuite}

class KeywordTest extends FunSuite with Matchers {

  val name = Keyword[String]("name")
  val department = Keyword[Map[String, Any]]("department")

  test("apply and get under successful inputs") {

    val joe: Map[String, Any] = Map(
      "name" -> "Joe Bloggs",
      "age" -> 23
    )

    name(joe) should be("Joe Bloggs")
    name.get(joe) should be(Some("Joe Bloggs"))
  }


  test("apply and get when key not present") {

    val joe: Map[String, Any] = Map(
      "age" -> 23
    )

    name.get(joe) should be(None)
    val ex = the [NoSuchElementException] thrownBy {
     name(joe)
    }
    ex.getMessage should include (joe.toString)
  }


  test("apply and get when key is of incorrect type") {

    val joe: Map[String, Any] = Map(
      "name" -> 123,
      "age" -> 23
    )

    name.get(joe) should be(None)
    val ex = intercept[ClassCastException] {
      name(joe)
    }
    ex.getMessage should (include ("String") and include("Int") )
  }


  test("example of function composition") {

    val hr: Map[String, Any] = Map(
      "name" -> "HR",
      "costCentre" -> 1
    )

    val joe: Map[String, Any] = Map(
      "department" -> hr
    )

    department(joe) should be(hr)
    
    val f = department andThen name
    f(joe) should be("HR")
  }

}
