package com.jc.software.server.encoder;

import com.jc.software.messages.ChatMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by jonataschagas on 14/03/18.
 */
public class ChatMessageEncoder extends MessageToByteEncoder<ChatMessage> {
    protected void encode(ChannelHandlerContext channelHandlerContext,
                          ChatMessage chatMessage, ByteBuf byteBuf) throws Exception {
        chatMessage.encode(byteBuf);
    }
}
