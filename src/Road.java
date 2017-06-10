/**
 * This class represents an edge in our RouteMap graph object.
 * store a start and end city that define the edge.
 * also as the edges are weighted we start 
 * @author Vince
 *
 */
public class Road{
    private City start;
    private City end; 
    private int travelCost;
    
    /**
     * initialises the road object so that it has no null fields. Road is an 
     * undirected edge.
     * @param start: the city the road start at
     * @param end : the city the road ends at. 
     * @param travelCost: the travel cost associated with travelling along this road.
     */
    public Road(City start,City end,int travelCost){
    	this.start =start;
    	this.end = end;
    	this.travelCost = travelCost; 
    }
    /**
     * given a city in this road fetch the other road.
     * @param from: the city given that is already inthe road.
     * @return the other city in the road.
     * @precondition: parameter from is a city in the road. Road is intialised.
     * @postcondition: other City  in the road is returned.
     */
    public City getOther(City from){
    	if(from.getName().equals(start.getName())){
    		return this.end;
    	}else if(from.getName().equals(end.getName())){
    		return this.start;
    	}
    	return null;
    }
    /**
     * get travel cost associated with the road aka its weight.
     * @return the travel cost of the road. 
     * @precondition:road has been initialised
     * @postcondition: travel cost of this road has been returned. 
     */
    public int getTravelCost(){
    	return this.travelCost;
    }
    	
    
}
