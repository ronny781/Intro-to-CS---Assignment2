import java.nio.channels.IllegalChannelGroupException;
import java.util.Arrays;

public class Assignment2 {

	
	/*-----------------------
	 *| Part A - tasks 1-11 |
	 * ----------------------*/
	
	
	public static void main(String[] args) {
	
		int[] lits1 = {1,2,3};
		int[] lits2 = {-1,2,3};

		
		

		
		boolean[] assign1 = {false, false, false, false}; 
		boolean[] assign2 = {false, false, true, true};  
		boolean[] assign3 = {false, true, false, false};
		
		int[][] cnf1 = exactlyOne(lits1);
		int[][] cnf2 = exactlyOne(lits2);
	//	for(int [] arr1 : cnf1) {
	//		System.out.println(Arrays.toString(arr1));
		
		
		//int[][] cnf = 	encode(flights);
		
		boolean[][] flights = {{false, false, true, true },
			                	 {false, false, true, true },
				                 {true, true, false, true },
			                   	{true, true, true, false}};
		int [] tour =  {0,2,1,3};
	    
		
		System.out.println(solve2(flights));

		
		int[][] cnf4 = 	noIllegalSteps(flights);
		for(int [] arr : cnf4) 
				System.out.println(Arrays.toString(arr));
		System.out.println("");
		int[][] cnf5 = 	noIllegalSteps(flights);
		for(int [] arr : cnf5) 
				System.out.println(Arrays.toString(arr));
			
		boolean[] assignment = {false, true, false, false, false,
				 false, true, false, true, false};
	//	int[] tour = solve(flights);
		//System.out.println(tour[0]);
	
	}



	// task 1 
	public static boolean isSquareMatrix(boolean[][] matrix) {
		boolean ans = true;
		if(matrix == null)
			ans = false;
		for(int i = 0; ans && i<matrix.length ; i++) { //i used && becuase if the matrix is null i want to make sure not to check its length becuase i will get a runtime error.
			if(matrix[i].length != matrix.length)
				ans = false;
		}
		
		return ans;
		
	}
	
	// task 2
	public static boolean isSymmetricMatrix(boolean[][] matrix) {
		boolean ans = true;
		for(int i = 0; i<matrix.length-1 & ans; i++) {
			for(int j = i + 1; j<matrix.length & ans; j++) {
				if(matrix[i][j] != matrix[j][i]) {
					ans = false;
				}
			}
		}
			return ans;
		
	}

	// task 3
	public static boolean isAntiReflexiveMatrix(boolean[][] matrix) {
		boolean ans = true;
		for(int i = 0; ans & i<matrix.length; i++) {
			if(matrix[i][i]==true)
				ans = false;
		}
		return ans;
	}
	
	// task 4
	public static boolean isLegalInstance(boolean[][] matrix) {
		boolean ans = false;
		if(isSquareMatrix(matrix) & isSymmetricMatrix(matrix) & isAntiReflexiveMatrix(matrix))
			ans = true; 
		return ans;
	}
	
	// task 5
	public static boolean isPermutation(int[] array) {
		
		boolean ans = true;
		for(int i = 0; ans & i<array.length; i++) {
			int counter= 0; // his job is to count for every value the number he appears
		
			for(int j = 0; j<array.length; j++)	{
			if(array[j]==i)
				counter = counter+1;
			}
		if(counter !=1) 
			ans = false;
		}
		
		return ans;
	}
	
	// task 6
	public static boolean hasLegalSteps(boolean[][] flights, int[] tour) {
		boolean ans = true; 
		for(int i = 0;ans & i<tour.length;i++) {
			
			if(i==tour.length-1) { // checks whether the line between the last city to the first exists.
				if(flights[tour[i]][tour[0]]==false) 
					ans =false;
				}
			else if(ans & flights[tour[i]][tour[i+1]]==false)// checks whether the line between the "n" city to "n+1" city exists.
				ans =false;
		}
		return ans;
	}
	
	// task 7
	public static boolean isSolution(boolean[][] flights, int[] tour) { 
		
		if(tour.length!= flights.length)
			throw new IllegalArgumentException("the length of tour is incorrect");
		boolean ans = false;
		if(tour[0]!=0 | !isPermutation(tour) | !hasLegalSteps(flights, tour)) //if one of the terms is false, the condition is false and therefore we return false answer;
			 ans = false;
		else 
			ans = true;
			return ans;
		
		
	}
	public static boolean evaluateLiteral(int literal, boolean[] assign) {
    	//---------------write your code BELOW this line only!--------------
		boolean literalValue = false;
		
		if(literal > 0)
			literalValue = assign[literal];
		else 
			literalValue = !assign[-literal];
		
		return literalValue;
        //---------------write your code ABOVE this line only!--------------
	}
	
	public static boolean evaluateClause(int[] clause, boolean[] assign) {
        //---------------write your code BELOW this line only!--------------
		boolean res = false;
		
		for(int i = 0; i< clause.length & !res ; i++) {
			int literal = clause[i];
			boolean literalValue = evaluateLiteral(literal, assign);
			
			res = res | literalValue;
		}
		return res;
        //---------------write your code ABOVE this line only!--------------
    }
	// task 8
	public static boolean evaluate(int[][] cnf, boolean[] assign) {
        //---------------write your code BELOW this line only!--------------
		boolean res =true;
		
		for(int clauseNum = 0; clauseNum<cnf.length & res ; clauseNum++)
			res = res & evaluateClause(cnf[clauseNum], assign);
		
		return res;
        //---------------write your code ABOVE this line only!--------------
	}


	// task 9
	public static int[][] atLeastOne(int[] lits) { // At least one means (True or True or True) as an example of three literals expression.
		int[][] cnf = new int[1][lits.length];
		for(int i = 0 ; i< lits.length ; i++) {
			cnf[0][i] = lits[i];
		}
		
		return cnf;
	}

	// task 10
	public static int[][] atMostOne(int[] lits) { // At most one means a conjunctive expression of (false or false)  expressions between every two literals of the array, for all pairs.
		
		int numOflits = lits.length;
		int numofClauses = numOflits * (numOflits - 1) / 2;
		int currIndex = 0;
		int[][] cnf = new int[numofClauses][numOflits];
		
		for (int i = 0;i<lits.length;i++) {
			for(int j = i + 1; j<lits.length;j++,currIndex++) {
				int[] clause = {-lits[i],-lits[j]};
				cnf[currIndex] = clause;
			}
		}
		return cnf;
		
	}
	
	// task 11
	public static int[][] exactlyOne(int[] lits) { // merging both "At least one" and "At most one" cnf's with an AND operator between them logically equals to exactly one.
		int[][] cnf1 = atMostOne(lits);
		int[][] cnf2 = atLeastOne(lits);
		int i = 0;
		int [][] cnf = new int[cnf1.length + cnf2.length][];
		for(int [] arr : cnf1) {
			cnf[i] = arr;
			i++;
		}
		for(int [] arr : cnf2) {
			cnf[i] = arr;
			i++;
		}
		return cnf;
	}
	

	/*------------------------
	 *| Part B - tasks 12-20 |
	 * -----------------------*/
	
	// task 12a
	public static int map(int i, int j, int n) {
		int k = n * i + j + 1; 	
		return k;
	}
	
	// task 12b
	public static int[] reverseMap(int k, int n) {
		int [] pair = new int[2];
		k = k-1;
		pair[0] = k/n;
		pair[1] = k % n;
		return pair;
	}
		
	
	// task 13
	public static int[][] oneCityInEachStep(int n) {
		int [][] cnf = new int[((n*(n-1))/2+1)*n][n]; // at most one always generates (n(n-1))/2) clauses of expressions and at least one always generates 1 expression. all that for every 0<=i<n, therefore X n times.
		
		int cnfIndex = 0; // index for the next available cell in array.
		for(int r = 0 ; r < n ; r++) {
			int lateralInClauseIndex = 0;
			int [] clause = new int[n];
		
			for(int g = 0 ; g < n ; g++) { // generates the clause with all n literals	
					clause[lateralInClauseIndex] = map(r,g,n);
					lateralInClauseIndex++;
			}
			int [][] cnf1 = exactlyOne(clause); // generates exactly one to be T from the clause above.
			for(int [] arr : cnf1) { // inserting the generated cnf term to a new cnf.
				cnf[cnfIndex] = arr;
				cnfIndex++;
			}
			
		}
		return cnf;
	}

	// task 14
	public static int[][] eachCityIsVisitedOnce(int n) {
		int [][] cnf = new int[((n*(n-1))/2+1)*n][n]; // at most one always generates (n(n-1))/2) clauses of expressions and at least one always generates 1 expression. all that for every 0<=j<n, therefore X n times.
		
		int cnfIndex = 0;
		for(int r = 0 ; r < n ; r++) {
			int lateralInClauseIndex = 0; // index for the next available cell in array.
			int [] clause = new int[n];
			for(int g = 0 ; g < n ; g++) {
					clause[lateralInClauseIndex] = map(g,r,n);
					lateralInClauseIndex++;
			}
			int [][] cnf1 = exactlyOne(clause); // generates exactly one to be T from the clause above.
			for(int [] arr : cnf1) { // inserting the generated cnf term to a new cnf.
				cnf[cnfIndex] = arr;
				cnfIndex++;
			}
			
		}
		return cnf;
	}
	
	// task 15
	public static int[][] fixSourceCity(int n) {
		int [][] cnf = {{map(0,0,n)}};
		return cnf;
	}
	
	// task 16	
    public static int[][] noIllegalSteps(boolean[][] flights) {
    	
		int cnfIndex = 0; // next available index in cnf.
		int n = flights.length;
		int [][] cnf;
		int counter = 0; // counting how many lanes don't exist.
		for(int j = 0; j<flights.length - 1; j++) { // This loops intend to count how many lanes are not exist, to determine the length of the cnf array we need.
			for(int k = j + 1; k<flights.length; k++) {
				if(!flights[j][k])
					counter = counter + 2; //flights is symmetry so it's good enough to check the existence of the lane only from one side.
			}
		}
		if(counter == 0) { // if all lanes exists it will return empty cnf;
			 cnf = new int[0][0];
		}
		else { 
			 cnf = new int[counter*n][]; //for every "false" lane we have n disjunctions.
			 for(int j = 0; j<flights.length - 1; j++) {
				 for(int k = j+1; k<flights.length; k++) {
					 if(!flights[j][k]) {
						 for(int i = 0 ; i < flights.length ; i++) {
							 int [] clause = {-map(i,j,n),-map(((i+1)%n),k,n)};
							 int [] clause1 = {-map(i,k,n),-map(((i+1)%n),j,n)};
							 cnf[cnfIndex] = clause;
							 cnfIndex++ ;
							 cnf[cnfIndex] = clause1;
							 cnfIndex++ ;
						 }
					 }

				 }
			 }
		}
		return cnf;
	}
	// task 17
	public static int[][] encode(boolean[][] flights) {
		int [][] constraint1 =  oneCityInEachStep(flights.length);
		int [][] constraint2 =  eachCityIsVisitedOnce(flights.length);
		int [][] constraint3 = fixSourceCity(flights.length);
		int [][] constraint4 = noIllegalSteps(flights);
		int [][] cnf = new int[constraint1.length + constraint2.length + constraint3.length +constraint4.length][];
		int nextAvailableIndex = 0;
		// inserting every constraint to a unified cnf.
		for(int [] array : constraint1) {
			cnf[nextAvailableIndex] = array;
			nextAvailableIndex++;
		}
		for(int [] array : constraint2) {
			cnf[nextAvailableIndex] = array;
			nextAvailableIndex++;
		}
		for(int [] array : constraint3) {
			cnf[nextAvailableIndex] = array;
			nextAvailableIndex++;
		}
     	for(int [] array : constraint4) {
			cnf[nextAvailableIndex] = array;
			nextAvailableIndex++;
		}
     	return cnf;	
	}

	// task 18
	public static int[] decode(boolean[] assignment, int n) {
		if(assignment == null || assignment.length!= n*n+1) //i used || because if the array is empty i don't want to check his length.
			throw new IllegalArgumentException("Assignment is null or the length is wrong");
		int [] tour = new int[n];
			for(int i = 0 ; i < n ; i++) {
				for(int j = 0 ; j < n ; j++) {
					if(assignment[map(i,j,n)])
						tour[i] = j;
				}
		}
			return tour;
	}
	
	// task19
	public static int[] solve(boolean[][] flights) {
	
		if(!isLegalInstance(flights)) 
			throw new IllegalArgumentException("flights isn't legal instance");
		int n = flights.length;
		int [] tour = null;
		SATSolver.init(n*n);	
		int [][] clauses = encode(flights);
		SATSolver.addClauses(clauses);
		boolean [] satSolverOutput = SATSolver.getSolution();
		if(satSolverOutput == null)
			throw new RuntimeException("Time out"); //The instance wasn't solvable, we run into a timeout
		
		else if(satSolverOutput.length == n*n+1 ) { //checks if the output array corresponds with desired output.
			tour = decode(satSolverOutput, n);
			
			if(!isSolution(flights, tour)) //checks if the solution is legal.
				throw new IllegalArgumentException("Satisfied but the solution isn't legal");
		}
			return tour;
	}
	
	// task20
	public static boolean solve2(boolean[][] flights) {
		int n = flights.length;
		int [] constraint5 = new int[n]; 
		int [] tour1 = solve(flights);
		boolean condition = false;
			for(int i = 0 ; i < tour1.length ; i++) //here i created a new constraint that bans the first solution to be satisfied again. 
				constraint5[i] =  -map(i,tour1[i],n);
			
			int [] tour2 = null;
			SATSolver.init(n*n);	
			int [][] clauses = encode(flights);
			SATSolver.addClauses(clauses);
			SATSolver.addClause(constraint5); //adds the the new constraint 
			boolean [] satSolverOutput = SATSolver.getSolution();
			if(satSolverOutput == null)
				throw new RuntimeException("Time out"); //The instance wasn't solvable, we run into a timeout
			
			else if(satSolverOutput.length == n*n+1 ) { //checks if the output array corresponds with desired output.
				tour2 = decode(satSolverOutput, n);
				
				if(!isSolution(flights, tour2)) //checks if the solution is legal.
					throw new IllegalArgumentException("Satisfied but the solution isn't legal");
				
				for(int i = 1 ; !condition & i < tour2.length ; i++) { //this for loop checks if the the route is the opposite to the first solution.
					if(tour1[i] != tour2[tour2.length-i])
						condition = true;
				}
			}
				return condition;
			
				

	
	}
	
}


	
	

