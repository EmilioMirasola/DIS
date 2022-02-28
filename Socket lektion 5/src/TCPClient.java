import dns.TcpDomainClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;


public class TCPClient {

	public static void main(String[] args) throws Exception {
		Socket socket = new Socket("localhost", 6790);
		TcpDomainClient domainClient = new TcpDomainClient(socket);


		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Hvis du vil tilføje et domæne, skriv da: 'tilmeld' + 'domænenavn' + 'ip adresse' ");
		System.out.println("Ellers indtast et domæne du vil kontakte");
		System.out.println("Ellers indtast 'domæner' for at få alle registrerede domæner");
		while (true) {
			String input = br.readLine();


			if (input.split(" ")[0].equalsIgnoreCase("tilmeld")) {
				String response = domainClient.register(input);
				System.out.println(response);
			} else if (input.split(" ")[0].equalsIgnoreCase("domæner")) {
				String domains = domainClient.getDomains(input);
				System.out.println("Registrerede domæner: " + domains);
			} else {
				String ip = domainClient.getIp(input);

				if (ip != null && !ip.isBlank()) {
					Socket domainSocket = new Socket(ip, 6789);
					System.out.println("Forbindelse oprettet til IP: " + ip);
					new WriteThread(domainSocket).start();
					new ReadThread(domainSocket, false).start();
				}
			}
		}
	}
}
