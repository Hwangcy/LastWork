package chat.server;

 

import java.io.DataInputStream;

import java.io.DataOutputStream;

import java.io.IOException;

import java.net.ServerSocket;

import java.net.Socket;

import java.util.Collections;

import java.util.HashMap;

import java.util.Iterator;

import java.util.Map;

 

public class ServerEx {

 

    private ServerSocket serverSocket;

    private Socket socket;

    private ServerGui gui;

    private String msg;

 

    private Map<String, DataOutputStream> clientsMap = new HashMap<String, DataOutputStream>(); 

    // �ؽ����� �̿��Ͽ� ������� nickname�� ���� 

 

    public final void setGui(ServerGui gui) {

        this.gui = gui;

    }

 

    public void setting() throws IOException {

            Collections.synchronizedMap(clientsMap); 

            serverSocket = new ServerSocket(7777); // ������Ʈ�� ���� 

            

            while (true) {

                System.out.println("������ ��ٸ��� �ֽ��ϴ�......");

                socket = serverSocket.accept(); // ������ ��� ����ڸ� ���� �� �ִ� 

                System.out.println(socket.getInetAddress() + "���� �����߽��ϴ�."); // ������ Ŭ������ �����Ͽ� ����������� ���������� �ִ´�.

                Receiver receiver = new Receiver(socket);

                receiver.start();

            }

    }

 

    public static void main(String[] args) throws IOException {

        ServerEx serverBackground = new ServerEx();

        serverBackground.setting();

    }

 

    public void addClient(String nickname, DataOutputStream out) throws IOException {

        sendMessage(nickname + "���� �����ϼ̽��ϴ�." + "\n");

        clientsMap.put(nickname, out);

    }

 

    public void removeClient(String nickname) {

        sendMessage(nickname + "���� �����̽��ϴ�.");

        clientsMap.remove(nickname);

    }

 

    // �޽��� ���� ����

    public void sendMessage(String msg) {

        Iterator<String> it = clientsMap.keySet().iterator();

        String key = "";

        while (it.hasNext()) {

            key = it.next();

            try {

                clientsMap.get(key).writeUTF(msg);

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

    }

    

    class Receiver extends Thread {

        private DataInputStream in;

        private DataOutputStream out;

        private String nick;

 

        public Receiver(Socket socket) throws IOException {  // ���ù� ������ �Է�, ��� ��Ʈ���� ��� �۵� 

            in = new DataInputStream(socket.getInputStream());

            out = new DataOutputStream(socket.getOutputStream());

            nick = in.readUTF();

            addClient(nick, out);

        }

 

        public void run() {

            try { // msg�� ��� �޾��ش� 

                while (in != null) {

                    msg = in.readUTF();

                    sendMessage(msg);

                    gui.appendMsg(msg);

                }

            } catch (IOException e) {               

                removeClient(nick); // ������� 

            }

        }

    }

}
