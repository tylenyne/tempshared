package crossxyed.SHARED

import org.jetbrains.skia.Path
import org.jetbrains.skia.Point
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

fun drawNgon(ngon: Ngon, brush: Path) : Path {
    brush.moveTo(Point((cos( 0f) * ngon.v.x).toFloat(), (sin(0f) * ngon.v.y).toFloat()))
    var angle: Double = (360 / ngon.sides * PI) / 180
    for(i in 1 until ngon.sides) {
        brush.lineTo(Point((cos(angle * i) * ngon.v.x).toFloat(), (sin(angle * i) * ngon.v.y).toFloat()))
    }
    brush.close()
    return brush
}

fun drawRectangle(rect: Rectangle, brush: Path) : Path{
    brush.moveTo(rect.toPoint())
    brush.lineTo(rect.toPoint().offset(0f, rect.width))
    brush.moveTo(rect.toPoint())
    brush.lineTo(rect.toPoint().offset(rect.height, 0f))
    brush.lineTo(rect.toPoint().offset(0f, rect.width))
    brush.close()
    return brush;
}

fun drawBevel(bevel: Bevel, brush: Path) {

}

fun drawUnAllocImage(file: String) {}

fun drawStaticImage(buffer: ByteArray) {

}