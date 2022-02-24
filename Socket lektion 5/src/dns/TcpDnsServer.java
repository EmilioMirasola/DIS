package dns;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class TcpDnsServer {
	static HashMap<String, String> map = new HashMap<>();

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(6790);
		Socket socket = serverSocket.accept();
		DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
		DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

		map.put("Jeppe", "10.10.139.136");
		map.put("Emilio", "10.10.131.94");

		while (true) {
			String[] request = dataInputStream.readLine().split(" ");
			if (request[0].equalsIgnoreCase("tilmeld")) {
				signUpDomain(request);
				dataOutputStream.writeBytes("Domænet: " + request[1] + " er blevet tilføjet \n");
			} else if (request[0].equalsIgnoreCase("domæner")) {
				dataOutputStream.writeBytes(map.toString()+ "\n");
			} else {
				String ip = getDomainIp(request[0]);
				dataOutputStream.writeBytes(ip + "\n");
			}
		}
	}

	private static String getDomainIp(String domain) {
		return map.get(domain);
	}

	private static void signUpDomain(String[] request) {
		map.put(request[1], request[2]);
	}
}
