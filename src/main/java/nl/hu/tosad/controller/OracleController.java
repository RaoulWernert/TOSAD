package nl.hu.tosad.controller;

import nl.hu.tosad.generator.OracleGenerator;
import nl.hu.tosad.model.rules.BusinessRule;
import nl.hu.tosad.model.rules.IGenerator;

class OracleController implements IController {
    private IGenerator sqlgenerator = new OracleGenerator();

    @Override
    public boolean generate(BusinessRule br) {
        System.out.println(br.accept(sqlgenerator));
        return true;
    }
}
