import java.io.*;
import java.util.*;

public class User {

    String userName;
    UUID userPassword;
    UUID userId;

    public User(String name, UUID password, UUID id){

    }

    public String getName() {
        return userName;
    }

    public UUID getPassword() {
        return userPassword;
    }

    public UUID getId() {
        return userId;
    }

    @Override
    public String toString() {
        return userName + " " + userPassword + " " + userId;
    }



    static Set<User> userRegInfo = new LinkedHashSet<>();


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

        System.out.println("User: ");
        for (User info : userRegInfo){
            System.out.println(info + "\nwas registered");
        }

    }

}
