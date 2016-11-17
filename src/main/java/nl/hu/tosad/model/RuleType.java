package nl.hu.tosad.model;

/**
 * Created by Raoul on 11/17/2016.
 */
public class RuleType {
    public enum Type {
        AttributeRangeRule("ARNG"),
        AttributeCompareRule("ACMP"),
        AttributeListRule("ALIS"),
        AttributeOtherRule("AOTH");

        private String code;
        Type(String t) {
            code = t;
        }
        public String getCode() {
            return code;
        }
    }
    private RuleType.Type type;
    public RuleType(RuleType.Type t){
        type = t;
    }
    public RuleType.Type getType(){
        return type;
    }

    public static Type getTypeFromStr(String typeStr){
        for (Type t : Type.values()){
            if(t.getCode().equals(typeStr)) {
                return t;
            }
        }
        return null;
    }
}
