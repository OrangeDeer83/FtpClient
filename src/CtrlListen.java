import java.io.BufferedReader;

class CtrlListen implements Runnable{
    BufferedReader ctrlInput = null;
    Interface window;

    public CtrlListen(Interface win, BufferedReader in){
        ctrlInput = in;
        this.window = win;
    }
    public void run(){
        while(true){
            try{
                if (ctrlInput.readLine() != null) {
                    window.ServerMsg.append(ctrlInput.readLine() + "\n");
                }
            } catch (Exception e) {
                System.exit(1);
            }
        }
    }
}