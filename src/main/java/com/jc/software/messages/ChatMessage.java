package com.jc.software.messages;

import io.netty.buffer.ByteBuf;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jonataschagas on 13/03/18.
 */
public class ChatMessage {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private String message;
    private String sender;
    private long timestamp;

    public ChatMessage() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void encode(ByteBuf byteBuffer) {
        byteBuffer.writeLong(timestamp);
        byteBuffer.writeInt(sender.length());
        byteBuffer.writeBytes(sender.getBytes());
        byteBuffer.writeInt(message.length());
        byteBuffer.writeBytes(message.getBytes());
    }

    public void decode(ByteBuf byteBuffer) {
        timestamp = byteBuffer.readLong();
        int senderLength = byteBuffer.readInt();
        byte[] senderBta = new byte[senderLength];
        byteBuffer.readBytes(senderBta);
        int messageLength = byteBuffer.readInt();
        byte[] messageBta = new byte[messageLength];
        byteBuffer.readBytes(messageBta);
        sender = new String(senderBta);
        message = new String(messageBta);
    }

    public String getFullMessage() {
        return sdf.format(new Date(timestamp)) + ":" + sender + ": " + message;
    }

}
