package scalaz

import std.AllInstances._
import org.scalacheck.Prop.forAll

class BooleanSyntaxTest extends SpecLite {
  "boolean syntax" in {
    import syntax.id._
    import syntax.std.boolean._

    "true.option" ! forAll { (i: Int) =>
      true.option(i).exists(_ == i)
    }

    "false.option" ! forAll { (i: Int) =>
      false.option(i).isEmpty
    }

    "boolean.whenM" ! forAll { (b: Boolean) =>
      b.whenM(None).isDefined != b
    }

    "boolean.unlessM" ! forAll { (b: Boolean) =>
      b.unlessM(None).isDefined == b
    }

    "boolean.guard" ! forAll { (b: Boolean, s: String) =>
      b.guard[Option](s) == b.option(s)
    }

    "boolean.prevent" ! forAll { (b: Boolean, s: String) =>
      b.prevent[Option](s) == (!b).option(s)
    }

    "true.??" ! forAll { (s: String) =>
      true ?? s == s
    }

    "false.??" ! forAll { (s: String) =>
      false ?? s == implicitly[Monoid[String]].zero
    }

    "true.!?" ! forAll { (s: String) =>
      true !? s == implicitly[Monoid[String]].zero
    }

    "false.!?" ! forAll { (s: String) =>
      false !? s == s
    }
  }
}