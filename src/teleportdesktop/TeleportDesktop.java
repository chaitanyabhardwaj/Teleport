/*
THIS IS THE TEST CLASS
*/

package teleportdesktop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class TeleportDesktop {

    public static void main(String[] args) throws InterruptedException {
        TeleportDesktop td = new TeleportDesktop();
        //td.startServer(5000); //starts server
        //td.startClient("192.168.43.1", 5000); //starts client
    }
    
    public void startServer(int port) {
        Thread thread = new Thread(() -> {
           TeleportServer server;
           try {
                server = new TeleportServer(port);
                server.activate(new File("my_file.mp4"));
            } catch (IOException ex) {
                System.out.println(ex.toString());
            }
        });
        thread.start();
    }
    
    public void startClient(String ip, int port) {
        try {
            TeleportClient client = new TeleportClient();
            List list = (List) client.capture(ip, port, new TeleportClient.FileMethod());
            System.out.println("Got File : " + list.get(1) + " (Size : " + list.get(0) + ")");
            //write to file
            File file = new File("" + list.get(1));
            if(file.exists())
                file.delete();
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] ba = (byte[])list.get(2);
            fos.write(ba);
            fos.flush();
            fos.close();
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }
    
}
