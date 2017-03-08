package tp.pr4.juegos;

import tp.pr4.logica.Tablero;
import tp.pr4.movimientos.Movimiento;
import tp.pr4.movimientos.MovimientoInvalido;

public class ReglasGravity extends ReglasFamiliaC4 {

	private int filas_gr; 
	private int columnas_gr;

	/**
	 * Constructor con argumentos para construir el tablero de Gravity con las
	 * dimensiones correspondientes. Si hay que dibujar el tablero por defecto,
	 * los argumentos ser�n 10
	 * 
	 * @param fila , columna : dimensiones del tablero
	 */
	public ReglasGravity(int fila, int columna) {
		filas_gr = fila;
		columnas_gr = columna;
	}

	/**
	 * Crea el tablero vac�o con las dimensiones por defecto (10 x 10)
	 * 
	 * @return tab : tablero
	 */
	public Tablero inicializaTablero() {

		Tablero tab = new Tablero(filas_gr, columnas_gr);

		return tab;
	}

	/**
	 * Crea el tablero con las dimensiones introducidas por el usuario
	 * 
	 * @param filas
	 *            , columnas : coordenadas del tablero
	 * @return tab : tablero
	 */
	public Tablero inicializaTablero(int filas, int columnas) {

		Tablero tab = new Tablero(filas, columnas);

		return tab;
	}
	
	/**
	 * Indica si un movimiento de Gravity es v�lido. Comprueba que las coordenadas se encuentren dentro 
	 * de las dimensiones del tablero y que la posici�n est� vac�a. Lanza una excepci�n si el movimiento no es v�lido
	 * @param tab : tablero
	 * @param mov : movimiento a ejecutar 
	 */

	public void movimientoValido(Tablero tab, Movimiento mov) throws MovimientoInvalido {

		if ((mov.getColumna() < 0) || (mov.getColumna() >= columnas_gr) || (mov.getFila() < 0) || (mov.getFila() >= filas_gr))
			throw new MovimientoInvalido("Posicion incorrecta. Las dimensiones son de: " + filas_gr + " x " + columnas_gr);

		else if (!tab.fichaVacia(mov.getFila(), mov.getColumna()))
			throw new MovimientoInvalido("Casilla ocupada.");
	}

	/**
	 * Indica si el tablero est� llemo
	 * @param tab : tablero
	 * @return true si el tablero est� lleno
	 */
	public boolean tableroLleno(Tablero tab) {
		boolean lleno = true;
		int fila = 0, col = 0;

		// Se sale del bucle en cuanto encuentre una ficha vacia
		while (fila < tab.getFilas() && lleno) {
			col = 0;
			while (col < tab.getColumnas() && lleno) {
				if (tab.fichaVacia(fila, col))
					lleno = false;
				else
					col++;
			}

			fila++;
		}

		return lleno;
	}

	/**
	 * Indica si la partida ha terminado en tablas, es decir, el tablero est� lleno y no hay 4 en raya para ning�n jugador
	 * @param tab : tablero
	 * @param mov : movimiento
	 * @return true si la partida ha terminado en tablas
	 */
	public boolean hayTablas(Tablero tab, Movimiento mov) {
		boolean hayTablas = false;

		if (tableroLleno(tab))
			hayTablas = true;

		return hayTablas;
	}
	
	protected boolean comprobarVertical(Tablero tab, int fila, int col) {
		int coincidentes = 1, f = fila, alto = tab.getFilas();
		
		// Comrpueba hacia arriba
		while ((f < alto - 1) && (tab.getCasilla(f + 1, col) == tab.getCasilla(f, col)) && (coincidentes < 4)) {
			f++;
			coincidentes++;
		}
			f = fila;
		
		// Comprueba hacia abajo
		while ((f > 0) && (tab.getCasilla(f - 1, col) == tab.getCasilla(f, col)) && (coincidentes < 4)) {
			coincidentes++;
			f--;
		}
		
		return (coincidentes == 4);
	}	
}
