package sa.ssSA;
import java.util.Random;
import java.io.* ;

public class Content implements Serializable
{
  private byte items[];		// Allele vector
  private int  L;			// Length of the item vector


  // CONSTRUCTOR - FILL UP THE CONTENTS
  public Content(int length)
  {

    items = new byte[length];
    L = length;
    for (int i=0; i<length; i++)
    if(Exe.rand.nextDouble()>0.5)          // Returns values in [0..1]
    items[i] = 1;
    else
    items[i] = 0;
  }

  public void set_item(int index, byte value)
  {
    items[index] = value;
  }

  public byte get_item(int index)
  {
    return items[index];
  }

  public void print()
  {
    for(int i=0; i<L; i++)
    System.out.print(items[i]);
  }

}
// END OF CLASS: Content