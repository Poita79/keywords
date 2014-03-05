package com.github.petekneller.keywords

import org.scalatest.{Matchers, FunSuite}
import Keyword.StringyMap

class KeywordTest extends FunSuite with Matchers {

  import StringyMap.implicits._

  val name = Keyword[String]("name")
  val department = Keyword[StringyMap]("department")

  test(".apply, .get and .conforms under successful inputs") {

    val joe: StringyMap = Map(
      "name" -> "Joe Bloggs",
      "age" -> 23
    )

    name(joe) should be("Joe Bloggs")
    name.get(joe) should be(Some("Joe Bloggs"))
  }


  test(".apply, .get and .conforms when key not present") {

    val joe: StringyMap = Map(
      "age" -> 23
    )

    name.get(joe) should be(None)
    val ex = the [NoSuchElementException] thrownBy {
     name(joe)
    }
    ex.getMessage should include (joe.toString)
  }

  test(".apply, .get and .conforms when key is of incorrect type") {

    val joe: StringyMap = Map(
      "name" -> 123,
      "age" -> 23
    )

    name.get(joe) should be(None)
    val ex = intercept[ClassCastException] {
      name(joe)
    }
    ex.getMessage should (include ("String") and include("Int") )
  }

  test("explainFailure can be used to return a useful debugging message") {

    val joe: StringyMap = Map(
      "age" -> 23
    )

    name.explainFailure(joe) should include (joe.toString)

    val joe2: StringyMap = Map(
      "name" -> 123,
      "age" -> 23
    )

    name.explainFailure(joe2) should include (joe2.toString)
  }


  test("example of function composition") {

    val hr: StringyMap = Map(
      "name" -> "HR",
      "costCentre" -> 1
    )

    val joe: StringyMap = Map(
      "department" -> hr
    )

    department(joe) should be(hr)
    
    val departmentName = department andThen name
    departmentName(joe) should be("HR")
  }

  test("keywords are covariant in value type") {
    val correctFoo = Keyword[Int]("foo")
    val wrongFoo = Keyword[String]("foo")
    val m = StringyMap("foo" -> 2)
    
    def doSomething(kw: Keyword[AnyVal]): Option[AnyVal] = kw.get(m)

    assert(doSomething(correctFoo) === Some(2))
    // the following won't compile
    // assert(f(wrongFoo) === Some(2))

  }

}
