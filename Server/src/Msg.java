import java.io.Serializable;

public class Msg implements Serializable {
    private String owner;
    private String text;
    private String type; //join msg

    public Msg(String owner, String text, String type) {
        this.owner = owner;
        this.text = text;
        this.type = type;
    }

    public String getOwner() {
        return owner;
    }

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }
}