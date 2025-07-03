import java.util.UUID;

public class Message {


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

    public String getTextMessage(){
        return textMessage;
    }

    public UUID getChatId(){
        return chatId;
    }

    public UUID getSenderId(){
        return senderId;
    }

    public UUID getMessageId(){
        return messageId;
    }

}
