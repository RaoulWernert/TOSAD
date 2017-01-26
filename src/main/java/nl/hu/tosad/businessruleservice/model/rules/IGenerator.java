package nl.hu.tosad.businessruleservice.model.rules;

public interface IGenerator {
    String generateDDL(AttributeRangeRule rule);
    String generateDDL(AttributeCompareRule rule);
    String generateDDL(AttributeListRule rule);
    String generateDDL(AttributeOtherRule rule);
    String generateDDL(TupleCompareRule rule);
    String generateDDL(TupleOtherRule rule);
    String generateDDL(InterEntityCompareRule rule);
    String generateDDL(EntityOtherRule rule);
    String generateDDL(ModifyRule rule);
}
