/*
* FileBeacon
* Protocol :
* 1. File Size - int
* 2. File name with extension - UTF
* 3. File bytes - byte
*/

package teleportdesktop;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileBeacon extends Beacon {
    
    private byte[] fileBytes;
    private int fileSize;
    private String fileName;
    
    private FileBeacon() {
        //non-instantiable
    }
    
    public static FileBeacon buildBeacon(File file) {
        FileBeacon beacon = new FileBeacon();
        try {
            if(file != null)
                beacon.setProjection(file);
        }
        catch(IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return beacon;
    }
    
    public void setProjection(File file) throws IOException {
        fileSize = (int)file.length();
        fileName = file.getName();
        fileBytes = new byte[fileSize];
        System.out.println("Projection Details :");
        System.out.println("File name : " + fileName);
        System.out.println("File Size : " + fileSize);
        //read file bytes
        FileInputStream fis = new FileInputStream(file);
        fis.read(fileBytes);
    }
    
    public void setProjection(String name, int len, byte []b) {
        fileName = name;
        fileSize = len;
        fileBytes = b;
        System.out.println("Projection Details :");
        System.out.println("File name : " + fileName);
        System.out.println("File Size : " + fileSize);
    }

    @Override
    public void run() {
        try {
            DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(getSocket().getOutputStream()));
            //write file size
            dos.writeInt(fileSize);
            dos.flush();
            //write file name
            dos.writeUTF(fileName);
            dos.flush();
            //write file bytes
            dos.write(fileBytes, 0, fileSize);
            dos.flush();
            //done!
            dos.close();
            getSocket().close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
