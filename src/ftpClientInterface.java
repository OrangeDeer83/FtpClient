import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ftpClientInterface {
    private JTextField host;
    private JTextField username;
    private JTextField password;
    private JTextField port;
    private JButton cntBtn;
    private JButton discntBtn;
    private JTextPane textFromHost;
    private JTextField localSite;
    private JTextField remoteSite;
    private JTextPane localSiteFile;
    private JTextPane remoteSiteFile;
    private JPanel panel;

    public PrintWriter ctrlOutput;
    public BufferedReader ctrlInput;
    final int CTRLPORT = 21;

    public ftpClientInterface(){
    }

    public  static void main(String[] args){
        JFrame frame = new JFrame("ComparePrice");
        frame.setContentPane(new ftpClientInterface().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocation(250, 200);
        frame.setSize(1500, 400);
    }


    public void openConnection(String host) throws IOException, UnknownHostException {
        Socket ctrlSocket;
        ctrlSocket = new Socket(host, CTRLPORT);
        ctrlOutput = new PrintWriter(ctrlSocket.getOutputStream());
        ctrlInput = new BufferedReader(new InputStreamReader(ctrlSocket.getInputStream()));
    }

    public String getHost(){
        return host.getText();
    }

    public String getUsername(){
        return username.getText();
    }

    public String getPassword(){
        return password.getText();
    }

    public String getPort(){
        return port.getText();
    }

    public String getTextFromHost(){
        return textFromHost.getText();
    }

    public void appendTextFromHost(String text){
        textFromHost.setText(getTextFromHost() + text);
    }

    public String getLocalSite(){
        return localSite.getText();
    }

    public String getRemoteSite(){
        return remoteSite.getText();
    }

    public String getLocalSiteFile(){
        return localSiteFile.getText();
    }

    public void appendLocalSiteFile(String text){
        localSiteFile.setText(getLocalSiteFile() + text);
    }

    public String getRemoteSiteFile(){
        return remoteSiteFile.getText();
    }

    public void appendRemoteSiteFile(String text){
        remoteSiteFile.setText(getRemoteSiteFile() + text);
    }
}
