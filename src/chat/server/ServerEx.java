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

    // 해쉬맵을 이용하여 사용자의 nickname을 저장 

 

    public final void setGui(ServerGui gui) {

        this.gui = gui;

    }

 

    public void setting() throws IOException {

            Collections.synchronizedMap(clientsMap); 

            serverSocket = new ServerSocket(7777); // 지정포트에 저장 

            

            while (true) {

                System.out.println("연결을 기다리고 있습니다......");

                socket = serverSocket.accept(); // 서버가 계속 사용자를 받을 수 있다 

                System.out.println(socket.getInetAddress() + "에서 접속했습니다."); // 쓰레드 클래스를 생성하여 사용자정보를 소켓정보를 넣는다.

                Receiver receiver = new Receiver(socket);

                receiver.start();

            }

    }

 

    public static void main(String[] args) throws IOException {

        ServerEx serverBackground = new ServerEx();

        serverBackground.setting();

    }

 

    public void addClient(String nickname, DataOutputStream out) throws IOException {

        sendMessage(nickname + "님이 접속하셨습니다." + "\n");

        clientsMap.put(nickname, out);

    }

 

    public void removeClient(String nickname) {

        sendMessage(nickname + "님이 나가셨습니다.");

        clientsMap.remove(nickname);

    }

 

    // 메시지 내용 전파

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

 

        public Receiver(Socket socket) throws IOException {  // 리시버 소켓은 입력, 출력 스트림을 계속 작동 

            in = new DataInputStream(socket.getInputStream());

            out = new DataOutputStream(socket.getOutputStream());

            nick = in.readUTF();

            addClient(nick, out);

        }

 

        public void run() {

            try { // msg를 계속 받아준다 

                while (in != null) {

                    msg = in.readUTF();

                    sendMessage(msg);

                    gui.appendMsg(msg);

                }

            } catch (IOException e) {               

                removeClient(nick); // 사용종료 

            }

        }

    }

}
