import java.io.*;
import java.util.*;

public class DataService {

    static List<Message> messages = new ArrayList<>();
    static Set<User> userRegInfo = new LinkedHashSet<>();
    static Set<User> chatsIds = new LinkedHashSet<>();

    static void sendMessage(Message message){
        messages.add(message);
        System.out.println(messages);

        try(BufferedWriter writer = new BufferedWriter(new FileWriter("src\\Messages.txt", true))) {
            writer.write(messages.toString() + "\n");
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static UUID findChatId(UUID requestedId) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src\\ChatsId.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    UUID parsedId = UUID.fromString(line.trim());
                    if (parsedId.equals(requestedId)) {
                        System.out.println("Chat ID found: " + requestedId);
                        return parsedId;
                    }
                } catch (IllegalArgumentException e) {

                }
            }
            System.out.println("Chat ID not found.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
}
