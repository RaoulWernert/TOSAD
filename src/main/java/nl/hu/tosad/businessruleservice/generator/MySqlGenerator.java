package nl.hu.tosad.businessruleservice.generator;

import nl.hu.tosad.businessruleservice.model.rules.*;

/**
 * Created by Raoul on 1/21/2017.
 */
public class MySqlGenerator extends BaseGenerator implements IGenerator {
    @Override
    public String generateDDL(AttributeRangeRule rule) {
        return null;
    }

    @Override
    public String generateDDL(AttributeCompareRule rule) {
        return null;
    }

    @Override
    public String generateDDL(AttributeListRule rule) {
        return null;
    }

    @Override
    public String generateDDL(AttributeOtherRule rule) {
        return null;
    }
}
