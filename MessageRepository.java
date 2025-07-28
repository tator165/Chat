import java.io.*;
import java.util.*;

public class MessageRepository {

    private static final String MESSAGE_FILE_PATH = "src/files/Messages.txt";
    private static final String CHATS_FILE_PATH = "src/files/ChatsId.txt";

    public static void sendMessage(Message message){
        ChatService.messages.put(message.getMessageId(), message);
        String line = message.getMessageId() + " " + message.getChatId() + " " + message.getSenderId() + " " + message.getTextMessage();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MESSAGE_FILE_PATH, true))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getAllMessages(User loggedUser){
        UUID userId = loggedUser.getId();

        try (BufferedReader reader = new BufferedReader(new FileReader(MESSAGE_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null){
                if (line.contains(userId.toString())) {
                    System.out.println("Your message: " + line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getAllChatMessages(UUID chatId, UUID loggedUser){
        try (BufferedReader reader = new BufferedReader(new FileReader(CHATS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null){
                if (line.contains(chatId.toString()) && line.contains(loggedUser.toString())){
                    try (BufferedReader messageReader = new BufferedReader(new FileReader(MESSAGE_FILE_PATH))) {
                        String messageLine;
                        while ((messageLine = messageReader.readLine()) != null){
                            if (messageLine.contains(chatId.toString())) {
                                System.out.println("Chat messages: " + messageLine);
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
