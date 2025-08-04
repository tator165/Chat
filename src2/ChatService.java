import java.util.*;

public class ChatService {

    static Map<UUID, Message> messages = new HashMap<>();

    public static void addUserToChat(User user) {
        System.out.print("Enter user ID to add: ");
        UUID userToAdd = UUID.fromString(MainService.scanner.nextLine());

        System.out.print("Enter chat ID: ");
        UUID chatId = UUID.fromString(MainService.scanner.nextLine());

        UserRepository.addUserToChat(user, chatId, userToAdd);
    }

}
