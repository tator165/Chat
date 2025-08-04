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
            Path path = Paths.get(CHATS_FILE);
            List<String> lines = Files.readAllLines(path);
            List<String> updatedLines = new ArrayList<>();

            for (String line : lines) {
                if (line.startsWith(chatId.toString())) {
                    if (!line.contains(userToAdd.toString())) {
                        line += " " + userToAdd;
                    }
                }
                updatedLines.add(line);
            }

            Files.write(path, updatedLines);

        } catch (IOException _) {}
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
        } catch (IOException _) {

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

    public static User login(String name, String passwordStr) {
        UUID password;
        try {
            password = UUID.fromString(passwordStr.trim());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid password format. It must be a UUID.");
            return null;
        }

        for (User user : UserService.userRegInfo.values()) {
            if (user.getName().equalsIgnoreCase(name) && user.getPassword().equals(password)) {
                return user;
            }
        }

        System.out.println("Wrong name or password.");
        return null;
    }


    public static void registration() throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = scanner.nextLine().trim();

        for (User user : UserService.userRegInfo.values()) {
            if (user.getName().equalsIgnoreCase(name)) {
                System.out.println("This name is already registered.");
                return;
            }
        }

        UUID password = UUID.randomUUID();
        UUID id = UUID.randomUUID();

        User newUser = new User(name, password, id);
        UserService.userRegInfo.put(id, newUser);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE, true))) {
            writer.write(name + " " + password + " " + id + "\n");
        }

        System.out.println("Registration successful!");
        System.out.println("Your generated password: " + password);
        System.out.println("Your ID: " + id);
    }
}
