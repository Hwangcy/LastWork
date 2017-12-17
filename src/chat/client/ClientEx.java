package chat.client;

 

import java.io.DataInputStream;

import java.io.DataOutputStream;

import java.io.IOException;

import java.net.Socket;

 

public class ClientEx { 

    private Socket socket;

    private DataInputStream in;

    private DataOutputStream out;

    private String msg; // ��ȭ 

    private String nickName; // �̸� 

    private ClientGui gui;

 

    public final void setGui(ClientGui gui) {

        this.gui = gui;

    }

 

    public void connet() {

        try {

            socket = new Socket("192.168.0.7", 7777); // Ŭ���̾�Ʈ ���� ���� (�����ּ�, ��Ʈ����) �ϰ� 

            System.out.println("���� ����Ǿ����ϴ�.");

            

            in = new DataInputStream(socket.getInputStream());  // ���� �Է� ��Ʈ�� 

            out = new DataOutputStream(socket.getOutputStream()); // ���� Ǯ�� ��Ʈ�� 

             

            out.writeUTF(nickName); // �г����� ���� 

            System.out.println("Ŭ���̾�Ʈ : �޽��� ���ۿϷ�");

            while(in!=null){

                msg=in.readUTF(); // �޽����� ���ڿ��� ���� 

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
