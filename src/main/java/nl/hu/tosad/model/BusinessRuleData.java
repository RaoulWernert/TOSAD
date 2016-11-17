package nl.hu.tosad.model;

/**
 * Created by Raoul on 11/17/2016.
 */
public class BusinessRuleData {
    public String table;
    public String attribute;
    public String min;
    public String max;
    public String operator;
    public String value;
    public String values;
    public String statement;
    public String code;
    public BusinessRuleData(String tab, String atr, String mi, String ma, String op, String val, String vals, String stat, String cod){
        table = tab;
        attribute = atr;
        min = mi;
        max = ma;
        operator = op;
        value = val;
        values = vals;
        statement = stat;
        code = cod;
    }
}
