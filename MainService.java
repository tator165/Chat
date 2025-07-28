import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

public class MainService {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new MainService().run();
    }

    public void run() {
        UserRepository.loadUsersFromFile();

        User loggedInUser = null;

        while (loggedInUser == null) {
            System.out.print("log or reg?: ");
            String response = scanner.nextLine().trim().toLowerCase();

            if (response.equals("log")) {
                loggedInUser = handleLogin();
            } else if (response.equals("reg")) {
                handleRegistration();
                loggedInUser = handleLogin();
            } else {
                System.out.println("Invalid input, try again.");
            }
        }

        while (true) {
            showMenu();
            int choice = getIntInput();

            switch (choice) {
                case 1 -> ChatRepository.getAllChats(loggedInUser.getId()); // not implemented
                case 2 -> MessageRepository.getAllMessages(loggedInUser);
                case 3 -> ChatRepository.createChat(loggedInUser.getId());
                case 4 -> writeMessageToChat(loggedInUser);
                case 5 -> addUserToChat(loggedInUser);
                case 6 -> getChatMessages(loggedInUser);
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private User handleLogin() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter password (UUID): ");
        String password = scanner.nextLine();
        System.out.print("Enter user ID (UUID): ");
        UUID userId = UUID.fromString(scanner.nextLine());

        return DataService.login(name, password, userId);
    }

    private void handleRegistration() {
        try {
            DataService.registration();
        } catch (IOException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }

    private void showMenu() {
        System.out.println("""
                Choose action:
                 1. Get All Chats (not implemented)
                 2. Get All Your Messages
                 3. Create Chat
                 4. Write Message to Chat
                 5. Add User to Chat
                 6. Get All Messages From Chat
                """);
    }

    private int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private void writeMessageToChat(User user) {
        System.out.print("Enter message text: ");
        String text = scanner.nextLine();

        System.out.print("Enter chat ID: ");
        UUID chatId = UUID.fromString(scanner.nextLine());

        if (UserRepository.checkAccess(user.getId())) {
            Message message = new Message(text, user.getId(), ChatRepository.findChatId(chatId), UUID.randomUUID());
            MessageRepository.sendMessage(message);
        } else {
            System.out.println("Access denied");
        }
    }

    private void addUserToChat(User user) {
        System.out.print("Enter user ID to add: ");
        UUID userToAdd = UUID.fromString(scanner.nextLine());

        System.out.print("Enter chat ID: ");
        UUID chatId = UUID.fromString(scanner.nextLine());

        UserRepository.addUserToChat(user, chatId, userToAdd);
    }

    private void getChatMessages(User user) {
        System.out.print("Enter chat ID: ");
        UUID chatId = UUID.fromString(scanner.nextLine());
        MessageRepository.getAllChatMessages(chatId, user.getId());
    }
}
