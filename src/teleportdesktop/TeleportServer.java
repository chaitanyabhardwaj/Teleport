package teleportdesktop;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TeleportServer {
    
    final public int PORT;
    final private ServerSocket SERVER_SOCKET;
    private boolean running;
    final private List<Socket> socketList = new ArrayList<>();
    
    public TeleportServer(int port) throws IOException {
        PORT = port;
        SERVER_SOCKET = new ServerSocket(PORT);
    }
    
    public void activate(Beacon beacon) throws IOException {
        if(running)
            return;
        running = true;
        Socket s;
        Thread t;
        while(running) {
            s = SERVER_SOCKET.accept();
            beacon.setSocket(s);
            socketList.add(s);
            t = new Thread(beacon);
            t.start();
        }
    }
    
    public void activate(File file) throws IOException {
        activate(FileBeacon.buildBeacon(file));
    }
    
    public void deactivate() throws IOException {
        running = false;
        //close all sockets
        for(Socket s : socketList)
            s.close();
    }
    
    public int numberOfConnections() {
        return socketList.size();
    }
    
}
