import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.ReferenceCountUtil;

public class FileEncoderOutbound extends MessageToMessageEncoder<ClientFile> {

  @Override
  public void encode(final ChannelHandlerContext ctx, final ClientFile msg, List<Object> out) throws Exception {
	ReferenceCountUtil.retain(msg);
	out.add(createHeader(ctx, msg));
    out.add(msg.clientFile);
  }

  public ByteBuf createHeader(final ChannelHandlerContext ctx, final ClientFile msg) {
	final int HEADER_LENGTH = 4 * 2 + msg.fileName.length();
    ByteBuf header = ctx.alloc().buffer(HEADER_LENGTH);
    header.writeInt(msg.fileName.length());
    header.writeBytes(msg.fileName.getBytes());
    header.writeLong(msg.fileLength);
    return header;
  }


}