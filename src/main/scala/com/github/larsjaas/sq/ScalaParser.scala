package com.github.larsjaas.sq

import scala.util.parsing.combinator.*

class ScalaParser extends RegexParsers {

    val popen = "\\([ \t\n]*".r
    val pclose = "\\)[ \t\n]*".r
    val eof = "$".r

    def nullval: Parser[ScalaLiteral] = "null" ^^ { _ => ScalaLiteral("null") }
    val argsep = ",[ \t\n]*".r

    def token: Parser[String] = """[A-Za-z][A-Za-z0-9_]+""".r ^^ { _.toString }

    def stringrun: Parser[ScalaLiteral] = "[^,\\(\\)]+".r ^^ {
      case x: String => ScalaLiteral(x)
    }

    def integer: Parser[ScalaLiteral] = """(0|[1-9][0-9]*)""".r ^^ {
      case x: String => ScalaLiteral(x)
    }

    def decimal: Parser[ScalaLiteral] = """(0|[1-9][0-9]*)\\.([0-9]*)""".r ^^ {
      case x: String => ScalaLiteral(x)
    }

    def empty: Parser[ScalaLiteral] = "" ^^ { _ => ScalaLiteral("") }

    def number: Parser[ScalaLiteral] = integer | decimal

    def literal: Parser[ScalaLiteral] = /* number | */ stringrun | nullval | empty

    def obj: Parser[ScalaPrintable] = token ~ popen ~ expression ~ rep(argsep ~ expression) ~ pclose ^^ {
      case t ~ _ ~ a ~ s ~ _ => {
        s.length match {
          case 0 => ScalaObject(t, Seq(a))
          case _ => ScalaObject(t, Seq(a) ++ s.map(arg => {
            arg._2 : ScalaPrintable
          }))
        }
      }
    }

    def optionDefined: Parser[ScalaPrintable] = "Some" ~ popen ~ expression ~ pclose ^^ {
      case _ ~ _ ~ t ~ _ => ScalaObject("Some", Seq(t))
    }

    def optionUndefined: Parser[ScalaPrintable] = "None" ^^ {
      case _ => ScalaObject("None", Seq.empty)
    }

    def option: Parser[ScalaPrintable] = optionUndefined | optionDefined

    def expression: Parser[ScalaPrintable] = option | obj | literal | empty

    def root: Parser[ScalaPrintable] = expression ~ eof ^^ {
      case x ~ _ => x
    }

}
