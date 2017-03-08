package tp.pr2.juegos;

import tp.pr2.logica.Ficha;
import tp.pr2.logica.Tablero;

public class ReglasConecta4 extends ReglasFamiliaC4 {

	private static final int FILAS_C4 = 6; 
	private static final int COLUMNAS_C4 = 7; 
	
	/**
	 * Crea el array del tablero con las dimensiones de Conecta 4
	 * 
	 * @return tab : tablero
	 */
	public Tablero inicializaTablero() {

		Tablero tab = new Tablero(FILAS_C4, COLUMNAS_C4);

		return tab;
	}

	/**
	 * Indica si un movimiento se puede hacer, es decir, si la columna está entre 0 y el 
	 * ancho del tablero
	 * 
	 * @param tab : tablero
	 * @param mov : último movimiento hecho
	 * @return true si se puede hacer el movimiento
	 */
	public boolean movimientoValido(Tablero tab, Movimiento mov) {

		return ((mov.columna >= 0) && (mov.columna < COLUMNAS_C4));
	}

	/**
	 * Comprueba si, tras realizar el último movimiento, hay un ganador
	 * 
	 * @param tab : tablero
	 * @param mov : último movimiento
	 * @return color del ganador. Si no hay ganador, devuelve VACIA
	 */
	public Ficha comprobarGanador(Tablero tab, Movimiento mov) {

		boolean ganador = false;

		if (comprobarVertical(tab, mov.fila, mov.columna)
				|| comprobarHorizontal(tab, mov.fila, mov.columna)
				|| comprobarDiagonalCreciente(tab, mov.fila, mov.columna)
				|| comprobarDiagonalDecreciente(tab, mov.fila, mov.columna)) {
			ganador = true;
		}

		if (ganador)
			return mov.turno;

		else
			return Ficha.VACIA;
	}

	/**
	 * Indica si el tablero está lleno. Para comprobarlo basta con mirar la última fila
	 * 
	 * @param tab : tablero
	 * @return true si el tablero está lleno
	 */
	public boolean tableroLleno(Tablero tab) {
		int i = tab.getAlto() - 1, j = 0;
		boolean lleno = true;

		while (j < tab.getAncho() && lleno) {
			if (tab.fichaVacia(i, j))
				lleno = false;

			else
				j++;
		}

		return lleno;
	}

	/**
	 * Indica si la partida ha terminado en tablas
	 * 
	 * @param tab : tablero
	 * @param mov : último movimiento
	 * @return hayTablas : true si la partida acaba en tablas
	 */
	public boolean hayTablas(Tablero tab, Movimiento mov) {
		boolean hayTablas = false;

		if (tableroLleno(tab))
			hayTablas = true;

		return hayTablas;
	}
}
