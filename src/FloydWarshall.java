//https://rosettacode.org/wiki/Floyd-Warshall_algorithm

import static java.lang.String.format;
import java.util.Arrays;


class Floyd{
	public void floydWarshall(int[][] pesos, int numVertices) {
 
        double[][] distancia = new double[numVertices][numVertices];
        for (double[] fila : distancia)
            Arrays.fill(fila, Double.POSITIVE_INFINITY);
 
        for (int[] w : pesos)
            distancia[w[0] - 1][w[1] - 1] = w[2];
 
        int[][] siguiente = new int[numVertices][numVertices];
        for (int i = 0; i < siguiente.length; i++) {
            for (int j = 0; j < siguiente.length; j++)
                if (i != j)
                    siguiente[i][j] = j + 1;
        }
        for (int k = 0; k < numVertices; k++)
            for (int i = 0; i < numVertices; i++)
                for (int j = 0; j < numVertices; j++)
                    if (distancia[i][k] + distancia[k][j] < distancia[i][j]) {
                        distancia[i][j] = distancia[i][k] + distancia[k][j];
                        siguiente[i][j] = siguiente[i][k];
                    }
 
        imprimirResultado(distancia, siguiente);
	}
	public void imprimirResultado(double[][] distancia, int[][] siguiente) {
        System.out.println("par     distancia    camino");
        for (int i = 0; i < siguiente.length; i++) {
            for (int j = 0; j < siguiente.length; j++) {
                if (i != j) {
                    int u = i + 1;
                    int v = j + 1;
                    String camino = format("%d -> %d    %2d     %s", u, v,
                            (int) distancia[i][j], u);
                    do {
                        u = siguiente[u - 1][v - 1];
                        camino += " -> " + u;
                    } while (u != v);
                    System.out.println(camino);
                }
            }
        }
	}
}//Class Floyd
public class FloydWarshall {

	public static void main(String[] args) {
		
		 int[][] pesos = {{1, 3, -2}, {2, 1, 4}, {2, 3, 3}, {3, 4, 2}, {4, 2, -1}};
	        int numVertices = 4;
	 Floyd floyd=new Floyd();
	 floyd.floydWarshall(pesos, numVertices);

	}

}