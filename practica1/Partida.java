package tp.pr1;

public class Partida {
	private Tablero tablero;
	private Ficha turno;
	private boolean terminada;
	private Ficha ganador;
	private Pila pila;

	public static final int FILAS = 6;
	public static final int COLUMNAS = 6;

	/**
	 * Constructora de Partida. Crea el tablero, inicializa la pila y asigna
	 * BLANCA como turno inicial
	 * 
	 * @param f: dimensión inicial de filas (ancho)
	 * @param c: dimensión inicial de columnas (alto)
	 */
	public Partida() {
		pila = new Pila();
		tablero = new Tablero(FILAS, COLUMNAS);
		turno = Ficha.BLANCA; // Inicialización del turno
	}

	/**
	 * Método que a través de tablero.toString() devuelve todo del tablero en
	 * una sola cadena para mostrarlo en Controlador
	 * 
	 * @return cadena : contiene todo el tablero
	 */
	public String toString() {
		String cadena = "";

		cadena = tablero.toString();

		return cadena;
	}

	/**
	 * Método que indica si la partida se ha terminado, ya sea porque se ha
	 * formado un grupo de cuatro fichas iguales o porque el tablero se ha
	 * llenado y no hay un ganador
	 * 
	 * @return terminada : true si la partida ha terminado
	 */
	public boolean getTerminada() {

		return terminada;
	}

	/**
	 * Método para saber de quien es el turno actual. El siguiente turno será
	 * del otro jugador
	 * 
	 * @return turno
	 */
	public Ficha getTurno() {

		return turno;
	}

	/**
	 * Método para obtener el ganador. Si la partida termina en tablas, el
	 * ganador será VACIA
	 * 
	 * @return ganador : BLANCA, NEGRA ó VACIA
	 */
	public Ficha getGanador() {

		return ganador;
	}

	/**
	 * Indica si el tablero está lleno, es decir, si todas las casillas tienen
	 * el valor BLANCA ó NEGRA Recorre el tablero hasta que encuentre una
	 * casilla VACIA
	 * 
	 * @return lleno : variable booleana con valor false si hay al menos una
	 *         casilla VACÍA en el tablero
	 */
	public boolean tableroLleno() {
		int i = tablero.getAlto() - 1, j = 0;
		boolean lleno = true;

		while (j < tablero.getAncho() && lleno) {
			if (tablero.fichaVacia(i, j))
				lleno = false;

			j++;
		}

		return lleno;
	}

	/**
	 * Devuelve la última fila con ficha (no VACÍA) de la columna dada, para
	 * insertar una nueva ficha encima
	 * 
	 * @param col
	 *            : columna en la que se va a insertar una ficha
	 * @return fila : última fila ocupada de la columna
	 */
	public int ultimaOcupada(int col) {
		int fila = 0;
		boolean encontrado = false;

		while ((fila < tablero.getAlto()) && !encontrado) {
			if (tablero.fichaVacia(fila, col))
				encontrado = true;
			else
				fila++;
		}

		// Si devuelve un -1 quiere decir que toda la columna estaba llena,
		// entonces no se puede insertar ninguna ficha
		if (!encontrado)
			fila = -1;

		return fila;
	}

	/**
	 * Resetea el tablero. Pone todas las casillas VACIAS, el turno es de las
	 * BLANCAS, y vacía la pila
	 */
	public void reset() {
		this.tablero.inicializaTablero();
		turno = Ficha.BLANCA;
		pila.resetPila();
	}

	/**
	 * Deshace el último movimiento en caso de que la pila tenga elementos y
	 * pone la correspondiente ficha VACIA. Por último, borra el movimiento de
	 * la pila y cambia el turno
	 * 
	 * @return ok : true si se ha podido desapilar el movimiento
	 */
	public boolean deshacer() {
		boolean ok = false;
		int col, fila;

		if (!pila.estaVacia()) {
			col = pila.obtenerUltimoMov();
			fila = ultimaOcupada(col) - 1;

			if (fila == -2)
				fila = tablero.getAncho() - 1;

			tablero.ponerFichaVacia(fila, col);
			pila.desapilar();
			ok = true;
			turno = cambiarTurno();
		} // Si la pila está vacía se mostrará un mensaje de error

		return ok;
	}

	/**
	 * Cambia el turno. Devuelve el turno contrario al obtenido de getTurno()
	 * 
	 * @return turno siguiente
	 */
	public Ficha cambiarTurno() {

		if (getTurno() == Ficha.BLANCA)
			turno = Ficha.NEGRA;
		else
			turno = Ficha.BLANCA;

		return turno;
	}

	/**
	 * Informa sobre el ganador de la partida
	 * 
	 * @return ganador : color de la Ficha ganadora
	 */
	public Ficha colorFichaGanador() {

		if (tableroLleno()) 
			ganador = Ficha.VACIA;
		else
			ganador = getTurno();

		return ganador;
	}

	/**
	 * Introduce una ficha del turno actual en la primera casilla libre de la
	 * columna dada. Por último, apila el movimiento y comprueba si hay cuatro
	 * fichas iguales consecutivas
	 * 
	 * @param color
	 *            : turno actual
	 * @param columna
	 *            : en la que introducir la ficha
	 * @return ok : true si se ha insertado la ficha
	 */
	public boolean ejecutaMovimiento(Ficha color, int columna) {
		boolean ok = false;
		int ancho = tablero.getAncho();

		if ((columna >= 0) && (columna < ancho)) {
			int fila = ultimaOcupada(columna); 
			if (fila != -1) {
				tablero.ponerFicha(fila, columna, color); 
														
				pila.apilar(columna);
				ok = true;

				if (!comprobar4enRaya(fila, columna) && !tableroLleno())
					turno = cambiarTurno();

				else { // Si hay cuatro en raya se acaba la partida
					terminada = true;
					ganador = colorFichaGanador();
				}
			}
		}

		return ok;
	}

	/**
	 * Comprueba si hay cuatro fichas iguales consecutivas en posición vertical.
	 * Se le proporciona la fila y la columna de la última ficha insertada
	 * 
	 * @param fila
	 * @param col
	 * @return true si hay un grupo vertical de cuatro fichas
	 */
	public boolean comprobarVertical(int fila, int col) {
		int coincidentes = 1;
		// Sólo compruba hacia abajo
		while ((fila > 0)
				&& (this.tablero.getFicha(fila - 1, col) == this.tablero
						.getFicha(fila, col)) && (coincidentes < 4)) {
			coincidentes++;
			fila--;
		}

		return (coincidentes == 4);
	}

	/**
	 * Comprueba si hay cuatro fichas iguales consecutivas en posición
	 * horizontal, tanto hacia la izquierda como hacia la derecha
	 * 
	 * @param fila : coordenadas de la última ficha insertada
	 * @param col
	 * @return true : si hay un grupo de cuatro fichas en horizontal
	 */
	public boolean comprobarHorizontal(int fila, int col) {
		int coincidentes = 1, ancho = tablero.getAncho(), c = col;

		while ((c < ancho - 1)
				&& (this.tablero.getFicha(fila, c + 1) == this.tablero
						.getFicha(fila, c)) && (coincidentes < 4)) {
			coincidentes++;
			c++;
		}
		c = col; /
		while ((c > 0)
				&& (this.tablero.getFicha(fila, c - 1) == this.tablero
						.getFicha(fila, c)) && (coincidentes < 4)) {
			coincidentes++; 
			c--;
		}

		return (coincidentes == 4);
	}

	/**
	 * Recorre la diagonal superior derecha e inferior izquierda partiendo desde
	 * la ficha insertada buscando un grupo diagonal de cuatro fichas.
	 * 
	 * @param fila
	 *            : coordenadas de la ficha insertada
	 * @param col
	 * @return true si hay grupo de cuatro fichas en posición diagonal
	 */
	public boolean comprobarDiagonalCreciente(int fila, int col) {
		int f = fila, c = col, alto = tablero.getAlto(), ancho = tablero
				.getAncho(), coincidentes = 1;

		// DIAGONAL SUPERIOR DERECHA
		while ((f < alto - 1)
				&& (c < ancho - 1)
				&& (this.tablero.getFicha(f + 1, c + 1) == this.tablero
						.getFicha(f, c)) && (coincidentes < 4)) {
			coincidentes++;
			f++;
			c++;
		}
		f = fila; 
		c = col;

		// DIAGONAL INFERIOR IZQUIERDA
		while ((f > 0)
				&& (c > 0)
				&& (this.tablero.getFicha(f - 1, c - 1) == this.tablero
						.getFicha(f, c)) && (coincidentes < 4)) {
			coincidentes++; 
			f--;
			c--;
		}

		return (coincidentes == 4);
	}

	/**
	 * Busca un grupo de cuatro fichas iguales partiendo desde la ficha
	 * insertada en su diagonal superior izquierda e inferior derecha
	 * 
	 * @param fila
	 *            : coordenadas de la última ficha insertada
	 * @param col
	 * @return true si hay cuatro fichas iguales en posición diagonal
	 */
	public boolean comprobarDiagonalDecreciente(int fila, int col) {
		int f = fila, c = col, alto = tablero.getAlto(), ancho = tablero
				.getAncho(), coincidentes = 1;

		// DIAGONAL SUPERIOR IZQUIERDA
		while ((f < alto - 1)
				&& (c > 0)
				&& (this.tablero.getFicha(f + 1, c - 1) == this.tablero
						.getFicha(f, c)) && (coincidentes < 4)) {
			coincidentes++;
			f++;
			c--;
		}

		f = fila; 
		c = col;

		// DIAGONAL INFERIOR DERECHA
		while ((f > 0)
				&& (c < ancho - 1)
				&& (this.tablero.getFicha(f - 1, c + 1) == this.tablero
						.getFicha(f, c)) && (coincidentes < 4)) {
			coincidentes++;
			f--;
			c++;
		}

		return (coincidentes == 4);
	}

	/**
	 * Método que indica si hay grupo en cualquiera de las direcciones
	 * examinadas
	 * 
	 * @param fila
	 *            : coordenadas de la última ficha introducida
	 * @param col
	 * @return true si hay un grupo de cuatro fichas iguales en cualquier
	 *         direccion
	 */
	public boolean comprobar4enRaya(int fila, int col) {

		return (comprobarVertical(fila, col) || comprobarHorizontal(fila, col)
				|| comprobarDiagonalCreciente(fila, col) || comprobarDiagonalDecreciente(
					fila, col));
	}
}
