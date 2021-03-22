package sa.ssSA;

import java.util.Random;
public class Algorithm
{
  private  double initialT;            //Initial temperature
  private  double T;                   //Value of the temperature
  private  int item_number;            //Number of iterations
  private  int item_length;            //Number of bits per item
  private  int sol_length;             //Number of items per solution
  private  double initialProb;         //Initial probability to accept a solution
  private  double   annealingFactor;   //Annealing factor to cool the temperature
  private  int stepsForAnnealing;      //Number of steps to cool the temperature
  private  Problem       problem;     // The problem being solved
  private  static Random r;           // Source for random values in this class
  private  Solution aux_sol;  // Internal auxiliar individual being computed
  private  Solution best_sol; // Best solution computed
  private  int iters_without_accept;

  // CONSTRUCTOR
  public Algorithm(Problem p, int in, int il, double initialProb, int numInitialEstimates,  double annealingFactor, int stepsForAnnealing)
  throws Exception
  {
    this.problem            = p;
    this.r                  = new Random();
    this.item_number        = in;
    this.item_length        = il;
    this.sol_length         = in*il;
    this.aux_sol            = new Solution(sol_length);
    this.best_sol           = new Solution(sol_length);
    this.annealingFactor    = annealingFactor;
    this.stepsForAnnealing  = stepsForAnnealing;

    //Evaluate the auxiliar solution to calculate the initial fitness
    aux_sol.set_fitness(evaluateStep(aux_sol));

    //fitness initalized to 0, so we have to set it to he minimun possible value. If it's a minimization problem, delete the minus sign.
    //if not, then negative fitnesses can't be setted
    best_sol.set_fitness(-Double.MAX_VALUE); 
    double averageFDiffs = (double)0;
    this.iters_without_accept = 0;
    /**
     Temperature initalization.
     A series of initial and neighbor solutions are generated. The mean fitness difference towards worst solutions is calculated and the temperature is cleared from the acceptance function     
    */

    for (int i = 0; i < numInitialEstimates; i++){
      Solution sol = new Solution(sol_length);
      sol.set_fitness(problem.Evaluate(sol));
      Solution neigh  = generateNeighbour(sol);
      neigh.set_fitness(problem.Evaluate(neigh));
      double deltaFitness =  neigh.get_fitness() - sol.get_fitness();

      averageFDiffs += Math.abs(deltaFitness);
    }

    averageFDiffs /= numInitialEstimates;

    this.T = -1. * averageFDiffs / Math.log(initialProb);
    this.initialT = this.T;
  }


  //Generate a neighbour solution where only a position of the original solution array changes
  public Solution generateNeighbour(Solution sol){
    Solution neigh = new Solution (sol_length);
    int indexToChange = Exe.rand.nextInt(sol_length);

    byte valueToSet = sol.get_item(indexToChange) == (byte) 1 ? (byte) 0 : (byte) 1;
    neigh.set_content(sol.get_content());
    neigh.set_item(indexToChange, valueToSet);

    return neigh;
  }

  //ACCEPTANCE FUNCTION
  public boolean accept(double deltaFitness) {
   /**
     Calculate the probability of accepting the change, which will be the exponential of 
     (the difference in fitness divided by the temperature)
     Generate a random between 0 and 1
     Return true if the random is less than the acceptance probability.
     */

    //if the problem is one of minimization, then deltaFitness has to be multiplied by -1
    double prob = Math.exp(deltaFitness/T);
    double randSample = Exe.rand.nextDouble();
    return (randSample < prob );
  }


  // EVALUATE THE FITNESS OF A SOLUTION
  public double evaluateStep(Solution sol)
  {
    return problem.evaluateStep(sol);
  }


  public void go_one_step() throws Exception
  {
    Solution neigh = generateNeighbour(aux_sol);
    neigh.set_fitness(evaluateStep(neigh));
    double deltaFitness = neigh.get_fitness() - aux_sol.get_fitness();
    //Check if accept the new solution
    if (accept(deltaFitness)){
      aux_sol.assign(neigh);
      iters_without_accept = 0;
      if(aux_sol.get_fitness() > best_sol.get_fitness())
        best_sol.assign(aux_sol);
    }
    else
    {
      iters_without_accept = iters_without_accept+1;
    }

    //Check number of steps to cool
    if (problem.get_fitness_counter() % stepsForAnnealing == 0)
      T *= annealingFactor;

    //Check if have to reheat
    if (iters_without_accept == 500){
      T = initialT;
      iters_without_accept = 0;
    }

  }

  public Solution get_best()  { return best_sol;  }
  public Solution get_current()  { return aux_sol;  }

}
// END OF CLASS: Algorithm

