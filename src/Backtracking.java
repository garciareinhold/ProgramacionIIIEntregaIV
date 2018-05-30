
public class Backtracking {

	private int[][] matriz;
	private boolean[] disponibles;
	private int valorMax;
	private int filCol;
	private int valorSuma;
	private boolean haySolucion;
	private int [] sumaCol;
	private int [] sumaFil;
	
	public Backtracking(int n, int k, int suma) {
		sumaCol= new int[n];
		sumaFil= new int[n];
		matriz = new int[n][n];
		valorMax = k+1;
		filCol = n;
		valorSuma = suma;
		haySolucion=false;
		disponibles= new boolean [k+1];
		llenarMatriz();
		llenarDisponibles();
	}
	
	private void llenarDisponibles() {
		//SETEO TODOS LOS POSIBLES VALORES EN TRUE, YA QUE AL PRINCIPIO ESTAN TODOS DISPONIBLES
		for (int i = 0; i < disponibles.length; i++) {
			if(i<this.valorSuma) {
				disponibles[i]=true;		
			}
		}
	}

	private void llenarMatriz() {
		//LLENO LA MATRIZ CON NÚMEROS INVÁLIDOS
		for (int i = 0; i < filCol; i++) {
			for (int j = 0; j < filCol; j++) {
				this.matriz[i][j] = -1;
			}
		}
	}
	
	public void jugar() {
		back(0, 0);
	}
	
	private void back(int row, int col) {

		int i=0;
		
		while(i<this.valorMax && !this.haySolucion) {
			
			if(estaDisponible(i)) {
				
				matriz[row][col]=i;
				disponibles[i]=false;
				incrementar(row, col, i);
				
				if (!hayPoda(row, col)) {
					
					if (isFull()) {
						imprimirSolucion();
						this.haySolucion=true; //BORRAR ESTA LINEA PARA QUE DE TODAS LAS SOLUCIONES
					}
					
					else {
						if (col != this.filCol - 1) {
							back(row, col + 1);// LLAMADA RECURSIVA CON LA SGTE COLUMNA
						} else if (row != this.filCol - 1) {
							back(row + 1, 0);//LLAMADA RECURSIVA CON LA SGTE FILA, COMIENZA EN POS 0 LA COLUMNA
						}
					}
				}

					decrementar(row, col, i);
					matriz[row][col] = -1;
					disponibles[i] = true;
				
			}
			i++;
			
		}
	}

	private boolean hayPoda(int row, int col) {
		boolean retorno=false;
		
		//CONDICIONES PODA
		if ((this.sumaCol[col]> this.valorSuma) || (this.sumaFil[row]> this.valorSuma)) {
			retorno= true;
		}
		else if((col== this.filCol-1)&&(this.sumaFil[row]<this.valorSuma)) {
			retorno= true;
		}
		else if((row== this.filCol-1)&&(this.sumaCol[col]<this.valorSuma)) {
			retorno= true;
		}
		
		return retorno;
	}

	private void decrementar(int row, int col, int valor) {
		//DECREMENTO LO PREVIAMENTE SUMADO EN LOS ARREGLOS CORRESPONDIENTES
		this.sumaCol[col]-=valor;
		this.sumaFil[row]-=valor;
	}

	private void incrementar(int row, int col, int valor) {
		//INCREMENTO EN LOS ARREGLOS CORRESPONDIENTES LOS VALORES PARCIALES DEL CUADRADO
		this.sumaCol[col]+=valor;
		this.sumaFil[row]+=valor;

	}

	private void imprimirSolucion() {

		System.out.println("Solucion:");
		for (int i = 0; i < filCol; i++) {
			for (int j = 0; j < filCol; j++) {
				System.out.print(matriz[i][j] + " ");
			}
			System.out.println(" ");
		}
	}
	
	private boolean isFull() {
		//DEVUELVE SI YA ESTA LLENO EL CUADRADO MÁGICO
		for (int i = 0; i < filCol; i++) {
			for (int j = 0; j < filCol; j++) {
				if (matriz[i][j] == -1)
					return false;
			}
		}
		return true;
	}
	
	private boolean estaDisponible(int i) {
		//ME FIJO SI YA PUSE EL NÚMERO
		if (disponibles[i] == true)
			return true;
		else
			return false;
	}
}
