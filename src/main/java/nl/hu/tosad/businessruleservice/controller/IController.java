package nl.hu.tosad.businessruleservice.controller;

import nl.hu.tosad.businessruleservice.model.rules.BusinessRule;

public interface IController {
    boolean generate(BusinessRule br);
}
