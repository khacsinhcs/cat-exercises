package com.alab.cat

import org.scalatest.{Matchers, WordSpec}

class PrintableSpec extends WordSpec with Matchers {

  trait Printable[A] {
    def format(a: A): String
  }

  object PrintableInstances {
    implicit def stringPrintable: Printable[String] = (a: String) => a

    implicit def intPrintable: Printable[Int] = (a: Int) => a.toString
  }

  object Printable {
    def format[A](a: A)(implicit printable: Printable[A]) = printable.format(a)

    def print[A](a: A)(implicit printable: Printable[A]) = println(format(a))
  }

  final case class Cat(name: String, age: Int, color: String)

  "print cat" in {
    implicit val catPrintable: Printable[Cat] = (cat: Cat) => s"${cat.name} is ${cat.age} year-old ${cat.color} cat"

    Printable.print(Cat("Tom", 12, "red"))
  }

  "print cat with better syntax" in {
    object PrintableSyntax {
      implicit class PrintableOps[A](value: A) {
        def format(implicit printable: Printable[A]): String = printable.format(value)

        def print(implicit printable: Printable[A]): Unit = println(printable.format(value))
      }
    }

    import PrintableSyntax._
    implicit val catPrintable: Printable[Cat] = (cat: Cat) => s"${cat.name} is ${cat.age} year-old ${cat.color} cat"

    Cat("Tom", 12, "red").print
  }
}
