package chat;

import java.net.*;
import java.io.*;
import java.util.*;

public class Server {
	HashMap clients;

	public Server() {
		clients = new HashMap();
		Collections.synchronizedMap(clients);
	}

	public void Start() {
		ServerSocket serverSocket = null;
		Socket socket = null;

		try {
			serverSocket = new ServerSocket(7777);
			System.out.println("������ ���۵Ǿ����ϴ�.");

			ServerSender sender = new ServerSender(); // ���� sender ������ ����
			sender.start();
			// ����� ���� �� ������ �� �ϳ��� �����ϱ� ����. (receiver�� Ŭ���̾�Ʈ ������ŭ ����)

			while (true) {
				socket = serverSocket.accept(); // Ŭ���̾�Ʈ �����û ��ٸ�
				
				ServerReceiver receiver = new ServerReceiver(socket);
				
				// ������ ������ ����, Ŭ���̾�Ʈ���� ������ �ϳ� ����.
				receiver.start();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // start()

	void sendToAll(String msg, String id) {
		Iterator it = clients.keySet().iterator();

		while (it.hasNext()) {
			try { // �������� Ŭ���̾�Ʈ�� ����ϴ� �κ�!
				DataOutputStream out = (DataOutputStream) clients.get(it.next());
				if (out.equals(clients.get(id)))
					continue; // �޼��� ���� ������ ��� ���ϰ� continue
				out.writeUTF(msg);
			} catch (IOException e) {
			}
		} // while
	} // sendToAll

	class ServerSender extends Thread {
		boolean chat = true;

		public void run() {
			Scanner scanner = new Scanner(System.in);
			String message = "";
			try {
				while (true) {
					message = scanner.nextLine();
					if (message.equals("shutup")) {
						sendToAll("Everybody Shutup!", "Server");
						continue;
					}
					sendToAll(message, "Server");
				}
			} catch (Exception e) {
			}
		} // run() }
	}

	class ServerReceiver extends Thread {
		Socket socket;
		DataInputStream in;
		DataOutputStream out;
		// Ŭ���̾�Ʈ ������ �����ϴ� ������; �����帶�� ���� ����ǰ���?
		String id; // Clients�� key��

		ServerReceiver(Socket socket) {
			this.socket = socket;
			try {
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
			} catch (IOException e) {
			}
		}

		public void run() {
			try {
				id = in.readUTF();
				if (clients.containsKey(id)) {
					System.out.println("������ �г����� �����մϴ�.");
					// �̺�Ʈ ���� �ȸ���
				} else if (clients.containsKey("Server")) {
					System.out.println("�� ���̵�� ���� �� �����ϴ�.");
					// �̺�Ʈ ���� �ȸ���
				}

				sendToAll("#" + id + "���� �����̽��ϴ�.", "Server");

				clients.put(id, out);
				System.out.println("���� ���������� ���� " + clients.size() + "�Դϴ�.");
				while (in != null) {
					sendToAll(in.readUTF(), id); // Ŭ���̾�Ʈid���� ���� �޼����� �Ѹ�
				}
			} catch (IOException e) {
				// ignore
			} finally {
				sendToAll("#" + id + "���� �����̽��ϴ�.", "Server");
				clients.remove(id);
				System.out.println("[" + socket.hashCode() + "]"
						+ "���� ������ �����Ͽ����ϴ�.");
				System.out.println("���� ���������� ���� " + clients.size() + "�Դϴ�.");
			} // try
		} // run
	} // ReceiverThread
} // class