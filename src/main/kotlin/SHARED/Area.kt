package crossxyed.SHARED

import org.jetbrains.skia.Color
import org.jetbrains.skia.Paint
import org.jetbrains.skia.PaintMode

fun drawSkeleton(paint: Paint) {
    paint.color = Color.WHITE;
    paint.mode = PaintMode.STROKE;
    paint.strokeWidth = 4f;
}