import io.netty.channel.DefaultFileRegion;

public class ClientFile {
	
	String fileName;
	long fileLength;
	DefaultFileRegion clientFile;
	
	public ClientFile(String fileName, long fileLength, DefaultFileRegion clientFile) {
		this.fileName = fileName;
		this.fileLength = fileLength;
		this.clientFile = clientFile;
	}

}
