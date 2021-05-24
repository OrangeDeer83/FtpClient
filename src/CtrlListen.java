import javax.swing.*;
import java.io.BufferedReader;

class CtrlListen implements Runnable{
    BufferedReader ctrlInput = null;
    Interface window;
    String line;
    JScrollBar jScrollBar;

    public CtrlListen(Interface win, BufferedReader in){
        ctrlInput = in;
        this.window = win;
        jScrollBar = window.jscrollpanel.getVerticalScrollBar();
    }
    public void run(){
        try{
            if ((line = ctrlInput.readLine()) != null) {
                window.ServerMsg.append(line);
            }
        } catch (Exception e) {
            System.exit(1);
        }
        jScrollBar.setValue(jScrollBar.getMaximum());

        while(true){
            try{
                if ((line = ctrlInput.readLine()) != null) {
                    window.ServerMsg.append("\n" + line);
                    window.currentMsg.setText(line);
                    jScrollBar.setValue(jScrollBar.getMaximum());
                }
            } catch (Exception e) {
                System.exit(1);
            }
        }
    }
}