package nl.hu.tosad.businessruleservice.model.rules;

public interface IGenerator {
    String generateDDL(AttributeRangeRule rule);
    String generateDDL(AttributeCompareRule rule);
    String generateDDL(AttributeListRule rule);
    String generateDDL(AttributeOtherRule rule);
}
