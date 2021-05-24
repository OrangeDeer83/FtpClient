import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Interface {
    public JTextField host;
    public JTextField username;
    public JPasswordField password;
    public JButton connBtn;
    public JTextField fileName;
    public JTextField dirName;
    private JButton GetFBtn;
    private JButton RmFBtn;
    private JButton PutFBtn;
    private JButton cdBtn;
    private JButton lsDetailBtn;
    private JButton MkDBtn;
    private JButton RmDBtn;
    private JButton ToAsciiBtn;
    private JButton ToBinaryBtn;
    private JButton BackRootBtn;
    private JButton UpOneDirBtn;
    public JTextArea FileList;
    public JTextArea ServerMsg;
    private JPanel panel;
    private JButton clearDirectoryNameButton;
    private JButton clearFileNameButton;
    private JButton lsBtn;
    private JButton lsCurDetailBtn;
    public JScrollPane jscrollpanel;
    public JLabel currentMsg;
    private Interface window;

    public PrintWriter ctrlOutput;
    public BufferedReader ctrlInput;
    private boolean connected = false;
    final int CTRLPORT = 21;
    private  boolean lsDetail = true;

    public Interface() {
        this.window = this;
        window.FileList.setEditable(false);
        window.ServerMsg.setEditable(false);

        connBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (window.connBtn.getText().equals("Connect")){
                    if (window.host.getText().length() == 0){
                        window.ServerMsg.append("Please Enter The Host!\n");
                        window.currentMsg.setText("Please Enter The Host!");
                    }
                    else{
                        try {
                            openConnection(window.host.getText());
                            if (ctrlOutput == null){
                                window.ServerMsg.append("Please Enter The CORRECT Host!\n");
                                window.currentMsg.setText("Please Enter The CORRECT Host!");
                                return;
                            }
                            if (ctrlOutput == null){
                                window.ServerMsg.append("Please Enter The CORRECT Host!\n");
                                window.currentMsg.setText("Please Enter The CORRECT Host!");
                                return;
                            }
                            FtpThread thread = new FtpThread(window, 1, ctrlOutput, ctrlInput, lsDetail);
                            thread.start();
                            thread.wait();
                            if (window.connBtn.getText().equals("Disconnect")){
                                window.ServerMsg.setText("");
                                window.getMsgs();
                            }
                            else {
                                window.ServerMsg.append("Please Enter CORRECT Username & Password!\n");
                                window.currentMsg.setText("Please Enter CORRECT Username & Password!");
                            }
                        } catch (IOException | InterruptedException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
                else{
                    new FtpThread(window, 15, ctrlOutput, ctrlInput, lsDetail).start();
                    window.ServerMsg.setText("");
                    window.currentMsg.setText("Good Bye~");
                }
            }
        });
        cdBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!window.connBtn.getText().equals("Connect")){
                    new FtpThread(window, 2, ctrlOutput, ctrlInput, lsDetail).start();
                }
                else {
                    window.currentMsg.setText("Hasn't connect Server yet.");
                    window.ServerMsg.append("Hasn't connect Server yet.\n");
                }
            }
        });
        lsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!window.connBtn.getText().equals("Connect")){
                    new FtpThread(window, 3, ctrlOutput, ctrlInput, lsDetail).start();
                    lsDetail = false;
                }
                else{
                    window.currentMsg.setText("Hasn't connect Server yet.");
                    window.ServerMsg.append("Hasn't connect Server yet.\n");
                }
            }
        });
        lsDetailBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!window.connBtn.getText().equals("Connect")){
                    new FtpThread(window, 4, ctrlOutput, ctrlInput, lsDetail).start();
                    lsDetail = true;
                }
                else {
                    window.currentMsg.setText("Hasn't connect Server yet.");
                    window.ServerMsg.append("Hasn't connect Server yet.\n");
                }
            }
        });
        clearFileNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.fileName.setText("");
            }
        });
        clearDirectoryNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.dirName.setText("");
            }
        });
        MkDBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!window.connBtn.getText().equals("Connect")){
                    new FtpThread(window, 5, ctrlOutput, ctrlInput, lsDetail).start();
                }
                else {
                    window.currentMsg.setText("Hasn't connect Server yet.");
                    window.ServerMsg.append("Hasn't connect Server yet.\n");
                }
            }
        });
        RmDBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!window.connBtn.getText().equals("Connect")){
                    new FtpThread(window, 6, ctrlOutput, ctrlInput, lsDetail).start();
                }
                else {
                    window.ServerMsg.append("Hasn't connect Server yet.\n");
                }
            }
        });
        lsCurDetailBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!window.connBtn.getText().equals("Connect")){
                    new FtpThread(window, 7, ctrlOutput, ctrlInput, lsDetail).start();
                    lsDetail = true;
                }
                else {
                    window.currentMsg.setText("Hasn't connect Server yet.");
                    window.ServerMsg.append("Hasn't connect Server yet.\n");
                }
            }
        });
        BackRootBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!window.connBtn.getText().equals("Connect")){
                    new FtpThread(window, 8, ctrlOutput, ctrlInput, lsDetail).start();
                }
                else {
                    window.currentMsg.setText("Hasn't connect Server yet.");
                    window.ServerMsg.append("Hasn't connect Server yet.\n");
                }
            }
        });
        UpOneDirBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!window.connBtn.getText().equals("Connect")){
                    new FtpThread(window, 9, ctrlOutput, ctrlInput, lsDetail).start();
                }
                else {
                    window.currentMsg.setText("Hasn't connect Server yet.");
                    window.ServerMsg.append("Hasn't connect Server yet.\n");
                }
            }
        });
        GetFBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!window.connBtn.getText().equals("Connect")){
                    new FtpThread(window, 10, ctrlOutput, ctrlInput, lsDetail).start();
                }
                else {
                    window.currentMsg.setText("Hasn't connect Server yet.");
                    window.ServerMsg.append("Hasn't connect Server yet.\n");
                }
            }
        });
        PutFBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!window.connBtn.getText().equals("Connect")){
                    new FtpThread(window, 11, ctrlOutput, ctrlInput, lsDetail).start();
                }
                else {
                    window.ServerMsg.append("Hasn't connect Server yet.\n");
                }
            }
        });
        RmFBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!window.connBtn.getText().equals("Connect")){
                    new FtpThread(window, 12, ctrlOutput, ctrlInput, lsDetail).start();
                }
                else {
                    window.currentMsg.setText("Hasn't connect Server yet.");
                    window.ServerMsg.append("Hasn't connect Server yet.\n");
                }
            }
        });
        ToAsciiBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!window.connBtn.getText().equals("Connect")){
                    new FtpThread(window, 13, ctrlOutput, ctrlInput, lsDetail).start();
                }
                else {
                    window.currentMsg.setText("Hasn't connect Server yet.");
                    window.ServerMsg.append("Hasn't connect Server yet.\n");
                }
            }
        });
        ToBinaryBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!window.connBtn.getText().equals("Connect")){
                    new FtpThread(window, 14, ctrlOutput, ctrlInput, lsDetail).start();
                }
                else {
                    window.currentMsg.setText("Hasn't connect Server yet.");
                    window.ServerMsg.append("Hasn't connect Server yet.\n");
                }
            }
        });
    }

    public void openConnection(String host) throws IOException, UnknownHostException,SocketException {
        Socket ctrlSocket;
        try {
            ctrlSocket = new Socket(host, CTRLPORT);
        }catch (SocketException se){
            return;
        }
        ctrlOutput = new PrintWriter(ctrlSocket.getOutputStream());
        ctrlInput = new BufferedReader(new InputStreamReader(ctrlSocket.getInputStream()));
    }

    public  static void main(String[] args){
        JFrame frame = new JFrame("FtpClient");
        frame.setContentPane(new Interface().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.add(new JButton("Button"), 0, 0);
        frame.pack();
        frame.setVisible(true);
        frame.setLocation(20, 200);
        frame.setSize(1500, 400);
    }

    public void getMsgs() {
        try {
            CtrlListen listener = new CtrlListen(window, ctrlInput);
            Thread listenerthread = new Thread(listener);
            listenerthread.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
