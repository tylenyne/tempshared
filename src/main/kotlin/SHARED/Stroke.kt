package crossxyed.SHARED
import SHARED.PovProjection
import dev.romainguy.kotlin.math.*;
import org.jetbrains.skia.Path
import org.jetbrains.skia.Point

open class Stroke(var p: Point = Point(0f, 0f)) {

    constructor(x: Float, y: Float) : this(Point(x, y))

    constructor(v: Float3) : this(PovProjection(v))

    open fun draw(brush: Path) : Path {
        return brush;
    }


}