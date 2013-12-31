package com.github.petekneller.keywords

import org.scalatest.{Matchers, FunSuite}
import Keyword.StringyMap

class KeywordPrimitivesTest extends FunSuite with Matchers {

  // numeric types
  testEquivalent((2.toShort): java.lang.Short, "java.lang.Short", 2: scala.Short, "scala.Short")
  testEquivalent(2: java.lang.Integer, "java.lang.Integer", 2: scala.Int, "scala.Int")
  testEquivalent(2: java.lang.Long, "java.lang.Long", 2: scala.Long, "scala.Long")
  testEquivalent(2: java.lang.Float, "java.lang.Float", 2: scala.Float, "scala.Float")
  testEquivalent(2: java.lang.Double, "java.lang.Double", 2: scala.Double, "scala.Double")

  // other
  testEquivalent((0.toByte): java.lang.Byte, "java.lang.Byte", 0: Byte, "scala.Byte")
  testEquivalent('a': java.lang.Character, "java.lang.Character", 'a': scala.Char, "scala.Char")
  testEquivalent(true: java.lang.Boolean, "java.lang.Boolean", true: scala.Boolean, "scala.Boolean")


  def testEquivalent[J: Manifest, S: Manifest](j: J, jName: String, s: S, sName: String) = {
    test(s"$jName and $sName are equivalent") {
      val javaKeyword = Keyword[J]("key")
      val scalaKeyword = Keyword[S]("key")

      val javaMap: StringyMap = Map("key" -> (j: J))
      val scalaMap: StringyMap = Map("key" -> (s: S)) // gets boxed to java type

      javaKeyword(javaMap) should be(j)
      javaKeyword(scalaMap) should be(j)
      scalaKeyword(scalaMap) should be(s)
      scalaKeyword(javaMap) should be(s)
    }
  }

}
