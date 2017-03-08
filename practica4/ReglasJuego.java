package tp.pr4.juegos;

import tp.pr4.logica.Ficha;
import tp.pr4.logica.Tablero;
import tp.pr4.movimientos.Movimiento;
import tp.pr4.movimientos.MovimientoInvalido;

public abstract class ReglasJuego {

	public abstract Tablero inicializaTablero();
	public abstract void movimientoValido(Tablero tab, Movimiento mov) throws MovimientoInvalido;
	public abstract Ficha comprobarGanador(Tablero tab, Movimiento mov);
	public abstract boolean hayTablas(Tablero tab, Movimiento mov);
	public abstract Ficha SiguienteTurno(Tablero tab, Ficha turno);
}
