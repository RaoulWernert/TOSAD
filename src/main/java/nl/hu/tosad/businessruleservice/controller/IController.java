package nl.hu.tosad.businessruleservice.controller;

import nl.hu.tosad.businessruleservice.model.TargetDatabase;
import nl.hu.tosad.businessruleservice.model.rules.BusinessRule;

import java.util.List;

public interface IController {
    List<String> getTables(TargetDatabase target);
    List<String> getColumns(TargetDatabase target, String tablename);
    String getPrimaryKey(TargetDatabase target, String tablename);
    String getForeignKey(TargetDatabase target, String targetTable, String referencingTable);
    void implement(String query, BusinessRule rule);
    void delete(BusinessRule rule);
}
