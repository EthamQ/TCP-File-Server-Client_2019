import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.DefaultFileRegion;

public class FileTransfer {
	
	enum FILETYPE { TEXT, IMAGE }
	
	String filePath;
	String fileName;
	Channel channel;
	static final String FILE_PATH = "C:\\Users\\rapha\\Desktop\\clientFiles\\";
	
	static final String TEXT_FILE_NAME = "clientText.txt";
	static final String TEXT_FILE_PATH = FILE_PATH + TEXT_FILE_NAME;
	
	static final String IMAGE_FILE_NAME = "clientImage.jpg";
	static final String IMAGE_FILE_PATH = FILE_PATH + IMAGE_FILE_NAME;
	
	public FileTransfer(ChannelFuture channelFuture) {
		this.channel = channelFuture.channel();
	}
	
	public void setFileType(FILETYPE type) {
		switch(type) {
		case TEXT:
			this.filePath = TEXT_FILE_PATH;
			this.fileName = TEXT_FILE_NAME;
			break;
		case IMAGE:
			this.filePath = IMAGE_FILE_PATH;
			this.fileName = IMAGE_FILE_NAME;
			break;
		}
	}
	
	public void sendFile() throws IOException {
		File file = new File(this.filePath);
 		RandomAccessFile raf = new RandomAccessFile(file,  "r");
 		DefaultFileRegion fileToWrite = new DefaultFileRegion(raf.getChannel(), 0, raf.length());
 		ClientFile toSend = new ClientFile(this.fileName, raf.length(), fileToWrite);
 		this.channel.writeAndFlush(toSend);
	}

}
