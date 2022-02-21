import java.io.DataInputStream;
import java.io.IOException;

public class ReadThread extends Thread {


	private final DataInputStream inputStream;
	private final boolean isServer;

	public ReadThread(DataInputStream inputStream, boolean isServer) {
		this.inputStream = inputStream;
		this.isServer = isServer;
	}

	@Override
	public void run() {
		try {
			String str = isServer ? "Server says: " : "Client says";
			System.out.println(str + inputStream.readUTF());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
