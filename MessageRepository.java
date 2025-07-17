import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MessageRepository {

    //add message to List and write to file
    static void sendMessage(Message message){
        ChatService.messages.add(message);
        System.out.println(ChatService.messages);

        try(BufferedWriter writer = new BufferedWriter(new FileWriter("src\\Messages.txt", true))) {
            writer.write(ChatService.messages.toString() + "\n");
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getAllMessages(User logedUser) throws FileNotFoundException {
        //List<String> messagesList = new ArrayList<>();
        File file = new File("src\\ChatsId.txt");
        List<String> updatedLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {
                updatedLines.add(line);
            }

        } catch (IOException e) {}


        if (logedUser.getId().equals(UserRepository.findUser(logedUser.getId()).getId()) ){
            try (BufferedReader reader = new BufferedReader(new FileReader("src\\Messages.txt"))){
                String line;
                while ((line = reader.readLine()) != null){
                    if (line.contains(logedUser.getId().toString())) {
                        //messagesList.add(line);
                        System.out.println("Your message: " + line);
                    }
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public static void getAllChatMessages(UUID chatId, UUID loggedUser){
        try (BufferedReader reader = new BufferedReader(new FileReader("src\\ChatsId.txt"))){
            String line;

            while ((line = reader.readLine()) != null){
                if (line.contains(chatId.toString()) && line.contains(loggedUser.toString())){
                    try (BufferedReader messageReader = new BufferedReader(new FileReader("src\\Messages.txt"))){
                        String messageLine;
                        while ((messageLine = messageReader.readLine()) != null){
                            if (messageLine.contains(chatId.toString())) {
                                //messagesList.add(messageLine);
                                System.out.println("Chat messages: " + messageLine);
                            }
                        }

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
