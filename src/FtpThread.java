import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class FtpThread extends Thread{
    ftpClientInterface window;
    PrintWriter ctrlOutput;
    BufferedReader ctrlInput;
    int func;


    public FtpThread(ftpClientInterface win, int func, PrintWriter ctrlOutput, BufferedReader ctrlInput){
        this.window = win;
        this.func = func;
        this.ctrlOutput = ctrlOutput;
        this.ctrlInput = ctrlInput;
    }

    public void run(){
        switch (func){
            case 1:
                doLogin();
                break;
            case 2:
                doLs();
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

    private String[][] convertToHtml(String content){
        String[] split = content.split("\s+|\n");
        System.out.println(split.length);
        String[][] output = new String[split.length / 9][2];
        System.out.println(output.length);
        for (int i = 1; i < output.length; i++){
            output[i - 1][0] = split[i * 9];
            output[i - 1][1] = split[8 + i * 9];
        }
        return output;
    }

    private void doLs(){
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
                //buff.toString();
            }
            String[][] result = convertToHtml(content);
            String[] localSiteFile = new String[/*result.length - 1*/1];
            for (int j = 0; j < result.length - 1; j++){
                if (result[j][0].charAt(0) == '-') {
                    localSiteFile[0] = "<img src='E:\\Homework\\NetworkApplication\\FtpClient\\document.png'>" + result[j][1];
                    localSiteFile[0] += "<br/>";
                    System.out.println(localSiteFile[0]);
                }
            }
            window.updateLocalSiteFile(localSiteFile);
            System.out.println(window.getLocalSiteFile());
            dataSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
