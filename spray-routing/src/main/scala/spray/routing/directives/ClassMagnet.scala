package spray.routing.directives

import scala.reflect.ClassTag

/** A magnet that wraps a ClassTag */
trait ClassMagnet[T] {
  def classTag: ClassTag[T]
  def runtimeClass: Class[T]

  /**
   * Returns a partial function that checks if the input value is of runtime type
   * T and returns the value if it does. Doesn't take erased information into account.
   */
  def extractPF: PartialFunction[Any, T]
}
object ClassMagnet {
  implicit def apply[T](u: Unit)(implicit tag: ClassTag[T]): ClassMagnet[T] =
    new ClassMagnet[T] {
      val classTag: ClassTag[T] = tag
      val runtimeClass: Class[T] = tag.runtimeClass.asInstanceOf[Class[T]]
      val extractPF: PartialFunction[Any, T] = {
        case x: T ⇒ x
      }
    }
}