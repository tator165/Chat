import java.io.PrintStream;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;

public class User {
    Scanner scan = new Scanner(System.in);
    Set<UserInfo> userRegInfo = new HashSet<>();

    public void registration(){
        System.out.print("Enter name: ");
        String name = scan.nextLine();

        UUID password = UUID.randomUUID();
        System.out.println("Generated password: " + password);

        UUID id = UUID.randomUUID();
        System.out.println("Generated id: " + id);

        userRegInfo.add(new UserInfo(name, password, id));

        System.out.println("User: ");
        for (UserInfo info : userRegInfo){
            System.out.println(info);
        }
    }
}
