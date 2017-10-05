import java.util.ArrayList;

class Node {

	
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
    Node(int S, int L, int Ma, int Mb, RobotLocation robotLocation, int pathCost, Node parentNode){
		this.S = S;
		this.L = L;
		this.Ma = Ma;
		this.Mb = Mb;
		this.robotLocation = robotLocation;
		this.parentNode = parentNode;
		this.pathCost = pathCost;
		
	}
	
	int getTotalCost(){
		return pathCost + calculateHeuristic();
	}
	
	/**Returns the sum of parcels in the wrong place
	 * 
	 * @return The sum of all state variables 
	 */
    int getSumOfState(){
		return S + L + Ma + Mb;
	}
	
	Node getParentNode(){
		return this.parentNode;
	}
	
	private int calculateHeuristic(){
		return  S + Ma + Mb + L;
	}
	
	/**This generates all possible states that could result from The Ronbot's action, with some optimisations.
	 * 
	 * @return An <code>ArrayList</code> of <code>Nodes</code> representing the child states of this state.
	 */
    ArrayList<Node> generateChildren(){
		
		ArrayList<Node> children = new ArrayList<>();
		
		switch (robotLocation) {

			case WAREHOUSEa:
				children.add(new Node(S, L, Ma, Mb, RobotLocation.TRUCK, pathCost + 1, this)); //Move to Truck without carrying parcel
				children.add(new Node(S, L, Ma, Mb, RobotLocation.WAREHOUSEb, pathCost + 1, this)); //Move to WarehouseB without carrying parcel
				if (Ma > 0) { //If there are medium parcels in A
					children.add(new Node(S, L, Ma - 1, Mb, RobotLocation.TRUCK, pathCost + 1, this)); //Carry Medium to Truck
				}

			case WAREHOUSEb:
				children.add(new Node(S, L, Ma, Mb, RobotLocation.TRUCK, pathCost + 1, this)); //Move without carrying parcel
				children.add(new Node(S, L, Ma, Mb, RobotLocation.WAREHOUSEa, pathCost + 1, this)); //Move to WarehouseA without carrying parcel
				if (Mb > 0) { //If there are medium parcels in B
					children.add(new Node(S, L, Ma, Mb - 1, RobotLocation.TRUCK, pathCost + 1, this)); //Carry Medium to Truck
				}

			case TRUCK:
				children.add(new Node(S, L, Ma, Mb, RobotLocation.WAREHOUSEa, pathCost + 1, this)); //Move to A without carrying parcel
				children.add(new Node(S, L, Ma, Mb, RobotLocation.WAREHOUSEb, pathCost + 1, this)); //Move to B without carrying parcel

				if (S > 0) {    // If there are small packages in truck to go to A
					children.add(new Node(S - 1, L, Ma, Mb, RobotLocation.WAREHOUSEa, pathCost + 1, this)); //Carry small to A
				}

				if (L > 0) {    //If there are large packages in truck to go to B
					children.add(new Node(S, L - 1, Ma, Mb, RobotLocation.WAREHOUSEb, pathCost + 1, this)); //Carry large to B
				}
		}
		return children;
	}

	int getS() {
		return S;
	}

	int getL() {
		return L;
	}

	int getMa() {
		return Ma;
	}

	int getMb() {
		return Mb;
	}

}
