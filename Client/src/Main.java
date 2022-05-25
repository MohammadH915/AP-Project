import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

//        System.out.println("insert your name");
//        String name = scanner.nextLine();

        Client client = new Client("127.0.0.1", 8888 );
        client.start();
    }
}