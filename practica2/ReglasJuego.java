package tp.pr2.juegos;

import tp.pr2.logica.Ficha;
import tp.pr2.logica.Tablero;

public abstract class ReglasJuego {

	public abstract Tablero inicializaTablero();

	public abstract boolean movimientoValido(Tablero tab, Movimiento mov);

	public abstract Ficha comprobarGanador(Tablero tab, Movimiento mov);

	public abstract boolean hayTablas(Tablero tab, Movimiento mov);

	public abstract Ficha SiguienteTurno(Tablero tab, Ficha turno);
}
