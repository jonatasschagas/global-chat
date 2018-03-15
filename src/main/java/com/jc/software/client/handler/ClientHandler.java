package com.jc.software.client.handler;

import com.jc.software.messages.ChatMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;

/**
 * Created by jonataschagas on 14/03/18.
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    final static Logger logger = Logger.getLogger(ClientHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx)
            throws Exception {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        logger.info(((ChatMessage) msg).getFullMessage());
    }

}
