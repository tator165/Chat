import java.io.*;
import java.util.UUID;

public class ChatRepository {

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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static void createChat(UUID userId){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src\\ChatsId.txt", true))) {
            UUID generatedChat = UUID.randomUUID();
            writer.write(generatedChat + " userId: " + userId + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }

    public static void getAllChats(UUID userId){
        if(UserService.userRegInfo.containsKey(userId)){
            System.out.println("Ok");
            User us = UserService.userRegInfo.get(userId);
        }
    }
}
