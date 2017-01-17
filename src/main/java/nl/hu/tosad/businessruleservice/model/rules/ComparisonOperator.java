package nl.hu.tosad.businessruleservice.model.rules;

/**
 * Created by Raoul on 11/17/2016.
 */

public enum ComparisonOperator {
    Equal("="),
    NotEqual("!="),
    Greater(">"),
    Less("<"),
    GreaterOrEqual(">="),
    LessOrEqual("<=");

    private String code;

    ComparisonOperator(String t) {
        code = t;
    }

    public String getCode() {
        return code;
    }
}
