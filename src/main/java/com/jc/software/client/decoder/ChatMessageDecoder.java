package com.jc.software.client.decoder;

import com.jc.software.messages.ChatMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * Created by jonataschagas on 14/03/18.
 */
public class ChatMessageDecoder extends ReplayingDecoder<ChatMessage> {
    protected void decode(ChannelHandlerContext channelHandlerContext,
                          ByteBuf byteBuf, List<Object> list) throws Exception {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.decode(byteBuf);
        list.add(chatMessage);
    }
}
