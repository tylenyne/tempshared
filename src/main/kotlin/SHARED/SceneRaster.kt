package crossxyed.SHARED

import org.jetbrains.skia.*

data class SceneRaster(val imageinfo: ImageInfo = ImageInfo(800, 600, ColorType.RGBA_8888, ColorAlphaType.PREMUL),
                       val backendRt: BackendRenderTarget = BackendRenderTarget.makeGL(800, 600, 0, 8, 0, FramebufferFormat.GR_GL_RGBA8),
                       val aspectratio: Float = 16/9f
){
    //////Skiko stuff

    val surf = Surface.makeFromBackendRenderTarget(
            DirectContext.makeGL(),
            backendRt,
            SurfaceOrigin.BOTTOM_LEFT,
            SurfaceColorFormat.RGBA_8888,
            ColorSpace.sRGB
        )


    val skeletonPreset = Paint().apply {
            color = Color.WHITE;
            mode = PaintMode.STROKE;
            strokeWidth = 4f;
    }

    val fleshPreset = Paint().apply {
        color = Color.WHITE;
        mode = PaintMode.FILL;
        strokeWidth = 4f;
    }

    val backgroundPreset = Paint().apply {
            color = Color.RED;
            mode = PaintMode.FILL;
            strokeWidth = 4f;
    }

    val brush = Path()

}