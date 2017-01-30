package nl.hu.tosad.businessruleservice.generator;

import nl.hu.tosad.businessruleservice.BusinessRuleService;
import nl.hu.tosad.businessruleservice.exceptions.BusinessRuleServiceException;
import nl.hu.tosad.businessruleservice.generator.constraintbuilder.ConstraintBuilder;
import nl.hu.tosad.businessruleservice.generator.triggerbuilder.TriggerBuilder;
import nl.hu.tosad.businessruleservice.model.rules.*;

import java.util.List;

/**
 * Created by Raoul on 1/21/2017.
 */
public class BaseGenerator implements IGenerator {
    @Override
    public String generateDDL(AttributeRangeRule rule) {
        switch(rule.getImplementation()) {
            case CONSTRAINT:
                return ConstraintBuilder.newConstraint(rule.getName())
                        .onTable(rule.getTable())
                        .onColumn(rule.getAttribute())
                        .addBetween(rule.getMin(), rule.getMax())
                        .build();
            case TRIGGER:
                return new TriggerBuilder(rule.getName())
                        .setTable(rule.getTable())
                        .setEvents(rule.getRuleType())
                        .setColumns(rule.getAttribute())
                        .addBetween(rule.getAttribute(), rule.getMin(), rule.getMax())
                        .setError(rule.getErrormsg())
                        .build();
            default:
                return null;
        }
    }

    @Override
    public String generateDDL(AttributeCompareRule rule) {
        switch(rule.getImplementation()) {
            case CONSTRAINT:
                return ConstraintBuilder.newConstraint(rule.getName())
                        .onTable(rule.getTable())
                        .onColumn(rule.getAttribute())
                        .addComparisonOperator(rule.getComparisonOperator())
                        .addValue(rule.getValue())
                        .build();
            case TRIGGER:
                return new TriggerBuilder(rule.getName())
                        .setTable(rule.getTable())
                        .setEvents(rule.getRuleType())
                        .setColumns(rule.getAttribute())
                        .addComparison(rule.getAttribute(), rule.getComparisonOperator(), rule.getValue())
                        .setError(rule.getErrormsg())
                        .build();
            default:
                return null;
        }
    }

    @Override
    public String generateDDL(AttributeListRule rule) {
        switch(rule.getImplementation()) {
            case CONSTRAINT:
                return ConstraintBuilder.newConstraint(rule.getName())
                        .onTable(rule.getTable())
                        .onColumn(rule.getAttribute())
                        .addOperators(rule.getLogicalOperator(), rule.getComparisonOperator())
                        .addValues(rule.getValues())
                        .build();
            case TRIGGER:
                return new TriggerBuilder(rule.getName())
                        .setTable(rule.getTable())
                        .setEvents(rule.getRuleType())
                        .setColumns(rule.getAttribute())
                        .addComparisons(rule.getAttribute(), rule.getLogicalOperator(), rule.getComparisonOperator(), getValuesFromList(rule.getValues()))
                        .setError(rule.getErrormsg())
                        .build();
            default:
                return null;
        }
    }

    @Override
    public String generateDDL(AttributeOtherRule rule) {
        switch(rule.getImplementation()) {
            case CONSTRAINT:
                return ConstraintBuilder.newConstraint(rule.getName())
                        .onTable(rule.getTable())
                        .onColumn(rule.getAttribute())
                        .addStatement(rule.getStatement())
                        .build();
            case TRIGGER:
                return new TriggerBuilder(rule.getName())
                        .setTable(rule.getTable())
                        .setEvents(rule.getRuleType())
                        .setColumns(rule.getAttribute())
                        .addStatement(rule.getStatement())
                        .setError(rule.getErrormsg())
                        .build();
            default:
                return null;
        }
    }

    @Override
    public String generateDDL(TupleCompareRule rule) {
        switch(rule.getImplementation()) {
            case CONSTRAINT:
                return ConstraintBuilder.newConstraint(rule.getName())
                        .onTable(rule.getTable())
                        .onColumns(rule.getColumn1(), rule.getColumn2())
                        .addOperator(rule.getOperator())
                        .build();
            case TRIGGER:
                return new TriggerBuilder(rule.getName())
                        .setTable(rule.getTable())
                        .setEvents(rule.getRuleType())
                        .setColumns(rule.getColumn1())
                        .addComparisonColumns(rule.getColumn1(), rule.getOperator(), rule.getColumn2())
                        .setError(rule.getErrormsg())
                        .build();
            default:
                return null;
        }
    }

    @Override
    public String generateDDL(TupleOtherRule rule) {
        switch(rule.getImplementation()) {
            case CONSTRAINT:
                return ConstraintBuilder.newConstraint(rule.getName())
                        .onTable(rule.getTable())
                        .addStatement(rule.getStatement())
                        .build();
            case TRIGGER:
                return new TriggerBuilder(rule.getName())
                        .setTable(rule.getTable())
                        .setEvents(rule.getRuleType())
                        .addStatement(rule.getStatement())
                        .setError(rule.getErrormsg())
                        .build();
            default:
                return null;
        }
    }

    @Override
    public String generateDDL(InterEntityCompareRule rule) {
        String pk = BusinessRuleService.getInstance().getController(rule.getTarget().getType()).getPrimaryKey(rule.getTarget(), rule.getTable());
        String fk = BusinessRuleService.getInstance().getController(rule.getTarget().getType()).getForeignKey(rule.getTarget(), rule.getTable2(), rule.getTable());

        if(pk == null) {
            throw new BusinessRuleServiceException("Primary key not found in table: " + rule.getTable());
        }

        if(fk == null) {
            throw new BusinessRuleServiceException(String.format("Table %s does not have a foreign key associated with table %s", rule.getTable2(), rule.getTable()));
        }

        String tr1 = new TriggerBuilder(rule.getName() + "_1")
                .setTable(rule.getTable())
                .setEvents(rule.getRuleType())
                .setColumns(rule.getColumn())
                .addTableComp(rule.getTable2(), fk, pk, rule.getColumn2(), rule.getColumn(), rule.getOperator(), true)
                .setError(rule.getErrormsg())
                .build();
        String tr2 = new TriggerBuilder(rule.getName() + "_2")
                .setTable(rule.getTable2())
                .setEvents(rule.getRuleType())
                .setColumns(rule.getColumn2())
                .addTableComp(rule.getTable(), pk, fk, rule.getColumn(), rule.getColumn2(), rule.getOperator(), false)
                .setError(rule.getErrormsg())
                .build();
        return tr1+"#NEWTRG#\n"+tr2;
    }

    @Override
    public String generateDDL(EntityOtherRule rule) {
        List<String> columns = BusinessRuleService.getInstance().getController(rule.getTarget().getType()).getColumns(rule.getTarget(), rule.getTable());
        return new TriggerBuilder(rule.getName())
                .setTable(rule.getTable())
                .setEvents(rule.getRuleType())
                .setColumns(rule.getColumn(), rule.getColumn2())
                .setGlobalVariables(rule.getGvariables())
                .setBeforeStatement(rule.getBeforestatement())
                .setAfterStatement(rule.getStatement())
                .setAllTableColumns(columns)
                .setError(rule.getErrormsg())
                .build();
    }

    @Override
    public String generateDDL(ModifyRule rule) {
        return "WIP";
    }

    private String getValuesFromList(List<String> list) {
        String values = "";
        for (String str : list) {
            if (str.length() > 0) {
                values += "'" + str + "',";
            }
        }
        return values.substring(0, values.length() - 1);
    }
}
