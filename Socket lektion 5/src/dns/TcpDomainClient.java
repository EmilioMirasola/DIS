package dns;

import java.io.*;
import java.net.Socket;

public class TcpDomainClient {

	private final Socket socket;

	public TcpDomainClient(Socket socket) {
		this.socket = socket;
	}

	public String getIp(String domain) throws IOException {
		DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
		dataOutputStream.writeBytes(domain + "\n");

		DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
		String ip = dataInputStream.readLine();
		if (ip == null || ip.isBlank()) {
			System.out.println("Domænet: " + domain + " kendes ikke");
		}
		return ip;
	}

	public String register(String domainAndIp) throws IOException {
		DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
		dataOutputStream.writeBytes(domainAndIp + "\n");
		DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
		return dataInputStream.readLine();
	}

	public String getDomains(String input) throws IOException {
		DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
		dataOutputStream.writeBytes(input + "\n");

		DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
		return dataInputStream.readLine();
	}
}
