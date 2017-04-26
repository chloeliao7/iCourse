package iCourse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Server server = new Server();
		server.oneServer();
	}

	private void oneServer() {
		// TODO Auto-generated method stub
		int port = 5055;
		try {
			ServerSocket ss = null;
			try {
				ss = new ServerSocket(port);
				System.out.println("server start is ok!");
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("can not listen to : " + e);
			}
			Socket s = null;
			try {
				s = ss.accept();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Error : " + e);
			}
			String line;
			BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			   //The input stream is obtained by the Socket object, and the corresponding BufferedReader object is constructed
			PrintWriter writer = new PrintWriter(s.getOutputStream());
			   //The output stream is obtained by the Socket object and the PrintWriter object is constructed
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			   // Constructing BufferedReader objects from system standard input devices
			System.out.println("Client:" + in.readLine());
			   //Prints a string read from the client on the standard output

			line = br.readLine();
			   //Reads a string from the standard input
			while (!line.equals("end")) {
				   //If the string is "Bye", then the loop is stopped
				writer.println(line);
				   //output the string to the client
				writer.flush();
				   //Refresh the output stream so that Client receives the string immediately
				System.out.println("Server:" + line);
				   //Print read string on system standard output
				System.out.println("Client:" + in.readLine());
				   //Reads a string from the Client and prints it to the standard output
				line = br.readLine();
				   //Reads a string from the standard input of the system
			}
			writer.close();
			   //Turn off the Socket output stream
			in.close();
			   //Close Socket input stream
			in.close();
			s.close(); 
			   // close socket
			ss.close();
			   //close serversocket
		} catch (Exception e) {
			System.out.println("Error." + e);
			   //printing error message
		}
	}
}
