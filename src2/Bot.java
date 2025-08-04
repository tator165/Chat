import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

public class Bot {

    public static void start() throws IOException {
        Scanner scanner = new Scanner(System.in);
        String botName = "tator";
        UUID botPassword = UUID.fromString("8039c612-8a04-4314-b586-376be6059114");
        UUID botId = UUID.fromString("d757f4ec-c506-4168-b36c-abf68d31ca3d");

        User bot = new User(botName, botPassword, botId);

        System.out.println("Bot logged in: " + bot.getName());

        while (true) {
            MainRepository.showMenu();
            int choice = getIntInput();

            switch (choice) {
                case 1 -> ChatRepository.getAllChats(bot.getId()); // not implemented
                case 2 -> MessageRepository.getAllMessages(bot);
                case 3 -> ChatRepository.createChat(bot.getId());
                case 4 -> MessageRepository.writeMessageToChat(bot);
                //case 5 -> addUserToChat(bot);
                //case 6 -> getChatMessages(bot);
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
    }
    private static int getIntInput() {
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
