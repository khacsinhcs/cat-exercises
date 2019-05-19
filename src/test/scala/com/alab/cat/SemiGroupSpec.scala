package com.alab.cat
import cats.Semigroup
import cats.Monoid
import cats.implicits._
import cats.instances.int._
import cats.instances.option._
import cats.syntax._

import org.scalatest.{Matchers, WordSpec}
class SemiGroupSpec extends WordSpec with Matchers{
  "Integer semiGroup" in {
    1 |+| 1 should be(2)
    Semigroup[Int].combine(1, 2) should be(3)
    Semigroup[List[Int]].combine(List(1, 2, 3), List(4, 5, 6)) should be(List(1, 2, 3, 4, 5, 6))
    Semigroup[Int => Int].combine(_ + 1, _ * 10).apply(6) should be(67)
  }
  "Integer option " in {
    implicit val intOptionalSemigroup = Semigroup[Option[Int]]
    intOptionalSemigroup.combine(Some(2), Some(3)).combine(None) should be(Some(5))
    Some(3).combine(None)
  }
}
