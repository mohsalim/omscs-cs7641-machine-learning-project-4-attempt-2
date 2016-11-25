package burlap.oomdp.auxiliary;

import burlap.oomdp.core.Domain;
import burlap.oomdp.visualizer.Visualizer;

/**
 * This class provides a simple interface for constructing domains, but it is not required to create domains. All domains that
 * exist in BURLAP adhere to this interface for constructing domains.
 * @author James MacGlashan
 */
public interface DomainGenerator {

	/**
	 * Returns a newly instanced Domain object
	 * @return the newly instantiated Domain object.
	 */
	public Domain generateDomain();
	
	/**
	 * Retrieve 2D array of map.
	 * @return 2D array
	 */
	public int[][] getMap();
	
	/**
	 * Get/create new visualizer for this grid.
	 * @return Visualizer
	 */
	public Visualizer getVisualizer();
}
