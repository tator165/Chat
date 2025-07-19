import java.io.*;
import java.util.Scanner;
import java.util.UUID;

public class MainService {
    public static void main(String[] args) throws IOException {
        _entryPoint();
    }


    public static void chooseAction(User loggedInUser){
        System.out.println("Choose action: getAllChats = 1(do not work), getAllMessages = 2, createChat = 3, writeMessageToChat = 4, addUserToChat = 5, getAllMessagesFromChat = 6");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice){
            case  1 : ChatRepository.getAllChats(loggedInUser.getId()); chooseAction(loggedInUser); break;
            case  2 : MessageRepository.getAllMessages(loggedInUser); chooseAction(loggedInUser); break;
            case  3 : ChatRepository.createChat(loggedInUser.getId()); chooseAction(loggedInUser); break;
            case  4 :
                System.out.println("Enter message text:");
                String text = scanner.nextLine();
                System.out.println("DEBUG: text = '" + text + "'");
                System.out.println("Enter chatID text:");
                String idText = scanner.nextLine();
                System.out.println("DEBUG: idText = '" + idText + "'");
                if (UserRepository.checkAccess(loggedInUser.getId())){
                    MessageRepository.sendMessage(new Message(text, loggedInUser.getId(), ChatRepository.findChatId(UUID.fromString(idText)), UUID.randomUUID()));
                }
                else {
                    System.out.println("Access denied");
                }
                chooseAction(loggedInUser);
                break;
            case 5 :
                System.out.println("Write user`s id to add");
                UUID userToAdd = UUID.fromString(scanner.nextLine());
                System.out.println("Enter chat ID to add");
                UUID chatToAddUser = UUID.fromString(scanner.nextLine());
                UserRepository.addUserToChat(loggedInUser, chatToAddUser,userToAdd);
                chooseAction(loggedInUser);
                break;
            case 6 :
                System.out.println("Enter chat id: ");
                UUID chatId = UUID.fromString(scanner.nextLine());
                MessageRepository.getAllChatMessages(chatId, loggedInUser.getId());
        }
    }


    private static void _entryPoint() throws IOException {
        User loggedInUser;

        Scanner scanner = new Scanner(System.in);
        System.out.println("log or reg?: ");
        String response = scanner.nextLine();
        UUID userID = UUID.randomUUID(); //remove
        if(response.equals("log")){

            System.out.println("Enter name, password");
            String name = scanner.nextLine();
            String passwordString = scanner.nextLine();
            UserRepository.loadUsersFromFile();
            loggedInUser = DataService.login(name,passwordString,userID);
            if (loggedInUser != null) {
                chooseAction(loggedInUser);
            } else _entryPoint();
        } else if(response.equals("reg")){

            DataService.registration();

            System.out.println("Enter name, password");
            String name = scanner.nextLine();
            String passwordString = scanner.nextLine();
            UserRepository.loadUsersFromFile();
            loggedInUser = DataService.login(name,passwordString,userID);
            if (loggedInUser != null) {
                chooseAction(loggedInUser);
            } else _entryPoint();
        } else {
            System.out.println("try again");
            _entryPoint();
        }
    }
}