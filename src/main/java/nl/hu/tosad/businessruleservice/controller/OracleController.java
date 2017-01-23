package nl.hu.tosad.businessruleservice.controller;

import nl.hu.tosad.businessruleservice.model.TargetDatabase;
import nl.hu.tosad.businessruleservice.persistance.target.OracleTargetDAO;

import java.util.List;

public class OracleController implements IController {
    private OracleTargetDAO targetDAO;

    public OracleController() {
        targetDAO = new OracleTargetDAO();
    }

    @Override
    public List<String> getTables(TargetDatabase target) {
        return targetDAO.getTables(target);
    }

    @Override
    public List<String> getColumns(TargetDatabase target, String tablename) {
        return targetDAO.getColumns(target, tablename);
    }

    @Override
    public void implement(String query, TargetDatabase target) {
        targetDAO.implement(query, target);
    }
}
