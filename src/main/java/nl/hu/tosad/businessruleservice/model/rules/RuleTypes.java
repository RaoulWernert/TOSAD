package nl.hu.tosad.businessruleservice.model.rules;

/**
 * Created by Raoul on 11/17/2016.
 */
public enum RuleTypes {
    AttributeRangeRule("ARNG"),
    AttributeCompareRule("ACMP"),
    AttributeListRule("ALIS"),
    AttributeOtherRule("AOTH");

    private String code;

    RuleTypes(String t) {
        code = t;
    }

    public String getCode() {
        return code;
    }
}
