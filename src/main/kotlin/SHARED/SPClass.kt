package crossxyed.SHARED

import org.jetbrains.skia.Path

object SPClass {
    var sceneRaster: SceneRaster? = null
    var vertices = Array<Vertex>(
        0,
        init = TODO()
    );

    fun recvWindow() {
        sceneRaster = SceneRaster()
    }

    fun sendGeometry() {
        while(vertices.isNotEmpty()) {
            var element: Path? = Path()
            when(vertices.last()::class.simpleName){
                "Rectangle" -> {
                   element = sceneRaster?.let { drawRectangle(vertices.last() as Rectangle, it.brush) }
                }
                "Ngon" -> {
                    element = sceneRaster?.let { drawNgon(vertices.last() as Ngon, it.brush) }
                }

                "Bevel" -> {
                    element = null
                }
            }
            if (element == null) {
                return
            }
            sceneRaster?.surf?.canvas?.drawPath(element, skeleton)
        }
        return
    }

    fun overlay() {

    }
}