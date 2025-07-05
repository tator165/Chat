import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class Chat {
    private UUID chatId;
    UUID [] usersId;

    public UUID generateChatId() {
        chatId = UUID.randomUUID();
        return chatId;
    }

    public UUID[] getUsersId(UUID[] usersId){
            this.usersId = usersId;
            return usersId;
    }

    public UUID getChatId(){
        return chatId;
    }

}
