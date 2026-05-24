package SHARED

import dev.romainguy.kotlin.math.Float3
import org.jetbrains.skia.Point

val fov: Float = 90f

fun PovProjection(v: Float3): Point {
    if (v.z != 0f) {
        return Point(v.x / v.z * fov, v.y / v.z * fov)
    }
    return Point(v.x * fov, v.y * fov)
}


fun formatSimplexes(ts: Array<Simplex>): Array<Point> {
    var ret: Array<Point> = emptyArray()
    for (t in ts) {
        ret = ret.plus(t.A)
        ret = ret.plus(t.B)
        ret = ret.plus(t.C)
    }; return ret
}

fun storeSimplexes(tArray: FloatArray): Array<Simplex> {
    var ret: Array<Simplex> = emptyArray()
    for (i in 0 until tArray.size/9) {
        ret = ret.plus(Simplex(tArray.slice(i * 9 until i * 9 + 9).toFloatArray()))
    }; return ret
}