import java.util.Scanner;
import java.util.UUID;

public class Message {

    Scanner scanner = new Scanner(System.in);
    UUID chatId;
    String textMessage;
    UUID senderId;
    UUID messageId;

    public Message(String textMessage, UUID senderId, UUID chatId, UUID messageId) {
        this.chatId = chatId;
        this.textMessage = textMessage;
        this.senderId = senderId;
        this.messageId = messageId;
    }

    @Override
    public String toString() {
        return "Message{" +
                "textMessage='" + textMessage + '\'' +
                ", senderId=" + senderId +
                ", chatId=" + chatId +
                ", messageId=" + messageId +
                '}';
    }

    public String getTextMessage() {
        return textMessage;
    }

    public UUID getChatId(){
        return chatId;
    }

    public UUID getSenderId(){
        return senderId;
    }

    public UUID generateMessageId(){
        return UUID.randomUUID();
    }
    public UUID getMessageId(){
        return messageId;
    }

}
