
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws NumberFormatException, IOException {
        // 2 arguments
        // 1 argument for file
        // 1 argument for port server will start on
        String fileName = args[0];
        String port = args[1];

        File cookieFile = new File(fileName); // assume fileName is full path

        //initialize cookie input
        Cookie cookie = new Cookie();
        cookie.readCookieFile(fileName);

        if (!cookieFile.exists()) {
            System.out.println("Cookie file not found");
            System.exit(0);
        }

        // slide 8 - establish server connection
        ServerSocket ss = new ServerSocket(Integer.parseInt(port));
        System.out.println("Press enter to continue");

        //scanner to close server
        Scanner scanner = new Scanner(System.in);

        while (!scanner.nextLine().equals("quit")) {
            Socket socket = ss.accept();
            System.out.println("Accepted connection from: " + socket.getInetAddress());
            CookieClientHandler c = new CookieClientHandler(socket, cookie);
            c.start();
        }

        scanner.close();
        ss.close();
    }
}
