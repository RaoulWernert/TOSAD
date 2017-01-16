package nl.hu.tosad.controller;

import nl.hu.tosad.model.rules.BusinessRule;

interface IController {
    boolean generate(BusinessRule br);
}
