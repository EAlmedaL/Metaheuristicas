/*
 * ProblemOneMax.java
 *
 * Created on 30 de octubre de 2001, 13:20
 */

package ga.ssGA;

import java.util.Locale;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
/**
 *
 * @author  Eduardo Almeda
 * @version 
 */
public class ProblemMDKP extends Problem{

    //Initialize with the default problem.
    static private double[][] weights_ = {
                          {8,24,13,80,70,80,45,15,28,90,130,32,20,120,40},
                          {8,44,13,100,100,90,75,25,28,120,130,32,40,160,40},
                          {3,6,4,20,20,30,8,3,12,14,40,6,3,20,5},
                          {5,9,6,40,30,40,16,5,18,24,60,16,11,30,25},
                          {5,11,7,50,40,40,19,7,18,29,70,21,17,30,25},
                          {5,11,7,55,40,40,21,9,18,29,70,21,17,35,25},
                          {0,0,1,10,4,10,0,6,0,6,32,3,0,70,10},
                          {3,4,5,20,14,20,6,12,10,18,42,9,12,100,20},
                          {3,6,9,30,29,20,12,12,10,30,42,18,18,110,20},
                          {3,8,9,35,29,20,16,15,10,30,42,20,18,120,20}
                         };

    static private double [] capacities_ = {550,700,130,240,280,310,110,205,260,275};
    static private double [] profits_ = {100,220,90,400,300,400,205,120,160,580,400,140,100,1300,650};  
    static private int nDims_ = 10; //number of dimensions

    public void readFile(String filename) throws FileNotFoundException{
      
      if (! new File(filename).isFile()){
        System.out.println("The file does not exist. Exiting...");
        System.exit(0);
      }


      File f = new File(filename);
      Scanner s = new Scanner(f).useLocale(Locale.US); //Changing local for using the '.' as decimal separator (',' by default)


      set_geneL(1);
      set_geneN(s.nextInt());
      nDims_ = s.nextInt();
      double targetFitness = s.nextDouble();
      if (targetFitness != 0) set_target_fitness(targetFitness);
      profits_ = new double[CL];
      weights_ = new double[nDims_][CL];
      capacities_ = new double[nDims_];

      //read profits for each item
      for (int j=0; j < CL; j++) profits_[j] = s.nextDouble();

      //read weight for each item in each dimension
      for (int i=0; i < nDims_; i++){
        for (int j=0; j < CL; j++) 
          weights_[i][j] = s.nextDouble();
      }

      //read maximum capacity for each dimension
      for (int i=0; i < nDims_; i++) capacities_[i] = s.nextDouble();
  }


  public void set_weights(double[][] weights){
    for (int i = 0; i < nDims_; i++ ){
      for(int j = 0; j < CL; j++)
          weights_[i][j] = weights[i][j];

    }
  }

  public void set_capacities(double[] capacities){
    for(int i = 0; i < CL; i++)
      capacities_[i] = capacities[i];
  }

  public void set_profits(double[] profits){
    for(int i = 0; i < CL; i++)
     profits_[i] = profits[i];
  }

  public double Evaluate(Individual indiv) {
    return MDKP(indiv) ;
  }

  //    PRIVATE METHODS

  //get the violation of the capacity of an individual
  private double getMaxCapacityViolation(Individual indiv){

    double v = 0.0;
    double []sumWeights= new double[nDims_];




    //sum of weights for each dimension
    for(int j=0; j<CL; j++){
      if(indiv.get_allele(j)==1){

        for(int i=0; i<nDims_; i++){
          sumWeights[i]= sumWeights[i]+weights_[i][j];
        }
      }
    }

    for(int i=0; i<nDims_; i++){

      if (sumWeights[i] > capacities_[i]){
        v = v + (sumWeights[i]-capacities_[i]);
      }
    }
    return v;
  }

  private double sumProfits(Individual indiv){
    double f=0.0;
    for(int i=0; i<CL; i++){
      if(indiv.get_allele(i)==1){
        f=f+profits_[i];
      }
    }
    return f;
  }

  // Count the number of 1's in the string
  private double MDKP(Individual indiv)
  {
    double f = getMaxCapacityViolation(indiv)*(double)(-1);
    if (f==0)
      f = sumProfits(indiv);
    return f;
  
  }

}
