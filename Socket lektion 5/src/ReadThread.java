import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ReadThread extends Thread {

	private final DataInputStream inputStream;
	private final boolean isServer;

	public ReadThread(Socket socket, boolean isServer) throws IOException {
		this.inputStream = new DataInputStream(socket.getInputStream());
		this.isServer = isServer;
	}

	@Override
	public void run() {
		while (true) {
			try {
				String str = isServer ? "Client says : " : "Server says: ";
				System.out.println(str + inputStream.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
