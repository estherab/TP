package tp.pr2.juegos;

import tp.pr2.logica.Ficha;
import tp.pr2.logica.Tablero;

public class MovimientoConecta4 extends MovimientoFamiliaC4 {
	
	/**
	 * Llama a la constructoa de MovimientoFamiliaC4
	 */
	public MovimientoConecta4(int col, Ficha tur) {
		
		super(col, tur); 
	}

	/**
	 * Inserta una ficha en la columna insertada por el jugador y la fila calculada por
	 * ultimaOcupada()
	 * 
	 * @param tab : tablero
	 * @return ok : true si la columna no está llena
	 */
	public boolean ejecutaMovimiento(Tablero tab) {
		boolean ok = false;
		
		fila = ultimaOcupada(tab);
		
		if (fila != -1) {
			tab.ponerFicha(fila, columna, turno);
			ok = true;
		}
		
		return ok;
	}
	
	/**
	 * Después de desapilar un movimiento pone el valor VACIA en la casilla correspondiente
	 * 
	 * @param tab : tablero
	 */
	public void undo(Tablero tab) {
		
		tab.ponerFichaVacia(fila, columna);
	}
}
