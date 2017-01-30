package nl.hu.tosad.businessruleservice.model;

public class RuleType {
    private String code;
    private String name;
    private String description;
    private boolean insert;
    private boolean update;
    private boolean delete;

    public RuleType(String code, String name, String description, boolean insert, boolean update, boolean delete) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.insert = insert;
        this.update = update;
        this.delete = delete;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isInsert() {
        return insert;
    }

    public boolean isUpdate() {
        return update;
    }

    public boolean isDelete() {
        return delete;
    }
}
