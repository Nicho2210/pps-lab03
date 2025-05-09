package tasks

import org.junit.*
import org.junit.Assert.*

class TasksTest:
  import Tasks.*
  import u03.Sequences.*
  import Sequence.*
  import u02.Modules.*

  val sequence: Sequence[Person] = Cons(Person.Student("mario", 2015), Cons(Person.Teacher("Daniel", "OOP"), Cons(Person.Student("luigi", 2016), Nil())))
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