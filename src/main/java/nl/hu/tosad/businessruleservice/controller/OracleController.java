package nl.hu.tosad.businessruleservice.controller;

import nl.hu.tosad.businessruleservice.generator.OracleGenerator;
import nl.hu.tosad.businessruleservice.model.rules.BusinessRule;
import nl.hu.tosad.businessruleservice.model.rules.IGenerator;

public class OracleController implements IController {
    private IGenerator oracleGenerator;

    public OracleController() {
        oracleGenerator = new OracleGenerator();
    }

    @Override
    public boolean generate(BusinessRule br) {
        System.out.println(br.accept(oracleGenerator));
        return true;
    }
}
