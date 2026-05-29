package JVMSYS;

import org.lwjgl.PointerBuffer;
import org.lwjgl.system.*;
import org.lwjgl.system.libffi.FFICIF;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.file.Paths;

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
           args.put(MemoryUtil.memAddress(stack.ints(-1)))
                   .put(MemoryUtil.memAddress(stack.ints(48000)))
                   .put(MemoryUtil.memAddress(stack.ints(0)))
                   .put(MemoryUtil.memAddress(stack.longs(0))) //Could technically give window
                   .put(MemoryUtil.memAddress(stack.longs(0))).flip(); //Do have to flip it for some reason
           ByteBuffer retcode = stack.malloc(4);
           ffi_call(call, bass_init, retcode, args);
           System.out.println("BASS_Init: " + retcode.getInt());
       }
    }

    public static long createChannel(String filename) throws URISyntaxException {
        String filePath = Paths.get(Bass.class.getClassLoader().getResource(filename).toURI()).toAbsolutePath().toString();
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FFICIF call = FFICIF.calloc(stack);
            PointerBuffer argType = stack.pointers(
                    ffi_type_uint32,
                    ffi_type_pointer,
                    ffi_type_uint64,
                    ffi_type_uint64,
                    ffi_type_uint32
            );

            ByteBuffer fileStr = stack.ASCII(filePath, true);
            long fileStrAddr = MemoryUtil.memAddress(fileStr); //Because char** pretty dumb

            ffi_prep_cif(call, FFI_DEFAULT_ABI, ffi_type_uint32, argType);
            PointerBuffer args = stack.mallocPointer(argType.capacity());
            args.put(MemoryUtil.memAddress(stack.ints(0)))
                    .put(MemoryUtil.memAddress(stack.longs(fileStrAddr)))
                    .put(MemoryUtil.memAddress(stack.longs(0)))
                    .put(MemoryUtil.memAddress(stack.longs(0)))
                    .put(MemoryUtil.memAddress(stack.ints(0))).flip();
            ByteBuffer retcode = stack.malloc(4);
            ffi_call(call, createChannel, retcode, args);
            long stream = retcode.getInt();
            System.out.println("CreateChannel: " + stream);
            return stream;
        }
    }

    public static void channelPlay(long stream) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FFICIF call = FFICIF.calloc(stack);
            PointerBuffer argType = stack.pointers(ffi_type_uint32);

            ffi_prep_cif(call, FFI_DEFAULT_ABI, ffi_type_uint32, argType);

            PointerBuffer args = stack.mallocPointer(argType.capacity());
            args.put(MemoryUtil.memAddress(stack.ints((int) stream))).flip();

            ByteBuffer retcode = stack.malloc(4);
            ffi_call(call, channelPlay, retcode, args);

            System.out.println("ChannelPlay: " + retcode.getInt());
        }
    }

    public static void channelStop() {

    }

    public static void getError() {
        long getError = hso.getFunctionAddress("BASS_ErrorGetCode");

        try (MemoryStack stack = MemoryStack.stackPush()) {
            FFICIF call = FFICIF.calloc(stack);
            PointerBuffer argType = stack.mallocPointer(0);

            ffi_prep_cif(call, FFI_DEFAULT_ABI, ffi_type_uint32, argType);

            PointerBuffer args = stack.mallocPointer(argType.capacity());

            ByteBuffer retcode = stack.malloc(4);
            ffi_call(call, getError, retcode, args);

            System.out.println("GetError: " + retcode.getInt());
            System.exit(0);
        }
    }
}
