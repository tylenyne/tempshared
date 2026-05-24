package crossxyed.SHARED

import org.jetbrains.skia.Path

open class QuadStroke(var height: Float = 1f, var width: Float = 1f) : Stroke() {
    override fun draw(brush: Path) : Path {
        brush.moveTo(sp)
        brush.lineTo(sp.offset(0f, width))
        brush.moveTo(sp)
        brush.lineTo(sp.offset(height, 0f))
        brush.lineTo(sp.offset(0f, width))
        brush.close()
        return brush;
    }
}