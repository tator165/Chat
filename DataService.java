import java.io.*;
import java.util.*;

public class DataService {

    static List<Message> messages = new ArrayList<>();
    static Set<User> userRegInfo = new LinkedHashSet<>();

    static Set<User> chatsIds = new LinkedHashSet<>();

    //add message to List and write to file
    static void sendMessage(Message message){
        messages.add(message);
        System.out.println(messages);

        try(BufferedWriter writer = new BufferedWriter(new FileWriter("src\\Messages.txt", true))) {
            writer.write(messages.toString() + "\n");
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //check user and userToAdd and reject or add to file
    //check if they are already added and add to file
    static void addUserToChat(User allowedUser, UUID chatId, UUID userToAdd) {

        boolean isAuthorizedUser = userRegInfo.stream().anyMatch(user -> user.getId().equals(allowedUser.getId()));
        boolean isAuthorizedUserToAdd = userRegInfo.stream().anyMatch(user -> user.getId().equals(userToAdd));
        if (!isAuthorizedUser || !isAuthorizedUserToAdd) return;

        File file = new File("src\\ChatsId.txt");
        List<String> updatedLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith(chatId.toString())) {

                    if (!line.contains(userToAdd.toString())) {
                        line = line + " " + userToAdd;
                    }
                }
                updatedLines.add(line);
            }

        } catch (IOException _) {}

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        } catch (IOException _) {}
    }


    static UUID findChatId(UUID requestedId) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src\\ChatsId.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(" ");
                if (parts.length == 0) continue;

                try {
                    UUID parsedId = UUID.fromString(parts[0]);
                    if (parsedId.equals(requestedId)) {
                        System.out.println("Chat ID found: " + requestedId);
                        return parsedId;
                    }
                } catch (IllegalArgumentException _) {

                }
            }
            System.out.println("Chat ID not found.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //iterate over List and return user
    static User findUser(UUID userId) {
        for (User requestedUser : userRegInfo){
            if (requestedUser.getId().equals(userId)){
                return requestedUser;
            }
        }
        return null;
    }


    static boolean checkAccess(UUID requestedUserId) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src\\ChatsId.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(requestedUserId.toString())) {
                    System.out.println("User found: " + requestedUserId);
                    return true;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static void registration() throws IOException {
        System.out.print("Enter name: ");
        Scanner scan = new Scanner(System.in);
        String name = scan.nextLine();

        UUID password = UUID.randomUUID();
        System.out.println("Generated password: " + password);

        UUID id = UUID.randomUUID();
        System.out.println("Generated id: " + id);

        User newUser = new User(name, password, id);
        userRegInfo.add(newUser);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src\\Users.txt", true))) {
            writer.write(newUser + "\n");
        }

        System.out.println("User registered: " + newUser);
    }

    public static void getAllMessages(User logedUser) throws FileNotFoundException {
        //List<String> messagesList = new ArrayList<>();
        File file = new File("src\\ChatsId.txt");
        List<String> updatedLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {
                updatedLines.add(line);
            }

        } catch (IOException e) {}


        if (logedUser.getId().equals(DataService.findUser(logedUser.getId()).getId()) ){
            try (BufferedReader reader = new BufferedReader(new FileReader("src\\Messages.txt"))){
                String line;
                while ((line = reader.readLine()) != null){
                    if (line.contains(logedUser.getId().toString())) {
                        //messagesList.add(line);
                        System.out.println("Your message: " + line);
                    }
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public static void getAllChatMessages(UUID chatId){
        try (BufferedReader reader = new BufferedReader(new FileReader("src\\Chats.txt"))){
            String line;

            while ((line = reader.readLine()) != null){
                if (line.contains(chatId.toString())){
                    try (BufferedReader messageReader = new BufferedReader(new FileReader("src\\Messages.txt"))){
                        String messageLine;
                        while ((messageLine = messageReader.readLine()) != null){
                            if (line.contains(chatId.toString())) {
                                //messagesList.add(line);
                                System.out.println("Chat messages: " + messageLine);
                            }
                        }

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
