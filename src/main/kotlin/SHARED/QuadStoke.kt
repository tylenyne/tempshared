package crossxyed.SHARED

import org.jetbrains.skia.Path

open class QuadStoke(var height: Float = 1f, var width: Float = 1f) : Stroke() {
    override fun draw(brush: Path) : Path {
        brush.moveTo(p)
        brush.lineTo(p.offset(0f, width))
        brush.moveTo(p)
        brush.lineTo(p.offset(height, 0f))
        brush.lineTo(p.offset(0f, width))
        brush.close()
        return brush;
    }
}