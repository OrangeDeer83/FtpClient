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
    final int CTRLPORT = 21;
    private  boolean lsDetail = true;
    CtrlListen listenThread = null;

    public Interface() {
        this.window = this;
        window.FileList.setEditable(false);
        window.ServerMsg.setEditable(false);

        connBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (window.connBtn.getText().equals("Connect")){
                        ctrlOutput = null;
                        ctrlInput = null;
                        if (window.host.getText().length() == 0){
                            window.ServerMsg.append("Please Enter The Host!\n");
                            window.currentMsg.setText("Please Enter The Host!");
                        }
                        else{
                            if (window.username.getText().length() == 0){
                                window.ServerMsg.append("Please Enter Username!\n");
                                window.currentMsg.setText("Please Enter Username!");
                                return;
                            }
                            else if (window.password.getText().length() == 0){
                                window.ServerMsg.append("Please Enter Password!\n");
                                window.currentMsg.setText("Please Enter Password!");
                                return;
                            }
                            window.connBtn.setEnabled(false);
                            window.connBtn.setText("Connecting Host");
                            try {
                                openConnection(window.host.getText());
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                            if (ctrlOutput == null){
                                window.ServerMsg.append("Please Enter The CORRECT Host!\n");
                                window.currentMsg.setText("Please Enter The CORRECT Host!");
                                window.connBtn.setText("Connect");
                                window.connBtn.setEnabled(true);
                                return;
                            }
                        if (ctrlInput == null){
                            window.ServerMsg.append("Please Enter The CORRECT Host!\n");
                            window.currentMsg.setText("Please Enter The CORRECT Host!");
                            window.connBtn.setText("Connect");
                            window.connBtn.setEnabled(true);
                            return;
                        }
                        listenThread = window.getMsgs();
                        FtpThread loginThread = new FtpThread(window, 1, ctrlOutput, ctrlInput, lsDetail);
                        loginThread.start();
                        System.out.println("123");
                        while (listenThread.isLogin() == 0){ System.out.println();}
                        if (listenThread.isLogin() == 1){
                            new FtpThread(window, 7, ctrlOutput, ctrlInput, lsDetail).start();
                        }
                    }
                }
                else{
                    FtpThread quitThread = new FtpThread(window, 15, ctrlOutput, ctrlInput, lsDetail);
                    quitThread.start();
                    try {
                        quitThread.join();
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }
                }}).start();
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
        }catch (SocketException | UnknownHostException se){
            window.ServerMsg.append(se.getMessage());
            window.ServerMsg.append("\n");
            return;
        }
        ctrlOutput = new PrintWriter(ctrlSocket.getOutputStream());
        ctrlInput = new BufferedReader(new InputStreamReader(ctrlSocket.getInputStream()));
    }

    public  static void main(String[] args){
        JFrame frame = new JFrame("FtpClient");
        frame.setContentPane(new Interface().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocation(20, 200);
        frame.setSize(1500, 400);
    }

    public CtrlListen getMsgs() {
        CtrlListen listener = null;
        try {
            listener = new CtrlListen(window, ctrlInput);
            listener.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listener;
    }
}
