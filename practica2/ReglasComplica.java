package tp.pr2.juegos;

import tp.pr2.logica.Ficha;
import tp.pr2.logica.Tablero;

public class ReglasComplica extends ReglasFamiliaC4 {

	private static final int FILAS_CO = 7;
	private static final int COLUMNAS_CO = 4;

	/**
	 * Crea el tablero con las dimensiones correspondientes
	 * 
	 * @return tab : tablero
	 */
	public Tablero inicializaTablero() {

		Tablero tab = new Tablero(FILAS_CO, COLUMNAS_CO);

		return tab;
	}

	/**
	 * Indica si un movimiento se puede hacer, es decir, si la columna está entre 0 y el ancho del tablero
	 * 
	 * @return true si el movimiento se puede realizar
	 */
	public boolean movimientoValido(Tablero tab, Movimiento mov) {

		return ((mov.columna >= 0) && (mov.columna < COLUMNAS_CO));
	}

	/**
	 * Indica si hay un grupo de cuatro fichas iguales en alguna de las cuatro direcciones
	 * 
	 * @param tab : tablero
	 * @param fila : coordenada de la ficha
	 * @param col : coordenada de la ficha
	 * @return colorGrupo : color del grupo. VACIA si no hay grupo de ninguno de los dos jugadores
	 */
	public Ficha hayGrupo(Tablero tab, int fila, int col) {
		Ficha colorGrupo = Ficha.VACIA;

		if (comprobarVertical(tab, fila, col)
				|| comprobarHorizontal(tab, fila, col)
				|| comprobarDiagonalCreciente(tab, fila, col)
				|| comprobarDiagonalDecreciente(tab, fila, col))

			colorGrupo = tab.getFicha(fila, col);

		return colorGrupo;
	}

	/**
	 * Comprueba si hay un ganador
	 * 
	 *  @param tab : tablero
	 *  @param mov : último movimiento hecho
	 *  @return colorGrupo : color de la ficha de la que hay un grupo
	 */
	public Ficha comprobarGanador(Tablero tab, Movimiento mov) {
		Ficha colorGrupo = Ficha.VACIA;
		int contBlanca = 0, contNegra = 0;

		for (int i = mov.fila; i >= 0; i--) {
			colorGrupo = hayGrupo(tab, i, mov.columna);

			if (colorGrupo == Ficha.BLANCA)
				contBlanca++;
			else if (colorGrupo == Ficha.NEGRA)
				contNegra++;
		}

		if (contBlanca != contNegra) {
			if (contNegra > 0)
				colorGrupo = Ficha.NEGRA;

			else if (contBlanca > 0)
				colorGrupo = Ficha.BLANCA;
		}

		else
			colorGrupo = Ficha.VACIA;
		
		return colorGrupo;
	}

	/**
	 * En Complica nunca hay tablas
	 * 
	 * @param tab : tablero
	 * @param mov : último movimiento hecho
	 */
	public boolean hayTablas(Tablero tab, Movimiento mov) {
		
		return false; 
	}
}
