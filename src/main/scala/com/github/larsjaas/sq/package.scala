package com.github.larsjaas

package object sq {

    case class PrintContext(indent: Int, indentation: Int) {
      def getIndent: String = (" "*100).substring(0, indent)
    }

    sealed trait ScalaPrintable {
      def print()(implicit context: PrintContext): Unit = ???
    }

    case class ScalaObject(name: String, args: Seq[ScalaPrintable]) extends ScalaPrintable {
      override def print()(implicit context: PrintContext): Unit = {
        val indent = context.getIndent
        println(s"${indent}${name}(")
        args.foreach(a => a.print()(context.copy(indent = context.indent + context.indentation)))
        println(s"${indent})")
      }
    }

    case class ScalaLiteral(value: String) extends ScalaPrintable {
      override def print()(implicit context: PrintContext): Unit = {
        val indent = context.getIndent
        println(s"${indent}${value}")
      }
    }

}