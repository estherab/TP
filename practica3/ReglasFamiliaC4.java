package tp.pr3.juegos;

import tp.pr3.logica.Ficha;
import tp.pr3.logica.Tablero;
import tp.pr3.movimientos.Movimiento;

public abstract class ReglasFamiliaC4 extends ReglasJuego {
	
	/**
	 * Comprueba si hay un grupo de 4 fichas en posición vertical a partir de la ficha dada
	 * 
	 * @param tab : tablero
	 * @param fila : coordenada de la ficha
	 * @param col : coordenada de la ficha
	 * @return true si hay un grupo de 4 fichas iguales
	 */
	protected boolean comprobarVertical(Tablero tab, int fila, int col) {
		int coincidentes = 1;
		
		while ((fila > 0) && (tab.getFicha(fila - 1, col) == tab.getFicha(fila, col)) && (coincidentes < 4)) {
			coincidentes++;
			fila--;
		}

		return (coincidentes == 4);
	}

	/**
	 * Comprueba si hay un grupo de 4 fichas iguales en posición horizontal partiendo de la ficha dada
	 * 
	 * @param tab : tablero
	 * @param fila : coordenada de la ficha
	 * @param col : coordenada de la ficha
	 * @return true si hay un grupo de fichas
	 */
	protected boolean comprobarHorizontal(Tablero tab, int fila, int col) {
		int coincidentes = 1, ancho = tab.getAncho(), c = col;

		while ((c < ancho - 1) && (tab.getFicha(fila, c + 1) == tab.getFicha(fila, c)) && (coincidentes < 4)) {
			coincidentes++;
			c++;
		}
		c = col; 

		while ((c > 0) && (tab.getFicha(fila, c - 1) == tab.getFicha(fila, c))
				&& (coincidentes < 4)) {
			coincidentes++;
			c--;
		}

		return (coincidentes == 4);
	}

	/**
	 * Comprueba si hay un grupo de 4 fichas iguales en la diagonal creciente a partir
	 * de una ficha dada
	 * 
	 * @param tab : tablero
	 * @param fila : coordenada de la ficha
	 * @param col : coordenada de la ficha
	 * @return true si hay un grupo de 4 fichas iguales
	 */
	protected boolean comprobarDiagonalCreciente(Tablero tab, int fila, int col) {
		int f = fila, c = col, alto = tab.getAlto(), ancho = tab.getAncho(), coincidentes = 1;

		// DIAGONAL SUPERIOR DERECHA
		while ((f < alto - 1) && (c < ancho - 1)
				&& (tab.getFicha(f + 1, c + 1) == tab.getFicha(f, c))
				&& (coincidentes < 4)) {
			coincidentes++;
			f++;
			c++;
		}
		f = fila; 
		c = col;

		// DIAGONAL INFERIOR IZQUIERDA
		while ((f > 0) && (c > 0) && (tab.getFicha(f - 1, c - 1) == tab.getFicha(f, c)) && (coincidentes < 4)) {
			coincidentes++; 
			f--;
			c--;
		}

		return (coincidentes == 4);
	}

	/**
	 * Comprueba si hay un grupo de 4 fichas iguales en dirección diagonal decreciente 
	 * a partir de una ficha dada
	 * 
	 * @param tab : tablero
	 * @param fila : coordenada de la ficha
	 * @param col : coordenada de la ficha
	 * @return true si hay un grupo de 4 fichas
	 */
	protected boolean comprobarDiagonalDecreciente(Tablero tab, int fila, int col) {
		int f = fila, c = col, alto = tab.getAlto(), ancho = tab.getAncho(), coincidentes = 1;

		// DIAGONAL SUPERIOR IZQUIERDA
		while ((f < alto - 1) && (c > 0) && (tab.getFicha(f + 1, c - 1) == tab.getFicha(f, c)) && (coincidentes < 4)) {
			coincidentes++;
			f++;
			c--;
		}

		f = fila; 
		c = col;

		// DIAGONAL INFERIOR DERECHA
		while ((f > 0) && (c < ancho - 1) && (tab.getFicha(f - 1, c + 1) == tab.getFicha(f, c)) && (coincidentes < 4)) {
			coincidentes++; 
			f--;
			c++;
		}

		return (coincidentes == 4);
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

		if (comprobarVertical(tab, mov.getFila(), mov.getColumna())
				|| comprobarHorizontal(tab, mov.getFila(), mov.getColumna())
				|| comprobarDiagonalCreciente(tab, mov.getFila(), mov.getColumna())
				|| comprobarDiagonalDecreciente(tab, mov.getFila(), mov.getColumna())) {
			ganador = true;
		}

		if (ganador)
			return mov.getJugador();

		else
			return Ficha.VACIA;
	}
	
	/**
	 * Cambia el turno del jugador para la siguiente jugada
	 * 
	 * @param tab : tablero
	 * @param turno : turno actual
	 * @return siguiente : siguiente turno
	 */
	public Ficha SiguienteTurno(Tablero tab, Ficha turno) {
		Ficha siguiente;

		if (turno == Ficha.BLANCA)
			siguiente = Ficha.NEGRA;
		else
			siguiente = Ficha.BLANCA;

		return siguiente;
	}
}
