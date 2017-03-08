package tp.pr2.juegos;

import tp.pr2.logica.Ficha;
import tp.pr2.logica.Tablero;

public abstract class MovimientoFamiliaC4 extends Movimiento {

	/**
	 * LLama a la constructora de Movimiento
	 */
	public MovimientoFamiliaC4(int col, Ficha tur) {
		super(col, tur);
	}
	
	/**
	 * Calcula la última fila ocupada de la columna actual. En caso de que 
	 * la columna esté llena, devuelve el valor -1
	 * 
	 * @param tab : tablero
	 * @return fila : última fila ocupada de la columna dada
	 */
	public int ultimaOcupada(Tablero tab) {
		int fila = 0;
		boolean encontrado = false;

		while ((fila < tab.getAlto()) && !encontrado) {
			if (tab.fichaVacia(fila, columna))
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
	 * Devuelve el color del jugador actual
	 * 
	 * @return turno : BLANCA o NEGRA
	 */
	public Ficha getJugador() {
		
		return turno;
	}
}

