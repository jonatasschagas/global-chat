package com.jc.software.client.main;

import com.jc.software.client.decoder.ChatMessageDecoder;
import com.jc.software.client.encoder.ChatMessageEncoder;
import com.jc.software.client.handler.ClientHandler;
import com.jc.software.messages.ChatMessage;
import com.jc.software.server.main.GlobalChatServer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.log4j.Logger;

import java.util.Scanner;

/**
 * Created by jonataschagas on 14/03/18.
 */
public class GlobalChatClient {

    final static Logger logger = Logger.getLogger(GlobalChatClient.class);

    public static void main(String[] args) throws Exception {

        String host = args[1];
        int port = GlobalChatServer.PORT;
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {

                @Override
                public void initChannel(SocketChannel ch)
                        throws Exception {
                    ch.pipeline().addLast(
                            new ChatMessageEncoder(),
                            new ChatMessageDecoder(),
                            new ClientHandler());
                }
            });

            ChannelFuture f = b.connect(host, port).sync();
            Channel channel = f.channel();
            ChatClientUI chatClientUI = new ChatClientUI(channel);
            chatClientUI.start();
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    private static class ChatClientUI extends Thread {

        private Channel channel;

        public ChatClientUI(Channel channel) {
            this.channel = channel;
        }

        @Override
        public void run() {

            Scanner sc = new Scanner(System.in);
            String sendeName = null;
            logger.info("What's your name?");
            sendeName = read(sc);
            logger.info("Type enter to send messages. Happy chatting!");
            while(true) {
                String message = read(sc);
                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setSender(sendeName);
                chatMessage.setTimestamp(System.currentTimeMillis());
                chatMessage.setMessage(message);
                channel.writeAndFlush(chatMessage);
            }
        }

    }

    static String read(Scanner sc) {
        return sc.nextLine();
    }


}
