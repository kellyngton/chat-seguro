import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatCliente {

    public static void main(String[] args) {

        try (Socket socket = new Socket("localhost", 12345);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Conectado ao servidor. Comece a digitar suas mensagens.");
            System.out.println("Digite '/sair' para encerrar o cliente.");

            Thread receiveThread = new Thread(() -> {
                try {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println("Mensagem recebida do servidor: " + line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            receiveThread.start();

            while (true) {
                String message = CriptografiaEngine.encrypt(scanner.nextLine(), 1, 12312);
                writer.println(message);
                if ("/sair".equals(message)) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

