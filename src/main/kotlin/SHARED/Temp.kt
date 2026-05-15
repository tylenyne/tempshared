package crossxyed.SHARED

import org.jetbrains.skia.Canvas
import org.jetbrains.skiko.*
import javax.swing.*
import java.awt.Dimension

fun main() {
    val layer = SkiaLayer()

    layer.renderDelegate = SkiaLayerRenderDelegate(layer, object : SkikoRenderDelegate {
        override fun onRender(canvas: Canvas, width: Int, height: Int, nanoTime: Long) {
            canvas.clear(0xFF1a1a2e.toInt())
            layer.needRedraw()
        }
    })

    SwingUtilities.invokeLater {
        val frame = JFrame("Skiko")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.preferredSize = Dimension(800, 600)
        frame.contentPane.add(layer)
        frame.pack()
        frame.isVisible = true
        layer.attachTo(frame.contentPane)
        layer.needRedraw()
    }
}