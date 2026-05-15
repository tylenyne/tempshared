package crossxyed.SHARED
import dev.romainguy.kotlin.math.*;
import org.jetbrains.skia.Point

open class Geometry(var point: Float2 = Float2()) {
    constructor(x: Float, y: Float) : this(Float2(x, y))

    constructor(p: Float3) : this(Point(p.x, p.y))

    constructor(p: Point) : this(p.x, p.y)

    fun toPoint(): Point {
        return Point(point.x, point.y)
    }
}

fun project(v: Float3): Geometry {
    return Geometry(v.xy/v.z)
}

fun project(vs: Array<Float2>): Array<Geometry> {
    return vs.indices.map { index -> Geometry(vs[index]) }.toTypedArray()
}