package crossxyed.SHARED

import org.jetbrains.skia.Codec
import org.jetbrains.skia.Color
import org.jetbrains.skia.Paint
import org.jetbrains.skia.PaintMode

var skeleton: Paint = Paint().apply {
    color = Color.WHITE;
    mode = PaintMode.STROKE;
   strokeWidth = 4f;
}