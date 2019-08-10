package slickless

import scala.annotation.tailrec
import scala.reflect.ClassTag
import shapeless.{ HList, ::, HNil, Generic }
import slick.ast.MappedScalaType
import slick.lifted.{ Shape, ShapeLevel, FlatShapeLevel, MappedProductShape, MappedProjection }

final class HListShape[L <: ShapeLevel, M <: HList, U <: HList : ClassTag, P <: HList]
    (val shapes: Seq[Shape[_<: ShapeLevel, _, _, _]]) extends MappedProductShape[L, HList, M, U, P] {

  def buildValue(elems: IndexedSeq[Any]) =
    elems.foldRight(HNil: HList)(_ :: _)

  def copy(shapes: Seq[Shape[_ <: ShapeLevel, _, _, _]]) =
    new HListShape(shapes)

  def classTag: ClassTag[U] = implicitly

  def runtimeList(value: HList): List[Any] = {
    @tailrec def loop(value: HList, acc: List[Any] = Nil): List[Any] = value match {
      case HNil     => acc
      case hd :: tl => loop(tl, hd :: acc)
    }

    loop(value).reverse
  }

  override def getIterator(value: HList): Iterator[Any] =
    runtimeList(value).iterator

  def getElement(value: HList, idx: Int): Any =
    runtimeList(value)(idx)
}

trait HListShapeImplicits {
  implicit def hnilShape[L <: ShapeLevel]: HListShape[L, HNil, HNil, HNil] =
    new HListShape(Nil)

  implicit def hconsShape[
    L <: ShapeLevel, L1 <: ShapeLevel, L2 <: ShapeLevel, 
    M1, M2 <: HList,
    U1, U2 <: HList,
    P1, P2 <: HList
  ](implicit
    s1: Shape[L1, M1, U1, P1],
    s2: HListShape[L2, M2, U2, P2]
  ): HListShape[L, M1 :: M2, U1 :: U2, P1 :: P2] =
    new HListShape(s1 +: s2.shapes)

  implicit class HListShapeOps[T <: HList](hlist: T) {
    def mappedWith[
      R: ClassTag, U <: HList, L <: FlatShapeLevel, P
    ](gen: Generic.Aux[R, U])(
      implicit shape: Shape[L, T, U, P]
    ) = new MappedProjection[R, U](
      shape.toNode(hlist),
      MappedScalaType.Mapper(
        (gen.to _).asInstanceOf[Any => Any],
        (gen.from _).asInstanceOf[Any => Any],
        None
      ),
      implicitly[ClassTag[R]]
    )
  }
}
