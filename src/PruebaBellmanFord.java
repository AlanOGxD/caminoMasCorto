//http://jorgep.blogspot.com/2010/10/ruta-mas-corta-solucion-por-el.html

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

class BellmanFord {
	LinkedList<Aristas> aristas;
    float etiquetas[];
    int predecesor[];
    int numeroVertices, totalAristas, nodoOrigen;
    final int INFINITY = 999;
 
    private static class Aristas {
 
        int origen, destino;
        float coste;
 
        public Aristas(int a, int b, float c) {
            origen = a;
            destino = b;
            coste = c;
        }
 
        @Override
        public String toString() {
            return "Aristas{" + "origen=" + origen + ", destino=" + destino + ", coste=" + coste + '}';
        }
    }
 
    public BellmanFord() throws IOException {
        float item;
        aristas = new LinkedList<Aristas>();
        DataInputStream in = new DataInputStream(System.in);
        System.out.print("Introduce numero de vertices ");
        numeroVertices = Integer.parseInt(in.readLine());
        System.out.println("Matriz de costes");
        for (int i = 0; i < numeroVertices; i++) {
            for (int j = 0; j < numeroVertices; j++) {
                if (i != j) {
                    System.out.println("Introduce coste del nodo " + (i + 1) + " al nodo " + (j + 1));
                    item = Float.parseFloat(in.readLine());
                    if (item != 0) {
                        aristas.add(new Aristas(i, j, item));
                    }
                }
            }
        }
        totalAristas = aristas.size();
        etiquetas = new float[numeroVertices];
        predecesor = new int[numeroVertices];
        System.out.print("Introduce el vertice origen");
        nodoOrigen = Integer.parseInt(in.readLine()) - 1;
    }
 
    public void relajoArista() {
        int i, j;
        for (i = 0; i < numeroVertices; ++i) {
            etiquetas[i] = INFINITY;
        }
        etiquetas[nodoOrigen] = 0;
        for (i = 0; i < numeroVertices - 1; ++i) {
            for (j = 0; j < totalAristas; ++j) {
                System.out.println(aristas.get(j));
                if (etiquetas[aristas.get(j).origen] + aristas.get(j).coste < etiquetas[aristas.get(j).destino]) {
                    etiquetas[aristas.get(j).destino] = etiquetas[aristas.get(j).origen] + aristas.get(j).coste;
                    predecesor[aristas.get(j).destino] = aristas.get(j).origen;
                }
            }
            for (int p = 0; etiquetas.length < p; p++) {
                System.out.println("\t" + etiquetas[p]);
            }
        }
    }
 
    public boolean ciclo() {
        int j;
        for (j = 0; j < totalAristas; ++j) {
            if (etiquetas[aristas.get(j).origen] + aristas.get(j).coste < etiquetas[aristas.get(j).destino]) {
                return false;
            }
        }
        return true;
    }
}

final class Grafo{
	private int nnodos;
	private int nodos[][][];
	private char nombres[];
	Grafo(int n) {
		this.nnodos = n;
		this.nodos = new int[nnodos][nnodos][2];
		this.nombres = new char[nnodos];
	}
	public void ingresarArco(int n1, int n2, int peso) {
		this.nodos[n1][n2][0] = peso;
		this.nodos[n2][n1][0] = peso;
		this.nodos[n1][n2][1] = n1;
		this.nodos[n2][n1][1] = n2;
	}
	public void ingresarNombre(int nodo, char letra) {
		this.nombres[nodo] = letra;
	}
	public void calcular() {
		int i, j, k;
		for (i = 0; i < this.nnodos; i++) {
			for (j = 0; j < this.nnodos; j++) {
				for (k = 0; k < this.nnodos; k++) {
					if (this.nodos[i][k][0] + this.nodos[k][j][0] < this.nodos[i][j][0]) {
						this.nodos[i][j][0] = this.nodos[i][k][0]
								+ this.nodos[k][j][0];
						this.nodos[i][j][1] = k;
					}
				}
			}
		}
	}
	public int pesominimo(int org, int des) {
		return this.nodos[org][des][0];
	}
	public String caminocorto(int org, int des) {
		String cam;
		if (org == des) {
			cam = "->" + nombres[org];
		} else {
			cam = caminocorto(org, this.nodos[org][des][1]) + "->"
					+ nombres[des];
		}
		return cam;
	}
	public char getNombre(int nodo) {
		return this.nombres[nodo];
	}
	public static void main(String args[]) throws IOException {
		Grafo g;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String temp;
		int res;
		System.out.println("Entre el numero de nodos del grafo:\n");
		temp = br.readLine();
		res = Integer.parseInt(temp);
		g = new Grafo(res);
		for (int i = 0; i < res; i++) {
			System.out.println("Cual es el nombre del nodo [" + (i + 1)
					+ "]:\n");
			temp = br.readLine();
			g.ingresarNombre(i, temp.charAt(0));
		}
		for (int i = 0; i < res; i++) {
			for (int j = 0; j < res; j++) {
				if (i < j) {
					System.out.println("El nodo " + g.getNombre(i)
							+ " esta conectado con el nodo " + g.getNombre(j)
							+ " (s/n)\n");
					temp = br.readLine();
					if (temp.charAt(0) == 's') {
						int peso;
						System.out.println("Cual es el peso del arco:\n");
						temp = br.readLine();
						peso = Integer.parseInt(temp);
						g.ingresarArco(i, j, peso);
					} else {
						g.ingresarArco(i, j, 10000);
					}
				}
			}
		}
		g.calcular();
		for (int i = 0; i < res; i++) {
			for (int j = 0; j < res; j++) {
				if (i > j) {
					System.out.println("El camino mas corto entre los nodos:"
							+ g.getNombre(i) + "-" + g.getNombre(j) + " es: \n"
							+ g.caminocorto(i, j) + " y su peso es: "
							+ g.pesominimo(i, j));
				}
			}
		}
	}
}

public class PruebaBellmanFord {

	public static void main(String[] args) {
		BellmanFord bellman;
		try {
			bellman = new BellmanFord();
			bellman.relajoArista();
	        if (bellman.ciclo()) {
	            for (int i = 0; i < bellman.numeroVertices; i++) {
	                System.out.println("Coste desde " + bellman.nodoOrigen + " a " + (i + 1) + " =>" + bellman.etiquetas[i]);
	            }
	            for (int i = 0; i < bellman.numeroVertices; i++) {
	                System.out.println("El predecesor de " + (i + 1) + " es " + (bellman.predecesor[i] + 1));
	            }
	        } else {
	            System.out.println("Hay un ciclo negativo");
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}