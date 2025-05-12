package tasks

import org.junit.*
import org.junit.Assert.*

class TasksTest:
  import Tasks.*
  import u03.Sequences.*
  import Sequence.*
  import u02.Modules.*

  val sequence: Sequence[Person] = Cons(Person.Student("mario", 2015), Cons(Person.Teacher("daniel", "OOP"), Cons(Person.Student("luigi", 2016), Nil())))
  val sequenceStudents: Sequence[Person] = Cons(Person.Student("mario", 2015), Cons(Person.Student("luigi", 2016), Cons(Person.Student("luca", 2014), Nil())))
  val sequenceTeachers: Sequence[Person] = Cons(Person.Teacher("mario", "MDP"), Cons(Person.Teacher("luigi", "IOT"), Cons(Person.Teacher("luca", "CG"), Nil())))

  //--------------------------------------------Task2.1--------------------------------------------------

  @Test def coursesFromPersonTest(): Unit =
    assertEquals(Cons("OOP", Nil()), coursesFromPerson(sequence))
    assertEquals(Nil(), coursesFromPerson(Nil()))
    assertEquals(Nil(), coursesFromPerson(sequenceStudents))
    assertEquals(Cons("MDP", Cons("IOT", Cons("CG", Nil()))), coursesFromPerson(sequenceTeachers))


  @Test def coursesFromPersonWithFlatMapTest(): Unit =
    assertEquals(Cons("OOP", Nil()), coursesFromPersonWithFlatMap(sequence))
    assertEquals(Nil(), coursesFromPersonWithFlatMap(Nil()))
    assertEquals(Nil(), coursesFromPersonWithFlatMap(sequenceStudents))
    assertEquals(Cons("MDP", Cons("IOT", Cons("CG", Nil()))), coursesFromPersonWithFlatMap(sequenceTeachers))

  //--------------------------------------------Task2.1--------------------------------------------------

  @Test def foldLeftTest(): Unit =
    val lst = Cons(3, Cons(7, Cons (1, Cons(5, Nil()))))
    assertEquals(-16, foldLeft(lst)(0)(_ - _))

//--------------------------------------------Task2.1--------------------------------------------------

  @Test def countCoursesTest(): Unit =
    assertEquals(1, countCourses(sequence))
    assertEquals(0, countCourses(sequenceStudents))
    assertEquals(3, countCourses(sequenceTeachers))
    assertEquals(0, countCourses(Nil()))