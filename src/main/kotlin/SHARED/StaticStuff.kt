package SHARED

import dev.romainguy.kotlin.math.Float3
import org.jetbrains.skia.Point

fun OrthoProjection(v: Float3): Point {
    return enlargePlane(atCoordinates(Point(v.x, v.y)))
}

fun atCoordinates(p: Point): Point {
    return p.offset(400f, 300f)
}

fun enlargePlane(p: Point): Point {
    return p.scale(10f, 10f)
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