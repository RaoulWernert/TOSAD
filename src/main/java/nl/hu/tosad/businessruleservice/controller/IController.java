package nl.hu.tosad.businessruleservice.controller;

import nl.hu.tosad.businessruleservice.model.TargetDatabase;

import java.util.List;

public interface IController {
    List<String> getTables(TargetDatabase target);
    List<String> getColumns(TargetDatabase target, String tablename);
    void implement(String query, TargetDatabase target);
}
