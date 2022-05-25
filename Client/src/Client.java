import java.awt.font.NumericShaper;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client extends ClientData {
    private String ip;
    private int port;
    private String name;

    private String checkUser;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void start(){
        try {
            Socket socket = new Socket(ip,port);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

            // listener part
            Listener listener = new Listener(new ObjectInputStream(socket.getInputStream()));
            Thread listenerThread = new Thread(listener);
            listenerThread.start();

            //outputStream.writeObject(new Msg(name,"","join"));
            Scanner scanner = new Scanner(System.in);
            while(true) {
                try {
                    System.out.println("1. Sign Up\n2. Sign In");
                    int type = Integer.parseInt(scanner.nextLine());
                    if (type != 1 && type != 2)
                        continue;
                    if (type == 1) {
                        System.out.print("Username : ");
                        String user = scanner.nextLine();
                        System.out.print("Password : ");
                        String pass = scanner.nextLine();
                        System.out.print("Confirm Password : ");
                        String confPass = scanner.nextLine();
                        outputStream.writeObject(new Msg("Null", user, "CheckUser"));
                        if (checkUser.equals("False")) {

                        }
                    }
                    break;
                }
                catch(NumberFormatException e) {
                    System.out.println("Enter a Valid Number");
                }
            }

            //send part
            while (true){
                String text = scanner.nextLine();
                outputStream.writeObject(new Msg(this.name, text , "msg"));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    private class Listener implements Runnable{
        ObjectInputStream objectInputStream;

        public Listener(ObjectInputStream objectInputStream) {
            this.objectInputStream = objectInputStream;
        }

        @Override
        public void run() {
            while (true){
                try {
                    Msg msg = (Msg) objectInputStream.readObject();
                    if(msg.getType().equals("CheckUser")) {
                        checkUser = msg.getText();
                    }
                    else {
                        System.out.println(msg.getOwner().toString() + " " + msg.getText().toString());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}