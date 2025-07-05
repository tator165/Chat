import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class MainService {
    public static void main(String[] args) throws IOException {
        User user1 = new User();
        Chat chat1 = new Chat();
        user1.registration();


        UUID id = user1.userRegInfo.iterator().next().getId();
        UUID password = user1.userRegInfo.iterator().next().getPassword();
        String name = user1.userRegInfo.iterator().next().getName();;
        System.gc();

        login(user1, name, password, id);





        Scanner scanner = new Scanner(System.in);
        System.out.println("Create chat or use existing chat? ");
        String answer = scanner.nextLine();
        if (answer.equals("y")){
            chat1.generateChatId();
        }else {
            System.out.println("Enter chatId: ");
            answer = scanner.nextLine();
            getAllMessages(answer);
        }


        DataService messageHandler = new DataService();
        UUID messageId = UUID.randomUUID();
        Message message = new Message(scanner.nextLine(), id, chat1.getChatId(), messageId);
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

    public static List<String> getAllMessages(String chatId) throws FileNotFoundException {
        List<String> messagesList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\ihor\\IdeaProjects\\Chats\\src\\Chats.txt"))){
            String line;
            while ((line = reader.readLine()) != null){
                if (line.contains(chatId)) {
                    messagesList.add(line);
                    System.out.println(line);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return messagesList;
    }

    public void addMessage(){

    }
}