package tasks

import u03.Sequences.*
import Sequence.*
import u02.Modules.*
import Person.*

import scala.annotation.tailrec

object Tasks extends App:

  //--------------------------------------------Task2.1--------------------------------------------------

  private def isTeacher: Person => Boolean =
    case Teacher(_, _) => true
    case _ => false

  private def teacherToCourse: Person => String =
    case Teacher(_, c) => c
    case _ => ""

  private def mapper: Person => Sequence[String] =
    case Teacher(_, c) => Cons(c, Nil())
    case _ => Nil()

  def coursesFromPerson(s: Sequence[Person]): Sequence[String] = map(filter(s)(isTeacher))(teacherToCourse)
  def coursesFromPersonWithFlatMap(s: Sequence[Person]): Sequence[String] = flatMap(s)(mapper)

  //--------------------------------------------Task2.2--------------------------------------------------

  @tailrec
  def foldLeft[A, B](s: Sequence[A])(acc: B)(f: (B, A) => B): B = s match 
    case Cons(h, t) => foldLeft(t)(f(acc, h))(f)
    case Nil() => acc
  