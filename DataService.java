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
        UserService.userRegInfo.add(newUser);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src\\Users.txt", true))) {
            writer.write(newUser + "\n");
        }

        System.out.println("User registered: " + newUser);
    }

    public static User login(String name, String passwordStr) {
        UUID password;
        try {
            password = UUID.fromString(passwordStr.trim());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid password format");
            return null;
        }

        for (User u : UserService.userRegInfo) {
            if (u.getName().equals(name) && u.getPassword().equals(password)) {
                System.out.println("OK");
                return new User(u.getName(), u.getPassword(), u.getId());
            }
        }

        System.out.println("Wrong name or password");
        return null;
    }

}
