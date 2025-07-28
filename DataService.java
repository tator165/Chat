import java.io.*;
import java.util.*;

public class DataService {

    private static final String USER_FILE_PATH = "src/files/Users.txt";

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

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE_PATH, true))) {
            writer.write(name + " " + password + " " + id + "\n");
        }

        System.out.println("Registration successful!");
        System.out.println("Your generated password: " + password);
        System.out.println("Your ID: " + id);
    }

    public static User login(String name, String passwordStr, UUID providedId) {
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
}
