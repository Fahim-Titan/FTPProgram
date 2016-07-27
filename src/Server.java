import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private int currentTot;
    ServerSocket serversocket;
    Socket client;
    int bytesRead;
    Connect c = new Connect();
    BufferedReader input;
    PrintWriter output;

    public void start() throws IOException{
        System.out.println("Connection Starting on port:" + c.getPort());
        //make connection to client on port specified
        serversocket = new ServerSocket(c.getPort());

        //accept connection from client
        client = serversocket.accept();

        System.out.println("Waiting for connection from client");

        try {
            logInfo();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void logInfo() throws Exception{
        //open buffered reader for reading data from client
        input = new BufferedReader(new InputStreamReader(client.getInputStream()));

        String username = input.readLine();
        System.out.println("SERVER SIDE" + username);
        String password = input.readLine();
        System.out.println("SERVER SIDE" + password);

        //open printwriter for writing data to client
        output = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));

        if(username.equals(c.getUsername()) &&password.equals(c.getPassword())){
            output.println("Welcome, " + username);
            File file = new File("D:\\XXXXXFahimXXXXX\\ArtofWarbySunTzu.pdf");
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);

            //Get socket's output stream
            OutputStream os = client.getOutputStream();

            //Read File Contents into contents array
            byte[] contents;
            long fileLength = file.length();
            long current = 0;

            long start = System.nanoTime();
            while(current!=fileLength){
                int size = 10000;
                if(fileLength - current >= size)
                    current += size;
                else{
                    size = (int)(fileLength - current);
                    current = fileLength;
                }
                contents = new byte[size];
                bis.read(contents, 0, size);
                os.write(contents);
                System.out.print("Sending file ... "+(current*100)/fileLength+"% complete!\n");
            }

            os.flush();
            //File transfer done. Close the socket connection!
            serversocket.close();
           //serversocket.close();
            System.out.println("File sent succesfully!");
        }else{
            output.println("Login Failed");
        }

    }
    public static void main(String[] args){
        Server server = new Server();
        try {
            server.start();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}