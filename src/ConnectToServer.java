
import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


class ConnectToServer extends JFrame {
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int width = screenSize.width;
    private int height = screenSize.height;
    private int w = 400;
    private int h = 350;
    private Client client;

    public void run() throws IOException {


    setUpClient();


    }



    private void setUpClient() throws IOException {
        int portNumber = 8080;
        try {
            String portNum=JOptionPane.showInputDialog("Enter Port Number : ");
            portNumber = Integer.parseInt(portNum);
            portNumber = Integer.valueOf(portNum);
        } catch (NumberFormatException n) {
          JOptionPane.showMessageDialog(this,"Port must be a number");
        }
        String ipAddress = JOptionPane.showInputDialog("Enter The IP Address of The Server :");
        client = new Client( ipAddress, portNumber);

    }
}
