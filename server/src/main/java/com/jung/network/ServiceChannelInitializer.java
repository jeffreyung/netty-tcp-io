package com.jung.network;

import com.google.common.flogger.FluentLogger;
import com.jung.network.codec.decode.RequestDecoder;
import com.jung.network.codec.encode.ResponseDataEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.util.logging.Level;

/**
 * A {@link ChannelInitializer} for the service pipeline
 *
 * @author Jeffrey Ung
 */
public class ServiceChannelInitializer extends ChannelInitializer<SocketChannel> {

    private static final FluentLogger logger = FluentLogger.forEnclosingClass();
    private final ChannelHandler handler;

    public ServiceChannelInitializer(ChannelHandler handler) {
        this.handler = handler;
    }

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("timeout", new ReadTimeoutHandler(10));
        pipeline.addLast("encoder", new ResponseDataEncoder());
        pipeline.addLast("decoder", new RequestDecoder());
        pipeline.addLast("handler", handler);
        logger.at(Level.INFO).log("Connection received from %s", ch.remoteAddress().getAddress());
    }

}