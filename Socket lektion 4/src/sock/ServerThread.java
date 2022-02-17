package sock;

import javax.print.DocFlavor;
import java.net.*;
import java.io.*;

public class ServerThread extends Thread {
	Socket connSocket;

	public ServerThread(Socket connSocket) {
		this.connSocket = connSocket;
	}

	public void run() {
		try {
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connSocket.getOutputStream());

			// Do the work and the communication with the client here
			// The following two lines are only an example
			String clientSentence = inFromClient.readLine();
			System.out.println("clientSentence = " + clientSentence);

			String docName = "C:/Users/emili/datamatiker/DIS/DIS prog/Socket lektion 4/src/sock" + clientSentence.split(" ")[1];
			System.out.println("docName = " + docName);

			String contentType = ContentType(docName);

			System.out.println("contentType = " + contentType);

			outToClient.writeBytes("HTTP 200 OK\n");
			outToClient.writeBytes(contentType);
			outToClient.writeBytes("Connection:Close\n");
			outToClient.writeBytes("\n");

			outToClient.write(read(docName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// do the work here
	}

	public String ContentType(String docuname) {
		//returns the Content-Type headerline for a given document-name
		if (docuname.endsWith(".html")) {
			return ("Content-Type: text/html\n");
		} else if (docuname.endsWith(".gif")) {
			return ("Content-Type: image/gif\n");
		} else if (docuname.endsWith(".png")) {
			return ("Content-Type: image/png\n");
		} else if (docuname.endsWith(".jpg")) {
			return ("Content-Type: image/jpg\n");
		} else if (docuname.endsWith(".js")) {
			return ("Content-Type: text/javascript\n");
		} else if (docuname.endsWith(".css")) {
			return ("Content-Type: text/css\n");
		} else {
			return ("Content-Type: text/plain\n");
		}
	}

	public byte[] read(String aInputFileName) throws FileNotFoundException {
		// returns the content of a file in a binary array
		System.out.println("Reading in binary file named : " + aInputFileName);
		File file = new File(aInputFileName);
		System.out.println("File size: " + file.length());
		byte[] result = new byte[(int) file.length()];
		try {
			InputStream input = null;
			try {
				int totalBytesRead = 0;
				input = new BufferedInputStream(new FileInputStream(file));
				while (totalBytesRead < result.length) {
					int bytesRemaining = result.length - totalBytesRead;
					int bytesRead = input.read(result, totalBytesRead, bytesRemaining);
					// input.read() returns -1, 0, or more :
					if (bytesRead > 0) {
						totalBytesRead = totalBytesRead + bytesRead;
					}
				}
				System.out.println("Num bytes read: " + totalBytesRead);
			} finally {
				System.out.println("Closing input stream.");
				input.close();
			}
		} catch (FileNotFoundException ex) {
			throw ex;
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return result;
	}
}
