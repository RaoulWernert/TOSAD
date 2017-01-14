package nl.hu.tosad.rules;

/**
 * Created by Raoul on 11/17/2016.
 */
public abstract class BusinessRule {
    private String name;
    private String table;
    private String category;

    BusinessRule(String name, String table, String category) {
        this.name = name;
        this.table = table;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getTable(){
        return table;
    }

    public String getCategory() {
        return category;
    }
}
