package nl.hu.tosad.businessruleservice.controller;

import nl.hu.tosad.businessruleservice.model.TargetDatabase;

import java.util.List;

public interface IController {
    /**
     * Gets all the tables from the given database
     * @param target The database from which the table names will be retrieved
     * @return A list of names from the tables in the given database
     */
    List<String> getTables(TargetDatabase target);

    /**
     * Gets all the columns from the given table
     * @param target The database where the given table is part of
     * @param tablename The tablename of which the column names wil be retrieved
     * @return A list of names from the columns in the given table
     */
    List<String> getColumns(TargetDatabase target, String tablename);

    /**
     * Gets the primary key column name of the given table
     * @param target The database where the given table is part of
     * @param tablename The tablename of which the primary key will be retrieved
     * @return The name of the primary key column of the given table
     */
    String getPrimaryKey(TargetDatabase target, String tablename);

    /**
     * Gets the foreign key column name that references the referencingTable from targetTable
     * @param target The database where the given tables are a part of
     * @param targetTable The name of the table that holds the foreign key column
     * @param referencingTable The name of the table that the foreign key references
     * @return The columnname of the foreign key
     */
    String getForeignKey(TargetDatabase target, String targetTable, String referencingTable);

    /**
     * Executes the given business rule query on the given database
     * @param query The business rule query that will be executed on the database
     * @param name The name of the business rule
     * @param target The database where the query must be executed on
     * @param isTrigger If the business rule is of type trigger or not
     */
    void implement(String query, String name, TargetDatabase target, boolean isTrigger);

    /**
     * Drops the business rule from the given database
     * @param name The name of the business rule
     * @param table The name of the table on which the business rule is implemented
     * @param target The database on which the business rule is implemented
     * @param isTrigger If the business rule is of type trigger or not
     */
    void delete(String name, String table, TargetDatabase target, boolean isTrigger);
}
