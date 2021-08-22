
public class cnfexample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int nVars = 3;
		SATSolver.init(nVars);
		int[] clause = {1} ;
		SATSolver.addClause(clause);
		int[][] clauses = {{-1,-2}, {2,3}} ;
		SATSolver.addClauses(clauses);
		boolean[] assignment = SATSolver.getSolution() ;
		if (assignment == null)
		 System.out.println("TIMEOUT");
		else if (assignment.length == nVars+1)
		System.out.println("SAT");
		else
		System.out.println("UNSAT");

		}
	}
