package com.github.petekneller.keywords

import org.scalatest.FunSuite

class StringyMapTest extends FunSuite {

  import StringyMap.implicits._

  test("StringyMap offers a convenience constructor that helps smooth type inference") {

    // Every once in a while, the type inference for a newly created stringy map, eg.
    // val foo = Map("foo" -> 2)
    // will fail to infer the correct type (TODO: a good example?)
    // and you'll have to change it to the more verbose
    // val foo = Map[String, Any]("foo" -> 2)
    // which to my mind hides the intent.
    // So we have a convenience constructor that specifies the type to avoid that inference issue
    val foo = StringyMap("foo" -> 2, "bar" -> "baz")

  }

  test("StringyMap offers a convenience constructor for using keywords directly") {

    val foo = Keyword[Int]("foo")
    val bar = Keyword[Boolean]("bar")

    // you could use it this way
    val m1 = StringyMap(foo.key -> 2)
    assert(m1("foo") === 2)

    // or
    val m2 = StringyMap(foo -> 2, bar -> false)
    assert(m2("foo") === 2)
    assert(m2("bar") === false)
  }

}
