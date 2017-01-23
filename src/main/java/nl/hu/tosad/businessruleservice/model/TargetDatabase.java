package nl.hu.tosad.businessruleservice.model;

/**
 * Created by Raoul on 1/22/2017.
 */
public class TargetDatabase {
    private int id;
    private String type;
    private String username;
    private String password;
    private String url;
    private String name;
    private int owner;

    public TargetDatabase(int id, String type, String username, String password, String url, String name, int owner) {
        this.id = id;
        this.type = type;
        this.username = username;
        this.password = password;
        this.url = url;
        this.name = name;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public int getOwner() {
        return owner;
    }
}
