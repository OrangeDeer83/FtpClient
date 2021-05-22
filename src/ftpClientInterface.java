import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
    private JEditorPane textFromHost;
    private JTextField localSite;
    private JTextField remoteSite;
    private JEditorPane localSiteFile;
    private JPanel panel;
    private JEditorPane remoteEditorFile;
    ftpClientInterface window;

    public PrintWriter ctrlOutput;
    public BufferedReader ctrlInput;
    final int CTRLPORT = 21;


    public ftpClientInterface(){
        this.window = this;
        window.localSiteFile.setEditorKit(JEditorPane.createEditorKitForContentType("text/html"));
        window.localSiteFile.setEditable(false);
        //panel.add(new JButton("Button"));

        cntBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    openConnection("127.0.0.1"/*port.getText()*/);
                    new FtpThread(window, 1, ctrlOutput, ctrlInput).start();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        discntBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FtpThread(window, 2, ctrlOutput, ctrlInput).start();
            }
        });
        /*panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                System.out.println(e.getSource().getClass());
            }
        });*/
        panel.addMouseMotionListener(new MouseMotionAdapter() {
        });
        localSiteFile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                window.panel.add(new Button("Button"), 0);
            }
        });
    }
    public  static void main(String[] args){
        JFrame frame = new JFrame("ComparePrice");
        frame.setContentPane(new ftpClientInterface().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.add(new JButton("Button"), 0, 0);
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
        return localSiteFile.getText().split("<body>|</body>")[1];
    }

    public void updateLocalSiteFile(String[] text){
        localSiteFile.setText(text[0]);
        for (int i = 1; i < text.length; i++){
            localSiteFile.setText(getLocalSiteFile() + text[i]);
        }
    }

    /*public String getRemoteSiteFile(){
        return remoteSiteFile.getText();
    }

    public void appendRemoteSiteFile(String text){
        remoteSiteFile.setText(getRemoteSiteFile() + text);
    }*/
}
