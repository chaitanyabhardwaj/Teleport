package teleportdesktop;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TeleportClient {
    
    private Socket socket;
    
    public Object capture(String ip, int port, Method m)throws IOException {
        socket = new Socket(ip, port);
        m.execute(socket);
        socket.close();
        return m.getResult();
    }
    
    public static class FileMethod implements Method<List> {
        
        final private ArrayList<Object> dataList = new ArrayList<>();
        
        @Override
        public void execute(Socket s) {
            int temp, read, total = 0;
            byte byteArr[];
            try {
                DataInputStream din = new DataInputStream(new BufferedInputStream(s.getInputStream()));
                //get file size
                temp = din.readInt();
                dataList.add(temp);
                //get file name
                dataList.add(din.readUTF());
                //get file data
                byteArr = new byte[temp];
                while(total < temp) {
                    read = din.read(byteArr, total, (temp - total));
                    total += read;
                }
                dataList.add(byteArr);
                din.close();
                s.close();
            }
            catch(IOException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public List getResult() {
            return dataList;
        }
        
    }
    
}
