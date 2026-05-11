package crossxyed
import org.jetbrains.skia.Surface

class SceneHandler(fframe: Surface) {

    fun build(shapes: Array<Geometry>): Surface {
        while(shapes.isNotEmpty()) {
            when(shapes.last()::class.simpleName){
                "Rectangle" -> {

                }
            }
        }
    }

    fun overlay() {

    }
}