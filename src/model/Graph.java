package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Graph {
    private HashMap<String, List<String>> vertexMap = new HashMap<String, List<String>>();
    List<Tweet> actualTwits = new ArrayList<Tweet>();
    
    
    /**
     * add new tweet to the graph 
     * @throws IOException 
     **/
    public void add(Tweet newTw) throws IOException {
		//validate new tweet and delete old edges
		validateTwits(newTw);
		
		//Get combination of edges and add new edges.
		//For avoid tags entity removing, we just added clone of tagList
		ArrayList<String> tagsToClone = (ArrayList<String>) newTw.getTagList();
		
		@SuppressWarnings("unchecked")
		ArrayList<String> tags = (ArrayList<String>) tagsToClone.clone();
		
		Iterator<String> it = tags.iterator();
		while(it.hasNext()) {
			int j = 0;
			String firstTag = it.next();
			j++;
			
			for (int i = j; i < tags.size(); i++) {
				String secTag = tags.get(i);
				addEdge(firstTag, secTag);
			}
				
			it.remove();
		}
		//add to list with actual tweets
		actualTwits.add(newTw);
		
		FileManager.saveSecondFeatureOutput(countAvgDegree());
	}
    
    /**
     * delete from the graph 
     **/
    public void deleteTweet(Tweet tw) throws FileNotFoundException, UnsupportedEncodingException {
		//Get combination of edges and delete them
		List<String> tags = tw.getTagList();
		Iterator<String> it = tags.iterator();
		while(it.hasNext()) {
			int j = 0;
			String firstTag = it.next();
			j++;
			
			for (int i = j; i < tags.size(); i++) {
				String secTag = tags.get(i);
				deleteEdge(firstTag, secTag);
			}
				
			it.remove();
		}
	}


	public double countAvgDegree() {
    	if(vertexMap.size() == 0) {
    		return 0.00;
    	}
    		
    	
    	Set<String> keys = vertexMap.keySet();
    	int degree = 0;
    	for(String key : keys) {
    		List<String> edgeList = vertexMap.get(key);
    		degree += edgeList.size();
    	}
    	
    	double dividedDegree = degree/ vertexMap.size();
    	return dividedDegree;
    }
    
    private void addVertex(String vertexName) {
        if (!hasVertex(vertexName)) {
            vertexMap.put(vertexName, new ArrayList<String>());
        }
    }
 
    private boolean hasVertex(String vertexName) {
        return vertexMap.containsKey(vertexName);
    }
 
    private boolean hasEdge(String vertexName1, String vertexName2) {
        if (!hasVertex(vertexName1)) return false;
        List<String> edges = vertexMap.get(vertexName1);
        return Collections.binarySearch(edges, vertexName2) >= 0;
    }
 
    private void addEdge(String vertexName1, String vertexName2) {
    	//Check out is this edge already exist
    	if(hasEdge(vertexName1, vertexName2))
    		return; 
    	
        if (!hasVertex(vertexName1)) 
        	addVertex(vertexName1);
        
        if (!hasVertex(vertexName2)) 
        	addVertex(vertexName2);
        
        List<String> edges1 = vertexMap.get(vertexName1);
        List<String> edges2 = vertexMap.get(vertexName2);
        
        edges1.add(vertexName2);
        edges2.add(vertexName1);
        Collections.sort(edges1);
        Collections.sort(edges2);
    }
    
    
    private void deleteEdge(String vertexName1, String vertexName2) {
    	if(hasEdge(vertexName1, vertexName2)) {
    		List<String> edges1 = vertexMap.get(vertexName1);
            List<String> edges2 = vertexMap.get(vertexName2);
            
            edges1.remove(Collections.binarySearch(edges1, vertexName2));
            edges2.remove(Collections.binarySearch(edges2, vertexName1));
            
            //delete vertex if there is no connections with vertex
            if(edges1.size() == 0)
            	vertexMap.remove(vertexName1);
            if(edges2.size() == 0)
            	vertexMap.remove(vertexName2);
            
            Collections.sort(edges1);
            Collections.sort(edges2);
    	}
    }
    
    /**
     * Check the time difference between new comming tweet and old tweets
     * if the difference greater than 60 sec than delete the tweet
     **/
	private void validateTwits(Tweet newTw) throws FileNotFoundException, UnsupportedEncodingException {
		if(actualTwits.size() == 0)
			return; 
		Iterator<Tweet> it = actualTwits.iterator();
		while(it.hasNext()) {
			Tweet twit = it.next();
			
			long mlSecDiff = newTw.getDate().getTime() - twit.getDate().getTime();
			long secDiff = mlSecDiff/1000;
			
			if(secDiff >= 60) {
				//delete old edges
				deleteTweet(twit);
				
				//remove from actual tweet list
				it.remove();
			}
		}
		
	}
	
}
