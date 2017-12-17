package chat.client;

 

import java.io.DataInputStream;

import java.io.DataOutputStream;

import java.io.IOException;

import java.net.Socket;

 

public class ClientEx { 

    private Socket socket;

    private DataInputStream in;

    private DataOutputStream out;

    private String msg; // 대화 

    private String nickName; // 이름 

    private ClientGui gui;

 

    public final void setGui(ClientGui gui) {

        this.gui = gui;

    }

 

    public void connet() {

        try {

            socket = new Socket("192.168.0.7", 7777); // 클라이언트 소켓 생성 (서버주소, 포트접속) 하고 

            System.out.println("서버 연결되었습니다.");

            

            in = new DataInputStream(socket.getInputStream());  // 소켓 입력 스트림 

            out = new DataOutputStream(socket.getOutputStream()); // 소켓 풀력 스트림 

             

            out.writeUTF(nickName); // 닉네임을 생성 

            System.out.println("클라이언트 : 메시지 전송완료");

            while(in!=null){

                msg=in.readUTF(); // 메시지의 문자열을 읽음 

                gui.appendMsg(msg);             

            }

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

 

    public static void main(String[] args) {

        ClientEx clientBackground = new ClientEx();

        clientBackground.connet();

    }

 

    public void sendMessage(String msg2) {

        try {

            out.writeUTF(msg2);

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

 

    public void setNickname(String nickName) {

        this.nickName = nickName;

    }

}
