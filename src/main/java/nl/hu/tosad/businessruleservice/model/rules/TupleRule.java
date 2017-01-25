package nl.hu.tosad.businessruleservice.model.rules;

import nl.hu.tosad.businessruleservice.model.BusinessRuleData;

/**
 * Created by Raoul on 1/26/2017.
 */
public abstract class TupleRule extends BusinessRule {
    public String Column1;
    public String Column2;

    public TupleRule(BusinessRuleData data) {
        super(data);
        Column1 = data.getTargetcolumn();
        Column2 = data.getTargetcolumn2();
    }
    public String getColumn1() {
        return Column1;
    }

    public String getColumn2() {
        return Column2;
    }
}
