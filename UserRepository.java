import java.io.*;
import java.nio.file.*;
import java.util.*;

public class UserRepository {

    private static final String USER_FILE = "src/files/Users.txt";
    private static final String CHATS_FILE = "src/files/ChatsId.txt";

    public static void addUserToChat(User allowedUser, UUID chatId, UUID userToAdd) {
        if (!UserService.userRegInfo.containsKey(allowedUser.getId())) return;
        if (UserService.userRegInfo.values().stream().noneMatch(u -> u.getId().equals(userToAdd))) return;

        try {
            List<String> lines = Files.readAllLines(Paths.get(CHATS_FILE));
            List<String> updatedLines = new ArrayList<>();

            for (String line : lines) {
                if (line.startsWith(chatId.toString())) {
                    if (!line.contains(userToAdd.toString())) {
                        line += " " + userToAdd;
                    }
                }
                updatedLines.add(line);
            }

            Files.write(Paths.get(CHATS_FILE), updatedLines);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static User findUser(UUID userId) {
        return UserService.userRegInfo.get(userId);
    }

    public static boolean checkAccess(UUID requestedUserId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(CHATS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(requestedUserId.toString())) return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void loadUsersFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split(" ");
                if (parts.length == 3) {
                    try {
                        String name = parts[0];
                        UUID password = UUID.fromString(parts[1]);
                        UUID id = UUID.fromString(parts[2]);
                        UserService.userRegInfo.put(id, new User(name, password, id));
                    } catch (IllegalArgumentException ignored) {
                        System.out.println("Skipping invalid user line: " + line);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load users from file", e);
        }
    }
}
