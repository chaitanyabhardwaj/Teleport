package teleportdesktop;

import java.net.Socket;

public abstract class Beacon implements Runnable {

    private Socket socket;
    
    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    
    public Socket getSocket() {
        return this.socket;
    }
    
}
