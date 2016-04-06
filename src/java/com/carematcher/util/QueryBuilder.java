/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carematcher.util;

/**
 *
 * @author Pooja Purohit
 */
public class QueryBuilder {
    
    public enum QueryType {
        SELECT
    }
    private QueryType type;
    
    private String queryString;
    
    private boolean where = false;
    
    public QueryBuilder(QueryType type) {
        switch(type) {
            case SELECT:
                queryString = "SELECT x FROM ";
            default:
                break;
        }
    }
    
    public void setTable(String tableName) {
        if (tableName != null && !tableName.trim().isEmpty())
            queryString = queryString + tableName.trim() + " x";
    }
    
    public void joinTable(String targetVar, String joinColumn) {
        if (joinColumn != null && !joinColumn.trim().isEmpty() &&
            targetVar != null && !targetVar.trim().isEmpty()) {
            String join = " JOIN x." + targetVar.trim() + " y WHERE y." + joinColumn.trim() + "=:" + joinColumn.trim();
            //String j = "SELECT x FROM Address x JOIN x.user y WHERE y.userId";
            queryString = queryString + join;
        }
    }
    
    public void addClause(String varName) {
        if (varName != null && !varName.trim().isEmpty())
            varName = varName.trim();
        
        if (!where) {
            String whereString = " WHERE x." + varName + "=:" + varName;
            queryString = queryString + whereString;
            where = true;
        }
        else {
            String andString = " AND x." + varName + "=:" + varName;
            queryString = queryString + andString;
        }
    }
    
    public String getQuery() {
        return queryString;
    }
}
