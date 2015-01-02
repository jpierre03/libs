
public class ProduitPoint extends Thread  {

	private double P[][];
	  private double A[][], B[][];
	  private int j,i;

	  public ProduitPoint(double m1[][],double m2[][],double pr[][],int j,int i) {
		A = m1;
		B = m2;
		P = pr;
		this.j = j;
		this.i = i;
	  }

	  public void run() {
		P[j][i]=0;
		for(int a=0;a<3;a++) {
			P[j][i] += A[j][a] * B[a][i];
		}
//		System.out.println("Resultat["+j+","+i+"] = "+P[j][i]+" ");
		System.out.println("["+j+" , "+i+"] = "+P[j][i]+" ");
	  }

}
