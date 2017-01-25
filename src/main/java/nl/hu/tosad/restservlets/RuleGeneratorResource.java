package nl.hu.tosad.restservlets;

import nl.hu.tosad.businessruleservice.BusinessRuleService;
import nl.hu.tosad.businessruleservice.exceptions.BusinessRuleServiceException;

import javax.ws.rs.*;
import java.sql.SQLException;
import java.util.List;

@Path("/ruleservice")
public class RuleGeneratorResource {

    @POST
    @Path("generate")
    @Produces("text/plain")
    public String generate(@FormParam("ruleid") int ruleid) {
        System.out.println("generate: " + ruleid);

        try {
            String successmsg = "SUCCESS " + BusinessRuleService.getInstance().generate(ruleid);
            System.out.println(successmsg);
            return successmsg;
        } catch (BusinessRuleServiceException e) {
            Throwable cause = e.getCause();

            if(cause != null && cause instanceof SQLException) {
                SQLException sqle = (SQLException) cause;
                if(sqle.getErrorCode() == 2293) {
                    String msg = "Data in table does not conform to given constraint.";
                    System.out.println(msg);
                    return msg;
                }
            }

            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }

    @GET
    @Path("tables")
    @Produces("text/plain")
    public String getTables(@QueryParam("targetid") int targetid) {
        System.out.println("Get tables: " + targetid);
        return createList(BusinessRuleService.getInstance().getTables(targetid));
    }

    @GET
    @Path("columns")
    @Produces("text/plain")
    public String getColumns(@QueryParam("targetid") int targetid, @QueryParam("tablename") String tablename) {
        System.out.println("Get columns: " + tablename);
        return createList(BusinessRuleService.getInstance().getColumns(targetid, tablename));
    }

    private String createList(List<String> list) {
        String response = "";
        for (int i = 0; i < list.size(); i++) {
            response += list.get(i) + (i + 1 < list.size() ? "," : "");
        }
        return response;
    }
}
