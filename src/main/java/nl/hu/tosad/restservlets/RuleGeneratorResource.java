package nl.hu.tosad.restservlets;

import nl.hu.tosad.businessruleservice.BusinessRuleService;
import nl.hu.tosad.businessruleservice.exceptions.BusinessRuleServiceException;
import nl.hu.tosad.logging.Logger;

import javax.ws.rs.*;
import java.sql.SQLException;
import java.util.List;

@Path("/ruleservice")
public class RuleGeneratorResource {
    private Logger logger = Logger.getInstance();

    @POST
    @Path("generate")
    @Produces("text/plain")
    public String generate(@FormParam("ruleid") int ruleid) {
        try {
            return "SUCCESS " + BusinessRuleService.getInstance().generate(ruleid);
        } catch (BusinessRuleServiceException e) {
            Throwable cause = e.getCause();

            if(cause != null && cause instanceof SQLException) {
                SQLException sqle = (SQLException) cause;
                if(sqle.getErrorCode() == 2293) {
                    return "Data in table does not conform to given constraint.";
                }
                Logger.getInstance().Log(cause);
                return cause.getMessage();
            }
            Logger.getInstance().Log(e);
            return e.getMessage();
        }
    }

    @POST
    @Path("update")
    @Produces("text/plain")
    public String update(@FormParam("ruleid") int ruleid) {
        String deletemsg = delete(ruleid);
        if(!deletemsg.startsWith("SUCCESS")) {
            return deletemsg;
        } else {
            return generate(ruleid);
        }
    }

    @POST
    @Path("delete")
    @Produces("text/plain")
    public String delete(@FormParam("ruleid") int ruleid) {
        try {
            BusinessRuleService.getInstance().delete(ruleid);
            return "SUCCESS DELETED RULE " + ruleid;
        } catch (BusinessRuleServiceException e) {
            return e.getMessage();
        }
    }

    @GET
    @Path("tables")
    @Produces("text/plain")
    public String getTables(@QueryParam("targetid") int targetid) {
        String response = createList(BusinessRuleService.getInstance().getTables(targetid));

        logger.Log(String.format("GetTables  | req: %d | resp: %s", targetid, response));
        return response;
    }

    @GET
    @Path("columns")
    @Produces("text/plain")
    public String getColumns(@QueryParam("targetid") int targetid, @QueryParam("tablename") String tablename) {
        if(tablename.trim().isEmpty()) {
            logger.Log("Tablename is empty.");
            throw new IllegalArgumentException();
        }

        String response = createList(BusinessRuleService.getInstance().getColumns(targetid, tablename));

        logger.Log(String.format("GetColumns | req: %d %s | resp: %s", targetid, tablename, response));
        return response;
    }

    @GET
    @Path("check")
    @Produces("text/plain")
    public String serverCheck(@QueryParam("targetid") int targetid) {
        boolean response = BusinessRuleService.getInstance().checkTarget(targetid);
        logger.Log(String.format("CheckTarget  | req: %d | resp: %s", targetid, targetid+" = "+(response?"online":"offline")));
        return response?"1":"0";
    }

    private String createList(List<String> list) {
        String response = "";
        for (int i = 0; i < list.size(); i++) {
            response += list.get(i) + (i + 1 < list.size() ? "," : "");
        }
        return response;
    }
}
