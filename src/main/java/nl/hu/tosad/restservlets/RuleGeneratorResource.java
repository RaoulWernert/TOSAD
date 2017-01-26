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


        try {
            String successmsg = "SUCCESS " + BusinessRuleService.getInstance().generate(ruleid);
            System.out.println(String.format("Generate | req: %d | resp: %s", ruleid, successmsg));
            return successmsg;
        } catch (BusinessRuleServiceException e) {
            Throwable cause = e.getCause();

            if(cause != null && cause instanceof SQLException) {
                SQLException sqle = (SQLException) cause;
                if(sqle.getErrorCode() == 2293) {
                    String msg = "Data in table does not conform to given constraint.";
                    System.out.println(String.format("Generate | req: %d | resp: %s", ruleid, msg));
                    return msg;
                }
            }

            System.out.println(String.format("Generate | req: %d | resp: %s", ruleid, e.getMessage()));
            return e.getMessage();
        }
    }

    @GET
    @Path("tables")
    @Produces("text/plain")
    public String getTables(@QueryParam("targetid") int targetid) {
        String response = createList(BusinessRuleService.getInstance().getTables(targetid));

        System.out.println(String.format("GetTables | req: %d | resp: %s", targetid, response));
        return response;
    }

    @GET
    @Path("columns")
    @Produces("text/plain")
    public String getColumns(@QueryParam("targetid") int targetid, @QueryParam("tablename") String tablename) {
        String response = createList(BusinessRuleService.getInstance().getColumns(targetid, tablename));

        System.out.println(String.format("GetColumns | req: %d %s | resp: %s", targetid, tablename, response));
        return response;
    }

    private String createList(List<String> list) {
        String response = "";
        for (int i = 0; i < list.size(); i++) {
            response += list.get(i) + (i + 1 < list.size() ? "," : "");
        }
        return response;
    }
}
