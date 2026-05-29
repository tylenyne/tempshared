package JVMSYS;

import org.lwjgl.PointerBuffer;
import org.lwjgl.system.*;
import org.lwjgl.system.libffi.FFICIF;

import static org.lwjgl.system.libffi.LibFFI.*;

public class Bass {
    static SharedLibrary hso;
    static long createChannel, channelPlay, channelStop;

    public static void init() {
       hso = Library.loadNative(Bass.class, "bass.dll", "bass.dll");
       long bass_init = hso.getFunctionAddress("BASS_Init");
       createChannel = hso.getFunctionAddress("BASS_StreamCreateFile");
       channelPlay = hso.getFunctionAddress("BASS_ChannelPlay");
       channelStop = hso.getFunctionAddress("BASS_ChannelStop");
       try (MemoryStack stack = MemoryStack.stackPush()) {
           FFICIF call = FFICIF.calloc(stack);
           PointerBuffer argType = stack.pointers(
                   ffi_type_sint32,
                   ffi_type_uint32,
                   ffi_type_uint32,
                   ffi_type_pointer,
                   ffi_type_pointer
           );

           ffi_prep_cif(call, FFI_DEFAULT_ABI, ffi_type_sint32, argType);

           PointerBuffer args = stack.mallocPointer(argType.capacity());
           args.put(stack.ints(-1))
                   .put(MemoryUtil.memAddress(stack.ints(48000)))
                   .put(MemoryUtil.memAddress(stack.ints(0)))
                   .put(MemoryUtil.memAddress(stack.longs(0))) //Could technically give window
                   .put(MemoryUtil.memAddress(stack.longs(0)));
           ffi_call(call, bass_init, stack.malloc(4), args);

       }
    }

    public static void createChannel() {

    }

    public static void channelPlay(String resource) {

    }

    public static void channelStop() {

    }
}
