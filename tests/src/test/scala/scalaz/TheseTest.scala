package scalaz

import scalaz.scalacheck.ScalazProperties._
import scalaz.scalacheck.ScalazArbitrary._
import std.AllInstances._
import \&/._
import syntax.contravariant._
import org.scalacheck.Prop.forAll

object TheseTest extends SpecLite {
  type TheseInt[a] = Int \&/ a

  checkAll(monad.laws[TheseInt])
  checkAll(traverse.laws[TheseInt])
  checkAll(equal.laws[Int \&/ Int])
  checkAll(bitraverse.laws[\&/])

  "align unalign" should {
    "List" ! forAll { (a: List[Int], b: List[Int]) =>
      unalignList(alignList(a, b)) must_=== ((a, b))
    }
  }

  object instances {
    def functor[L] = Functor[({type λ[α] = L \&/ α})#λ]
    def apply[L: Semigroup] = Apply[({type λ[α] = L \&/ α})#λ]
    def applicative[L: Semigroup] = Applicative[({type λ[α] = L \&/ α})#λ]
    def monad[L: Semigroup] = Monad[({type λ[α] = L \&/ α})#λ]
    def zip[L: Semigroup] = Zip[({type λ[α] = L \&/ α})#λ]
    def cobind[L] = Cobind[({type λ[α] = L \&/ α})#λ]
    def foldable[L] = Foldable[({type λ[α] = L \&/ α})#λ]
    def traverse[L] = Traverse[({type λ[α] = L \&/ α})#λ]
    def bifunctor = Bifunctor[\&/]
    def bifoldable = Bifoldable[\&/]
    def bitraverse = Bitraverse[\&/]

    // checking absence of ambiguity
    def functor[L: Semigroup] = Functor[({type λ[α] = L \&/ α})#λ]
  }
}