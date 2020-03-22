package com.jung.network;

import com.google.common.flogger.FluentLogger;
import com.jung.network.codec.decode.ClientResponseData;
import com.jung.network.codec.encode.ClientRequestData;
import io.netty.channel.*;

import java.util.logging.Level;

/**
 * An extension of {@link SimpleChannelInboundHandler} which is useful in this case
 * vs {@link ChannelInboundHandler} because {@link SimpleChannelInboundHandler} releases the objects
 * right away. {@link ChannelInboundHandlerAdapter} does not.
 *
 * @author Jeffrey Ung
 */
public final class ClientHandler extends SimpleChannelInboundHandler<Object> {

    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ClientRequestData msg = new ClientRequestData();
        msg.setIntValue(123);
        msg.setStringValue("hello chat server");
        ChannelFuture future = ctx.writeAndFlush(msg);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object message) {
        ClientResponseData responseData = (ClientResponseData) message;
        logger.at(Level.INFO).log("Response: %s", responseData.getIntValue());
    }

}