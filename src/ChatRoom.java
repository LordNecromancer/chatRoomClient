
import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


class ChatRoom extends JFrame {
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int width = screenSize.width;
    private int height = screenSize.height;
    private JTextArea textShown;
    private JButton submit;
    private JTextField textField;
    private Client client;
    private SetUpGraphics graphics=new SetUpGraphics(width,height);
    private int w = 400;
    private int h = 350;

    private int startingPointWidth = width / 2 - w / 2;
    private int startingPointHeight = height / 2 - h / 2;

    public ChatRoom(Client client,JTextArea textArea)  {
        this.client=client;
        this.textShown=textArea;
    }

    public void run() {


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(startingPointWidth, startingPointHeight, w, h);
        JPanel contentPane = graphics.createContentPane(this);

        submit = new JButton("Send");
        submit.setSize(100, 50);
        contentPane.add(submit, BorderLayout.SOUTH);
        textField = new JTextField("Type....");
        contentPane.add(textField, BorderLayout.NORTH);
        JPanel center=graphics.createMiddle(contentPane);
        setUpTextArea(center);
        addActionListeners();
        this.setVisible(true);


    }



    private void setUpTextArea(JPanel center) {
        textShown = new JTextArea();
        textShown.setEditable(false);
        textShown.setVisible(true);
        textShown.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textShown);
        center.add(scrollPane);

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        DefaultCaret caret = (DefaultCaret) textShown.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        client.setDisplayArea(textShown);
    }

    private void addActionListeners() {
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {



                try {
                    if(!textField.getText().startsWith("@")) {
                        client.send(textField.getText());
                    }
                    else{JOptionPane.showMessageDialog(ChatRoom.this,"Words should not start with '@'");}
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                textField.setText("");

            }
        });
    }


}
