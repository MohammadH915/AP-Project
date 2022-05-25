import java.util.ArrayList;

public class DataBase {
    private  ArrayList <ClientData> clientDatas = new ArrayList<>();
    private int maxIdClient;

    public DataBase() {
        this.clientDatas = new ArrayList<>();
        this.maxIdClient = 1;
    }

    public int getMaxIdClient() {
        return maxIdClient;
    }

    public void setMaxIdClient(int maxId) {
        this.maxIdClient = maxId;
    }

    public void addClient(ClientData clientData) {
        clientDatas.add(clientData);
    }

    public ClientData getClinet(String username) {
        for(ClientData clientData : clientDatas) {
            if(clientData.getUsername().equals(username))
                return clientData;
        }
        return null;
    }

    public boolean checkClient(String username) {
        for(ClientData clientData : clientDatas) {
            if(clientData.getUsername().equals(username))
                return true;
        }
        return false;
    }

    public boolean checkPassword(String username, String password) {
        for(ClientData clientData : clientDatas) {
            if(clientData.getUsername().equals(username) && clientData.getPassword().equals(password))
                return true;
        }
        return false;
    }
}
