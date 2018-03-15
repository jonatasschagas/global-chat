package com.jc.software.server.main;

import com.jc.software.server.decoder.ChatMessageDecoder;
import com.jc.software.server.encoder.ChatMessageEncoder;
import com.jc.software.server.handler.GlobalChatHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.log4j.Logger;

/**
 * Created by jonataschagas on 13/03/18.
 */
public class GlobalChatServer {

    final static Logger logger = Logger.getLogger(GlobalChatServer.class);

    public static int PORT = 9544;
    public static String HOST = null;

    public static void main(String[] args) throws Exception {
        HOST = args[1];
        new GlobalChatServer().run();
    }

    public void run() throws Exception {
        final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            ch.pipeline().addLast(
                                    new ChatMessageDecoder(),
                                    new ChatMessageEncoder(),
                                    new GlobalChatHandler(channels)
                            );
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);


            ChannelFuture f = b.bind(PORT).sync();
            f.channel().closeFuture().sync();
            logger.info("Server is up and running. Host " + HOST + " and listening to port " + PORT);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

}
