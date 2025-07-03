import java.util.HashSet;
import java.util.Set;

public class DataService {

    Set<Message> messages = new HashSet<>();

    public void sendMessage(Message message){
        messages.add(new Message(message.getTextMessage(), message.getSenderId(), message.getChatId(), message.getMessageId()));
        System.out.println(messages);
    }
}
