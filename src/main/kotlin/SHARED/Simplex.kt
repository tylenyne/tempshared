package SHARED

import dev.romainguy.kotlin.math.Float3
import org.jetbrains.skia.Point

fun assert(tArr: FloatArray) { //Maybe make return FloatArray
    require(tArr.size == 9) { "Expected 9 elements but got ${tArr.size}" }
}

data class Simplex(val A: Point = Point(0f, 0f), val B: Point = Point(0f, 0f), val C: Point = Point(0f, 0f)) {

    constructor(A: Float3, B: Float3, C: Float3) : this(PovProjection(A), PovProjection(B), PovProjection(C))

    constructor(triArray: Array<Float3>) : this(triArray[0], triArray[1], triArray[2])

    constructor(nonArr: FloatArray) : this(Float3(nonArr[0], nonArr[1], nonArr[2])
        ,Float3(nonArr[3], nonArr[4], nonArr[5])
        ,Float3(nonArr[6], nonArr[7], nonArr[8])
        ,assert(nonArr)
    )

    constructor(A: Float3, B: Float3, C: Float3, F: Unit) : this(A, B, C)
}