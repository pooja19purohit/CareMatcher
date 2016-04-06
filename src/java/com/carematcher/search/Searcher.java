/**
 *
 * @author Pooja Purohit
 */

package com.carematcher.search;

import java.io.File;
import java.io.IOException;;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Searcher {
	
    private final IndexSearcher indexSearcher;
    private final MultiFieldQueryParser queryParser;
    private Query query;
    private final String[] fields = {"firstName", "lastName", "midInit", "email", 
        "description", "name", "licenseDescription", "licenseName", "companyName", "policyName" };
    
    
    public Searcher(String indexDirectoryPath) 
        throws IOException{
	   
	   /*
	    * MultiFieldQueryParser queryParser = new MultiFieldQueryParser(
                                        new string[] {"bodytext", "title"},
                                        analyzer);
	    */
        
        Directory indexDirectory = 
        FSDirectory.open(new File(indexDirectoryPath));
        indexSearcher = new IndexSearcher(indexDirectory);
        queryParser = new MultiFieldQueryParser(Version.LUCENE_36,
                fields,
                new StandardAnalyzer(Version.LUCENE_36));
    }
   
    public TopDocs search( String searchQuery) 
        throws IOException, ParseException{
        query = queryParser.parse(searchQuery);
        return indexSearcher.search(query, LuceneSearch.getMaxSearch());
    }

    public Document getDocument(ScoreDoc scoreDoc) 
        throws CorruptIndexException, IOException{
        return indexSearcher.doc(scoreDoc.doc);	
    }

    public void close() throws IOException{
        indexSearcher.close();
    }
    
    public void allowLeadingWildCardSearch(boolean allow) {
        queryParser.setAllowLeadingWildcard(allow);
    }
}