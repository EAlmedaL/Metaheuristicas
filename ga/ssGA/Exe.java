///////////////////////////////////////////////////////////////////////////////
///            Steady State Genetic Algorithm v1.0                          ///
///                by Enrique Alba, July 2000                               ///
///                                                                         ///
///   Executable: set parameters, problem, and execution details here       ///
///////////////////////////////////////////////////////////////////////////////

package ga.ssGA;

import org.kohsuke.args4j.CmdLineException;  
import org.kohsuke.args4j.CmdLineParser;  
import org.kohsuke.args4j.Option;  
import java.io.IOException;  
import java.util.Random;
public class Exe
{

  public static Random rand;

  @Option(name="-m", aliases="--mutation", usage="Mutation probability.")  
  private double pm = -1;
  @Option(name="-c", aliases="--crossover", usage="Crossover probability.")  
  private double pc = -1;
  @Option(name="-s", aliases="--seed", usage="Random seed.")  
  private int seed = -1;  
  @Option(name="-f", aliases="--file", usage="Path of the file with the problem instance.", required=false)
  private String fileName = ""; 
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
                 

/*     
    // PARAMETERS PPEAKS 
    int    gn         = 512;                           // Gene number
    int    gl         = 1;                            // Gene length
    int    popsize    = 512;                          // Population size
    if (pc == -1) pc  = 0.8;                          // Crossover probability
    if (pm == -1) pm  = 1.0/(double)((double)gn*(double)gl); // Mutation probability
    double tf         = (double)1 ;              // Target fitness beign sought
    long   MAX_ISTEPS = 50000;
*/
      
    // PARAMETERS ONEMAX
/*    int    gn         = 512;                          // Gene number
    int    gl         = 1;                            // Gene length
    int    popsize    = 512;                          // Population size
    if (pc == -1) pc  = 0.8;                          // Crossover probability

    if (pm == -1) pm  = 1.0/(double)((double)gn*(double)gl); // Mutation probability
    double tf         = (double)gn*gl ;               // Target fitness being sought
    long   MAX_ISTEPS = 50000;

    Problem   problem;                                // The problem being solved
    problem = new ProblemOneMax();

    problem.set_geneN(gn);
    problem.set_geneL(gl);
    problem.set_target_fitness(tf);    
*/


    // problem = new ProblemPPeaks(); 
    



    Problem   problem;                                // The problem being solved

    //Parameters MDKP
    int    gn;                                       // Gene number
    int    gl;                                       // Gene length 
    int    popsize    = 15;                          // Population size
    if (pc == -1) pc = 0.8;                          // Crossover probability
    if (pm == -1) pm  = 0.1;                         // Mutation probability
    long   MAX_ISTEPS = 50000;


    //Default MQKP problem parameters. If the MQKP problem changes, these parameters have to been changed too.
    if (fileName == ""){
        problem = new ProblemMDKP();  
        gn         = 15;                          // Gene number
        gl         = 1;                           // Gene length
        double tf         = (double)4015 ;           // Target fitness being sought
        problem.set_geneN(gn);
        problem.set_geneL(gl);
        problem.set_target_fitness(tf);

    //Extern problem read from a file.
    }else{
        ProblemMDKP problem_aux = new ProblemMDKP();
        problem_aux.readFile(fileName);
        gn = problem_aux.get_geneN();
        gl = problem_aux.get_geneL();
        problem = problem_aux;
    }


    Algorithm ga;          // The ssGA being used
    ga = new Algorithm(problem, popsize, gn, gl, pc, pm);


    int step = 0;
 /*   for (step=0; step<MAX_ISTEPS; step++)
    {  
      ga.go_one_step();
//      System.out.print(step); System.out.print("  ");
      System.out.print(ga.get_avgf());
      System.out.print("  "); System.out.print(ga.get_individual().get_fitness());
      System.out.print("  "); System.out.println(ga.get_BESTF());


      // if(     (problem.tf_known())                    &&
      // sa.get_current().get_fitness()>=problem.get_target_fitness()
      // )
      // { 
      //   System.out.print("Solution Found! After ");
      //   System.out.print(problem.get_fitness_counter());
      //   System.out.println(" evaluations");
      //   break;
      // }

    }*/


    while(true)
    {
      step=step+1;
      ga.go_one_step();
//    System.out.print(step); System.out.print("  ");
//    System.out.print(sa.get_current().get_fitness());
//    System.out.print("  "); System.out.println(sa.get_best().get_fitness());
      if((problem.tf_known())  &&  (ga.get_solution().get_fitness() >= problem.get_target_fitness()) )
      { 
//         System.out.print("Solution Found! After ");
//         System.out.print(problem.get_fitness_counter());
//         System.out.println(" evaluations");
        break;
      }

    }

    long fin = System.currentTimeMillis(); 
    double tiempo = (double) ((fin - inicio));

    System.out.print("Tiempo (ms): "+tiempo+"\t");
    System.out.print("Iteraciones: " + step); System.out.print("\t"); 
    System.out.println("Fitness: " + ga.get_solution().get_fitness());

    System.out.println("Solución:");

    // Print the solution
    for(int i=0;i<gn*gl;i++)
      System.out.print( (ga.get_solution()).get_allele(i) ); System.out.println();
  }

}
// END OF CLASS: Exe
