package crossxyed.SHARED

import JVMSYS.Module
import SHARED.Simplex
import SHARED.formatSimplexes
import SHARED.storeSimplexes
import org.jetbrains.skia.*

object Scene : Module() {
    var sceneRaster: SceneRaster? = null
    //Have to be sorted in Z order so they can overlap
    var shapes = emptyArray<Stroke>()
    //IDK what order this has to be sorted in
    var simplices = emptyArray<Simplex>()

    var tsvp: Array<Point> = emptyArray()

    fun recvWindow() {
        sceneRaster = SceneRaster()
    }

    fun recvRender(ts: FloatArray) {
        simplices = storeSimplexes(ts)
        tsvp = formatSimplexes(simplices)
        println("first triangle: ${tsvp.take(3).map { "(${it.x}, ${it.y})" }}")
    }

    fun Loop() {
        sceneRaster?.surf?.canvas?.drawPaint(sceneRaster?.backgroundPreset!!)
        for (shape in shapes) {
            var element: Path? = Path()
            when(shape::class.simpleName){
                "QuadStroke" -> {
                   element = sceneRaster?.let { (shape as QuadStroke).draw(it.brush) }
                }
                "NStroke" -> {
                    element = sceneRaster?.let { (shape as NStroke).draw(it.brush) }
                }
            }
            if (element == null) {
                return
            }

            sceneRaster?.surf?.canvas?.drawPath(element, sceneRaster?.skeletonPreset!!)
        }

        for (simplex in simplices) {
            try {
                var element = Path().apply {
                    moveTo(simplex.A)
                    lineTo(simplex.B)
                    lineTo(simplex.C)
                    closePath()
                }
                sceneRaster?.surf?.canvas?.drawPath(element, sceneRaster?.skeletonPreset!!)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        //sceneRaster?.surf?.canvas?.drawTriangles(tsvp, null, null, null, BlendMode.SRC, sceneRaster?.skeletonPreset!!)
        return
    }

    fun buffer() {
        sceneRaster?.surf?.flushAndSubmit()
    }

}