package crossxyed.SHARED
import dev.romainguy.kotlin.math.*;
import org.jetbrains.skia.Point

open class Vertex(var v: Float3 = Float3()) {
    constructor(x: Float, y: Float) : this(Float3(x, y, 0f))

    constructor(p: Point) : this(p.x, p.y)

    fun toPoint(): Point {
        return Point(v.x, v.y)
    }

    fun projectize(): Float3 {
        if (v.z != 0f) {
            return Float3(v.x / v.z, v.y / v.z, 0f)
        }
        return v
    }
}