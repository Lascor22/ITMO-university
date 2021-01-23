package ru.ifmo.rain.karimov.hello;

import info.kgeorgiy.java.advanced.hello.HelloServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.nio.channels.SelectionKey.OP_READ;
import static java.nio.channels.SelectionKey.OP_WRITE;

public class HelloUDPNonblockingServer implements HelloServer {
    private DatagramChannel serverChannel;
    private Selector selector;
    private ExecutorService mainThread;

    public static void main(String[] args) {
        if (args == null || args.length != 2) {
            System.out.println("Wrong input. Need <port> <threads>");
            return;
        }
        int port, threads;

        try {
            port = Integer.parseInt(args[0]);
            threads = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Arguments 'port' and 'threads' are expected to be integers: " + e.getMessage());
            return;
        }

        try (final HelloServer server = new HelloUDPNonblockingServer()) {
            server.start(port, threads);
        }
    }

    @Override
    public void start(final int port, final int threads) {
        mainThread = Executors.newSingleThreadExecutor();
        final InetSocketAddress address = new InetSocketAddress(port);
        try {
            selector = Selector.open();
            serverChannel = DatagramChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.register(selector, OP_READ);
            serverChannel.bind(address);
        } catch (IOException e) {
            System.err.println("Can't open channel and selector " + e.getMessage());
            return;
        }
        mainThread.submit(this::serve);
    }

    private void serve() {
        try {
            while (!Thread.interrupted()) {
                if (selector.select() > 0) {
                    for (Iterator<SelectionKey> it = selector.selectedKeys().iterator(); it.hasNext(); ) {
                        final SelectionKey key = it.next();
                        try {
                            if (key.isValid()) {
                                final DatagramChannel channel = (DatagramChannel) key.channel();
                                final MetaInfo metaInfo = (MetaInfo) key.attachment();
                                if (key.isReadable()) {
                                    ByteBuffer buffer = ByteBuffer.allocate(HelloUDPUtils.BUFFER_SIZE);
                                    SocketAddress address;
                                    try {
                                        address = channel.receive(buffer);
                                    } catch (IOException e) {
                                        System.err.println("Error while trying to receive: " + e.getMessage());
                                        return;
                                    }
                                    if (!HelloUDPUtils.isEmpty(buffer)) {
                                        String message = HelloUDPUtils.convertToString(buffer).trim();
                                        key.attach(new MetaInfo(address, message));
                                        key.interestOps(OP_WRITE);
                                    }
                                } else if (key.isWritable()) {
                                    String message = "Hello, " + metaInfo.getMessage();
                                    ByteBuffer responseBuffer = HelloUDPUtils.convertToByteBuffer(message);
                                    try {
                                        channel.send(responseBuffer, metaInfo.getAddress());
                                    } catch (IOException e) {
                                        System.err.println("Error while trying to send: " + e.getMessage());
                                    }
                                    key.interestOps(OP_READ);
                                }
                                selector.wakeup();
                            }
                        } finally {
                            it.remove();
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error while trying to select " + e.getMessage());
        }
    }

    @Override
    public void close() {
        mainThread.shutdown();
        try {
            mainThread.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("Could not terminate executor pools: " + e.getMessage());
        }

        try {
            serverChannel.close();
            selector.close();
        } catch (IOException e) {
            System.err.println("Could not close channel or selector " + e.getMessage());
        }
    }

    private static class MetaInfo {
        private final SocketAddress address;
        private final String message;

        public MetaInfo(SocketAddress address, String message) {
            this.address = address;
            this.message = message;
        }

        public SocketAddress getAddress() {
            return address;
        }

        public String getMessage() {
            return message;
        }

    }
}
