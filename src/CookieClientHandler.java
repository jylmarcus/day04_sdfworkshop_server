import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;

public class CookieClientHandler extends Thread {
    private Socket socket;
    private Cookie cookie;

    public CookieClientHandler() {

    }

    public CookieClientHandler(Socket socket, Cookie cookie) {
        this.socket = socket;
        this.cookie = cookie;
    }

    @Override
    public void run() {
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
                        dos.writeUTF(this.cookie.getRandomCookie());
                        dos.flush();
                    } else if(msgReceived.equals("close")){
                        dos.writeUTF("Closing client");
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
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
