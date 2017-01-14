package nl.hu.tosad.model.rules;

public interface ISQLGenerator {
    String generateSQL(AttributeRangeRule rule);
    String generateSQL(AttributeCompareRule rule);
    String generateSQL(AttributeListRule rule);
    String generateSQL(AttributeOtherRule rule);
}
