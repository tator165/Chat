import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class DataService {

    Set<Message> messages = new LinkedHashSet<>();

    public void sendMessage(Message message){
        messages.add(new Message(message.getTextMessage(), message.getSenderId(), message.getChatId(), message.getMessageId()));
        System.out.println(messages);

        try(BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\ihor\\IdeaProjects\\Chats\\src\\Chats.txt", true))) {
            writer.write(messages.toString() + "\n");
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
