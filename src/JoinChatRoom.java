
import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


class JoinChatRoom extends JFrame {
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int width = screenSize.width;
    private int height = screenSize.height;
    private int w = 400;
    private int h = 350;
    private int startingPointWidth = width / 2 - w / 2;
    private int startingPointHeight = height / 2 - h / 2;
    JTextArea textShown;
    private JButton joinRoom;
    private JButton createRoom;
    private JTextField IpAddress;
    private JTextField port;
    private JTextField textField;
    private Client client;
    private boolean isStarted = false;
    private SetUpGraphics graphics=new SetUpGraphics(width,height);

    public JoinChatRoom(Client client)  {
        this.client = client;

    }

    public void run() throws IOException {


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(startingPointWidth, startingPointHeight, w, h);
        JPanel contentPane = graphics.createContentPane(this);
        port = new JTextField(5);
        IpAddress = new JTextField(5);
        joinRoom = new JButton("Join");
        joinRoom.setSize(100, 50);
        contentPane.add(joinRoom, BorderLayout.SOUTH);
        createRoom = new JButton("Create");
        createRoom.setSize(100, 50);
        contentPane.add(joinRoom, BorderLayout.EAST);
        contentPane.add(createRoom,BorderLayout.WEST);

        textField = new JTextField("Enter the room name here to join...");
        contentPane.add(textField, BorderLayout.NORTH);
        JPanel center=graphics.createMiddle(contentPane);
        setUpTextArea(center);
        addActionListeners();
        this.setVisible(true);


    }



    private void setUpTextArea(JPanel center) throws IOException {
        textShown = new JTextArea();
        textShown.setEditable(false);
        textShown.setVisible(true);
        textShown.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textShown);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        DefaultCaret caret = (DefaultCaret) textShown.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        center.add(scrollPane);
        client.setDisplayArea(textShown);
        client.getRoomsList();
    }

    private void addActionListeners() {
        joinRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {



                try {
                    client.send(textField.getText());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                textField.setText("");
            }

        });

        createRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String roomName=JOptionPane.showInputDialog("Enter the room name","@");
                    client.send("#"+roomName);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });
    }

}
