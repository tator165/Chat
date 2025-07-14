import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class MainService {
    public static void main(String[] args) throws IOException {



//        UUID id = user1.userRegInfo.iterator().next().getId();
//        UUID password = user1.userRegInfo.iterator().next().getPassword();
//        String name = user1.userRegInfo.iterator().next().getName();;
//        System.gc();

        entryPoint();
    }


    public static void chooseAction(User loggedInUser) throws FileNotFoundException {
        System.out.println("Choose action: getAllChats = 1(do not work), getAllMessages = 2, createChat = 3, writeMessageToChat = 4, addUserToChat = 5, getAllMessagesFromChat = 6");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice){
            case  1 : getAllChats(loggedInUser.getId()); chooseAction(loggedInUser); break;
            case  2 : DataService.getAllMessages(loggedInUser); chooseAction(loggedInUser); break;
            case  3 : createChat(loggedInUser.getId()); chooseAction(loggedInUser); break;
            case  4 :
                System.out.println("Enter message text:");
                String text = scanner.nextLine();
                System.out.println("DEBUG: text = '" + text + "'");
                System.out.println("Enter chatID text:");
                String idText = scanner.nextLine();
                System.out.println("DEBUG: idText = '" + idText + "'");
                if (DataService.checkAccess(loggedInUser.getId())){
                    DataService.sendMessage(new Message(text, loggedInUser.getId(), DataService.findChatId(UUID.fromString(idText)), UUID.randomUUID()));
                    chooseAction(loggedInUser);
                    break;
                }
                else {
                    System.out.println("Access denied"); chooseAction(loggedInUser); break;
                }
            case 5 :
                System.out.println("Write user`s id to add");
                UUID userToAdd = UUID.fromString(scanner.nextLine());
                System.out.println("Enter chat ID to add");
                UUID chatToAddUser = UUID.fromString(scanner.nextLine());
                DataService.addUserToChat(loggedInUser, chatToAddUser,userToAdd);
                chooseAction(loggedInUser);
                break;
            case 6 :
                System.out.println("Enter chat id: ");
                UUID chatId = UUID.fromString(scanner.nextLine());
                DataService.getAllChatMessages(chatId);
        }
    }
    public static User login(String name, String passwordStr) {
        UUID password;
        try {
            password = UUID.fromString(passwordStr.trim());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid password format");
            return null;
        }

        for (User u : DataService.userRegInfo) {
            if (u.getName().equals(name) && u.getPassword().equals(password)) {
                System.out.println("OK");
                return new User(u.getName(), u.getPassword(), u.getId());
            }
        }

        System.out.println("Wrong name or password");
        return null;
    }

    public static void getAllChats(UUID userId){
        for(User idInfo : DataService.userRegInfo){
            if(userId.equals(idInfo.getId())){
                System.out.println("OK");
            }
        }
    }

    private static void entryPoint() throws IOException {
        User loggedInUser;

        Scanner scanner = new Scanner(System.in);
        System.out.println("log or reg?: ");
        String response = scanner.nextLine();
        if(response.equals("log")){

            System.out.println("Enter name, password");
            String name = scanner.nextLine();
            String passwordString = scanner.nextLine();
            loadUsersFromFile();
            loggedInUser = login(name,passwordString);
            if (loggedInUser != null) {
                chooseAction(loggedInUser);
            } else entryPoint();
        } else if(response.equals("reg")){

            DataService.registration();

            System.out.println("Enter name, password");
            String name = scanner.nextLine();
            String passwordString = scanner.nextLine();
            loadUsersFromFile();
            loggedInUser = login(name,passwordString);
            if (loggedInUser != null) {
                chooseAction(loggedInUser);
            } else entryPoint();
        } else {
            System.out.println("try again");
            entryPoint();
        }
    }
    public static void createChat(UUID userId){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src\\ChatsId.txt", true))) {
            UUID generatedChat = UUID.randomUUID();
            writer.write(generatedChat + " userId: " + userId + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }



    public static void loadUsersFromFile() {
        try (Scanner fileScanner = new Scanner(new File("src\\Users.txt"))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split(" ");
                    if (parts.length == 3) {
                        String name = parts[0];
                        UUID password = UUID.fromString(parts[1]);
                        UUID id = UUID.fromString(parts[2]);
                        DataService.userRegInfo.add(new User(name, password, id));
                    }
                }
            }
        } catch (IOException _) {}
    }
}