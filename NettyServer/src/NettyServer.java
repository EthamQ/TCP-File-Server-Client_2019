import javax.net.ssl.SSLContext;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class NettyServer {
	
	int PORT = 3000;
	SSLContext context = null;
	
	public static void main (String[] args) {
		System.out.println("Creating Server ...");
		NettyServer nettyServer = new NettyServer();
		try {
			nettyServer.createServer();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createServer() throws InterruptedException {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
	try {
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(bossGroup, workerGroup)
		.channel( NioServerSocketChannel.class)
		.handler(new LoggingHandler(LogLevel.INFO))
		.childHandler(new MyChannelInitializer(context));
		Channel channel = bootstrap.bind(PORT).sync().channel();
		channel.closeFuture().sync();
		}
	catch(Error e) {
		System.out.println(e.getMessage());
	}
	finally {
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
		}
	}
}





