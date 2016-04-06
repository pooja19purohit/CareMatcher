/**
 *
 * @author: Pooja Purohit
 * @Source: Some blocks from StackOverflow, TutorialPoint
 */

package com.carematcher.search;

import java.io.IOException;
import java.sql.SQLException;
import java.util.EnumMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;


public class LuceneSearch {
    
    /** The path to the database index for Lucene to search
     * 
     */
    private static String index = null;
    
    /** The path to the data file for Lucene
     * 
     */
    private static String data = null;
    
    /** The url of the Database connection
     * 
     */
    private static String url = null;
    
    /** The DB user
     * 
     */
    private static String user = null;
    
    /** The password of the user for the DB
     * 
     */
    private static String pass = "poo";
    
    /** The JDBC driver class name to use in the indexing function
     * 
     */
    private static String driver = null;
    
    /** The maximum number of searches to perform
     * 
     */
    private static Integer max_search = null;
    
    /** The flag used to perform leading wildcard searches
     * 
     */
    private static boolean allowLeadingWildcards = false;
    
    /** Index initializer, only called once per application, while index = null
     * 
     * @param indexPath the path for the index file (set in web.xml context param)
     */
    public static void setIndex(String indexPath) {
        if (index == null) index = indexPath;
    }
    
    /** Data initializer, only called once per application, while data = null
     * 
     * @param dataPath the path for the data file (set in web.xml context param)
     */
    public static void setData(String dataPath) {
        if (data == null) data = dataPath;
    }
    
    /** DB URL Initializer, only called once per application, while url = null
     * 
     * @param dbUrl the url of the database connection
     */
    public static void setURL(String dbUrl) {
        if (url == null) url = dbUrl;
    }
    
    /** DB User Initializer, only called once per application, while user = null
     * 
     * @param dbUser the user for the connected database
     */
    public static void setUser(String dbUser) {
        if (user == null) user = dbUser;
    }
    
    /** DB Password Initializer, only called once per application, while pass = null
     * 
     * @param dbPass the password for the connected database
     */
    public static void setPass(String dbPass) {
        if (pass == null) pass = dbPass;
    }
    
    /** JDBC Driver class name initializer, only called once per application, while driver = null
     * 
     * @param jdbcDriver 
     */
    public static void setDriver(String jdbcDriver) {
        if (driver == null) driver = jdbcDriver;
    }
    
    /** Maximum Search count Initializer, only called once per application, while max_search = null
     * 
     * @param max 
     */
    public static void setMaxSearch(int max) {
        if (max_search == null) max_search = max;
    }
    
    /** Gets the maximum allowed searches for this Lucene setup
     * 
     * @return 
     */
    public static int getMaxSearch() {
        return max_search;
    }
    
    /** Regenerates the Lucene index of the datasource
     * 
     * @return true if the data was reindexed, false on exception
     */
    public static boolean reindex() {
        Indexer indexer = null;
        try {
            indexer = new Indexer(index, url, user, pass, driver);
            indexer.createIndex(data);
        } catch (IOException ex) {
            Logger.getLogger(LuceneSearch.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (InstantiationException ex) {
            Logger.getLogger(LuceneSearch.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IllegalAccessException ex) {
            Logger.getLogger(LuceneSearch.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LuceneSearch.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(LuceneSearch.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            if (indexer != null) {
                try {
                    indexer.close();
                } catch (IOException ex) {
                    Logger.getLogger(LuceneSearch.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        return true;
    }
    
    /** Performs a search on the indexed data based on the user-input query string
     * 
     * @param searchQuery the literal search query string
     * @param type the SearchType enum to specify the kind of search being performed
     * @return a Map, keyed on the ResultKey enums, containing search result data & metadata
     */
    public static Map<ResultKey, Object> search(String searchQuery, SearchType type) {
        Map<ResultKey, Object> results = new EnumMap<ResultKey, Object>(ResultKey.class);
        
        Searcher searcher;
        try {
            //  Check the search type, and override the fields if searching for a specific item
            searcher = new Searcher(index);
            searcher.allowLeadingWildCardSearch(allowLeadingWildcards);
        } catch (IOException ex) {
            Logger.getLogger(LuceneSearch.class.getName()).log(Level.SEVERE, null, ex);
            return results;
        }
        TopDocs hits;
        try {
            long startTime = System.currentTimeMillis();
            hits = searcher.search(searchQuery);
            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            results.put(ResultKey.TOTAL_SEARCH_TIME, totalTime);
        } catch (IOException ex) {
            Logger.getLogger(LuceneSearch.class.getName()).log(Level.SEVERE, null, ex);
            return results;
        } catch (ParseException ex) {
            Logger.getLogger(LuceneSearch.class.getName()).log(Level.SEVERE, null, ex);
            return results;
        }
        
        for(ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc;
            try {
                doc = searcher.getDocument(scoreDoc);
            } catch (IOException ex) {
                Logger.getLogger(LuceneSearch.class.getName()).log(Level.SEVERE, null, ex);
                continue;
            }
            if (type == SearchType.USER) {
                LinkedHashSet<String> docResults = new LinkedHashSet<String>();
                String email = doc.get("email");
                if (email != null) {
                    docResults.add(email);
                    results.put(ResultKey.EMAIL, docResults);
                }
                
                String serviceName = doc.get("name");
                if (serviceName != null) {
                    docResults = new LinkedHashSet<String>();
                    docResults.add(serviceName);
                    results.put(ResultKey.SERVICE, docResults);
                }
                
                String licenseName = doc.get("licenseName");
                if (licenseName != null) {
                    docResults = new LinkedHashSet<String>();
                    docResults.add(licenseName);
                    results.put(ResultKey.LICENSE, docResults);
                }
                
                String policyName = doc.get("policyName");
                if (policyName != null) {
                    docResults = new LinkedHashSet<String>();
                    docResults.add(policyName);
                    results.put(ResultKey.INSURANCE, docResults);
                }
            }
            else if (type == SearchType.SERVICE || type == SearchType.SERVICE_BY_NAME) {
                LinkedHashSet<String> docResults = new LinkedHashSet<String>();
                String name = doc.get("name");
                if (name != null) {
                    docResults.add(name);
                    results.put(ResultKey.SERVICE, docResults);
                }
            }
        }
        
        try {
            searcher.close();
        } catch (IOException ex) {
            Logger.getLogger(LuceneSearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return results;
    }
    
    public static void allowLeadingWildcardSearch(boolean allow) {
        allowLeadingWildcards = allow;
    }    
}
