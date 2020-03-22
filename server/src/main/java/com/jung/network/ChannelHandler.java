package com.jung.network;

import com.google.common.flogger.FluentLogger;
import com.jung.network.codec.decode.RequestData;
import com.jung.network.codec.encode.ResponseData;
import io.netty.channel.*;

import java.util.logging.Level;

/**
 * An extension of {@link SimpleChannelInboundHandler} which is useful in this case
 * vs {@link ChannelInboundHandler} because {@link SimpleChannelInboundHandler} releases the objects
 * right away. {@link ChannelInboundHandlerAdapter} does not.
 *
 * @author Jeffrey Ung
 */
public final class ChannelHandler extends SimpleChannelInboundHandler<Object> {

    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object message) {
        RequestData requestData = (RequestData) message;
        ResponseData responseData = new ResponseData();
        responseData.setIntValue(requestData.getIntValue() * 2);
        ChannelFuture future = ctx.writeAndFlush(responseData);
        future.addListener(ChannelFutureListener.CLOSE);
        logger.at(Level.INFO).log("Request: %s", requestData.getStringValue());
    }

}