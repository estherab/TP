package tp.pr2.juegos;

import tp.pr2.logica.Ficha;
import tp.pr2.logica.Tablero;

public abstract class Movimiento {
	protected int columna;
	protected int fila;
	protected Ficha turno;

	/**
	 * Constructora de Movimiento. Inicializa los atributos columna y turno con los
	 * datos de la jugada introducidos por el usuario. Fila se inicializa a -1 para
	 * calcularla posteriormente
	 * 
	 * @param col : columna introducida por el usuario
	 * @param turno : turno del jugador actual
	 */
	public Movimiento(int col, Ficha turno) {
		this.columna = col;
		this.turno = turno;
		this.fila = -1;
	}

	
	public abstract boolean ejecutaMovimiento(Tablero tab);

	public abstract void undo(Tablero tab);
	
	public abstract int ultimaOcupada(Tablero tab);
	
	public abstract Ficha getJugador();
}
