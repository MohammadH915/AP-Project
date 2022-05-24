import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client extends ClientData {
    private String ip;
    private int port;
    private String name;

    public Client(int id, String username, String password, String email, String phoneNumber, String ip, int port, String name) {
        super(id, username, password, email, phoneNumber);
        this.ip = ip;
        this.port = port;
        this.name = name;
    }

    public void start(){
        try {
            Socket socket = new Socket(ip,port);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

            // listener part
            Listener listener = new Listener(new ObjectInputStream(socket.getInputStream()));
            Thread listenerThread = new Thread(listener);
            listenerThread.start();

            outputStream.writeObject(new Msg(name,"","join"));


            //send part
            Scanner scanner = new Scanner(System.in);
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
                    System.out.println( msg.getOwner().toString() + " " + msg.getText().toString());


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }


        }
    }
}