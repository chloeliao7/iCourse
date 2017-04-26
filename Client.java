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
			   //Connect to the server on the local monitor 5055 port server
			System.out.println("client start ! ");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			   //packing what user print to stream and then use br to represent it
			PrintWriter write = new PrintWriter(socket.getOutputStream());
			   //The output stream is obtained by the Socket object and the PrintWriter object is constructed
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			   //The input stream is obtained by the Socket object, and the corresponding BufferedReader object is constructed
			String readline;
			   //define a string named readline
			readline = br.readLine();
			   //Reads a string from the standard input
			while (!readline.equals("end")) { 
				   //If the string is "Bye", then the loop is stopped
				write.println(readline);
				   //Output the string to the client
				write.flush();
				   //Refresh the output stream so that Client receives the string immediately
				System.out.println("Client:" + readline);
				   //Print read string on system standard output
				System.out.println("Server:" + in.readLine());
				   //Reads a string from the Server and prints it to the standard output
				readline = br.readLine();
				   //Reads a string from the standard input of the system
			}
			   //keep while loop
			write.close();
			   //Turn off the Socket output stream
			in.close();
			   //Close Socket input stream
			socket.close();
			   //close socket
		} catch (Exception e) {
			  // TODO: handle exception
			System.out.println("Error  : " + e);
			   //when some errors happened,it will show the error on the screen 
		}   
	}
}
