import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class FtpThread extends Thread{
    ftpClientInterface window;
    int func;


    public FtpThread(ftpClientInterface win, int func){
        this.window = win;
        this.func = func;
    }

    public void run(){
        switch (func){
            case 1:
                connect();
                break;
        }
    }

    private void connect(){

    }

}
