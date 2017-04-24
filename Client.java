package iCourse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	public static void main(String[] args) throws IOException {
		try {
			Socket socket = new Socket("localhost", 5055);
			System.out.println("client start ! ");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter write = new PrintWriter(socket.getOutputStream());
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String readline;
			readline = br.readLine();
			while (!readline.equals("end")) {
				write.println(readline);
				write.flush();
				System.out.println("Client:" + readline);
				System.out.println("Server:" + in.readLine());
				readline = br.readLine();
			}
			write.close();
			in.close();
			socket.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error  : " + e);
		}
	}
}
