import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class MainService {
    public static void main(String[] args) throws IOException {

        User loggedInUser = null;

        Scanner scanner = new Scanner(System.in);
        System.out.println("log or reg?: ");
        String response = scanner.nextLine();
        if(response.equals("log")){

            System.out.println("Enter name, password");
            String name = scanner.nextLine();
            String passwordString = scanner.nextLine();
            loadUsersFromFile();
            loggedInUser = login(name,passwordString);

        } else if(response.equals("reg")){

            DataService.registration();

            System.out.println("Enter name, password");
            String name = scanner.nextLine();
            String passwordString = scanner.nextLine();
            loadUsersFromFile();
            loggedInUser = login(name,passwordString);


        } else System.out.println("try again");


        chooseAction(loggedInUser);


//        UUID id = user1.userRegInfo.iterator().next().getId();
//        UUID password = user1.userRegInfo.iterator().next().getPassword();
//        String name = user1.userRegInfo.iterator().next().getName();;
//        System.gc();

        Chat chat1 = new Chat();



    }


    public static void chooseAction(User loggedInUser){
        System.out.println("Choose action: getAllChats = 1(Неправильно работает), getAllMessages = 2, createChat = 3, writeMessageToChat = 4");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice){
            case  1 : getAllChats(loggedInUser.getId()); break;
            case  2 : getAllMessages(); break;
            case  3 : createChat(); break;
            case  4 :
                System.out.println("Enter message text:");
                String text = scanner.nextLine();
                System.out.println("Enter chatID text:");
                String idText = scanner.nextLine();
                DataService.sendMessage(new Message(text, loggedInUser.getId(), DataService.findChatId(UUID.fromString(idText)), UUID.randomUUID()));
                break;
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

    public static UUID createChat(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src\\ChatsId.txt", true))) {
            UUID generatedChat = UUID.randomUUID();
            writer.write(generatedChat.toString());
            return generatedChat;
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }

    public static List<String> getAllMessages() {
        List<String> messagesList = new ArrayList<>();
        System.out.println("Enter chatId: ");
        Scanner scanner = new Scanner(System.in);
        String enteredId;
        enteredId = scanner.nextLine();
        try (BufferedReader reader = new BufferedReader(new FileReader("src\\Messages.txt"))){
            String line;
            while ((line = reader.readLine()) != null){
                if (line.contains(enteredId)) {
                    messagesList.add(line);
                    System.out.println(line);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return messagesList;
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
        } catch (IOException e) {}
    }
}