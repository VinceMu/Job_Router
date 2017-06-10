/**
 * We our basic unit of a path, similar to a road
 * but contextually different and we have a boolean 
 * isJob to ascertain whether this job is an actual job 
 * the truck must do or an empty job.
 * @author Vince
 */
public class Job {
    private City startJob;
    private City endJob;
    private boolean isJob;
    
    /**
     * initialise job class so that no fields are left empty
     * or uninitialised. 
     * @param start: the city the job starts at.
     * @param end: the city the job ends at
     * @param notEmpty: is this an empty job
     */
    public Job(City start,City end,boolean notEmpty){
    	this.startJob = start;
    	this.endJob = end;
    	this.isJob = notEmpty; 
    }
    /**
     * get the starting city that the job begins in.
     * @return the city jobs starts at.
     * @precondition: job is initialised.
     * @postcondition: startJob is returned. 
     */
    public City getStart(){
    	return this.startJob;
    }
    /**
     * get the ending city that the job begins in.
     * @return the city jobs ends at.
     * @precondition: job is initialised.
     * @postcondition: endJob is returned. 
     */
    public City getEnd(){
    	return this.endJob;
    }
    /**
     * whether this job is a job or just an empty job.
     * @return is this job an actual job
     * @precondition: job is initialised.
     * @postcondition: isJob is returned. 
     */
    public boolean isJob(){
    	return this.isJob;
    }
    /**
     * to redefine whether this job is a job or not. 
     * @param isEmpty: to set whether this job is an actual job or empty job
     * @precondition:job is initialised.
     * @postcondition: isJob is set to the isEmpty. 
     */
    public void setIsJob(boolean isEmpty){
    	this.isJob = isEmpty; 
    }
    
    /**
     * We define two jobs as equal when they have the same 
     * start and end cities. 
     * @return are the two jobs equal
     *@param otherJob: the other Job we want to compare if equal 
     *@precondition:both jobs are initialised.
     *@postcondition:a boolean is return which indicates whether 
     *				 the two jobs are equal.
     */
    @Override
    public boolean equals(Object otherJob){
    	if(otherJob == null){
    		return false;
    	}
    	Job newJob = null;
    	try{
    		newJob = (Job)otherJob;
    	}catch(Exception e){
    		return false;
    	}
    	if(newJob.getStart().getName().equals(this.getStart().getName()) &&
    			newJob.getEnd().getName().equals(this.getEnd().getName())){
    		return true;
    	}
    	return false;
    	
    }
    /**
     * serve the Job in a formatted string. 
     * @return a printable version of a job
     * @precondition: job is initialised
     * @postcondition:job is unchanged string is return containing details of the job
     */
    @Override
    public String toString(){
    	if(this.isJob == true){
        	return "Job " + this.startJob.getName() + " to " + this.endJob.getName();
    	} else{
        	return "Empty " + this.startJob.getName() + " to " + this.endJob.getName();
    	}
    }
    
}
