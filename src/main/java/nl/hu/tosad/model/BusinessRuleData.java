package nl.hu.tosad.model;

/**
 * Created by Raoul on 11/17/2016.
 */
public class BusinessRuleData {
    public int id;
    public String name;
    public String table;
    public String attribute;
    public String min;
    public String max;
    public String cOperator;
    public String lOperator;
    public String value;
    public String code;
    public String ruletype_code;
    public String targetdb;

    public BusinessRuleData(int id,
                            String ruleName, String table, String attribute,
                            String min, String max,
                            String cOperator,
                            String lOperator,
                            String value,
                            String sqlCode,
                            String ruletype_code,
                            String target) {

        this.id = id;
        this.name = ruleName;
        this.table = table;
        this.attribute = attribute;
        this.min = min;
        this.max = max;
        this.cOperator = cOperator;
        this.lOperator = lOperator;
        this.value = value;
        this.code = sqlCode;
        this.ruletype_code = ruletype_code;
        this.targetdb = target;
    }
}
