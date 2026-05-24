package crossxyed.SHARED

import org.jetbrains.skia.Path
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

open class NStroke(var radius: Float, var sides: Int) : Stroke() {
    override fun draw(brush: Path) : Path {
        brush.moveTo(sp.offset(cos( 0f) * radius, sin(0f) * radius))
        var angle: Float = (360 / sides * PI.toFloat()) / 180
        for(i in 1 until sides) {
            brush.lineTo(sp.offset(cos(angle * i) * radius, sin(angle * i) * radius))
        }
        brush.close()
        return brush
    }
}

