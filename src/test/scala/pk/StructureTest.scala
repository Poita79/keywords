package pk

import org.scalatest.{Matchers, FunSuite}

class StructureTest extends FunSuite with Matchers {

  val name = Keyword[String]("name")
  val costCentre = Keyword[Int]("costCentre")
  val department = Structure("department",
      name,
      costCentre
  )

  val hr = Map(
    "name" -> "HR",
    "costCentre" -> 1
  )

  test("conforms and cast succeed when the structure of the map is valid") {

    department.conforms(hr) should be(true)
    department.cast(hr) should be(Some(hr))
  }

  test("conforms and cast fail when Keywords are missing") {

    val missingCostCentre = Map(
      "name" -> "HR"
    )

    department.conforms(missingCostCentre) should be(false)
    department.cast(missingCostCentre) should be(None)
  }

  test("conforms and cast fail when Keyword is of wrong type") {

    val costCentreOfWrongType = Map(
      "name" -> "HR",
      "costCentre" -> "1"
    )

    department.conforms(costCentreOfWrongType) should be(false)
    department.cast(costCentreOfWrongType) should be(None)
  }


  val age = Keyword[Int]("age")
  val employee = Structure("employee",
    name,
    age,
    department
  )

  val joe = Map(
    "name" -> "Joe Bloggs",
    "age" -> 23,
    "department" -> hr
  )

  test("Structures can be nested") {
    employee.conforms(joe) should be(true)
    employee.cast(joe) should be(Some(joe))
  }

  test("Structure is a Keyword and can be used as such") {

    department(joe) should be(hr)
    department.get(joe) should be(Some(hr))
  }


}
