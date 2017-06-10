import java.util.Deque;
import java.util.LinkedList;
/**
 * our interface for implementing the strategy pattern
 * to provide a heuristic for our a* search.
 * All concrete heuristics must implement this interface.
 * @author Vince
 */
public interface Heuristic {
	/**
	 * analyses a path and return a heuristic value
	 * @param jobList:list off all the jobs to do
	 * @param currentPath:currentPath we are ascribing a heuristic value to 
	 * @param map:the map we are performing a* search on .
	 * @return a heuristic value
	 * @precondition: all parameters are initialised and map and jobs are valid and solvable.
	 * @postcondition: parameters are not modified in anyway.
	 */
    public int evaluate(LinkedList<Job> jobList,Deque<Job> currentPath,RouteMap map);
}
