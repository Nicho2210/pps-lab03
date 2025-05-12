package u03
import org.junit.*
import org.junit.Assert.*

import u03.Streams.*
import Stream.*
import u03.Sequences.*
import Sequence.*

class StreamTest:

  @Test def testIterate(): Unit =
    val str1 = Stream.iterate(0)(_ + 1) // {0,1,2,3,..}
    assertEquals(Cons(0, Cons(1, Cons(2, Cons(3, Nil())))), toList(Stream.take(str1)(4)))

  @Test def testMap(): Unit =
    val str1 = Stream.iterate(0)(_ + 1) // {0,1,2,3,..}
    val str2 = Stream.map(str1)(_ + 1) // {1,2,3,4,..}
    assertEquals(Cons(1, Cons(2, Cons(3, Cons(4, Nil())))), toList(Stream.take(str2)(4)))

  @Test def testFilter(): Unit =
    val str1 = Stream.iterate(0)(_ + 1) // {0,1,2,3,..}
    val str2 = Stream.filter(str1)(x => x % 2 == 1) // {1,3,5,7,..}
    assertEquals(Cons(1, Cons(3, Cons(5, Cons(7, Nil())))), toList(Stream.take(str2)(4)))

  @Test def takeWhile(): Unit =
    val str1 = Stream.iterate(0)(_ + 1) // {0,1,2,3,..}
    val str2 = Stream.takeWhile(str1)(_ < 5) // {0,1,2,3,4}
    assertEquals(Cons(0, Cons(1, Cons(2, Cons(3, Cons(4, Nil()))))), Stream.toList(str2))

  @Test def fill(): Unit =
    val str1 = Stream.toList(Stream.fill(3)("a"))
    assertEquals(Cons ("a", Cons ("a", Cons ("a", Nil ()))), str1)

  @Test def fibonacci(): Unit =
    val fibonacci: Stream[Int] = Stream.fibonacci()
    assertEquals(Stream.toList(Stream.take(fibonacci)(5)), Cons (0, Cons (1, Cons (1, Cons (2, Cons (3, Nil ()))))))

  @Test def interleave(): Unit = 
    val str1 = Stream.iterate(0)(_ + 1) // {0,1,2,3,..}
    val str2 = Stream.iterate(100)(_ + 1) // {0,1,2,3,..}
    assertEquals(empty(), Stream.interleave(empty(), empty()))
    assertEquals(Cons (0, Cons (100, Cons (1, Cons (101, Cons (2, Nil ()))))), Stream.toList(Stream.interleave(Stream.take(str1)(3), Stream.take(str2)(2))))
    assertEquals(Cons (0, Cons (100, Cons (101, Cons (102, Cons (103, Nil ()))))), Stream.toList(Stream.interleave(Stream.take(str1)(1), Stream.take(str2)(4))))
    assertEquals(Cons (0, Cons (100, Cons (1, Cons (2, Cons (3, Nil ()))))), Stream.toList(Stream.interleave(Stream.take(str1)(4), Stream.take(str2)(1))))

end StreamTest
