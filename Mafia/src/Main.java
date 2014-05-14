import java.net.Socket;
import java.util.Scanner;

import chat.Client;
import chat.Server;
import game.Game;

public class Main {

	public static void main(String[] args) {
		try {
			System.out.println("-----�޴�----");
			System.out.println("1. ��������");
			System.out.println("2. ��������");
			Scanner sc = new Scanner(System.in);

			byte sel;
			sel = sc.nextByte();
			switch (sel) {
			case 1:
				new Server().Start();
				break;
			case 2:
				String serverIp = "127.0.0.1";
				System.out.println("�г����� �Է��ϼ���.");
				String id = sc.next();

				new Client().Start(id, serverIp);
				break;
			}
		} catch (Exception e) {

		}

	}

}
