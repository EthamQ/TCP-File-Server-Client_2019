import javax.net.ssl.SSLContext;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.AttributeKey;

public class MyChannelInitializer extends ChannelInitializer<SocketChannel>{
	
	private final SSLContext sslCtx;
	public MyChannelInitializer(SSLContext sslCtx) { this.sslCtx = sslCtx; }
	
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) {
		System.out.println("Server handler added ...");
	}
	
	@Override
	protected void initChannel(SocketChannel channel) throws Exception {
		System.out.println("Server initChannel ...");
		ChannelPipeline p = channel.pipeline();
		p.addLast(new FileDecoderInbound());
		
	}

}
