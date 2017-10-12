import java.util.ArrayList;

public class Node {

	
	private int S, L, Ma, Mb, pathCost;
	private Node parentNode;
	private RobotLocation robotLocation;
	private NodeHeuristic heuristic;
	
	/**
	 * Construct a Node representing a state in the state space, with attributes and methods for determining parent and child states
	 * 
	 * @param S The number of Small packages in the truck
	 * @param L The number of Large packages in the truck
	 * @param Ma The number of medium packages in Warehouse A
	 * @param Mb The number of medium packages in Warehouse B
	 * @param robotLocation The location of the Ronbot
	 * @param pathCost The path cost to get to this node from the Root node. Does not include Heuristic cost.
	 * @param parentNode The node (state) from which this node (state) was derived.
	 * @param heuristic The <code>NodeHeuristic</code> to use for evaluating cost to goal.
	 */
	public Node(int S, int L, int Ma, int Mb, RobotLocation robotLocation, int pathCost, Node parentNode, NodeHeuristic heuristic){
		this.S = S;
		this.L = L;
		this.Ma = Ma;
		this.Mb = Mb;
		this.robotLocation = robotLocation;
		this.parentNode = parentNode;
		this.pathCost = pathCost;
		this.heuristic = heuristic;
		
	}
	
	/**
	 * Get the total A* search cost for this node; the sum of the path cost from the root node to here, and the estimated cost to goal. 
	 * 
	 * @return The sum of path and heuristic costs.
	 */
	public int getTotalCost(){
		return pathCost + getHeuristicCost();
	}
	
	/**
	 * Returns the sum of parcels in the wrong place. Used to find the goal state.
	 * 
	 * @return The sum of all state variables 
	 */
	public int getSumOfState(){
		return S + L + Ma + Mb;
	}
	
	/**
	 * Returns an array of integers representing the parcels in this state. In the order of MediumA, MediumB, Small, Large.
	 * 
	 * @return Integer array {Ma, Mb, S, L}
	 */
	public int[] getParcelState(){
		int[] x = {Ma, Mb, S, L};
		return x;
	}
	
	public RobotLocation getRobotLocation(){
		return robotLocation;
	}
	
	public Node getParentNode(){
		return this.parentNode;
	}
	
	public int getPathCost(){
		return pathCost;
	}
	
	/**
	 * Method for getting the heuristic cost to goal state.
	 * 
	 * @return The calculated cost.
	 */
	public int getHeuristicCost(){
		return heuristic.getHeuristicCost(S, L, Ma, Mb);
	}
	
	
	/**This generates all possible states that could result from The Ronbot©'s action, with some optimisations.
	 * 
	 * @return An <code>ArrayList</code> of <code>Nodes</code> representing the child states of this state.
	 */
	public ArrayList<Node> generateChildren(){
		
		ArrayList<Node> children = new ArrayList<>();
		
		switch (robotLocation){
			
			case WAREHOUSEa:		
				children.add(new Node(S, L, Ma, Mb, RobotLocation.TRUCK, pathCost+1, this, heuristic)); //Move to Truck without carrying parcel
				children.add(new Node(S, L, Ma, Mb, RobotLocation.WAREHOUSEb, pathCost+1, this, heuristic)); //Move to WarehouseB without carrying parcel
				if (Ma>0){ //If there are medium parcels in A
					children.add(new Node(S, L, Ma-1, Mb, RobotLocation.TRUCK, pathCost+1, this, heuristic)); //Carry Medium to Truck
				}
				break;
				
			case WAREHOUSEb:				
				children.add(new Node(S, L, Ma, Mb, RobotLocation.TRUCK, pathCost+1, this, heuristic)); //Move without carrying parcel
				children.add(new Node(S, L, Ma, Mb, RobotLocation.WAREHOUSEa, pathCost+1, this, heuristic)); //Move to WarehouseA without carrying parcel
				if (Mb>0){ //If there are medium parcels in B
					children.add(new Node(S, L, Ma, Mb-1, RobotLocation.TRUCK, pathCost+1, this, heuristic)); //Carry Medium to Truck
				}
				break;
				
			case TRUCK:
				children.add(new Node(S, L, Ma, Mb, RobotLocation.WAREHOUSEa, pathCost+1, this, heuristic)); //Move to A without carrying parcel
				children.add(new Node(S, L, Ma, Mb, RobotLocation.WAREHOUSEb, pathCost+1, this, heuristic)); //Move to B without carrying parcel
				
				if(S>0){	// If there are small packages in truck to go to A
					children.add(new Node(S-1, L, Ma, Mb, RobotLocation.WAREHOUSEa, pathCost+1, this, heuristic)); //Carry small to A
				}
				
				if(L>0){	//If there are large packages in truck to go to B
					children.add(new Node(S, L-1, Ma, Mb, RobotLocation.WAREHOUSEb, pathCost+1, this, heuristic)); //Carry large to B
				}
				break;
		}
			
		return children;
		
	}
	
	@Override
	public int hashCode(){
		return (S + "|"+L+"|"+Ma+"|"+Mb+"|"+robotLocation.toString()).hashCode();
		
	}
	
	
}
