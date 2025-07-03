import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;

public class DataService {

    Set<Message> messages = new HashSet<>();
    Scanner scan = new Scanner(System.in);

    public void sendMessage(Message message){
        String textMessage = scan.nextLine();

        UUID messageId = UUID.randomUUID();

        messages.add(new Message(textMessage, messageId, message.getSenderId(), message.getChatId()));
    }
}
