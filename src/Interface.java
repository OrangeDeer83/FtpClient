import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Interface {
    public JTextField host;
    public JTextField username;
    public JTextField password;
    private JButton connBtn;
    private JButton disconnBtn;
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
    private Interface window;

    public PrintWriter ctrlOutput;
    public BufferedReader ctrlInput;
    private boolean connected = false;
    final int CTRLPORT = 21;

    public Interface() {
        this.window = this;
        window.FileList.setEditable(false);
        window.ServerMsg.setEditable(false);

        connBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!connected){
                    try {
                        openConnection("127.0.0.1"/*port.getText()*/);
                        new FtpThread(window, 1, ctrlOutput, ctrlInput).start();
                        window.getMsgs();
                        connected = true;
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
                else{
                    window.ServerMsg.append("You have already connected Server now.\n");
                }
            }
        });
        disconnBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (connected){
                    try{
                        ctrlOutput.println("QUIT");
                        ctrlOutput.flush();
                        connected = false;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        System.exit(1);
                    }
                }
                else{
                    window.ServerMsg.append("Hasn't connect Server yet.\n");
                }
            }
        });
        cdBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (connected){
                    new FtpThread(window, 2, ctrlOutput, ctrlInput).start();
                }
                else {
                    window.ServerMsg.append("Hasn't connect Server yet.\n");
                }
            }
        });
        lsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (connected){
                    new FtpThread(window, 3, ctrlOutput, ctrlInput).start();
                }
                else{
                    window.ServerMsg.append("Hasn't connect Server yet.\n");
                }
            }
        });
        lsDetailBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (connected){
                    new FtpThread(window, 4, ctrlOutput, ctrlInput).start();
                }
                else {
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
                if (connected){
                    new FtpThread(window, 5, ctrlOutput, ctrlInput).start();
                }
                else {
                    window.ServerMsg.append("Hasn't connect Server yet.\n");
                }
            }
        });
        RmDBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (connected){
                    new FtpThread(window, 6, ctrlOutput, ctrlInput).start();
                }
                else {
                    window.ServerMsg.append("Hasn't connect Server yet.\n");
                }
            }
        });
        lsCurDetailBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (connected){
                    new FtpThread(window, 7, ctrlOutput, ctrlInput).start();
                }
                else {
                    window.ServerMsg.append("Hasn't connect Server yet.\n");
                }
            }
        });
        BackRootBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (connected){
                    new FtpThread(window, 8, ctrlOutput, ctrlInput).start();
                }
                else {
                    window.ServerMsg.append("Hasn't connect Server yet.\n");
                }
            }
        });
        UpOneDirBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (connected){
                    new FtpThread(window, 9, ctrlOutput, ctrlInput).start();
                }
                else {
                    window.ServerMsg.append("Hasn't connect Server yet.\n");
                }
            }
        });
        GetFBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (connected){
                    new FtpThread(window, 10, ctrlOutput, ctrlInput).start();
                }
                else {
                    window.ServerMsg.append("Hasn't connect Server yet.\n");
                }
            }
        });
        PutFBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (connected){
                    new FtpThread(window, 11, ctrlOutput, ctrlInput).start();
                }
                else {
                    window.ServerMsg.append("Hasn't connect Server yet.\n");
                }
            }
        });
        RmFBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (connected){
                    new FtpThread(window, 12, ctrlOutput, ctrlInput).start();
                }
                else {
                    window.ServerMsg.append("Hasn't connect Server yet.\n");
                }
            }
        });
        ToAsciiBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (connected){
                    new FtpThread(window, 13, ctrlOutput, ctrlInput).start();
                }
                else {
                    window.ServerMsg.append("Hasn't connect Server yet.\n");
                }
            }
        });
        ToBinaryBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (connected){
                    new FtpThread(window, 14, ctrlOutput, ctrlInput).start();
                }
                else {
                    window.ServerMsg.append("Hasn't connect Server yet.\n");
                }
            }
        });
    }

    public void openConnection(String host) throws IOException, UnknownHostException {
        Socket ctrlSocket;
        ctrlSocket = new Socket(host, CTRLPORT);
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
