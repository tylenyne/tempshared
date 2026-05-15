package crossxyed.SHARED

import org.jetbrains.skia.*

object Scene {
    val imageinfo by lazy {
        ImageInfo(800, 600, ColorType.RGBA_8888, ColorAlphaType.PREMUL)
    }
    val backendRt by lazy {
         BackendRenderTarget.makeGL(800, 600, 0, 8, 0, FramebufferFormat.GR_GL_RGBA8)
    }
    val video by lazy {
        Surface.makeFromBackendRenderTarget(DirectContext.makeGL(), backendRt, SurfaceOrigin.BOTTOM_LEFT, SurfaceColorFormat.RGBA_8888, ColorSpace.sRGB)
    }
    val brush by lazy {
        Path()
    }
}