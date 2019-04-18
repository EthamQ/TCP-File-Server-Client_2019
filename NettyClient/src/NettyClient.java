
import java.net.InetSocketAddress;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;


public class NettyClient {
	
	final String HOST = "localhost";
	final int PORT = 3000;
	
	public static void main(String[] args) {
		try {
			System.out.println("Connecting client ...");
			new NettyClient().connectClient();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void connectClient() throws InterruptedException {
	    Bootstrap clientBootstrap = new Bootstrap();
		EventLoopGroup group = new NioEventLoopGroup();
		try{
		    clientBootstrap.group(group);
		    clientBootstrap.channel(NioSocketChannel.class);
		    clientBootstrap.remoteAddress(new InetSocketAddress(HOST, PORT));
		    clientBootstrap.handler(new MyChannelInitializer());
		    ChannelFuture channelFuture = clientBootstrap.connect().sync();
		    this.sendFile(channelFuture);
		    channelFuture.channel().closeFuture().sync();
		} 
		catch(Exception e) {
			System.out.println("Client connect error: " + e.getMessage());
		}
		finally {
		    group.shutdownGracefully().sync();
		}
	}

	public void sendFile(ChannelFuture channelFuture) throws Exception {
		System.out.println("Sending file ...");
		FileTransfer fileTransfer = new FileTransfer(channelFuture);
		fileTransfer.setFileType(FileTransfer.FILETYPE.TEXT);
		fileTransfer.sendFile();
		}
	
}
