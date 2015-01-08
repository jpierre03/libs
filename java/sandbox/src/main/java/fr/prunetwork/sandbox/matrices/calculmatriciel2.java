

class calculmatriciel2 {
  public static void main(String[] args) {
//	double A[][] = { {3, 4, 5}, {2, 3, 1}, {8, 9, 0} };
//	double B[][] = { {1, 2, 8}, {0, 7, 6}, {5, 2, 9} };
	double A[][] = { {1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0,0,0,1} };
	double B[][] = { {1, 2, 8, 4}, {0, 7, 6, 3}, {5, 2, 9, 2}, {5, 2, 9, 2} };
	double P[][] = new double[4][4];
/*	
	28.0 	44.0 	 93.0
	 7.0 	27.0 	 43.0
	 8.0 	79.0 	118.0
*/
	for(int j=0;j<4;j++) {
		for(int i=0;i<4;i++) {
			ProduitPoint point = new ProduitPoint(A,B,P,j,i);
			point.start();
		}
	}
	while ((Thread.currentThread().activeCount())>1);
	System.out.println();
	for(int j=0;j<4;j++) {
		for(int i=0;i<4;i++) {
			System.out.print(P[j][i]+" ");
		}
		System.out.println();
	}
  }
}
