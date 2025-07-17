import java.io.*;
import java.util.*;

public class DataService {




    static Set<User> chatsIds = new LinkedHashSet<>();


    //check user and userToAdd and reject or add to file
    //check if they are already added and add to file


    public static void registration() throws IOException {
        System.out.print("Enter name: ");
        Scanner scan = new Scanner(System.in);
        String name = scan.nextLine();

        UUID password = UUID.randomUUID();
        System.out.println("Generated password: " + password);

        UUID id = UUID.randomUUID();
        System.out.println("Generated id: " + id);

        User newUser = new User(name, password, id);
        UserService.userRegInfo.put(id,newUser);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src\\Users.txt", true))) {
            writer.write(newUser + "\n");
        }

        System.out.println("User registered: " + newUser);
    }

    public static User login(String name, String passwordStr, UUID userId) {
        UUID password;
        try {
            password = UUID.fromString(passwordStr.trim());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid password format");
            return null;
        }

        if (UserService.userRegInfo.containsKey(userId)) {
            User user = UserService.userRegInfo.get(userId);
            if (user.getPassword().equals(UUID.fromString(passwordStr))) {
                return new User(name, password, userId);
            }
        }

        System.out.println("Wrong name or password");
        return null;
    }

}
