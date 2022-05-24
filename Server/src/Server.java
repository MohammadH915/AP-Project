import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListMap;

public class Server {
    private int port;
    private ConcurrentLinkedQueue<ClientHandler> clients = new ConcurrentLinkedQueue<ClientHandler>();

    public Server(int port) {
        this.port = port;
    }

    public void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("server is running on port :" + port);
            while (true) {
                Socket socket = serverSocket.accept();
                Thread thread = new Thread(new ClientHandler(socket));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendToAll(String sender, Msg msg) {
        Iterator iteratorClients = clients.iterator();
        while (iteratorClients.hasNext()) {
            ClientHandler clientHandler = (ClientHandler) iteratorClients.next();

            if (clientHandler.name.equals(sender) && msg.getType().equals("msg")) {
                //ignore the msg
            } else if (msg.getType().equals("msg")) {
                //send to others
                clientHandler.sendToClient(msg);
            } else if (msg.getType().equals("join")) {
                //join will send to all even who join the chat room
                clientHandler.sendToClient(msg); // commands return to all clients
            }
        }
    }


    private class ClientHandler implements Runnable {
        private Socket socket;
        private ObjectOutput objectOutput;
        private ObjectInput objectInput;
        private String name;

        public ClientHandler(Socket socket) {
            try {
                this.socket = socket;
                this.name = "";
                this.objectInput = new ObjectInputStream(socket.getInputStream());
                this.objectOutput = new ObjectOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        @Override
        public void run() {
            try {
                while (true) {
                    Msg msg = (Msg) objectInput.readObject();
                    if (msg.getType().equals("join")) {
                        System.out.println(msg.getOwner().toString() + " is joined to server");
                        this.name = msg.getOwner();
                        clients.add(this);
                        sendToAll("", new Msg(msg.getOwner(), "has joined the group", "join"));

                    } else {
                        System.out.println(msg.getOwner().toString() + " -- " + msg.getText());
                        sendToAll(msg.getOwner(), msg);
                    }
                }
            } catch (ClassNotFoundException e) {
                System.out.println("new error");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("new error accord");
                e.printStackTrace();
            }

        }

        private synchronized void sendToClient(Msg msg) {
            try {
                objectOutput.writeObject(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}