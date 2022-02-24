import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class WriteThread extends Thread {

	private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private final DataOutputStream outputStream;

	public WriteThread(Socket socket) throws IOException {
		this.outputStream = new DataOutputStream(socket.getOutputStream());
	}

	@Override
	public void run() {
		while (true) {
			try {
				String line = br.readLine();
				outputStream.writeBytes(line + "\n");
				outputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
