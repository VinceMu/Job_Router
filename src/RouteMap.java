
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
/**
 * represents our graph object by aggregating the road and city classes.
 * We use a map to represent the graph as it allows us to easily traverse 
 * adjacent cities given a city. 
 * @author Vince
 */
public class RouteMap {
	private Map<City, LinkedList<Road>> routeMap; 
	/**
	 * initialises the map object that represents the graph. 
	 */
	public RouteMap(){
		routeMap = new HashMap<City,LinkedList<Road>>(); 
	}
	/**
	 * add a new city to the RouteMap based on name and unloading fee.
	 * @param name: name of the city 
	 * @param unloadingFee: the unloading fee associated with the city
	 * @precondition:valid input for name and unloadingFee(unloading Fee>=0)
	 * @postcondition:RouteMap contains the new City.new list of roads is created
	 * to accommodate any future roads that might lead out from the city. 
	 */
	public void addCity(String name,int unloadingFee){
		routeMap.put(new City(name,unloadingFee),new LinkedList<Road>());
	}
	/**
	 * returns a list of adjacent cities to the one given.
	 * @param c:the city whose adjacent cities are required. 
	 * @return: list of cities adjacent to c.
	 * @precondition: c is a city in the RouteMap. routeMap is initialised. 
	 * @postcondition: RouteMap unmodified, list of adjacent is returned to caller function. 
	 */
	public LinkedList<City>getAdjacentCities(City c){
		LinkedList<City> adjacents = new LinkedList<City>();
		for(Road adj: routeMap.get(c)){
			adjacents.add(adj.getOther(c));
		}
		return adjacents;
	}
	/**
	 * calculate the travel cost when traveling between two directly connected cities.
	 * @param Start:the city we start from.
	 * @param end: the city we travel to. 
	 * @return the travel cost of the road that connects the start and end cities. 
	 * @precondition: start and end city are directly connected cities in RouteMap.
	 */
	public int getTravelCost(City Start,City end){
		for(Road r:routeMap.get(Start)){
			if(r.getOther(Start) == end){
				return r.getTravelCost();
			}
		}
		return 0; 
	}
	/**
	 * 
	 * @param c1:city that the road starts from
	 * @param c2: city that the road goes to.
	 * @param travelCost:weight of the road we are adding. 
	 * @precondition: cities by the name of c1 and c2 are unique and in
	 * 				  RouteMap. travelCost is valid input. 
	 * @postcondition:2 new roads added in RouteMap for the each of the two cities that define
	 * 				  the road. 
	 */
	public void addRoad(String c1, String c2, int travelCost){		
		City start= this.getCity(c1);
		City end=this.getCity(c2);
		if(routeMap.containsKey(start)&&routeMap.containsKey(end)){
			routeMap.get(start).add(new Road(start,end,travelCost));
			routeMap.get(end).add(new Road(end,start,travelCost));
		}
		
	}
	/**
	 * We return a City within stored within the routeMap hashMap, based on the name 
	 * of the city. .
	 * @param name: name of the city that is required. 
	 * @return the city that is required.
	 * @precondition: name is equal to the name of a unique city stored in RouteMap. 
	 * @postcondition: RouteMap is unchanged,City stored within the routeMap hashmap is 
	 * 				   returned. 
	 */
	public City getCity(String name){
		for(City c :routeMap.keySet()){
		    if(c.getName().equals(name)){
		    	return c;
		    }
		}
		return null;
	}
	/**
	 * Check if a list of "supposedly" all the cities actually contains all the cities.
	 * @param allCities: the list that is is being tested for whether it contains all the 
	 * 		  			 the cities.
	 * @return true/false whether that list contains all the cities.
	 * @precondition: allCities not null,routeMap contains at least one city. 
	 * @postcondition: RouteMap is unchanged. allCities is unchanged.  
	 */
	public boolean checkContaining(LinkedList<City> allCities){
		for(City c:routeMap.keySet()){
			if(allCities.contains(c)==false){
				return false;
			}
		}
		return true;
	}

}
