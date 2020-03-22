package teleportdesktop;

import java.net.Socket;

public interface Method<T> {
    
    void execute(Socket socket);
    T getResult();
    
}
