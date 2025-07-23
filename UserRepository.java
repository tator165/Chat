import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class UserRepository {


    static void addUserToChat(User allowedUser, UUID chatId, UUID userToAdd) {

        boolean isAuthorizedUser = UserService.userRegInfo.containsKey(allowedUser.getId());
        boolean isAuthorizedUserToAdd = UserService.userRegInfo.values().stream().anyMatch(user -> user.getId().equals(userToAdd));
        if (!isAuthorizedUser || !isAuthorizedUserToAdd) return;

        File file = new File("src\\files\\ChatsId.txt");

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

    //iterate over List and return user
    static User findUser(UUID userId) {
        if(UserService.userRegInfo.containsKey(userId)) return UserService.userRegInfo.get(userId);
        return null;
    }

    static boolean checkAccess(UUID requestedUserId) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src\\files\\ChatsId.txt"))) {
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

    public static void loadUsersFromFile() {
        try (Scanner fileScanner = new Scanner(new File("src\\files\\Users.txt"))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split(" ");
                    if (parts.length == 3) {
                        String name = parts[0];
                        UUID password = UUID.fromString(parts[1]);
                        UUID id = UUID.fromString(parts[2]);
                        UserService.userRegInfo.put(id, new User(name, password, id));
                    }
                }
            }
        } catch (IOException _) {}
    }
}
