import java.io.*;
import java.util.*;

public class User {

    String userName;
    UUID userPassword;
    UUID userId;

    public User(String name, UUID password, UUID id){
        this.userName = name;
        this.userPassword = password;
        this.userId = id;
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

}
