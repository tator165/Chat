import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DataService {

    List<Message> messages = new ArrayList<>();


    public void sendMessage(Message message){
        messages.add(message);
        System.out.println(messages);

        try(BufferedWriter writer = new BufferedWriter(new FileWriter("src\\Chats.txt", true))) {
            writer.write(messages.toString() + "\n");
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
