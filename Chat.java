import java.util.UUID;

public class Chat {
    private UUID chatId;
    UUID [] usersId;

    public UUID[] getUsersId(UUID[] usersId){
            this.usersId = usersId;
            return usersId;
    }

    public UUID getChatId(){
        return chatId;
    }

}
