package nl.hu.tosad.businessruleservice.generator;

import java.util.List;

/**
 * Created by Raoul on 1/21/2017.
 */
public class BaseGenerator{
    protected String getValuesFromList(List<String> list){
        String values = "";
        for (String str : list) {
            values += "'" + str + "',";
        }
        return values.substring(0, values.length() - 1);
    }
}
