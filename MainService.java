import java.util.UUID;

public class MainService {
    public static void main(String[] args) {
        User user = new User();
        user.registration();  // регистрируем


        UUID id = user.userRegInfo.iterator().next().getId();

        getAllChats(user, id);
    }

    public void login(String username, UUID password, UUID id){


    }

    public static void getAllChats(User user, UUID userId){
        for(UserInfo info : user.userRegInfo){
            if(userId.equals(info.getId())){
                System.out.println("OK");
            }
        }
    }
}