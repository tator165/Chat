import java.util.UUID;

public class UserWrapper {
    String name;
    UUID password;
    UUID id;

    public UserWrapper(String name, UUID password, UUID id) {
        this.name = name;
        this.password = password;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public UUID getPassword() {
        return password;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return name + " " + password + " " + id;
    }
}