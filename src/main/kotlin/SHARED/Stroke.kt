package crossxyed.SHARED
import SHARED.OrthoProjection
import dev.romainguy.kotlin.math.*;
import org.jetbrains.skia.Path
import org.jetbrains.skia.Point

open class Stroke(var sp: Point = Point(0f, 0f)) {

    constructor(x: Float, y: Float) : this(Point(x, y))

    constructor(v: Float3) : this(OrthoProjection(v))

    open fun draw(brush: Path) : Path {
        return brush;
    }


}