package nl.hu.tosad.restservlets;

import nl.hu.tosad.businessruleservice.BusinessRuleService;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/ruleservice")
public class RuleGeneratorResource {

    @POST
    @Path("generate")
    @Produces("text/plain")
    public String generate(@FormParam("ruleid") int ruleid) {
        return String.valueOf(BusinessRuleService.getInstance().generate(ruleid));
    }
}
