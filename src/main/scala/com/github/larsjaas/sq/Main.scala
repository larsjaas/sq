package com.github.larsjaas.sq

import scala.util.parsing.combinator.Parsers
import scala.util.parsing.combinator._

import java.io.{BufferedReader, StringReader, InputStreamReader}
import java.util.stream.Collectors


object Main {
    def main(args: Array[String]): Unit = {
        if (args.headOption == Some("--version")) {
          println(s"${BuildInfo.name} v${BuildInfo.version}")
          System.exit(0)
        }

        val parser = new ScalaParser()
        val input = new scala.io.BufferedSource(System.in).getLines().mkString("\n")
        parser.parse(parser.root, input) match {
            case parser.Success(matched: ScalaPrintable, _) => matched.print()(PrintContext(0, 2))
            case parser.Success(matched, _) => println(matched)
            case parser.Failure(msg, _) => println("FAILURE: " + msg)
            case parser.Error(msg, _) => println("ERROR: " + msg)
        }
    }
}
