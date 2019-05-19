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


  "It should be toJson with Json singleton Object" in {
    object Json {
      def toJson[A](value: A)(implicit jsonWriter: JsonWriter[A]) = jsonWriter.write(value)
    }

    import JsonWriterInstances._
    //We have to import implicit value personWriter in JsonWriterInstances
    val person = Person("Sinh", "khacsinhcs@gmail.com")
    Json.toJson(person)
    Json.toJson("Hello world") should be(JsString("Hello world"))
  }

  "This is the time to learn about json syntax" in {
    object JsonSyntax {

      implicit class JsonWriterOps[A](value: A) {
        def toJson(implicit w: JsonWriter[A]) = w.write(value)
      }

    }
    import JsonWriterInstances._
    import JsonSyntax._
    val person = Person("Sinh", "khacsinhcs@gmail.com")

    "This is Json".toJson should be(JsString("This is Json"))
    person.toJson
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
