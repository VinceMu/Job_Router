import java.util.Deque;
import java.util.LinkedList;

/**
 * This class is the environment which all the RouteMap class and
 * RouteOptimizer class interact with each based on a jobList.
 * Input from the user is fed to this class before being delegated to 
 * the RouteMap and RouteOptimizer and job list objects. 
 * @author Vince
 */
public class RoutingEngine {
	
	private RouteOptimizer solver; 
	private RouteMap map;
	private LinkedList<Job> jobList;
	
	/**
	 * Initialising all the private fields in RoutingEngine. 
	 */
	public RoutingEngine(){
		this.map = new RouteMap();
		this.solver = new RouteOptimizer();
		this.jobList = new LinkedList<Job>();
	}
	/**
	 * execute an unloading command by creating a new city with the input given.
	 * @param cost: unloading cost of new city.
	 * @param town: name of the new city. 
	 * @precondition: town is a unique name, cost is valid(cost>=0)
	 * @postcondition: map contains a new city with unloading value of cost and name 
	 * 				   of town. This new city has no roads connected to it. 
	 */
	public void executeUnloading(int cost,String town){
	   map.addCity(town, cost);	
	}
	/**
	 * execute a cost command by creating a weighted road between 2 cities with input given. 
	 * @param cost : the travel cost associated with the road between town1 and town2.
	 * @param town1 : name of the city the road leads from.
	 * @param town2 : name of the city the road leads to. 
	 * @precondition: town1 and town2 are the name of two unique cities that exist in map.
	 * 				  cost is valid(cost >=0)
	 * @postcondition:RouteMap contains 2 roads that as a whole represent the single road between
	 * 				  town1 and town2. 
	 */
	public void executeCost(int cost, String town1,String town2){
		map.addRoad(town1, town2,cost);
	}
	/**
	 * execute a job command by adding a new job to the job list. 
	 * @param town1: the name of the city the job starts from.
	 * @param town2: the name of the city the job ends at.
	 * @precondition: town1 and town2 are two cities in map that are directly connected.
	 * @postcondition: a new job has been added to the jobList. 
	 */
	public void executeJob(String town1,String town2){
		jobList.add(new Job(map.getCity(town1),map.getCity(town2),true));
	}
	/**
	 * We get the shortest path(if possible) that completes all the jobs in jobList. 
	 * @param source: the name of the city we start from. 
	 * @return: formatted String of the shortest path through all the jobs in jobList.
	 * @precondition: source is a valid city in map, jobList contains at least one job
	 * 				  jobList contains jobs that are between cities in map. 
	 * @postcondition: formatted string returned, statistical fields  in solver(cost &nodes expanded)
	 * 				   are changed, to reflect the performance cost of the a* search.
	 */
	public String getRoute(String source){
		String routeString = "";		
		if(this.solver.isSolvable(map, source,jobList) == false){
			return this.solver.getNodesExpanded() + " nodes expanded" + System.lineSeparator() + "No solution";
		}
		
		Deque<Job> quickest = this.solver.findOptimalRoute(map, jobList, map.getCity(source),new TotalJobCostHeuristic());
		routeString += this.solver.getNodesExpanded() + " nodes expanded" + System.lineSeparator();
		routeString += "cost = "+ this.solver.getCost() + System.lineSeparator();
		for(Job j: quickest){
			routeString += j.toString() +System.lineSeparator();
		}
	    return routeString;
	}

}
