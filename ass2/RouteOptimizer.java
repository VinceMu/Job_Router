import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
/**
 * Class we use to perform searches on a RouteMap object. 
 * This class contains statistics pertaining to the last search
 * that was executed. 
 * @author Vince
 */

public class RouteOptimizer {
	
	private int NodesExpanded;
	private int cost; 
	/**
	 * We initialise all the private fields representing the statistics to be 0. 
	 */
	public RouteOptimizer(){
		this.NodesExpanded = 0;
		this.cost = 0;
	}
	/**
	 * Uses the a* algorithm to find the shortest path through
	 *  a RouteMap that completes a given list of jobs.
	 * @param map: the RouteMap object that we are traversing. 
	 * @param toDo: the list of jobs that must be completed. 
	 * @param source: the city which the "truck" starts from. 
	 * @param h: a heuristic class passed as the interface as the 'using' class should not be
	 * 			 aware of the strategy being implemented.
	 * @return: a Deque which is represents a list jobs both empty and not that has the lowest
	 *   		cost that completes all the jobs. 
	 * @precondition: all the parameters are initialised and h represents a class that implements the 
	 * 				  heuristic interface. map must contain at least once city. source must be a valid 
	 * 				  name of a city that is within map. jobList must have atleast one job. 
	 */
    public Deque<Job> findOptimalRoute(RouteMap map, LinkedList<Job> toDo,City source,Heuristic h){
  		LinkedList<Deque<Job>> openSet = new LinkedList<Deque<Job>>();
  		Deque<Job> initialPath = null;
  		this.cost = 0;
  		this.NodesExpanded = 0;
  		
  		for(City c: map.getAdjacentCities(source)){
  			initialPath = new LinkedList<Job>();
  			initialPath.add(new Job(source,c,false));
  		    openSet.add(initialPath);
  		}
  		
		this.NodesExpanded++;
		
  		Map<Deque<Job>,Integer>scores = new HashMap<Deque<Job>,Integer>();
  		Map<Deque<Job>,Integer>distances = new HashMap<Deque<Job>,Integer>();
  		
  		for(Deque<Job> path : openSet){
  		   distances.put(path,map.getTravelCost(path.peek().getStart(),path.peek().getEnd()));
  		   scores.put(path,distances.get(path)+h.evaluate(toDo, path, map));

  		}
  		
  		while(openSet.isEmpty() == false){
  			Deque<Job> currentPath = null;
  			int currentscores = Integer.MAX_VALUE;
  			for(Deque<Job> path:openSet){
  				if(currentscores > scores.get(path)){
  					currentscores =  scores.get(path);
  					currentPath = path;
  				}
  			}
  			
  			//checking if current contains all jobs then we have reached our goal
  			if(currentPath.size() >= toDo.size()){	
  			   LinkedList<Job> toDoCopy = new LinkedList<Job>(toDo);
  			   for(Job path:currentPath){ 
  				   this.cost += map.getTravelCost(path.getStart(),path.getEnd());
  				   for(int i= 0; i<toDoCopy.size();i++){ //this avoids concurrent modification exception
  				      Job curJob = toDoCopy.get(i);
  				      if(path.equals(curJob)){
  				    	 this.cost += curJob.getEnd().getUnloadingFee();
  				         toDoCopy.remove(curJob);
  				         path.setIsJob(true);
  			         }
  			         if(toDoCopy.isEmpty()==true){
  				         return currentPath;
  			         }
  			      }
  			   }
  			}
  			
  			openSet.remove(currentPath);
  			this.cost = 0;

  			
  			for(City adj:map.getAdjacentCities(currentPath.getLast().getEnd())){
  				Job newExpand= new Job(currentPath.getLast().getEnd(),adj,false);
  				Deque<Job> newPath = new LinkedList<Job>(currentPath);  
  				newPath.add(newExpand);

	  			int estimatedDistance = distances.get(currentPath)+ map.getTravelCost(currentPath.getLast().getEnd(), adj);
  				int estimatedScore = estimatedDistance + h.evaluate(toDo,newPath,map);
  				
  				if(openSet.contains(newPath) == false){
  					openSet.add(newPath);
  				}else if(estimatedScore >= scores.get(newPath)){
  					continue;
  				}
	  		distances.put(newPath, estimatedDistance);
  	  		scores.put(newPath,estimatedScore);
  				
  			}
  			this.NodesExpanded++;
  		}		
  		return null;
    }
    /**
     * determines whether a  path through all the jobs in a routeMap is actually possible and hence
     * whether the routeMap can be solved with the a* algorithm.
     * @param routeMap: the RouteMap that is being tested whether it is solvable. 
     * @param source : the name of the city we start from 
     * @param jobList : the list of jobs that must be done.
     * @return whether or not the RouteMap can be solved by findOptimalRoute().
     * @precondition:all the parameters are initialised.map must contain at least once city. source must be a valid 
	 * 				  name of a city that is within map. jobList must have atleast one job. 
     */
	public boolean isSolvable(RouteMap routeMap,String source,LinkedList<Job> jobList){
		this.NodesExpanded = 0;
		LinkedList<City> discovered = new LinkedList<City>(); 
		City first = routeMap.getCity(source);
		DFS(first,discovered,routeMap);
		
		//If a routeMap can be split into subgraph it still maybe solvable depending
		//on the job locations and whether all jobs are located on the subgraph of the source
		if(routeMap.checkContaining(discovered) == true){
			return true;
		}
		boolean isIn = true;
		for(Job j:jobList){	
			isIn = false;
			for(City c:discovered){
				if(j.getStart().getName().equals(c.getName())||j.getEnd().getName().equals(c.getName())){
					isIn = true;
					break;
				}
			}
			if(isIn == false){
				return isIn;
			}
		}
		return isIn;
	}
	/**
	 * performs a recursive depth first search that checks whether the graph is split into two or more subgraphs
	 * or not. 
	 * @param starting: the city to start the depth first search from.
	 * @param discovered:list of cities that are already evaluated. 
	 * @param routeMap: the RouteMap containing all the cities to be evaluated 
	 * @precondition: starting is a city in routeMap .discovered is initialised. 
	 * @postcondition: adjacent cities to starting are added into discovered. 
	 */
	private void DFS(City starting,LinkedList<City>discovered,RouteMap routeMap){
		discovered.add(starting);
		for(City adj : routeMap.getAdjacentCities(starting)){
			this.NodesExpanded++;
			if(discovered.contains(adj) == false){
				DFS(adj,discovered,routeMap);
			}
		}
	}
	/**
	 * gets number of nodes expanded by the most recently called algorithm. 
	 * @return number of nodes expanded.
	 */
    public int getNodesExpanded(){
    	return this.NodesExpanded;
    }
    /**
     * get the cost  associated with the most recently called algorithm.
     * @return cost 
     */
    public int getCost(){
    	return this.cost;
    }
}
