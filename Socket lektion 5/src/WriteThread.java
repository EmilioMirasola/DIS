import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;

public class WriteThread extends Thread {

	private final BufferedReader br;
	private final DataOutputStream outputStream;

	public WriteThread(BufferedReader br, DataOutputStream outputStream) {
		this.br = br;
		this.outputStream = outputStream;
	}

	@Override
	public void run() {
		try {
			String line = br.readLine();
			outputStream.writeUTF(line);
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
