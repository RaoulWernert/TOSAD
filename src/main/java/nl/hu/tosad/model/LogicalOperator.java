package nl.hu.tosad.model;

public enum LogicalOperator {
    Any("ANY"),
    All("ALL"),
    In("IN"),
    NotIn("NOT IN");

    private String code;

    LogicalOperator(String t) {
        code = t;
    }

    public String getCode() {
        return code;
    }
}
