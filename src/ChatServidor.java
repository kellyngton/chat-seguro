import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServidor{
    private static List<PrintWriter> clientWriters = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Servidor iniciado. Aguardando conexões de clientes...");
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Novo cliente conectado: " + clientSocket.getInetAddress());
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                clientWriters.add(writer);

                Thread clientThread = new Thread(new ClientHandler(clientSocket, writer));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        private final PrintWriter writer;

        public ClientHandler(Socket clientSocket, PrintWriter writer) {
            this.clientSocket = clientSocket;
            this.writer = writer;
        }

        @Override
        public void run() {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("Mensagem recebida de " + clientSocket.getInetAddress() + ": " + line);
                    broadcast(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void broadcast(String encryptedMessage) {
        String message = getDecrypt(encryptedMessage);
        for (PrintWriter writer : clientWriters) {
            writer.println(message);
        }
    }

    private static String getDecrypt(String encryptedMessage) {
        return CriptografiaEngine.decrypt(encryptedMessage, 1, 12312);
    }
}

