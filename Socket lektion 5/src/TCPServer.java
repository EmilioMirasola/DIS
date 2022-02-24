import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

	public static void main(String[] args) throws Exception {
		ServerSocket serverSocket = new ServerSocket(6789);
		Socket socket = serverSocket.accept();

		new WriteThread(socket).start();
		new ReadThread(socket, true).start();

	}
}
