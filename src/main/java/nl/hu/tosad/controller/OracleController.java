package nl.hu.tosad.controller;

import nl.hu.tosad.generator.OracleGenerator;
import nl.hu.tosad.model.rules.BusinessRule;
import nl.hu.tosad.model.rules.IGenerator;

class OracleController {
    private IGenerator sqlgenerator = new OracleGenerator();

    boolean generate(BusinessRule br) {
        System.out.println(br.accept(sqlgenerator));
        return true;
    }
}
