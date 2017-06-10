import java.util.Deque;
import java.util.LinkedList;
/**
 * A heuristic for our a* search in RouteOptimizer. As we are using the 
 * Strategy design pattern to implement our Heuristic for a*, this heuristic 
 * implements the Heuristic interface. 
 * This heuristic uses the total cost of jobs in the joblist to help generate a value.
 * @author Vince
 */
public class TotalJobCostHeuristic implements Heuristic{

	private int totalJobCost;
	
	/*
	 * we initialise the totalJobCost to 0.
	 */
	public TotalJobCostHeuristic(){
		this.totalJobCost=0;
	}
	/**
	 * uses the formula jobsRemaining * averageJobCost to calculate a heuristic
	 * @param jobList:list of jobs to do
	 * @param currentPath: the current path that is being assigned a heuristic value
	 * @param map:the RouteMap the jobs are taking place on. 
	 * @return heuristic value for currentPath.
	 * @precondition: jobList contains atleast one job, currentPath is not empty, map is a* solvable
	 * 			      for jobList.
	 * @postcondition: parameters are unchanged, heuristics is returned to caller function. 
	 */
	@Override
	public int evaluate(LinkedList<Job> jobList, Deque<Job> currentPath,RouteMap map) {
		int jobsRemaining = jobList.size();
		LinkedList<Job> toDoCopy = new LinkedList<Job>(jobList);
		if(this.totalJobCost == 0){
			initialiseJobCost(jobList,map);
		}	
		for(Job path:currentPath){ 		
		    for(int i= 0; i<toDoCopy.size();i++){ //this avoids concurrent modification exception
				  Job curJob = toDoCopy.get(i);
				    if(path.equals(curJob)){
				        jobsRemaining--;
				        toDoCopy.remove(i);
			         }
			      }
			   }
		int heuristicValue = jobsRemaining   * (this.totalJobCost/jobList.size());
		return heuristicValue;
	}
	/**
	 * initialises totalJobCost by summing the travel costs in the jobList
	 * @param jobList:jobs to do
	 * @param map: the map the jobs are being carried out on
	 * @precondition:jobList has at least one Job, map is solvable for the job(s) in jobList
	 * @postcondition:totalJobCost contains the total travel cost of all the jobs in jobList. 
	 */
	private void initialiseJobCost(LinkedList<Job>jobList, RouteMap map){
		for(Job j:jobList){
			int currCost = map.getTravelCost(j.getStart(),j.getEnd());
			this.totalJobCost += currCost;
		}
	}

}
