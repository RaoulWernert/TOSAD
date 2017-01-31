package nl.hu.tosad.businessruleservice.model;

import nl.hu.tosad.businessruleservice.exceptions.BusinessRuleServiceException;

import java.util.Objects;

public class RuleType {
    private String code;
    private String name;
    private String description;
    private boolean insert;
    private boolean update;
    private boolean delete;

    public RuleType(String code, String name, String description, boolean insert, boolean update, boolean delete) {
        try {
            this.code = Objects.requireNonNull(code, "RuleType Code cannot be null.");
            this.name = Objects.requireNonNull(name, "RuleType Name cannot be null.");
            this.description = description;
            this.insert = insert;
            this.update = update;
            this.delete = delete;
        } catch(NullPointerException e) {
            throw new BusinessRuleServiceException(e);
        }
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
