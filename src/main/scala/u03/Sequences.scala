package u03

import u03.Optionals.Optional
import u03.Optionals.Optional.*

import scala.annotation.tailrec

object Sequences: // Essentially, generic linked lists

  enum Sequence[E]:
    case Cons(head: E, tail: Sequence[E])
    case Nil()

  object Sequence:

    def sum(l: Sequence[Int]): Int = l match
      case Cons(h, t) => h + sum(t)
      case _ => 0

    def map[A, B](l: Sequence[A])(mapper: A => B): Sequence[B] = l match
      case Cons(h, t) => Cons(mapper(h), map(t)(mapper))
      case Nil() => Nil()

    def filter[A](l1: Sequence[A])(pred: A => Boolean): Sequence[A] = l1 match
      case Cons(h, t) if pred(h) => Cons(h, filter(t)(pred))
      case Cons(_, t) => filter(t)(pred)
      case Nil() => Nil()

    // Lab 03

    /*
     * Skip the first n elements of the sequence
     * E.g., [10, 20, 30], 2 => [30]
     * E.g., [10, 20, 30], 3 => []
     * E.g., [10, 20, 30], 0 => [10, 20, 30]
     * E.g., [], 2 => []
     */
    @tailrec
    def skip[A](s: Sequence[A])(n: Int): Sequence[A] = s match
      case Nil() => Nil()
      case Cons(_, t) if n > 0 => skip(t)(n - 1)
      case _ => s

    /*
     * Zip two sequences
     * E.g., [10, 20, 30], [40, 50] => [(10, 40), (20, 50)]
     * E.g., [10], [] => []
     * E.g., [], [] => []
     */
    def zip[A, B](first: Sequence[A], second: Sequence[B]): Sequence[(A, B)] = (first, second) match
      case (Cons(h1, t1) , Cons(h2, t2)) => Cons((h1, h2), zip(t1, t2))
      case _ => Nil()

    /*
     * Concatenate two sequences
     * E.g., [10, 20, 30], [40, 50] => [10, 20, 30, 40, 50]
     * E.g., [10], [] => [10]
     * E.g., [], [] => []
     */
    def concat[A](s1: Sequence[A], s2: Sequence[A]): Sequence[A] = (s1, s2) match
      case (Nil(), _) => s2
      case (_, Nil()) => s1
      case (Cons(h1, t1), _) => Cons(h1, concat(t1, s2))

    /*
     * Reverse the sequence
     * E.g., [10, 20, 30] => [30, 20, 10]
     * E.g., [10] => [10]
     * E.g., [] => []
     */

    def reverse[A](s: Sequence[A]): Sequence[A] = s match
      case Cons(head, tail) => concat(reverse(tail), Cons(head, Nil()))
      case Nil() => Nil()

    /*
     * Map the elements of the sequence to a new sequence and flatten the result
     * E.g., [10, 20, 30], calling with mapper(v => [v, v + 1]) returns [10, 11, 20, 21, 30, 31]
     * E.g., [10, 20, 30], calling with mapper(v => [v]) returns [10, 20, 30]
     * E.g., [10, 20, 30], calling with mapper(v => Nil()) returns []
     */
    def flatMap[A, B](s: Sequence[A])(mapper: A => Sequence[B]): Sequence[B] = s match
      case Cons(h, t) => concat(mapper(h), flatMap(t)(mapper))
      case Nil() => Nil()

    /*
     * Get the minimum element in the sequence
     * E.g., [30, 20, 10] => 10
     * E.g., [10, 1, 30] => 1
     */
    @tailrec
    def min(s: Sequence[Int]): Optional[Int] = s match
      case Cons(h, t) if t == Nil() => Just(h)
      case Cons(h, Cons(h2, t2)) if h <= h2 => min(Cons(h, t2))
      case Cons(h, Cons(h2, t2)) if h2 < h => min(Cons(h2, t2))
      case _ => Empty()

    /*
     * Get the elements at even indices
     * E.g., [10, 20, 30] => [10, 30]
     * E.g., [10, 20, 30, 40] => [10, 30]
     */
    def evenIndices[A](s: Sequence[A]): Sequence[A] =
      @annotation.tailrec
      def _evenIndices(s: Sequence[A], flag: Boolean, acc: Sequence[A]): Sequence[A] = (s, flag) match {
        case (Cons(h, t), f) if f => _evenIndices(t, !f, Cons(h, acc))
        case (Cons(h, t), f) if !f => _evenIndices(t, !f, acc)
        case _ => acc
      }
      reverse(_evenIndices(s, true, Nil()))

    /*
     * Check if the sequence contains the element
     * E.g., [10, 20, 30] => true if elem is 20
     * E.g., [10, 20, 30] => false if elem is 40
     */
    @tailrec
    def contains[A](s: Sequence[A])(elem: A): Boolean = s match
      case Cons(h, _) if h == elem => true
      case Cons(h, t) if h != elem => contains(t)(elem)
      case _ => false

    /*
     * Remove duplicates from the sequence
     * E.g., [10, 20, 10, 30] => [10, 20, 30]
     * E.g., [10, 20, 30] => [10, 20, 30]
     */
    def distinct[A](s: Sequence[A]): Sequence[A] =
      @annotation.tailrec
      def _distinct(s: Sequence[A], acc: Sequence[A]): Sequence[A] = s match {
        case Cons(h, t) if !contains(acc)(h) => _distinct(t, Cons(h, acc))
        case Cons(h, t) if contains(acc)(h) => _distinct(t, acc)
        case _ => acc
      }
      reverse(_distinct(s, Nil()))

    /*
     * Group contiguous elements in the sequence
     * E.g., [10, 10, 20, 30] => [[10, 10], [20], [30]]
     * E.g., [10, 20, 30] => [[10], [20], [30]]
     * E.g., [10, 20, 20, 30] => [[10], [20, 20], [30]]
     */
    def group[A](s: Sequence[A]): Sequence[Sequence[A]] =
      @annotation.tailrec
      def _group(s: Sequence[A], current: Sequence[A], acc: Sequence[Sequence[A]]) : Sequence[Sequence[A]] = s match {
        case Cons(h, t) if current == Nil() => _group(t, Cons(h, Nil()), acc)
        case Cons(h, t) if current != Nil() => current match {
          case Cons(ch, ct) if h == ch => _group(t, Cons(h, current), acc)
          case _ => _group(t, Cons(h, Nil()), Cons(reverse(current), acc))
        }
        case _ => current match {
          case Nil() => acc
          case _ => Cons(reverse(current), acc)
        }
      }
      reverse(_group(s, Nil(), Nil()))
    /*
     * Partition the sequence into two sequences based on the predicate
     * E.g., [10, 20, 30] => ([10], [20, 30]) if predicate is (_ < 20)
     * E.g., [11, 20, 31] => ([20], [11, 31]) if predicate is (_ % 2 == 0)
     */
    def partition[A](s: Sequence[A])(predicate: A => Boolean): (Sequence[A], Sequence[A]) = 
      @annotation.tailrec
      def _partition(s: Sequence[A], predicate: A => Boolean, firstAcc: Sequence[A], secondAcc: Sequence[A]): (Sequence[A], Sequence[A]) = s match {
        case Cons(h, t) if predicate(h) => _partition(t, predicate, Cons(h, firstAcc), secondAcc)
        case Cons(h, t) => _partition(t, predicate, firstAcc, Cons(h, secondAcc))
        case _ => (reverse(firstAcc), reverse(secondAcc))
      }
      _partition(s, predicate, Nil(), Nil())

  end Sequence
end Sequences

@main def trySequences(): Unit =
  import Sequences.*
  val sequence = Sequence.Cons(10, Sequence.Cons(20, Sequence.Cons(30, Sequence.Nil())))
  println(Sequence.sum(sequence)) // 30

  import Sequence.*

  println(sum(map(filter(sequence)(_ >= 20))(_ + 1))) // 21+31 = 52
