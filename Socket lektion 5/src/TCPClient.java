import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;


public class TCPClient {

	public static void main(String args[]) throws Exception {
		Socket socket = new Socket("localhost", 6789);
		DataInputStream inputStream = new DataInputStream(socket.getInputStream());
		DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

		String str = "";
		while (!str.equals("stop")) {
			new WriteThread(bufferedReader, outputStream).start();
			new ReadThread(inputStream, false).start();
		}

		outputStream.close();
		socket.close();
	}
}
