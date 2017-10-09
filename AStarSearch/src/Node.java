import java.util.ArrayList;

public class Node {

	
	private int S, L, Ma, Mb, pathCost;
	private Node parentNode;
	private RobotLocation robotLocation;
	
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
	 */
	public Node(int S, int L, int Ma, int Mb, RobotLocation robotLocation, int pathCost, Node parentNode){
		this.S = S;
		this.L = L;
		this.Ma = Ma;
		this.Mb = Mb;
		this.robotLocation = robotLocation;
		this.parentNode = parentNode;
		this.pathCost = pathCost;
		
	}
	
	/**
	 * 
	 * @return The sum of the path and heuristic cost of this node
	 */
	public int getTotalCost(){
		return pathCost + calculateHeuristic();
	}
	
	/**Returns the sum of parcels in the wrong place
	 * 
	 * @return The sum of all state variables 
	 */
	public int getSumOfState(){
		return S + L + Ma + Mb;
	}
	
	/**Returns an array of integers representing the parcels in this state. In the order of MediumA, MediumB, Small, Large.
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
	
	public int calculateHeuristic(){
		//return  S + Ma + Mb + L;
		
		return calculateSecondHeuristic();
	}
	
	public int calculateSecondHeuristic(){
		
		int x = (2*(Math.min(Ma, S)))+(2*(Math.min(Mb, L)));
		int a = Math.max(Ma - S, S - Ma);
		int b = Math.max(Mb-L, L-Mb);
		
		if (((Ma - S)>0 && (L-Mb)>0) || ((Mb-L)>0 && (S-Ma)>0)){
			return x + 2* (a + b) - Math.min(a, b) -1;
			
		} else {
			return x+ (2 * (a+b)) -1;
		}
	}
	
	/**This generates all possible states that could result from The Ronbot©'s action, with some optimisations.
	 * 
	 * @return An <code>ArrayList</code> of <code>Nodes</code> representing the child states of this state.
	 */
	public ArrayList<Node> generateChildren(){
		
		ArrayList<Node> children = new ArrayList<>();
		
		switch (robotLocation){
			
			case WAREHOUSEa:		
				children.add(new Node(S, L, Ma, Mb, RobotLocation.TRUCK, pathCost+1, this)); //Move to Truck without carrying parcel
				children.add(new Node(S, L, Ma, Mb, RobotLocation.WAREHOUSEb, pathCost+1, this)); //Move to WarehouseB without carrying parcel
				if (Ma>0){ //If there are medium parcels in A
					children.add(new Node(S, L, Ma-1, Mb, RobotLocation.TRUCK, pathCost+1, this)); //Carry Medium to Truck
				}
				break;
				
			case WAREHOUSEb:				
				children.add(new Node(S, L, Ma, Mb, RobotLocation.TRUCK, pathCost+1, this)); //Move without carrying parcel
				children.add(new Node(S, L, Ma, Mb, RobotLocation.WAREHOUSEa, pathCost+1, this)); //Move to WarehouseA without carrying parcel
				if (Mb>0){ //If there are medium parcels in B
					children.add(new Node(S, L, Ma, Mb-1, RobotLocation.TRUCK, pathCost+1, this)); //Carry Medium to Truck
				}
				break;
				
			case TRUCK:
				children.add(new Node(S, L, Ma, Mb, RobotLocation.WAREHOUSEa, pathCost+1, this)); //Move to A without carrying parcel
				children.add(new Node(S, L, Ma, Mb, RobotLocation.WAREHOUSEb, pathCost+1, this)); //Move to B without carrying parcel
				
				if(S>0){	// If there are small packages in truck to go to A
					children.add(new Node(S-1, L, Ma, Mb, RobotLocation.WAREHOUSEa, pathCost+1, this)); //Carry small to A
				}
				
				if(L>0){	//If there are large packages in truck to go to B
					children.add(new Node(S, L-1, Ma, Mb, RobotLocation.WAREHOUSEb, pathCost+1, this)); //Carry large to B
				}
				break;
		}
			
		return children;
		
	}
	
	
	
	
}
