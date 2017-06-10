/***
 * Represents a node in our Graph.
 * we store info pertaining to a city
 * that is name and it's unload fee
 * @author Vince
 *
 */
public class City{
	
    private String name;
    private int unloadingFee;
    
    /**
     * Initialises a city completely such that there are
     * no empty fields.
     * @param name:name of the city each name is unique
     * @param unloadingFee:unloading fee of the city
     */
    public City(String name,int unloadingFee){
    	this.name = name;
    	this.unloadingFee = unloadingFee; 
    }
    
    /**
     * get the name of the city
     * @return name of the city
     */
    public String getName(){
    	return this.name;
    }
    /**
     * get the value stored in the city i.e unloading fee
     * @return unloading fee of the city
     */
    public int getUnloadingFee(){
    	return this.unloadingFee;
    }
   
}
