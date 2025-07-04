import java.util.*;

public class User {
    Scanner scan = new Scanner(System.in);
    Set<UserWrapper> userRegInfo = new LinkedHashSet<>();

    public void registration(){
        System.out.print("Enter name: ");
        String name = scan.nextLine();

        UUID password = UUID.randomUUID();
        System.out.println("Generated password: " + password);

        UUID id = UUID.randomUUID();
        System.out.println("Generated id: " + id);

        userRegInfo.add(new UserWrapper(name, password, id));

        System.out.println("User: ");
        for (UserWrapper info : userRegInfo){
            System.out.println(info);
        }
    }
}
