package nl.hu.tosad.businessruleservice.model;

import nl.hu.tosad.businessruleservice.exceptions.BusinessRuleServiceException;

import java.util.Objects;

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
        try {
            this.id = Objects.requireNonNull(id, "TargetDatabase ID cannot be null.");
            this.type = Objects.requireNonNull(type, "TargetDatabase Type cannot be null.");
            this.username = Objects.requireNonNull(username, "TargetDatabase Username cannot be null.");
            this.password = Objects.requireNonNull(password, "TargetDatabase Password cannot be null.");
            this.url = Objects.requireNonNull(url, "TargetDatabase URL cannot be null.");
            this.name = Objects.requireNonNull(name, "TargetDatabase Name cannot be null.");
            this.owner = owner;
        } catch(NullPointerException e) {
            throw new BusinessRuleServiceException(e);
        }
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
