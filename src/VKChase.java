import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class VKChase {
    // Trying to write a simple app without patterns
    public static void main(String[] args) throws IOException {
        System.out.println("User link:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String userlink = br.readLine();
        String result = Request.send("https://api.vk.com/method/utils.resolveScreenName?screen_name=" + userlink + "&v=5.37");
        System.out.println(result);
    }
}
