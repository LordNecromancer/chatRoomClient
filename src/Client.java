import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;


public class Client  {
    private String ipAddress;
    private int port;
    private JTextArea text;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private Client client;
    private Socket socket;
    private String name;
    private boolean isStarted = false;
    private JoinChatRoom joinChatRoom;

    Client(String ipAddress, int port) throws IOException {
        this.name = JOptionPane.showInputDialog("Choose Your Name  :");
        this.ipAddress = ipAddress;
        this.port = port;
        this.client=this;
        run();
    }

    public void run() {


        final String[] string = {""};
        try {
            socket = new Socket(ipAddress, port);
            dataOutputStream= new DataOutputStream(socket.getOutputStream());
            joinChatRoom=new JoinChatRoom(this);
            joinChatRoom.run();
            text=joinChatRoom.textShown;
            Socket finalSocket = socket;
            final InputStream[] inputStream = {null};


            Thread receive = new Thread() {
                @Override
                public void run() {
                    try {
                        inputStream[0] = finalSocket.getInputStream();
                        dataInputStream = new DataInputStream(finalSocket.getInputStream());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    while (true) {


                        try {
                            string[0] = dataInputStream.readUTF();
                            System.out.println(string[0]);

                            if(string[0].equals("JoinedChatRoom$")){
                                ChatRoom chatRoom=new ChatRoom(client,joinChatRoom.textShown);
                                chatRoom.run();
                                joinChatRoom.dispose();

                            }
                            else if(string[0].equals("NoSuchRoom$")){


                                text.setText("No such chat room exists."+"\r\n");
                            }
                            else if(string[0].startsWith("@")){
                                text.setText(text.getText() + string[0] + "\r\n");
                            }
                            else if (string[0].length()>name.length()+5) {
                                text.setText(text.getText() + string[0] + "\r\n");
                            }
                        } catch (IOException ignored) {

                        }
                    }
                }
            };
            receive.start();
        } catch (IOException e1) {
            text.append("\r\n" + "No server found with these information");

        }
    }


    void send(String msg) throws IOException {

        try {


            if (!isStarted) {
                dataOutputStream.writeUTF("%name%"+name);
                isStarted = true;
            }
            dataOutputStream.writeUTF(msg);
            dataOutputStream.flush();


        } catch (IOException e) {
            text.append("\r\n" + "Sorry the server is unavailable right now :(");
            closeConnection();
        }
    }

    private void closeConnection() throws IOException {
        dataInputStream.close();
        dataOutputStream.close();
        socket.close();
    }
    void getRoomsList() throws IOException {
        this.dataOutputStream.writeUTF("getRoomsList$");
    }

    public void setDisplayArea(JTextArea textArea){
        this.text=textArea;
    }
}
