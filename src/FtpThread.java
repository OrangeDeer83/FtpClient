import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FtpThread extends Thread{
    Interface window;
    PrintWriter ctrlOutput;
    BufferedReader ctrlInput;
    int func;
    boolean lsDetail;


    public FtpThread(Interface win, int func, PrintWriter ctrlOutput, BufferedReader ctrlInput, boolean lsDetail){
        this.window = win;
        this.func = func;
        this.ctrlOutput = ctrlOutput;
        this.ctrlInput = ctrlInput;
        this.lsDetail = lsDetail;
    }

    public void run(){
        switch (func){
            case 1:
                doLogin();
                break;
            case 2:
                doCd();
                break;
            case 3:
                doLs();
                break;
            case 4:
                doLsDetail();
                break;
            case 5:
                doMkdir();
                break;
            case 6:
                doRmdir();
                break;
            case 7:
                if (lsDetail)
                {
                    doLsCurDetail();
                }
                else {
                    doLsCur();
                }
                break;
            case 8:
                doCdRoot();
                break;
            case 9:
                doUpOneDir();
                break;
            case 10:
                doGet();
                break;
            case 11:
                doPut();
                break;
            case 12:
                doDel();
                break;
            case 13:
                doAscii();
                break;
            case 14:
                doBinary();
                break;
            case 15:
                doQuit();
                break;
        }
        window.dirName.setText("");
        window.fileName.setText("");
    }


    public Socket dataConnection(String ctrlcmd){
        String cmd = "PORT ";
        int i;
        Socket dataSocket = null;
        try{
            if (window.host.getText().equalsIgnoreCase("localhost")){
                cmd = "PORT 127,0,0,1,";
            }
            else{
                cmd = cmd + window.host.getText().replace(".", ",") + ",";
            }
            ServerSocket serverDataSocket = new ServerSocket(0, 1);
            cmd = cmd + ((serverDataSocket.getLocalPort() / 256) & 0xff) + "," + (serverDataSocket.getLocalPort() & 0xff);
            ctrlOutput.println(cmd);
            ctrlOutput.flush();
            ctrlOutput.println(ctrlcmd);
            ctrlOutput.flush();
            dataSocket = serverDataSocket.accept();
            serverDataSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataSocket;
    }

    private void doLogin(){
        BufferedReader lineread = new BufferedReader(new InputStreamReader(System.in));
        try{
            ctrlOutput.println("USER " + window.username.getText());
            ctrlOutput.flush();
            ctrlOutput.println("PASS " + window.password.getText());
            ctrlOutput.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doCd() {
        if (window.dirName.getText().length() == 0){
            window.currentMsg.setText("Please Enter Directory Name!");
        }
        else{
            try{
                ctrlOutput.println("CWD " + window.dirName.getText());
                ctrlOutput.flush();
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        if (lsDetail)
        {
            doLsCurDetail();
        }
        else {
            doLsCur();
        }
    }

    private void doLs(){
        if (window.dirName.getText().length() == 0){
            window.currentMsg.setText("Please Enter Directory Name!");
        }
        else{
            try{
                int n;
                byte[] buff = new byte[1024];
                Socket dataSocket = dataConnection("NLST " + window.dirName.getText());
                BufferedInputStream dataInput = new BufferedInputStream(dataSocket.getInputStream());
                String content = "";
                String a;
                int i = 0;
                while ((n = dataInput.read(buff)) > 0){
                    content += new String(buff, 0, n,"UTF-8");
                }
                window.FileList.setText(content);
                dataSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void doLsDetail(){
        if (window.dirName.getText().length() == 0){
            window.currentMsg.setText("Please Enter Directory Name!");
        }
        else{
            try{
                int n;
                byte[] buff = new byte[1024];
                Socket dataSocket = dataConnection("LIST " + window.dirName.getText());
                BufferedInputStream dataInput = new BufferedInputStream(dataSocket.getInputStream());
                String content = "";
                String a;
                int i = 0;
                while ((n = dataInput.read(buff)) > 0){
                    content += new String(buff, 0, n,"UTF-8");
                }
                window.FileList.setText(content);
                dataSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void doMkdir() {
        if (window.dirName.getText().length() == 0){
            window.currentMsg.setText("Please Enter Directory Name!");
        }
        else{
            try{
                ctrlOutput.println("MKD " + window.dirName.getText());
                ctrlOutput.flush();
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        if (lsDetail)
        {
            doLsCurDetail();
        }
        else {
            doLsCur();
        }
    }

    private void doRmdir() {
        if (window.dirName.getText().length() == 0){
            window.currentMsg.setText("Please Enter Directory Name!");
        }
        else{
            try{
                ctrlOutput.println("RMD " + window.dirName.getText());
                ctrlOutput.flush();
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        if (lsDetail)
        {
            doLsCurDetail();
        }
        else {
            doLsCur();
        }
    }

    private void doLsCurDetail() {
        try{
            int n;
            byte[] buff = new byte[1024];
            Socket dataSocket = dataConnection("LIST");
            BufferedInputStream dataInput = new BufferedInputStream(dataSocket.getInputStream());
            String content = "";
            String a;
            int i = 0;
            while ((n = dataInput.read(buff)) > 0){
                content += new String(buff, 0, n,"UTF-8");
            }
            window.FileList.setText(content);
            dataSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doLsCur() {
        try{
            int n;
            byte[] buff = new byte[1024];
            Socket dataSocket = dataConnection("NLST");
            BufferedInputStream dataInput = new BufferedInputStream(dataSocket.getInputStream());
            String content = "";
            String a;
            int i = 0;
            while ((n = dataInput.read(buff)) > 0){
                content += new String(buff, 0, n,"UTF-8");
            }
            window.FileList.setText(content);
            dataSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doCdRoot() {
        try{
            ctrlOutput.println("CWD /");
            ctrlOutput.flush();
        } catch(Exception e){
            e.printStackTrace();
        }
        if (lsDetail)
        {
            doLsCurDetail();
        }
        else {
            doLsCur();
        }
    }

    private void doUpOneDir() {
        try{
            ctrlOutput.println("CWD ..");
            ctrlOutput.flush();
        } catch(Exception e){
            e.printStackTrace();
        }
        if (lsDetail)
        {
            doLsCurDetail();
        }
        else {
            doLsCur();
        }
    }

    private void doGet() {
        if (window.fileName.getText().length() == 0){
            window.currentMsg.setText("Please Enter FileName!");
        }
        else{
            try {
                int n;
                byte[] buff = new byte[1024];
                Socket dataSocket = dataConnection("RETR " + window.fileName.getText());
                String filenameAtServer = window.fileName.getText();
                String filename = filenameAtServer.split("/|\\\\")[filenameAtServer.split("/|\\\\").length - 1];
                FileOutputStream outfile = new FileOutputStream(filename);
                BufferedInputStream dataInput = new BufferedInputStream(dataSocket.getInputStream());
                while((n = dataInput.read(buff)) > 0){
                    outfile.write(buff, 0, n);
                }
                dataSocket.close();
                outfile.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void doPut() {
        if (window.fileName.getText().length() == 0){
            window.currentMsg.setText("Please Enter FileName!");
        }
        else{
            try {
                int n;
                byte[] buff = new byte[1024];
                FileInputStream sendfile = null;
                try {
                    sendfile = new FileInputStream(window.fileName.getText());
                }catch (FileNotFoundException e){
                    window.currentMsg.setText("File Not Found!!");
                    return;
                }
                String filenameAtServer = window.fileName.getText();
                String filename = filenameAtServer.split("/|\\\\")[filenameAtServer.split("/|\\\\").length - 1];
                Socket dataSocket = dataConnection("STOR " + window.fileName.getText());
                OutputStream outstr = dataSocket.getOutputStream();
                while ((n = sendfile.read(buff)) > 0){
                    outstr.write(buff, 0, n);
                }
                dataSocket.close();
                sendfile.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if (lsDetail)
        {
            doLsCurDetail();
        }
        else {
            doLsCur();
        }
    }

    private void doDel() {
        if (window.fileName.getText().length() == 0){
            window.currentMsg.setText("Please Enter FileName!");
        }
        else{
            try{
                System.out.println(window.fileName.getText());
                ctrlOutput.println("DELE " + window.fileName.getText());
                ctrlOutput.flush();
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        if (lsDetail)
        {
            doLsCurDetail();
        }
        else {
            doLsCur();
        }
    }

    public void doAscii(){
        try{
            ctrlOutput.println("TYPE A");
            ctrlOutput.flush();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void doBinary(){
        try{
            ctrlOutput.println("TYPE I");
            ctrlOutput.flush();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void doQuit() {
        try{
            ctrlOutput.println("QUIT");
            ctrlOutput.flush();
            window.connBtn.setText("Connect");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
