import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class MainService {
    public static void main(String[] args) throws IOException {
        User user1 = new User();
        user1.loadUsersFromFile();
        Chat chat1 = new Chat();


        Scanner scanner = new Scanner(System.in);
        System.out.println("log or reg?: ");
        if(scanner.nextLine().equals("log")){
            System.out.println("Enter name, password");
            String name = scanner.nextLine();
            String passwordString = scanner.nextLine();
            login(user1, name, passwordString);
        } else if(scanner.nextLine().equals("reg")){
            user1.registration();
        }



//        UUID id = user1.userRegInfo.iterator().next().getId();
//        UUID password = user1.userRegInfo.iterator().next().getPassword();
//        String name = user1.userRegInfo.iterator().next().getName();;
//        System.gc();

//



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
//        Message message = new Message(scanner.nextLine(), id, chat1.getChatId(), messageId);
//        messageHandler.sendMessage(message);


    }

    public static User login(User user, String name, String passwordStr) {
        UUID password;
        try {
            password = UUID.fromString(passwordStr.trim());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid password format");
            return null;
        }

        for (UserWrapper loginInfo : user.userRegInfo) {
            if (loginInfo.getName().equals(name) && loginInfo.getPassword().equals(password)) {
                System.out.println("OK");
                return user;
            }
        }

        System.out.println("Wrong name or password");
        return null;
    }

    public static void getAllChats(User user, UUID userId){
        for(UserWrapper idInfo : user.userRegInfo){
            if(userId.equals(idInfo.getId())){
                System.out.println("OK");
            }
        }
    }

    public static List<String> getAllMessages(String chatId) {
        List<String> messagesList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src\\Chats.txt"))){
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
}