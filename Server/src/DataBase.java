import java.util.ArrayList;

public class DataBase {
    private  ArrayList <ClientData> clientDatas = new ArrayList<>();
    private int maxId;

    public DataBase() {
        this.clientDatas = new ArrayList<>();
        this.maxId = 1;
    }

    public int getMaxId() {
        return maxId;
    }

    public void setMaxId(int maxId) {
        this.maxId = maxId;
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
