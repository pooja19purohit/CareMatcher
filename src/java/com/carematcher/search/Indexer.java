package com.carematcher.search;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.sql.* ;
import java.util.EnumMap;
import java.util.Map;

public class Indexer {

    private final IndexWriter writer;
    private final String url;
    private final String user;
    private final String pass;
    private final String driver;
    
    private enum IndexType {
        USER,
        LICENSE,
        SERVICE,
        INSURANCE
    }
    
    @SuppressWarnings("deprecation")
    public Indexer(String indexDirectoryPath, String url, String user, String pass, String driver) throws IOException{
        //this directory will contain the indexes
        Directory indexDirectory = 
        FSDirectory.open(new File(indexDirectoryPath));
        this.url = url;
        this.user = user;
        this.pass = pass;
        this.driver = driver;
        System.out.println("indexer driver: " + this.driver);
        //create the indexer
        writer = new IndexWriter(indexDirectory, 
        new StandardAnalyzer(Version.LUCENE_36),true,
        IndexWriter.MaxFieldLength.UNLIMITED);
    }

    public void close() throws CorruptIndexException, IOException{
        writer.close();
    }
    
    private Document getUserDocument(ResultSet rs) throws IOException, SQLException {
        Document document = new Document();
        Field email = new Field("email", rs.getString("EMAIL"), Field.Store.YES, Field.Index.ANALYZED);
        document.add(email);
        Field firstName = new Field("firstName", rs.getString("FIRSTNAME"), Field.Store.YES, Field.Index.ANALYZED);
        document.add(firstName);
        Field lastName = new Field("lastName", rs.getString("LASTNAME"), Field.Store.YES, Field.Index.ANALYZED);
        document.add(lastName);
        Field midInit = new Field("midInit", rs.getString("MIDINIT"), Field.Store.YES, Field.Index.ANALYZED);
        document.add(midInit);
        
        return document;
    }
    
    private Document getServiceDocument(ResultSet rs) throws IOException, SQLException {
        Document document = new Document();
        Field servDesc = new Field("description", rs.getString("DESCRIPTION"), Field.Store.YES, Field.Index.ANALYZED);
        document.add(servDesc);
        Field servName = new Field("name", rs.getString("NAME"), Field.Store.YES, Field.Index.ANALYZED);
        document.add(servName);
        
        return document;
    }
    
    private Document getLicenseDocument(ResultSet rs) throws IOException, SQLException {
        Document document = new Document();
        Field licDesc = new Field("licenseDescription", rs.getString("LICENSEDESCRIPTION"), Field.Store.YES, Field.Index.ANALYZED);
        document.add(licDesc);
        Field licName = new Field("licenseName", rs.getString("LICENSENAME"), Field.Store.YES, Field.Index.ANALYZED);
        document.add(licName);
        
        return document;
    }
    
    private Document getInsuranceDocument(ResultSet rs) throws IOException, SQLException {
        Document document = new Document();
        
        Field iCompName = new Field("companyName", rs.getString("COMPANYNAME"), Field.Store.YES, Field.Index.ANALYZED);
        document.add(iCompName);
        Field iPolcName = new Field("policyName", rs.getString("POLICYNAME"), Field.Store.YES, Field.Index.ANALYZED);
        document.add(iPolcName);
        
        return document;
    } 


    private void indexDatabase(IndexType type, ResultSet rs) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, CorruptIndexException, IOException {
        Document document = null;
        switch(type) {
            case USER:
                document = getUserDocument(rs);
                break;
            case SERVICE:
                document = getServiceDocument(rs);
                break;
            case LICENSE:
                document = getLicenseDocument(rs);
                break;
            case INSURANCE:
                document = getInsuranceDocument(rs);
                break;
            default:
                break;
        }
        
        if (document != null) writer.addDocument(document);
    }
    
    public int createIndex(String dataDirPath) 
        throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
        
        Map<IndexType, ResultSet> resultMap = new EnumMap<IndexType, ResultSet>(IndexType.class);
        Map<IndexType, String> queryMap = new EnumMap<IndexType, String>(IndexType.class);
        queryMap.put(IndexType.USER, "SELECT * FROM USER");
        queryMap.put(IndexType.SERVICE, "SELECT * FROM SERVICE");
        queryMap.put(IndexType.LICENSE, "SELECT * FROM LICENSE");
        queryMap.put(IndexType.INSURANCE, "SELECT * FROM INSURANCE");
        
        // connect to the database and build the queries
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, user, pass);
        
        //  Get the result sets from the various queries
        for (IndexType type : queryMap.keySet()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(queryMap.get(type));
            resultMap.put(type, rs);
        }
        
        //  Loop over each result set & index the data
        for (IndexType type : resultMap.keySet()) {
            ResultSet rs = resultMap.get(type);
            while (rs.next()) {
                indexDatabase(type, rs);
            }
        }
        
	conn.close();
        return writer.numDocs();
    }
}