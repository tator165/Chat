import java.util.UUID;

public class Chat {
    private UUID chatId;
    UUID [] usersId;

    public void setChatId(UUID chatId) {
        this.chatId = chatId;
    }

    public UUID[] getUsersId(UUID[] usersId){
            this.usersId = usersId;
            return usersId;
    }
}
