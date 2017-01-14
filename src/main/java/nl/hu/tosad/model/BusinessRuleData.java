package nl.hu.tosad.model;

/**
 * Created by Raoul on 11/17/2016.
 */
public class BusinessRuleData {
    public String name;
    public String table;
    public String attribute;
    public String min;
    public String max;
    public String cOperator;
    public String lOperator;
    public String value;
    public String values;
    public String statement;
    public String code;

    public BusinessRuleData(String nam, String tab, String atr, String mi, String ma, String cOper, String lOper, String val, String vals, String stat, String cod){
        name = nam;
        table = tab;
        attribute = atr;
        min = mi;
        max = ma;
        cOperator = cOper;
        lOperator = lOper;
        value = val;
        values = vals;
        statement = stat;
        code = cod;
    }
}
