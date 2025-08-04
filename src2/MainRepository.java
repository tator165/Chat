import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

public class MainRepository {
    Scanner scanner = new Scanner(System.in);
    private User handleLogin() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter password (UUID): ");
        String password = scanner.nextLine();

        return UserRepository.login(name, password);
    }

    private void handleRegistration() {
        try {
            UserRepository.registration();
        } catch (IOException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }

    public void run() throws IOException {
        UserRepository.loadUsersFromFile();

        User loggedInUser = null;

        System.out.println("user or bot?: ");
        String resp = scanner.nextLine();
        if (resp.equals("user")){

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
                    case 1 -> ChatRepository.getAllChats(loggedInUser.getId());
                    case 2 -> MessageRepository.getAllMessages(loggedInUser);
                    case 3 -> ChatRepository.createChat(loggedInUser.getId());
                    case 4 -> MessageRepository.writeMessageToChat(loggedInUser);
                    case 5 -> ChatService.addUserToChat(loggedInUser);
                    case 6 -> getChatMessages(loggedInUser);
                    case 7 -> {
                        System.out.print("Enter message UUID to delete: ");
                        String input = scanner.nextLine().trim();
                        if (input.isEmpty()) {
                            System.out.println("UUID cannot be empty.");
                        } else {
                            try {
                                UUID id = UUID.fromString(input);
                                MessageRepository.deleteMessage(id);
                            } catch (IllegalArgumentException e) {
                                System.out.println("Invalid UUID format.");
                            }
                        }
                    }
                    default -> System.out.println("Invalid choice");
                }
            }
        } else if(resp.equals("bot")){
            Bot.start();
        } else System.out.println("restart");
    }

    public static void showMenu() {
        System.out.println("""
                Choose action:
                 1. Get All Chats
                 2. Get All Your Messages
                 3. Create Chat
                 4. Write Message to Chat
                 5. Add User to Chat
                 6. Get All Messages From Chat
                 7. Delete Message From Chat
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



    private void getChatMessages(User user) {
        System.out.print("Enter chat ID: ");
        UUID chatId = UUID.fromString(scanner.nextLine());
        MessageRepository.getAllChatMessages(chatId, user.getId());
    }

}
