package chat.client;

 

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.util.ArrayList;

import java.util.Scanner;

 

import javax.swing.JFrame;

import javax.swing.JTextArea;

import javax.swing.JTextField;

 

public class ClientGui  extends JFrame implements ActionListener{

 

    private static final long serialVersionUID = 1L;

    private JTextArea textArea = new JTextArea(50, 30);

    private JTextField textField = new JTextField(30);

    private ClientEx client = new ClientEx();

    private static String nickName;

     

    public ClientGui() {      

        add(textArea, BorderLayout.CENTER);

        add(textField, BorderLayout.SOUTH);

        

        textField.addActionListener(this);

        

        setTitle(nickName);	//클라이언트 

        setSize(400, 700);	

        setVisible(true);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

         

        client.setGui(this);

        client.setNickname(nickName);

        client.connet();

    }     

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in); // 닉네임 설정 

        System.out.print("닉네임을 만드세요 : ");

        nickName = scanner.nextLine();

        scanner.close();        

        

        new ClientGui();         

    } 

    @Override   

    public void actionPerformed(ActionEvent e) {

        String msg = nickName+ " : " + textField.getText()+"\n"; // 채팅이 시작되면 "이름: 대화"가 출력

        client.sendMessage(msg);

        textField.setText("");

    } 

    public void appendMsg(String msg) {

    textArea.append(msg);

    }

} 
