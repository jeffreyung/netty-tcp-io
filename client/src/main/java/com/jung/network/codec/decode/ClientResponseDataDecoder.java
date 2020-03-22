package com.jung.network.codec.decode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class ClientResponseDataDecoder extends ReplayingDecoder<ClientResponseData> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        ClientResponseData data = new ClientResponseData();
        data.setIntValue(in.readInt());
        out.add(data);
    }
}
