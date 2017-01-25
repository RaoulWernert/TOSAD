package nl.hu.tosad.businessruleservice.model;

/**
 * Created by Raoul on 11/17/2016.
 */
public class BusinessRuleData {
    private int id;
    private String ruleName;
    private TargetDatabase target;
    private RuleType ruleType;
    private int creator;
    private String implementation;
    private boolean implemented;
    private String targettable;
    private String targettable2;
    private String targetcolumn;
    private String targetcolumn2;
    private String min;
    private String max;
    private String c_operator;
    private String value;
    private String l_operator;
    private String statement;

    public BusinessRuleData(int id,
                            String ruleName,
                            TargetDatabase target,
                            RuleType ruleType,
                            int creator,
                            String implementation,
                            boolean implemented,
                            String targettable,
                            String targettable2,
                            String targetcolumn,
                            String targetcolumn2,
                            String min,
                            String max,
                            String c_operator,
                            String value,
                            String l_operator,
                            String statement) {
        this.id = id;
        this.ruleName = ruleName;
        this.target = target;
        this.ruleType = ruleType;
        this.creator = creator;
        this.implementation = implementation;
        this.implemented = implemented;
        this.targettable = targettable;
        this.targettable2 = targettable2;
        this.targetcolumn = targetcolumn;
        this.targetcolumn2 = targetcolumn2;
        this.min = min;
        this.max = max;
        this.c_operator = c_operator;
        this.value = value;
        this.l_operator = l_operator;
        this.statement = statement;
    }

    public int getId() {
        return id;
    }

    public String getRuleName() {
        return ruleName;
    }

    public TargetDatabase getTarget() {
        return target;
    }

    public RuleType getRuleType() {
        return ruleType;
    }

    public int getCreator() {
        return creator;
    }

    public String getImplementation() {
        return implementation;
    }

    public boolean isImplemented() {
        return implemented;
    }

    public String getTargettable() {
        return targettable;
    }

    public String getTargettable2() {
        return targettable2;
    }

    public String getTargetcolumn() {
        return targetcolumn;
    }

    public String getTargetcolumn2() {
        return targetcolumn2;
    }

    public String getMin() {
        return min;
    }

    public String getMax() {
        return max;
    }

    public String getC_operator() {
        return c_operator;
    }

    public String getValue() {
        return value;
    }

    public String getL_operator() {
        return l_operator;
    }

    public String getStatement() {
        return statement;
    }
}
