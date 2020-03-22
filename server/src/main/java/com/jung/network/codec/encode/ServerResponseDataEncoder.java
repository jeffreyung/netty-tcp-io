package com.jung.network.codec.encode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ServerResponseDataEncoder
        extends MessageToByteEncoder<ServerResponseData> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ServerResponseData msg, ByteBuf out) {
        out.writeInt(msg.getIntValue());
    }
}
