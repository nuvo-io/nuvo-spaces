package nuvo.spaces.remote

import nuvo.core.Tuple
import nuvo.nio.RawBuffer
import java.nio.ByteBuffer

abstract class SpaceMessage


/**
 * Create a Space with the given name if it does not exist already
 *
 * @param spaceName  space name
 */
case class CreateSpace(spaceName: String) extends SpaceMessage

/**
 * Looks up a given space. This will result
 *
 * @param spaceName
 */
case class LookupSpace(spaceName: String) extends SpaceMessage

/**
 * Delete the Space with the given name.
 *
 * @param hash  the space hash
 */
case class DeleteSpace (hash: Long) extends SpaceMessage


/**
 * Provides the hash that has to be used when addressing this space.
 * This message is returned as result of a create operation or lookup.
 *
 * @param spaceName
 * @param hash
 */
case class SpaceHash(spaceName: String, hash: Int) extends SpaceMessage


/**
 * Read the first tuple that satisfies the predicate *p*
 *
 * @param hash cookie used to identify the request
 * @param p the predicate
 */
case class ReadTuple(hash: Int, p: Tuple => Boolean) extends SpaceMessage

/**
 * Read all the tuples that satisfy the predicate *p*
 *
 * @param hash cookie used to identify the request
 * @param p
 */
case class ReadAllTuple(hash: Int, p: Tuple => Boolean)  extends SpaceMessage


/**
 * Take the first tuple that satisfies the predicate *p*
 *
 * @param hash cookie used to identify the request
 * @param p the predicate
 */
case class TakeTuple(hash: Int, p: Tuple => Boolean) extends SpaceMessage

/**
 * Take all the tuples that satisfy the predicate *p*
 *
 * @param hash cookie used to identify the request
 * @param p
 */
case class TakeAllTuple(hash: Int, p: Tuple => Boolean)  extends SpaceMessage


/**
 * Provide a space tuple
 *
 * @param t the tuple
 */
case class SpaceTuple(hash: Int, t: Tuple) extends SpaceMessage

case class NoMatchingTuple(hash: Int) extends SpaceMessage


// NOTE: The following message should be removed since it does not allow
// for incrementally delivering the tuples
/**
 * Provides a list of tuples
 *
 * @param tl the list of tuples
 */
case class SpaceTupleList(hash: Int, tl: List[Tuple]) extends SpaceMessage

/**
 * Marks the beginning of a list of Tuples
 */
case class TListBegin(hash: Int) extends SpaceMessage

/**
 * Marks the end of a list of Tuples
 */
case class TListEnd(hash: Int) extends SpaceMessage

/**
 * Defines a stream subscription
 *
 * @param p
 */
case class OpenStream(hash: Int, p: Tuple => Boolean) extends SpaceMessage

case class CloseStream(hash: Int) extends SpaceMessage

case class StreamCookie(hash: Int) extends SpaceMessage

case class StreamTuple(hash: Int, t: Tuple) extends SpaceMessage

case class StreamTupleList(hash: Int, t: List[Tuple]) extends SpaceMessage

case class CompareAndSwap(hash: Int, p: Tuple => Boolean, t: Tuple) extends SpaceMessage

case class WriteTuple(hash: Int, t: Tuple) extends SpaceMessage

case class WriteTupleList(hash: Int, tl: List[Tuple]) extends SpaceMessage


/**
 * Get a tuple. The serialzied key value should follow this message
 * @param hash
 */
case class GetTuple(hash: Int, key: RawBuffer) extends SpaceMessage

/**
* Remove a tuple. The serialzied key value should follow this message
* @param hash
*/
case class RemoveTuple(hash: Int) extends SpaceMessage
