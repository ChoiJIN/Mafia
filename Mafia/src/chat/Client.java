package chat;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
	public void Start(String id, String serverIp) {
		try {
			// 소켓을 생성하여 연결을 요청한다.
			Socket socket = new Socket(serverIp, 7777);
			System.out.println("서버에 연결되었습니다.");

			Thread sender = new Thread(new ClientSender(socket, id));
			Thread receiver = new Thread(new ClientReceiver(socket));

			// 클라이언트의 쓰레드 시작
			sender.start(); // sender 쓰레드 시작
			receiver.start(); // receiver 쓰레드 시작
		} catch (ConnectException ce) {
			ce.printStackTrace();
		} catch (Exception e) {
		}
	}

	public static class ClientSender extends Thread {
		Socket socket;
		DataOutputStream out;
		String name;
		public boolean chat = true;

		public ClientSender(Socket socket, String name) {
			this.socket = socket;
			try {
				out = new DataOutputStream(socket.getOutputStream());
				this.name = name;
			} catch (Exception e) {
			}
		}

		public void run() {
			Scanner scanner = new Scanner(System.in);
			String message = "";
			try {
				if (out != null) {
					out.writeUTF(name);
				}

				while (out != null) {
					if ((message = scanner.nextLine()).charAt(0) == '@') {
						continue;
					}
					if (chat){
						out.writeUTF("[" + name + "]" + message);
					}
				}
			} catch (IOException e) {
			}
		} // run()
	}

	public static class ClientReceiver extends Thread {
		Socket socket;
		DataInputStream in;

		public ClientReceiver(Socket socket) {
			this.socket = socket;
			try {
				in = new DataInputStream(socket.getInputStream());
			} catch (IOException e) {
			}
		}

		public void run() {
			while (in != null) {
				try {
					System.out.println(in.readUTF());
				} catch (IOException e) {
				}
			}
		} // run
	}
} // class