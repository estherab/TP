package tp.pr3.juegos;

import tp.pr3.logica.Tablero;
import tp.pr3.movimientos.Movimiento;
import tp.pr3.movimientos.MovimientoInvalido;

public class ReglasConecta4 extends ReglasFamiliaC4 {

	public static final int FILAS_C4 = 6; 
	public static final int COLUMNAS_C4 = 7; 
	
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
	public void  movimientoValido(Tablero tab, Movimiento mov) throws MovimientoInvalido {

		if ((mov.getColumna() < 0) || (mov.getColumna() >= COLUMNAS_C4))
			throw new MovimientoInvalido("PosiciÃ³n incorrecta. Las dimensiones son de: " + FILAS_C4 + " x " + COLUMNAS_C4);
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
