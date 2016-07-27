import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
public class Client {
    private final String FILENAME = null;
    Connect c = new Connect();
    Socket socket;
    BufferedReader read;
    PrintWriter output;

    public void startClient() throws UnknownHostException, IOException{
        //Create socket connection
        socket = new Socket(c.gethostName(), c.getPort());

        //create printwriter for sending login to server
        output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        Scanner scanner = new Scanner(System.in);
        //prompt for user name
        String username = JOptionPane.showInputDialog(null, "Enter User Name:");
//        String username = scanner.nextLine();
        scanner.nextLine();
        //send user name to server
        output.println(username);

        //prompt for password
        String password = JOptionPane.showInputDialog(null, "Enter Password");
//        String password = scanner.nextLine();
        //send password to server
        output.println(password);
        output.flush();

        //create Buffered reader for reading response from server
        read = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        //read response from server


        //display response
        //JOptionPane.showMessageDialog(null, response);

        byte[] contents = new byte[10000];

        //Initialize the FileOutputStream to the output file's full path.
        FileOutputStream fos = new FileOutputStream("D:\\XXXXXFahimXXXXX\\4-2\\lab\\ArtofWarbySunTzu.pdf");
//            FileOutputStream fos = new FileOutputStream(path);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        InputStream is = socket.getInputStream();

        //No of bytes read in one read() call
        int bytesRead = 0;

        while ((bytesRead = is.read(contents)) != -1)
            bos.write(contents, 0, bytesRead);

        bos.flush();
        String response = read.readLine();
        System.out.println("This is the response: " + response);
        socket.close();

        System.out.println("File saved successfully!");
    }

    public void fileInfo(){

    }

    public static void main(String args[]){
        Client client = new Client();
        try {
            client.startClient();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
