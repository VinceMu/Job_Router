import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
/**
 * the console port of our application
 * We isolate the console aspect of the program 
 * so that this program can easily be ported to other
 * platforms such as gui application. Also this design will 
 * save the need to have redundant methods in RoutingEngine class
 * if we were to merge the 2 classes together. Heuristic Analysis at 
 * bottom of file.
 * @author Vince
 */
public class FreightSystem {
	/**
	 * loads data from the file into routeMap 
	 * and jobList before perform a* to find the 
	 * more optimal path.
	 * @param args: the input file with commands
	 */
	public static void main(String [] args){
		
		RoutingEngine router =  new RoutingEngine();
		FreightSystem freightSystem = new FreightSystem();
		
		Scanner sc = null;
	    try{
	        sc = new Scanner(new FileReader(args[0]));  
	    }
	    catch (FileNotFoundException e) {
	    	System.out.println(e.toString());
	    }  
	    finally{
	    	if (sc != null){
	    	  while(sc.hasNextLine()){
	    	     freightSystem.readInput(sc.nextLine(),router);
	    	  }
	        }
	    }
	    System.out.println(router.getRoute("Sydney").trim());
	    
	}
	/**
	 * parses a line in the input text file
	 * so that it can be execute by the routing engine.
	 * @param input:a line of input from the text fil
	 * @param router:the routingEngine object.
	 * @precondition: input is in valid input and in proper
	 *                order, RoutingEngine has been initialised.
	 * @postCondtion:RoutingEngine has been modified as per the command
	 * 				 given by the input. 	
	 */
	private void readInput(String input, RoutingEngine router){
	   String args[] = input.split(" ");
	   switch(args[0]){
	     case "Unloading": router.executeUnloading(Integer.parseInt(args[1]), args[2]);
	    	 break;
	     case "Cost": router.executeCost(Integer.parseInt(args[1]), args[2], args[3]);
	    	 break;
	     case "Job": router.executeJob(args[1], args[2]);
	    	 break;
	   default:
		     break;	     
	   }
	}
}
/**
 * Heuristic Analysis
 * My heuristic did drastically increase performance by lowering the number of nodes expanded by 
 * a seemingly drastic amount. The sample input(input1.txt) went from 452 to 38 nodes expanded with the addition
 * of my heuristic. Likewise input2.txt,input3.txt, input5.txt,input9.txt all witnessed exponential decreases
 * in amount of nodes expanded. To add evidence to nodes expanded being correlated with performance I timed
 * how long it took to solve input3.txt and the time went from 5.2seconds to 0.5 seconds with the addition of
 * my heuristic. The result of this improvement is due to my heuristic prioritising paths which have completed
 * more jobs over path which had not. 
 * Despite this my heuristic failed to improve performance on input6.txt and input7.txt as they featured 
 * complex graphs and edges of drastically varying weights. I theorise this is because I didn't take into 
 * account currentPath.size() into my heuristic. If I had been able to incorporate this and make the heuristic 
 * admissable, I would have been able to 'prune' unwanted paths that go back and forth between 2 lightly weighted
 * edges. 
*/
