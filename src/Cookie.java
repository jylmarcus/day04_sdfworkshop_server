import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cookie {
    List<String> cookies = null;

    public void readCookieFile(String fileName) throws IOException {
        // instantiate the cookies collection object
        cookies = new ArrayList<String>();
        // use a File object to pass the fileName
        File cookieFile = new File(fileName);
        // use FileReader and BufferReader for reading from cookie file
        // instantiate FR then BR
        FileReader fr = new FileReader(cookieFile);
        BufferedReader br = new BufferedReader(fr);

        String lineInput = "";
        // while loop to loop through the file
        // read each line and add into the cookies collection object
        while((lineInput = br.readLine())!= null) {
            cookies.add(lineInput);
        }

        //close readers
        br.close();
        fr.close();
    }

    public String getRandomCookie() {
        int max = cookies.size();
        Random rand = new Random();
        String randomCookie;

        try {
            cookies.get(rand.nextInt(max));
        } catch (IllegalArgumentException ex){
            ex.printStackTrace();
        } finally {
            randomCookie = cookies.get(rand.nextInt(max));
        }

        return randomCookie;
        
    }
}
