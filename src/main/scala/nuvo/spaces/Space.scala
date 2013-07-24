package nuvo.spaces

import language.existentials
import nuvo.core.{Tuple, Duration, Time}
import java.util.concurrent.atomic.AtomicLong

object Space {
  import prelude._
  val anonymousSpaceName = "AnonymousSpace:"

  val counter = new AtomicLong()

  def apply[T <: Tuple]() = {
    val loc = SpaceLocator(anonymousSpaceName + counter.getAndIncrement)
    LocalSpace.builderWithOptLocator(loc)
  }

  def apply[T <: Tuple](name: String) = {
    SpaceLocator(name) match {
      case loc @ Some(LocalSpaceLocator(s)) => LocalSpace.builderWithOptLocator(loc)
      case loc @ Some(RemoteSpaceLocator(s, l)) => RemoteSpace.builderWithOptLocator(loc)
      case None => None
    }
  }
}

/**
 * This trait defines the operations supported by a nuvo tuple space.
 *
 */
trait Space[T <: Tuple] {

  type Key = Any
  type SpaceEntry = (Key, T)

  /**
   * Write a tuple within this space
   *
   * @param tuple the tuple to be written into the space
   */
  def write(tuple: T)

  /**
   * Write a list of tuples within this space
   *
   * @param tuples the tuples to be written into the space
   */
  def write(tuples: List[T])

  /**
   * Writes a tuple at time t, where t >= Time.now. The ability to write
   * tuples in the future makes it easy to model timer as well as reminders.
   *
   * @param tuple the tuple to be written into the space
   * @param t the time at which the tuple will be written.
   */
  def write(tuple: T, t: Time)

  /**
   * Writes a list of tuples at time t, where t >= Time.now. The ability to write
   * tuples in the future makes it easy to model timer as well as reminders.
   *
   * @param tuples the tuples to be written into the space
   * @param t the time at which the tuple will be written.
   */
  def write(tuples: List[T], t: Time)

  /**
   * Read a tuple that matches the given matcher. A copy of the tuple will be
   * returned to the application. Yet the original tuple will remain within the tuple-space.
   *
   * @param p the predicate used to identify the tuple
   * @tparam Q the typle type
   * @return Some tuple if the matching is successful, None otherwise
   */
  def read[Q <: T](p: Tuple => Boolean): Option[Q]


  /**
   * Take a tuple that matches the given matcher. A copy of the tuple will be
   * returned to the application. Yet the original tuple will be removed from the tuple-space.
   *
   * @param p the predicate used to identify the tuple
   * @tparam Q the typle type
   * @return Some tuple if the matching is successful, None otherwise
   */
  def take[Q <: T](p: Tuple => Boolean): Option[Q]


  /**
   * Blocking version of the read operation.
   *
   * @param p the predicate used to identify the tuple
   * @tparam Q the typle type
   * @return Some tuple if the matching is successful, None otherwise
   */
  def sread[Q <: T](p: Tuple => Boolean): Option[Q]

  /**
   * Blocking version of the read operation. This operation will block for
   * at most a given amount of time.
   *
   * @param p the predicate used to identify the tuple
   * @param timeout the maximum amount of time for which this call will block
   * @tparam Q the typle type
   * @return Some tuple if the matching is successful, None otherwise
   */
  def sread[Q <: T](p: Tuple => Boolean, timeout: Duration): Option[Q]

  /**
   * Blocking version of the take operation.
   *
   * @param p the predicate used to identify the tuple
   * @tparam Q the typle type
   * @return Some tuple if the matching is successful, None otherwise
   */
  def stake[Q <: T](p: Tuple => Boolean): Option[Q]

  /**
   * Blocking version of the take operation. This operation will block for
   * at most a given amount of time.
   *
   * @param p the predicate used to identify the tuple
   * @param timeout the maximum amount of time for which this call will block
   * @tparam Q the typle type
   * @return Some tuple if the matching is successful, None otherwise
   */
  def stake[Q <: T](p: Tuple => Boolean, timeout: Duration): Option[Q]

  /**
   * Read all the tuple that match the given criteria.
   *
   * @param p the predicate used to identify the tuple
   * @tparam Q the typle type
   * @return A List of the matching tuples
   */
  def readAll[Q <: T](p: Tuple => Boolean): Iterable[Q]


  /**
   * Take all the tuple that match the given criteria.
   *
   * @param p the predicate used to identify the tuple
   * @tparam Q the typle type
   * @return A List of the matching tuples
   */
  def takeAll[Q <: T](p: Tuple => Boolean): Iterable[Q]


  /**
   * Execute a function somewhere on this space.
   *
   * @param f the function to execute
   */
  def exec(f: (Space[T]) => Unit)

  /**
   * Execute a function somewhere on this space.
   *
   * @param f the function to execute
   * @param d the delay after which the function will be executed
   */
  def exec(f: (Space[T]) => Unit, d: Duration)

  /**
   * Create a stream that will be pushing updates for a given type T matching the
   * provided matcher.
   *
   * @param p The predicate used to filter tuple-space updates
   * @param observer The observer to be associated wit the Stream
   * @tparam Q The type of the tuple-space entries that will be consumed
   * @return the newly create stream
   */
  def stream[Q <: T](p: Tuple =>  Boolean, observer: Q => Unit): Stream[Q]

  /**
   * Create a stream that will be pushing updates for a given type T matching the
   * provided matcher.
   *
   * @param p The predicate used to filter tuple-space updates
   * @tparam Q The type of the tuple-space entries that will be consumed
   * @return the newly create stream
   */


  /**
   * Get the tuple with the given key value if present. Otherwise returns None.
   *
   * @param key the key of the tuple to get
   * @tparam Q the type of the tuple
   * @return the tuple if present, None otherwise
   */
  def get[Q <: T: Manifest](key: Key): Option[Q]

  /**
   * Remove the tuple with the given key value if present. Otherwise returns None.
   *
   * @param key the key of the tuple to get
   * @tparam Q the type of the tuple
   * @return the tuple if present, None otherwise
   */
  def remove[Q <: T](key: Key): Option[Q]


  /**
   * Atomically swap the tuple that satisfies the predicate p with the tuple q.
   * @param p a predicate
   * @param q the tuple that will be swapped
   * @tparam Q the type of the tuple
   * @return the swapped out tuple
   */
  def compareAndSwap[Q <:T](p: Tuple => Boolean, q: Q): Option[T]


  /**
   * Create a new space by applying the given function f.
   *
   * @param f the function to apply to the element of the space
   * @tparam Q the target type
   * @return a new space that
   */
  def map[Q <: Tuple](f: T => Q): Space[Q]

  /**
   * Return a Space whose elements satisfy the filter predicate
   * @param p the predicate that will be used for selecting the tuples
   * @return the space containing the tuples that satisfy the predicate
   */
  def filter(p: T => Boolean): Space[T]

  /**
   * Return a Space whose elements do not satisfy the filter predicate
   * @param p the predicate that will be used for selecting the tuples to drop
   * @return the space containing the tuples that do not satisfy the predicate
   */
  def filterNot(p: T => Boolean): Space[T]

  /**
   * Filter the space using a predicate on the key.
   *
   * @param p the key predicate
   * @return the space containing all the matchign tuples
   */
  def filterKeys(p: Key => Boolean): Space[T]

  /**
   * Aggregate tuples into a single value
   *
   * @param z the initial value
   * @param op the fold operator
   * @tparam R the fold type
   * @return a tuplereresenting the folded eresi
   */
  def fold[R >: (Key, T)](z: R)(op: (R,R) => R): R


  /**
   * Create a sub-space hierarchically nested in this space. As an example,
   * the "A" could be the top level Space for "A.B", "A.B.C", etc.
   *
   * In general sub-spaces are a way of organizing spaces and as such a read in to a
   * top-level space will return a value as far as it can find it in the top level or
   * on any one of the nested spaces.
   *
   * @param name the sub-space name
   * @tparam Q the type of the tuple included in the subspace, notice that Q <: T
   * @return the newly create subspace
   */
  def createSubSpace[Q <: T](name: String): Space[Q]

  /**
   * Get a subspace by looking it up by name.
   *
   * @param name subspace name
   * @tparam Q the space type
   * @return the subspace
   */
  def subSpace[Q <: T](name: String): Option[Space[Q]]

  /**
   * Closes the spaces by releasing all the resources.
   */
  def close()
}
