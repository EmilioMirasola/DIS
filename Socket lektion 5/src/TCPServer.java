import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

	public static void main(String args[]) throws Exception {
		ServerSocket ss = new ServerSocket(6789);

		Socket s = ss.accept();
		DataInputStream inputStream = new DataInputStream(s.getInputStream());
		DataOutputStream outputStream = new DataOutputStream(s.getOutputStream());

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String str = "";
		while (!str.equals("stop")) {
			new WriteThread(br, outputStream).start();
			new ReadThread(inputStream, true).start();
		}

		inputStream.close();
		s.close();
		ss.close();
	}

}
