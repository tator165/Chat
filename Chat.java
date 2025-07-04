import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class Chat {
    private UUID chatId;
    UUID [] usersId;

    public UUID generateChatId() {
        chatId = UUID.randomUUID();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\ihor\\IdeaProjects\\Chats\\src\\Chats.txt", true))) {
            writer.write(String.valueOf(chatId));
            writer.newLine();
        } catch (IOException e){}

        return chatId;
    }

    public UUID[] getUsersId(UUID[] usersId){
            this.usersId = usersId;
            return usersId;
    }



}
