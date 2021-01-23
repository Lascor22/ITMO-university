package ru.ifmo.rain.karimov.hello;

import info.kgeorgiy.java.advanced.hello.HelloClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.nio.channels.SelectionKey.OP_READ;
import static java.nio.channels.SelectionKey.OP_WRITE;

public class HelloUDPNonblockingClient implements HelloClient {
    private static final int SELECTOR_TIMEOUT = 600;

    @Override
    public void run(String host, int port, String prefix, int threads, int requests) {
        List<DatagramChannel> channels = new ArrayList<>();
        try {
            final InetSocketAddress address = new InetSocketAddress(InetAddress.getByName(host), port);
            Selector selector = Selector.open();
            for (int i = 0; i < threads; i++) {
                DatagramChannel channel = DatagramChannel.open();
                channel.configureBlocking(false);
                channel.connect(address);
                SelectionKey key = channel.register(selector, OP_WRITE);
                key.attach(new MetaInfo(i));
                channels.add(channel);
            }
            run(prefix, threads, requests, selector, channels);
        } catch (UnknownHostException e) {
            System.err.println("Unable to reach specified host: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error while opening channels and selector: " + e.getMessage());
        }
    }

    private void run(String prefix, int threads, int requests, Selector selector, List<DatagramChannel> channels) {
        try {
            int finished = 0;
            try {
                while (finished < threads) {
                    selector.select(SELECTOR_TIMEOUT);
                    if (selector.selectedKeys().isEmpty()) {
                        selector.keys().forEach(key -> key.interestOps(OP_WRITE));
                    }
                    for (Iterator<SelectionKey> it = selector.selectedKeys().iterator(); it.hasNext(); ) {
                        final SelectionKey key = it.next();
                        try {
                            if (key.isValid()) {
                                final DatagramChannel channel = (DatagramChannel) key.channel();
                                final MetaInfo metaInfo = (MetaInfo) key.attachment();
                                if (key.isReadable()) {
                                    if (read(requests, channel, metaInfo)) {
                                        finished++;
                                    }
                                    key.interestOps(OP_WRITE);
                                }
                                if (finished >= threads) {
                                    return;
                                }
                                if (key.isWritable()) {
                                    write(prefix, requests, channel, metaInfo);
                                    key.interestOps(OP_READ);
                                }
                            }
                        } finally {
                            it.remove();
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Error while trying to select " + e.getMessage());
            }
        } finally {
            channels.forEach(channel -> {
                try {
                    channel.close();
                } catch (IOException e) {
                    System.err.println("Error while closing channel " + e.getMessage());
                }
            });
        }
    }

    private boolean read(final int requests, final DatagramChannel channel, final MetaInfo metaInfo) {
        ByteBuffer buffer = ByteBuffer.allocate(HelloUDPUtils.BUFFER_SIZE);
        try {
            channel.receive(buffer);
        } catch (IOException e) {
            System.err.println("Error while receiving message " + e.getMessage());
            return false;
        }
        String message = HelloUDPUtils.convertToString(buffer).trim();
        if (message.contains(metaInfo.getMessage())) {
            metaInfo.incrementRequestId();
//            System.out.println((String.format("Received '%s'", message)));
            if (metaInfo.getRequestId() >= requests) {
                return true;
            }
        }
        return false;
    }

    private void write(final String prefix, final int requests,
                       final DatagramChannel channel, final MetaInfo metaInfo) {
        if (metaInfo.getRequestId() < requests) {
            String message = prefix + metaInfo.getThreadId() + "_" + metaInfo.getRequestId();
            try {
                channel.write(HelloUDPUtils.convertToByteBuffer(message));
            } catch (IOException e) {
                System.err.println("Error while sending message " + e.getMessage());
                return;
            }
            metaInfo.setMessage(message);
//            System.out.println(String.format("Sending '%s'", message));
        }
    }

    public static void main(String[] args) {
        if (args == null || args.length != 5) {
            System.out.println("Wrong input. Need <host> <port> <prefix> <threads> <requests>");
            return;
        }
        final String host, prefix;
        final int port, threads, requests;
        try {
            host = args[0];
            port = Integer.parseInt(args[1]);
            prefix = args[2];
            threads = Integer.parseInt(args[3]);
            requests = Integer.parseInt(args[4]);

        } catch (NumberFormatException e) {
            System.out.println("Arguments 'port', 'threads' and 'requests' are expected to be integers: " +
                    e.getMessage());
            return;
        }
        new HelloUDPNonblockingClient().run(host, port, prefix, threads, requests);
    }

    private static class MetaInfo {
        private final int threadId;
        private int requestId;
        private String message;

        private MetaInfo(int threadId) {
            this.threadId = threadId;
            this.requestId = 0;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getThreadId() {
            return threadId;
        }

        public int getRequestId() {
            return requestId;
        }

        public void incrementRequestId() {
            requestId++;
        }
    }
}
