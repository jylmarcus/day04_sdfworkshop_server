import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class App {
    public static void main(String[] args) throws NumberFormatException, IOException {
        // 2 arguments
        // 1 argument for file
        // 1 argument for port server will start on
        String fileName = args[0];
        String port = args[1];

        File cookieFile = new File(fileName); // assume fileName is full path

        // test cookie class
        Cookie cookie = new Cookie();
        cookie.readCookieFile(fileName);
        System.out.println(cookie.getRandomCookie());
        System.out.println(cookie.getRandomCookie());

        if (!cookieFile.exists()) {
            System.out.println("Cookie file not found");
            System.exit(0);
        }

        // slide 8 - establish server connection
        ServerSocket ss = new ServerSocket(Integer.parseInt(port));
        Socket socket = ss.accept();

        // slide 9
        try (InputStream is = socket.getInputStream()) {
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis); // read up on this later

            // store data sent over from client
            String msgReceived = "";

            try (OutputStream os = socket.getOutputStream()) {
                BufferedOutputStream bos = new BufferedOutputStream(os);
                DataOutputStream dos = new DataOutputStream(bos);

                while (!msgReceived.equals("close")) {
                    // slide 9 - receive message
                    msgReceived = dis.readUTF();

                    if (msgReceived.equals("get-cookie")) {
                        // instantiate cookie.java
                        // get a random cookie
                        // send cookie using DOS
                        dos.writeUTF(cookie.getRandomCookie());
                        dos.flush();
                    } else {
                        dos.writeUTF("Please enter a valid command");
                        dos.flush();
                    }
                    // slide 10 - send message

                }

                // close all output stream
                dos.close();
                bos.close();
                os.close();

            } catch (EOFException ex) {
                ex.printStackTrace();
            }

            // close all input stream
            dis.close();
            bis.close();
            is.close();

        } catch (EOFException ex) {
            socket.close();
            ss.close();
        }
    }
}
