package nl.hu.tosad.businessruleservice.model.rules;

import nl.hu.tosad.businessruleservice.exceptions.BusinessRuleServiceException;
import nl.hu.tosad.businessruleservice.model.BusinessRuleData;

import java.util.Objects;

/**
 * Created by Raoul on 1/26/2017.
 */
public abstract class TupleRule extends BusinessRule {
    private String Column1;
    private String Column2;

    public TupleRule(BusinessRuleData data) {
        super(data);
        try {
            Column1 = Objects.requireNonNull(data.getTargetcolumn(), "TupleRule Column1 cannot be null.");
            Column2 = Objects.requireNonNull(data.getTargetcolumn2(), "TupleRule Column2 cannot be null.");
        } catch(NullPointerException e) {
            throw new BusinessRuleServiceException(e);
        }
    }
    public String getColumn1() {
        return Column1;
    }

    public String getColumn2() {
        return Column2;
    }
}
