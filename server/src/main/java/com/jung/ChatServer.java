package com.jung;

import com.google.common.flogger.FluentLogger;
import com.jung.network.ChannelHandler;
import com.jung.network.ServiceChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.logging.Level;

public class ChatServer {

    private static final int PORT = 43594;

    /**
     * Logging system used to to log messages for the chat server.
     */
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    /**
     * Constructs a new chat server.
     */
    private ChatServer() {
        logger.at(Level.INFO).log("Chat server is initializing...");
    }

    /**
     * Main method for the chat server.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        try {
            ChatServer chatServer = new ChatServer();
            chatServer.init(ChatServer.PORT);
        } catch (Exception e) {
            logger.at(Level.SEVERE).withCause(e).log();
        }
    }

    /**
     * Initializes the chat server.
     *
     * @throws InterruptedException An exception is thrown when there is an interruption.
     */
    private void init(int port) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {

            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ServiceChannelInitializer(new ChannelHandler()))
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            logger.at(Level.INFO).log("Binding to port %d", port);
            ChannelFuture f = bootstrap.bind(port).sync();
            logger.at(Level.INFO).log("Server is now online");
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

}
