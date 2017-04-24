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
			// 由Socket对象得到输入流，并构造相应的BufferedReader对象
			BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			// 由Socket对象得到输出流，并构造PrintWriter对象
			PrintWriter writer = new PrintWriter(s.getOutputStream());
			// 由系统标准输入设备构造BufferedReader对象
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			// 在标准输出上打印从客户端读入的字符串
			System.out.println("Client:" + in.readLine());

			line = br.readLine();
			while (!line.equals("end")) {
				writer.println(line);
				writer.flush();
				System.out.println("Server:" + line);
				System.out.println("Client:" + in.readLine());
				line = br.readLine();
			}
			writer.close();
			in.close();
			in.close();
			s.close(); // 关闭Socket
			ss.close();
		} catch (Exception e) {
			System.out.println("Error." + e);
		}
	}
}
