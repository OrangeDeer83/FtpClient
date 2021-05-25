import javax.swing.*;
import java.io.BufferedReader;

class CtrlListen extends Thread {
    BufferedReader ctrlInput = null;
    Interface window;
    String line;
    JScrollBar jScrollBar;
    int login;


    public CtrlListen(Interface win, BufferedReader in){
        ctrlInput = in;
        this.window = win;
        jScrollBar = window.jscrollpanel.getVerticalScrollBar();
        login = 0;
    }

    public void run(){
        while(login != -1){
            try{
                if ((line = ctrlInput.readLine()) != null) {
                    if (login == 1 || login == 2 || login == -1){
                        window.ServerMsg.append("\n" + line);
                        window.currentMsg.setText(line);
                    }
                    if (line.split(" ")[0].equals("230")){
                        login = 1;
                        window.ServerMsg.setText("Logged on!");
                        window.connBtn.setText("Disconnect");
                        window.connBtn.setEnabled(true);
                    }
                    if (line.split(" ")[0].equals("530")){
                        login = 2;
                        window.ServerMsg.setText("Please Enter CORRECT Username & Password!\n");
                        window.currentMsg.setText("Please Enter CORRECT Username & Password!");
                        window.connBtn.setText("Connect");
                        window.connBtn.setEnabled(true);
                    }
                    if (line.split(" ")[0].equals("221")){
                        login = -1;
                        window.dirName.setText("");
                        window.fileName.setText("");
                        window.currentMsg.setText("Good Bye");
                        window.connBtn.setText("Connect");
                        window.ServerMsg.setText("");
                        window.FileList.setText("");
                        continue;
                    }
                    jScrollBar.setValue(jScrollBar.getMaximum());
                }
            } catch (Exception e) {
            }
        }
    }

    public int isLogin(){
        return login;
    }
}