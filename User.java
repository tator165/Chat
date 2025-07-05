import java.io.*;
import java.util.*;

public class User {
    Scanner scan = new Scanner(System.in);
    Set<UserWrapper> userRegInfo = new LinkedHashSet<>();


    public void registration() throws IOException {
        System.out.print("Enter name: ");
        String name = scan.nextLine();

        UUID password = UUID.randomUUID();
        System.out.println("Generated password: " + password);

        UUID id = UUID.randomUUID();
        System.out.println("Generated id: " + id);

        UserWrapper newUser = new UserWrapper(name, password, id);
        userRegInfo.add(newUser);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src\\Users.txt", true))) {
            writer.write(newUser + "\n");
        }

        System.out.println("User: ");
        for (UserWrapper info : userRegInfo){
            System.out.println(info + "\nwas registered");
        }

    }

    public void loadUsersFromFile() {
        try (Scanner fileScanner = new Scanner(new File("src\\Users.txt"))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split(" ");
                    if (parts.length == 3) {
                        String name = parts[0];
                        UUID password = UUID.fromString(parts[1]);
                        UUID id = UUID.fromString(parts[2]);
                        userRegInfo.add(new UserWrapper(name, password, id));
                    }
                }
            }
        } catch (IOException e) {
        }
    }

}
