import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

public class MainService {
    public static void main(String[] args) throws IOException {
        User user = new User();

        user.registration();


        UUID id = user.userRegInfo.iterator().next().getId();
        UUID password = user.userRegInfo.iterator().next().getPassword();
        String name = user.userRegInfo.iterator().next().getName();
        Chat chat = new Chat();
        System.gc();

        login(user, name, password, id);
        getAllChats(user, id);
        UUID messageId = UUID.randomUUID();
        UUID chatId = UUID.randomUUID();
        DataService messageHandler = new DataService();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Create chat");
        String answer = scanner.nextLine();
        if (answer.equals("y")){
            chat.generateChatId();
        }
        Message message = new Message(scanner.nextLine(), id, chatId, messageId);
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