import java.util.Arrays;


public class StatePrettyPrinter {


	private enum Path {
		AtoB, AtoTRUCK, BtoTRUCK
	}

	private String baseOutputFormatted = "+-------------+%n| Warehouse A |%n+-------------+%n| Med:%15$7.7s |%n+-------------+%1$s%n|      %2$1.1s      |          %5$1.1s%n|     %3$3.3s     |          %5$1.1s%n|      %4$1.1s      |   +------+------+%n+------+------+   |    Truck    |%n       %6$1.1s          +-------------+%n       %6$1.1s          | Sma:%17$7.7s |%n       %6$1.1s          | Lrg:%18$7.7s |%n       %6$1.1s          +-------------+%n       %6$1.1s          |      %7$1.1s      |%n       %6$1.1s          |     %8$3.3s     |%n+------+------+   |      %9$1.1s      |%n| Warehouse B |   +------+------+%n+-------------+          %10$1.1s%n| Med:%16$7.7s |          %10$1.1s%n+-------------+%11$s%n|      %12$1.1s      |%n|     %13$3.3s     |%n|      %14$1.1s      |%n+-------------+%n";

	private final String ronbotHead = "+";
	private final String ronbotBody = "|O|";
	private final String ronbotLeg = "\"";
	private final String horizontalLine = "----------+";
	private final String verticalLine = "|";

	private final int aToTruckLineHorizontal = 0; // %1$
	private final int aRonbotHead = 1; // %2$
	private final int aRonbotBody = 2; // %3$
	//private final int aRonbotLeg = 3; // %4$
	private final int aToTruckLineVertical = 4; // %5$
	private final int aToBLine = 5; // %6$

	private final int truckRonbotHead = 6; // %7$
	//private final int truckRonbotBody = 7; // %8$
	//private final int truckRonbotLeg = 8; // %9$

	private final int bToTruckLineVertical = 9; // %10$
	private final int bToTruckLineHorizontal = 10; // %11$
	private final int bRonbotHead = 11; // %12$
	//private final int bRonbotBody = 12; // %13$
	//private final int bRonbotLeg = 13; // %14$

	// All of the following are strictly 7 characters long when printed
	private final int Ma = 14; // %15$
	private final int Mb = 15; // %16$
	private final int S = 16; // %17$
	private final int L = 17; // %18$

	// Note that a number of these are strictly limited to a certain character length in the formatting
	private String[] variables = new String[18];


	/**
	 * Adds all paths to the current output
	 */
	private void addAllPaths() {

		addPath(Path.AtoB);
		addPath(Path.AtoTRUCK);
		addPath(Path.BtoTRUCK);
	}


	/**
	 * Adds the specified path to the current output
	 * 
	 * @param path The Path to be added
	 */
	private void addPath(Path path) {

		switch (path) {
			case AtoB:
				variables[aToBLine] = verticalLine;
				break;
				
			case AtoTRUCK:
				variables[aToTruckLineVertical] = verticalLine;
				variables[aToTruckLineHorizontal] = horizontalLine;
				break;
				
			case BtoTRUCK:
				variables[bToTruckLineVertical] = verticalLine;
				variables[bToTruckLineHorizontal] = horizontalLine;
				break;
		}
	}
	
	/**
	 * Adds the appropriate path taken by the Ronbot in transitioning from one state to the other to output.
	 * The order of states does not matter.
	 * 
	 * @param state1 The initial state
	 * @param state2 The resulting state
	 */
	private void addPath(Node state1, Node state2){
		
		if ((state1.getRobotLocation() == RobotLocation.TRUCK) ^ (state2.getRobotLocation() == RobotLocation.TRUCK)) { // If one is at the truck
			if ((state1.getRobotLocation() == RobotLocation.WAREHOUSEa) ^ (state2.getRobotLocation() == RobotLocation.WAREHOUSEa))
				addPath(Path.AtoTRUCK);
			else
				addPath(Path.BtoTRUCK);
		} else
			addPath(Path.AtoB);
	}


	/**
	 * Adds the Ronbot to output in the specified location
	 * 
	 * @param location 
	 */
	@SuppressWarnings ("incomplete-switch")
	private void addRobot(RobotLocation location) {

		int index = aRonbotHead; // The starting index of the robot in the array. Updated depending on location passed.

		switch (location) {
			case WAREHOUSEb:
				index = bRonbotHead;
				break;

			case TRUCK:
				index = truckRonbotHead;
				break;

		}

		variables[index] = ronbotHead;
		variables[index + 1] = ronbotBody;
		variables[index + 2] = ronbotLeg;
	}


	/**
	 * Adds all parcel state values to output
	 * 
	 * @param state Integer Array in order {Ma, Mb, S, L}
	 */
	private void addValues(int[] state) {

		variables[Ma] = Integer.toString(state[0]);
		variables[Mb] = Integer.toString(state[1]);
		variables[S] = Integer.toString(state[2]);
		variables[L] = Integer.toString(state[3]);

	}

	/**
	 * Adds to output any change in parcel state values between the two states given.
	 * <bold>Must</bold> be called <bold>after</bold> <code>addValues()</code>
	 * Order of states will affect output.
	 * 
	 * @param state1 The inital state
	 * @param state2 The resulting state
	 */
	private void addValueDifferences(int[] state1, int[] state2){
		
		for (int i = 0; i<state1.length; i++){
			int diff = state2[i] - state1[i];
			if (diff != 0)
				variables[Ma+i] = "("+Integer.toString(diff)+")"+variables[Ma+i]; //This relies on the ordering of Node.getParcelState matching the order in variables[].
		}
		
		
	}

	/**
	 * Reset all output variables to the initial value. Must be called first, before any changes to state are made. 
	 */
	private void initialiseVariables() {

		Arrays.fill(variables, "");
	}


	/**
	 * Given a state node, this prints to standard output the graphical representation of that state
	 * 
	 * @param state
	 */
	public void printState(Node state) {

		initialiseVariables();
		addValues(state.getParcelState());
		addAllPaths();
		addRobot(state.getRobotLocation());
		System.out.printf(baseOutputFormatted, (Object[]) variables);
	}

	/**
	 * Prints to standard output the graphical representation of the graph without state information
	 */
	public void printStateless() {

		initialiseVariables();
		addAllPaths();
		System.out.printf(baseOutputFormatted, (Object[]) variables);
	}


	/**
	 * Prints to standard output a graphical representation of the transition between states 1 and 2.
	 * This includes the path the Ronbot took, its current position, and any change in parcel quantities
	 * 
	 * @param state1 The initial state
	 * @param state2the state resulting from the Ronbot's action.
	 */
	public void printStateTransition(Node state1, Node state2) {

		initialiseVariables();
		addRobot(state2.getRobotLocation());
		addPath(state1, state2);
		
		addValues(state2.getParcelState());
		addValueDifferences(state1.getParcelState(), state2.getParcelState());

		System.out.printf(baseOutputFormatted, (Object[]) variables);
	}
	
	
	/**
	 * Print to standard output a separator for the state diagrams.
	 */
	public void printSeparator(){
		System.out.printf("%n%n==================================================%n%n");
	}
}
