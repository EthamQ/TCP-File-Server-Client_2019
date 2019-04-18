import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

enum DecState {
	READ_FILE_NAME_LENGTH, READ_FILE_NAME, READ_FILE
}

public class FileDecoderInbound extends ReplayingDecoder<DecState>{
	
	final static String SERVER_FILES_PATH = "C:\\Users\\rapha\\Desktop\\serverFiles\\";
	

	@Override
	protected void decode(ChannelHandlerContext arg0, ByteBuf in, List<Object> arg2) throws Exception {
		// File name length
		int fileNameLength = in.readInt();
		
		// File name
		ByteBuf fileNameBuf = in.readBytes(fileNameLength);
		byte[] byteArray = fileNameBuf.array();
		String fileName  = new String(byteArray);
		
		// File length
		long fileLength = in.readLong();
		
		// Create File and file channel
		File file = new File(SERVER_FILES_PATH + fileName);
		if (!file.exists()) {
			file.createNewFile();
			}
		ByteBuffer byteBuffer = toNioBuffer(in, fileLength);  
		RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
		FileChannel fileChannel = randomAccessFile.getChannel();
		
		                       
		
		// Write to file channel
		while (byteBuffer.hasRemaining()) {
			 fileChannel.position(file.length());
			 fileChannel.write(byteBuffer);
		}
		fileChannel.close();
		randomAccessFile.close();
	}
	
	public static ByteBuffer toNioBuffer(ByteBuf buffer, long fileLength) {
	    final byte[] bytes = new byte[(int) fileLength];
	    buffer.getBytes(buffer.readerIndex(), bytes);
	    return ByteBuffer.wrap(bytes);
	}
}
