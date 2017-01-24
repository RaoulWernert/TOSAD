package nl.hu.tosad.businessruleservice.model;

public class RuleType {
    private String code;
    private String naam;
    private String beschrijving;
    private boolean insert;
    private boolean update;
    private boolean delete;

    public RuleType(String code, String naam, String beschrijving, boolean insert, boolean update, boolean delete) {
        this.code = code;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.insert = insert;
        this.update = update;
        this.delete = delete;
    }

    public String getCode() {
        return code;
    }

    public String getNaam() {
        return naam;
    }

    public String getBeschrijving() {
        return beschrijving;
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
