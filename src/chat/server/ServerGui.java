package chat.server;

 

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.io.IOException; 

import javax.swing.JFrame;

import javax.swing.JTextArea;

import javax.swing.JTextField;

 

public class ServerGui extends JFrame implements ActionListener {

 

    private static final long serialVersionUID = 1L;

    private JTextArea textArea = new JTextArea(50, 30); //�� �Է°��� JText ���� 

    private JTextField textField = new JTextField(30);

    private ServerEx server = new ServerEx();

 

    public ServerGui() throws IOException {

 

        add(textArea, BorderLayout.CENTER);

        add(textField, BorderLayout.SOUTH);

        

        textField.addActionListener(this);

        

        setTitle("SERVER");  

        setSize(400, 700);

        setVisible(true);

        setDefaultCloseOperation(EXIT_ON_CLOSE); // server â ���� 

 

        server.setGui(this);

        server.setting();

    }

    public static void main(String[] args) throws IOException {

        new ServerGui();

    }

 

    @Override

    public void actionPerformed(ActionEvent e) {

        String msg = "���� : "+ textField.getText() + "\n";

        System.out.print(msg);

        server.sendMessage(msg);

        textField.setText("");

    }

    public void appendMsg(String msg) {

        textArea.append(msg);

    } 

}
