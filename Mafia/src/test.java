/*
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;

public class test {
	ServerSocket sers = null; // ���� ����
	Socket s = null; // Ŭ���̾�Ʈ ����
	List cl_list = Collections.synchronizedList(new ArrayList());
	Vector list = new Vector(); // �̸��� �����ϱ� ���� ����

	public static void main(String[] args) {
		new test(args);
	}

	// ������
	test(String[] args) {
		try {
			sers = new ServerSocket(Integer.parseInt("3000"));
		} catch (Exception e) {
			System.out.println("Can't open ServerSocket");
		}
		System.out
				.println("Java Chat Server started. Accepting the client ...");

		try {
			while (true) {
				s = sers.accept();
				// ����� ����� ������ ����
				ManageClient mc = new ManageClient(s);
				cl_list.add(mc);
				System.out.println("�����ڰ� �߰��Ǿ����ϴ�. �� �����ڴ� =" + (cl_list.size())
						+ "���Դϴ�.");

				// ������ ���� ����
				mc.start();
			}
		} catch (Exception e) {
		}
	}

	// ����� ����� ������
	class ManageClient extends Thread {
		Socket s;
		BufferedReader br;
		PrintWriter pw;

		// ������
		public ManageClient(Socket s) {
			this.s = s;
			try {
				br = new BufferedReader(new InputStreamReader(
						s.getInputStream()));
				pw = new PrintWriter(
						new OutputStreamWriter(s.getOutputStream()));
			} catch (Exception e) {
				System.out.println("Can't make stream");
			}
		}

		// run() �޼ҵ�
		public void run() {
			String msg;
			String name;
			int cnt = 0;

			while (true) {
				try {
					msg = br.readLine();
				} catch (Exception e) {
					System.out
							.println("Reading error for a client. (Could be logout)");
					exitClient(this);
					return;
				}
				int index = msg.indexOf("BYE");
				int si = msg.indexOf(":");

				if (msg.indexOf("[connect]") >= 0) { // ó�� ����� ���ӽ� �̸� ���Ϳ� �߰�
					list.addElement(msg.substring(0, si));
					broadcast(msg);
				} else if (index >= 0) {
					broadcast(msg.substring(0, index) + "���� ä���� �����մϴ�.");
					exitClient(this); // ä�� Ż�� ó��
					return; // run() �޼ҵ� ����
				} else if (msg.indexOf("[HIDE]") >= 0) { // �ӼӸ� ��û�� �ش� Ŭ���̾�Ʈ���Ը�
															// �޼����� ������.
					name = msg.substring(msg.lastIndexOf("<") + 1,
							msg.lastIndexOf(">"));
					for (int i = 0; System.out.println("member : "
							+ list.elementAt(i)
							+ " // "
							+ msg.substring(msg.indexOf("<") + 1,
									msg.indexOf(">")) + "->" + name);) {
						if (name.equals((String) list.elementAt(i)) == true) {
							cnt = i;
						}
					}
					System.out.println("cnt = " + cnt);
					unicast(msg, cnt);
				} else {
					System.out.println(msg);
					broadcast(msg); // ��� Ŭ���̾�Ʈ���� �޽����� ���
				}
				yield(); // �ٸ� �����忡�� ��� �纸
			}
		}
	}

	// Ư�� Ŭ���̾�Ʈ���� �޼����� ������ �޼ҵ� (�ӼӸ�)
	public void unicast(String msg, int i) {

		try {
			ManageClient c = (ManageClient) cl_list.get(i);
			c.pw.println(msg);
			c.pw.flush();
		} catch (Exception e) {
			System.out.println("�ӼӸ� ����");
		}
	}

	// ��� Ŭ���̾�Ʈ���� �޽����� ����ϴ� �޼ҵ�
	public void broadcast(String msg) {
		int numOfConnect = cl_list.size();
		for (int i = 0; i < numOfConnect; i++) {
			try {
				ManageClient mc = (ManageClient) cl_list.get(i);
				mc.pw.println(msg);
				mc.pw.flush();
			} catch (Exception e) {
				System.out.println("broadcast() error.");
			}
		}
	}

	// Ŭ���̾�Ʈ�� Ż���� �� ȣ��Ǵ� �޼ҵ�
	public void exitClient(ManageClient c) {
		int i = cl_list.indexOf(c);
		if (i >= 0) {
			cl_list.remove(i);
			list.removeElementAt(i);
			try {
				if (c.br != null)
					c.br.close();
				if (c.pw != null)
					c.pw.close();
				if (c.s != null)
					c.s.close();
			} catch (Exception e) {
				System.out.println("Exception error");
			}
			System.out.println("A Client exited! Number of client= "
					+ cl_list.size());
		} else {
			System.out.println("No such a client in Server! ");
		}
	}
}
*/
