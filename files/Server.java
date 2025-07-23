import java.net.*;
import java.io.*;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void _start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String greeting = in.readLine();
        if ("hello".equals(greeting)) {
            out.println("hello client");
        } else {
            out.println("unrecognised greeting");
        }

        stop();
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }

    public void greetingClient() throws IOException {
        Client client = new Client();
        client.startConnection("127.0.0.1", 6666);
        String response = client.sendMessage("hello");
        System.out.println("Server response: " + response);
        client.stopConnection();
    }

    public static void main(String[] args) {
        Server server = new Server();

        Thread serverThread = new Thread(() -> {

            try {
                server._start(6666);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        serverThread.start();

        try {
            server.greetingClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
