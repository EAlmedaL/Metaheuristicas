package sa.ssSA;

import java.io.* ;

public class Solution implements Serializable
{

  private Content sol;        // Solution vector
  private int        L;       // Length of the solution
  private double     fitness; // Fitness of the solution

  public Solution(int L)
  {
     sol   = new Content(L);
     fitness = 0.0;
     this.L  = L;
  }


  public void print()
  {
     sol.print();
     System.out.print("   ");
     System.out.println(fitness);
  }

  public int get_length()
  {
     return L;
  }

  public void set_fitness(double fit)
  {
     fitness = fit;
  }

  public double get_fitness()
  {
     return fitness;
  }



  public void set_item(int index, byte value)
  {
     sol.set_item(index, value);
  }

  public byte get_item(int index)
  {
     return sol.get_item(index);
  }

  private void copy(Content source, Content destination)
  {
     for (int i=0; i<L; i++)
     {
         destination.set_item(i, source.get_item(i));
     }
  }

  public void assign(Solution S)
  {
    //sol     = S.get_solution();
    copy(S.get_content(), sol);
    fitness = S.get_fitness();
    L       = S.get_length();
  }

  public void set_content(Content c)
  {
    copy(c, sol);
  }

  public Content get_content()
  {
    return sol;
  }

}
// END OF CLASS: Solution
