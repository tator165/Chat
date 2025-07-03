import java.util.UUID;

public class MainService {
    public static void main(String[] args) {
        User user = new User();

        user.registration();


        UUID id = user.userRegInfo.iterator().next().getId();
        UUID password = user.userRegInfo.iterator().next().getPassword();
        String name = user.userRegInfo.iterator().next().getName();

        login(user, name, password, id);
        getAllChats(user, id);
        UUID messageId = UUID.randomUUID();
        UUID chatId = UUID.randomUUID();
        DataService messageHandler = new DataService();

        Message message = new Message("Hi", id, chatId, messageId);
        messageHandler.sendMessage(message);
    }

    public static User login(User user, String name, UUID password, UUID id){
        for(UserWrapper loginInfo : user.userRegInfo){
            if(loginInfo.getId().equals(id) && loginInfo.getName().equals(name) && loginInfo.getPassword().equals(password)){
                System.out.println("OK");
            }
        }
        return user;
    }

    public static void getAllChats(User user, UUID userId){
        for(UserWrapper idInfo : user.userRegInfo){
            if(userId.equals(idInfo.getId())){
                System.out.println("OK");
            }
        }
    }

    public static void getAllMessages(UUID chatId){

    }

    public void addMessage(){

    }
}