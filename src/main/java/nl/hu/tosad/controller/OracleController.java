package nl.hu.tosad.controller;

import nl.hu.tosad.generator.OracleSQLGenerator;
import nl.hu.tosad.model.rules.BusinessRule;
import nl.hu.tosad.model.rules.ISQLGenerator;

class OracleController {
    private ISQLGenerator sqlgenerator = new OracleSQLGenerator();

    boolean generate(BusinessRule br) {
        System.out.println(br.accept(sqlgenerator));
        return true;
    }
}
