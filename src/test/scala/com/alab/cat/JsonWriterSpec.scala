package com.alab.cat

import org.scalatest.{Matchers, WordSpec}

class JsonWriterSpec extends WordSpec with Matchers {

  final case class Person(name: String, email: String)

  sealed trait Json

  final case class JsonObject(get: Map[String, Json]) extends Json

  final case class JsString(get: String) extends Json

  final case class JsNumber(get: Double) extends Json

  trait JsonWriter[A] {
    def write(value: A): Json
  }

  object JsonWriterInstances {
    implicit val stringWriter: JsonWriter[String] = (value: String) => JsString(value)
    implicit val numberWriter: JsonWriter[Double] = (value: Double) => JsNumber(value)
    implicit val personWriter: JsonWriter[Person] = (value: Person) => JsonObject(Map(
      "name" -> JsString(value.name),
      "email" -> JsString(value.email)
    ))
  }

}
