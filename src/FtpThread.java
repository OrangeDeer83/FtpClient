import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

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
                if (lsDetail)
                {
                    doLsCurDetail();
                }
                else {
                    doLsCur();
                }
                break;
            case 3:
                doLs();
                break;
            case 4:
                doLsDetail();
                break;
            case 5:
                doMkdir();
                if (lsDetail)
                {
                    doLsCurDetail();
                }
                else {
                    doLsCur();
                }
                break;
            case 6:
                doRmdir();
                if (lsDetail)
                {
                    doLsCurDetail();
                }
                else {
                    doLsCur();
                }
                break;
            case 7:
                doLsCurDetail();
                break;
            case 8:
                doCdRoot();
                if (lsDetail)
                {
                    doLsCurDetail();
                }
                else {
                    doLsCur();
                }
                break;
            case 9:
                doUpOneDir();
                if (lsDetail)
                {
                    doLsCurDetail();
                }
                else {
                    doLsCur();
                }
                break;
            case 10:
                doGet();
                break;
            case 11:
                doPut();
                if (lsDetail)
                {
                    doLsCurDetail();
                }
                else {
                    doLsCur();
                }
                break;
            case 12:
                doDel();
                if (lsDetail)
                {
                    doLsCurDetail();
                }
                else {
                    doLsCur();
                }
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
    }


    public Socket dataConnection(String ctrlcmd){
        String cmd = "PORT ";
        int i;
        Socket dataSocket = null;
        try{
            cmd = cmd + window.host.getText().replace(".", ",") + ",";
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
            System.exit(1);
        }
        return dataSocket;
    }

    private void doLogin(){
        BufferedReader lineread = new BufferedReader(new InputStreamReader(System.in));
        if (window.username.getText().length() == 0){
            window.ServerMsg.append("Please Enter Username!\n");
            window.currentMsg.setText("Please Enter Username!");
        }
        else if (window.password.getText().length() == 0){
            window.ServerMsg.append("Please Enter Password!\n");
            window.currentMsg.setText("Please Enter Password!");
        }
        else{
            try{
                ctrlOutput.println("USER " + window.username.getText());
                ctrlOutput.flush();
                ctrlOutput.println("PASS " + window.password.getText());
                ctrlOutput.flush();
                if (lsDetail)
                {
                    doLsCurDetail();
                }
                else {
                    doLsCur();
                }
                window.connBtn.setText("Disconnect");
                return;
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    private void doCd() {
        if (window.dirName.getText().length() == 0){
            window.ServerMsg.append("Please Enter Directory Name!\n");
            window.currentMsg.setText("Please Enter Directory Name!");
        }
        else{
            try{
                ctrlOutput.println("CWD " + window.dirName.getText());
                ctrlOutput.flush();
            } catch(Exception e){
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    private void doLs(){
        if (window.dirName.getText().length() == 0){
            window.ServerMsg.append("Please Enter Directory Name!\n");
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
                System.exit(1);
            }
        }
    }

    private void doLsDetail(){
        if (window.dirName.getText().length() == 0){
            window.ServerMsg.append("Please Enter Directory Name!\n");
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
                System.exit(1);
            }
        }
    }

    private void doMkdir() {
        if (window.dirName.getText().length() == 0){
            window.ServerMsg.append("Please Enter Directory Name!\n");
            window.currentMsg.setText("Please Enter Directory Name!");
        }
        else{
            try{
                ctrlOutput.println("MKD " + window.dirName.getText());
                ctrlOutput.flush();
            } catch(Exception e){
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    private void doRmdir() {
        if (window.dirName.getText().length() == 0){
            window.ServerMsg.append("Please Enter Directory Name!\n");
            window.currentMsg.setText("Please Enter Directory Name!");
        }
        else{
            try{
                ctrlOutput.println("RMD " + window.dirName.getText());
                ctrlOutput.flush();
            } catch(Exception e){
                e.printStackTrace();
                System.exit(1);
            }
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
            System.exit(1);
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
            System.exit(1);
        }
    }

    private void doCdRoot() {
        try{
            ctrlOutput.println("CWD /");
            ctrlOutput.flush();
        } catch(Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void doUpOneDir() {
        try{
            ctrlOutput.println("CWD ..");
            ctrlOutput.flush();
        } catch(Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void doGet() {
        if (window.dirName.getText().length() == 0){
            window.ServerMsg.append("Please Enter FileName!\n");
            window.currentMsg.setText("Please Enter FileName!");
        }
        else{
            try {
                int n;
                byte[] buff = new byte[1024];
                Socket dataSocket = dataConnection("RETR " + window.fileName.getText());
                FileOutputStream outfile = new FileOutputStream(window.fileName.getText());
                BufferedInputStream dataInput = new BufferedInputStream(dataSocket.getInputStream());
                while((n = dataInput.read(buff)) > 0){
                    outfile.write(buff, 0, n);
                }
                dataSocket.close();
                outfile.close();
            }catch (Exception e){
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    private void doPut() {
        if (window.dirName.getText().length() == 0){
            window.ServerMsg.append("Please Enter FileName!\n");
            window.currentMsg.setText("Please Enter FileName!");
        }
        else{
            try {
                int n;
                byte[] buff = new byte[1024];
                FileInputStream sendfile = new FileInputStream(window.fileName.getText());
                Socket dataSocket = dataConnection("STOR " + window.fileName.getText());
                OutputStream outstr = dataSocket.getOutputStream();
                while ((n = sendfile.read(buff)) > 0){
                    outstr.write(buff, 0, n);
                }
                dataSocket.close();
                sendfile.close();
            }catch (Exception e){
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    private void doDel() {
        if (window.dirName.getText().length() == 0){
            window.ServerMsg.append("Please Enter FileName!\n");
            window.currentMsg.setText("Please Enter FileName!");
        }
        else{
            try{
                System.out.println(window.fileName.getText());
                ctrlOutput.println("DELE " + window.fileName.getText());
                ctrlOutput.flush();
            } catch(Exception e){
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    public void doAscii(){
        try{
            ctrlOutput.println("TYPE A");
            ctrlOutput.flush();
        } catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void doBinary(){
        try{
            ctrlOutput.println("TYPE I");
            ctrlOutput.flush();
        } catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void doQuit() {
        try{
            ctrlOutput.println("QUIT");
            ctrlOutput.flush();
            window.connBtn.setText("Connect");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }
}
