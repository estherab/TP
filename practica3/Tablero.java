package tp.pr3.logica;

public class Tablero {

	private Ficha[][] tablero;
	private int ancho;
	private int alto;

	/**
	 * Constructora de Tablero. Crea el array de fichas y lo inicializa con el valor VACIA
	 * 
	 * @param f : dimension inicial de filas
	 * @param c : dimension inicial de columnas
	 */
	public Tablero(int f, int c) {

		this.ancho = c;
		this.alto = f;
		this.tablero = new Ficha[f][c];
		this.inicializaTablero();
	}

	/**
	 * Dibuja todos los elementos del tablero en una cadena. A parte de las
	 * fichas (se dibujan con ficha.toString()) dibuja los bordes laterales y la
	 * linea inferior con los numeros de las columnas
	 * 
	 * @return cadena : contiene todo el tablero (lineas y fichas)
	 */
	public String toString() {
		String cadena = "";

		for (int i = alto - 1; i >= 0; i--) {
			cadena += "|";
			
			for (int j = 0; j <= ancho - 1; j++)
				cadena += tablero[i][j].toString(); 

			cadena += "|" + (i+ 1) + "\n";
		}
		cadena += "+";

		for (int i = 0; i < ancho; i++)
			cadena += "-"; 

		cadena += "+ \n ";

		for (int i = 1; i <= ancho; i++)
			cadena += i; 

		return cadena + "\n";
	}

	/**
	 * Metodo para obtener el ancho del tablero
	 * 
	 * @return ancho : columnas totales del tablero
	 */
	public int getAncho() {

		return this.ancho;
	}

	/**
	 * Metodo para obtener el alto del tablero
	 * 
	 * @return alto : filas totales del tablero
	 */
	public int getAlto() {

		return this.alto;
	}

	/**
	 * Metodo para obtener una ficha del tablero dadas sus coordenadas
	 * 
	 * @param fila
	 * @param col
	 * @return tablero[fila][col] : ficha solicitada
	 */
	public Ficha getFicha(int fila, int col) {

		return tablero[fila][col];
	}

	/**
	 * Dibuja el tablero inicial de la partida con todas las fichas VACIAS.
	 * Tambien se invoca cuando el usuario resetea el juego.
	 */
	public void inicializaTablero() {
		
		for (int i = 0; i < alto; i++)
			for (int j = 0; j < ancho; j++)
				tablero[i][j] = Ficha.VACIA;
	}

	/**
	 * Dada una columna, pone la ficha del jugador actual en la primera fila
	 * libre de la columna
	 * 
	 * @param columna
	 * @param color : jugador actual (BLANCA o NEGRA)
	 */
	public void ponerFicha(int fila, int columna, Ficha color) {

		tablero[fila][columna] = color;
	}

	/**
	 * Dadas las coordenadas de una posici�n ocupada (BLANCA o NEGRA) del tablero,
	 * vacia la casilla
	 * 
	 * @param fila
	 * @param col
	 */
	public void ponerFichaVacia(int fila, int col) {

		tablero[fila][col] = Ficha.VACIA;
	}

	/**
	 * Indica si una posicion dada del tablero es valida, es decir, si se
	 * encuentra entre 0 y el ancho y alto del tablero
	 * 
	 * @param fila
	 * @param col
	 * @return valida : variable boolean (true = casilla valida)
	 */
	public boolean casillaValida(int fila, int col) {
		boolean valida = true;

		if ((fila >= alto) || (fila < 0) || (col >= ancho) || (col < 0))
			valida = false;

		return valida;
	}

	/**
	 * Se le proporcionan las coordenadas de una ficha e indica si esta vacia
	 * 
	 * @param fila
	 * @param col
	 * @return true si la ficha esta vacia
	 */
	public boolean fichaVacia(int fila, int col) {

		return tablero[fila][col] == Ficha.VACIA;
	}
}
