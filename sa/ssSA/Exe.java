///////////////////////////////////////////////////////////////////////////////
///            Steady State Genetic Algorithm v1.0                          ///
///                by Enrique Alba, July 2000                               ///
///                                                                         ///
///   Executable: set parameters, problem, and execution details here       ///
///////////////////////////////////////////////////////////////////////////////

package sa.ssSA;

import java.util.Random;
import org.kohsuke.args4j.CmdLineException;  
import org.kohsuke.args4j.CmdLineParser;  
import org.kohsuke.args4j.Option;  
import java.io.IOException;  

public class Exe
{

  public static Random rand;

  @Option(name="-a", aliases="--annealingFactor", usage="Factor used for decreasing the temperature.")  
  private double annealingFactor = -1;
  @Option(name="-f", aliases="--file", usage="Path of the file with the problem instance.", required=false)
  private String fileName = ""; 
  @Option(name="-s", aliases="--seed", usage="Random seed.")  
  private int seed = -1; 
  

  public static void main(String[] args) throws Exception {
        new Exe().doMain(args);
  }

  private void doMain(String args[]) throws Exception
  {


    CmdLineParser parser = new CmdLineParser(this);
      

    try {
        // parse the arguments.
        parser.parseArgument(args);


    } catch( CmdLineException e ) {
        // if there's a problem in the command line,
        // you'll get this exception. this will report
        // an error message.
        System.err.println(e.getMessage());
        System.err.println("java SampleMain [options...] arguments...");
        // print the list of available options
        parser.printUsage(System.err);
        System.err.println();

        return;
    }


    if (seed == -1) 
      rand = new Random();
    else
      rand = new Random(seed);
    long inicio = System.currentTimeMillis();
         
   //Parameters MDKP

    int    in;                                       // Item number
    int    il;                                       // Item length
    long   MAX_ISTEPS = 50000;
    double initialProb = 0.9999;
    int numInitialEstimates = 10;
    int stepsForAnnealing = 5;

    if (annealingFactor == -1) annealingFactor = 0.9;

    Problem problem;
    //Default MQKP problem parameters. If the MQKP problem changes, these parameters have to been changed too.  
    if (fileName == ""){

        problem = new ProblemMDKP();  
        in         = 15;                          // Item number
        il         = 1;                            //Item length
        double tf         = (double)4015 ;           // Target fitness being sought
        problem.set_itemN(in);
        problem.set_itemL(il);
        problem.set_target_fitness(tf);
        //Extern problem read from a file.

    }else{

        ProblemMDKP problem_aux = new ProblemMDKP();
        problem_aux.readFile(fileName);
        in = problem_aux.get_itemN();
        il = problem_aux.get_itemL();
        problem = problem_aux;
    }

    Algorithm sa;          // The ssSA being used
    sa = new Algorithm(problem, in, il, initialProb, numInitialEstimates, annealingFactor, stepsForAnnealing);

    int step = 0;
/*    for (step=0; step<MAX_ISTEPS; step++)
    {  
      sa.go_one_step();
      // System.out.print(step); System.out.print("  ");
      System.out.print(sa.get_current().get_fitness());
      System.out.print("  "); System.out.println(sa.get_best().get_fitness());

      // if(     (problem.tf_known())                    &&
      // sa.get_current().get_fitness()>=problem.get_target_fitness()
      // )
      // { 
      //   System.out.print("Solution Found! After ");
      //   System.out.print(problem.get_fitness_counter());
      //   System.out.println(" evaluations");
      //   break;
      // }

    }
*/

    while(true)
    {
      step=step+1;
      sa.go_one_step();
      // System.out.print(step); System.out.print("  ");
      // System.out.print(sa.get_current().get_fitness());
      // System.out.print("  "); System.out.println(sa.get_best().get_fitness());
      if((problem.tf_known())  &&  sa.get_current().get_fitness()>=problem.get_target_fitness() )
      { 
         // System.out.print("Solution Found! After ");
         // System.out.print(problem.get_fitness_counter());
         // System.out.println(" evaluations");
        break;
      }

    }

    // Print the solution

    long fin = System.currentTimeMillis(); 
    double tiempo = (double) ((fin - inicio));

    System.out.print("Tiempo (ms): "+tiempo+"\t");
    System.out.print("Iteraciones: " + step); System.out.print("\t"); 
    System.out.println("Fitness: " + sa.get_best().get_fitness());

    System.out.println("Solución:");



    for(int i=0;i<in*il;i++)
      System.out.print(sa.get_best().get_item(i)); System.out.println();
  }

}
// END OF CLASS: Exe
