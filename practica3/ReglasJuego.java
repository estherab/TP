package tp.pr3.juegos;

import tp.pr3.logica.Ficha;
import tp.pr3.logica.Tablero;
import tp.pr3.movimientos.Movimiento;
import tp.pr3.movimientos.MovimientoInvalido;

public abstract class ReglasJuego {

	public abstract Tablero inicializaTablero();

	public abstract void movimientoValido(Tablero tab, Movimiento mov) throws MovimientoInvalido;

	public abstract Ficha comprobarGanador(Tablero tab, Movimiento mov);

	public abstract boolean hayTablas(Tablero tab, Movimiento mov);

	public abstract Ficha SiguienteTurno(Tablero tab, Ficha turno);
}
