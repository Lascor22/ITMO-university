package ru.ifmo.rain.karimov.hello;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class HelloUDPUtils {

    public static final int BUFFER_SIZE = 1024;

    public static String convertToString(ByteBuffer buffer) {
        return new String(buffer.array(), StandardCharsets.UTF_8);
    }

    public static ByteBuffer convertToByteBuffer(String string) {
        return StandardCharsets.UTF_8.encode(CharBuffer.wrap(string));
    }

    public static boolean isEmpty(Buffer buffer) {
        return buffer.limit() - buffer.position() == buffer.capacity();
    }

}

