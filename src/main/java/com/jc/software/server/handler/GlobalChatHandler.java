package com.jc.software.server.handler;

import com.jc.software.messages.ChatMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import org.apache.log4j.Logger;

/**
 * Created by jonataschagas on 13/03/18.
 */
public class GlobalChatHandler extends ChannelInboundHandlerAdapter {

    final static Logger logger = Logger.getLogger(GlobalChatHandler.class);
    private final static int BUFFER_SIZE = 500;

    private ByteBuf tmp;
    private ChannelGroup channelGroup;

    public GlobalChatHandler(ChannelGroup channels) {
        this.channelGroup = channels;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        channelGroup.add(ctx.channel());
        if(tmp == null) {
            tmp = ctx.alloc().buffer(BUFFER_SIZE);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        channelGroup.remove(ctx.channel());
        if(channelGroup.isEmpty()) {
            tmp.release();
            tmp = null;
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ChatMessage chatMessage = (ChatMessage) msg;
        logger.info(chatMessage.getFullMessage());
        for (Channel ch : channelGroup) {
            ch.writeAndFlush(chatMessage);
        }
    }


}
