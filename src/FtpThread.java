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
                if (lsDetail)
                {
                    doLsCurDetail();
                }
                else {
                    doLsCur();
                }
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
        }
    }

    public Socket dataConnection(String ctrlcmd){
        String cmd = "PORT 127,0,0,1,";
        int i;
        Socket dataSocket = null;
        try{
            /*byte[] address = InetAddress.getLocalHost().getAddress();
            for(i = 0; i < 4; ++i){
                cmd = cmd + (address[i] & 0xff) + ",";
            }*/
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
        String loginName = "";
        String password = "";
        BufferedReader lineread = new BufferedReader(new InputStreamReader(System.in));
        try{
            ctrlOutput.println("USER " + "user"/*window.getUsername()*/);
            ctrlOutput.flush();
            ctrlOutput.println("PASS " + "user"/*window.getPassword()*/);
            ctrlOutput.flush();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void doCd() {
        try{
            ctrlOutput.println("CWD " + window.dirName.getText());
            ctrlOutput.flush();
        } catch(Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void doLs(){
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

    private void doLsDetail(){
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

    private void doMkdir() {
        try{
            ctrlOutput.println("MKD " + window.dirName.getText());
            ctrlOutput.flush();
        } catch(Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void doRmdir() {
        try{
            ctrlOutput.println("RMD " + window.dirName.getText());
            ctrlOutput.flush();
        } catch(Exception e){
            e.printStackTrace();
            System.exit(1);
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

    private void doPut() {
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

    private void doDel() {
        try{
            System.out.println(window.fileName.getText());
            ctrlOutput.println("DELE " + window.fileName.getText());
            ctrlOutput.flush();
        } catch(Exception e){
            e.printStackTrace();
            System.exit(1);
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

}
