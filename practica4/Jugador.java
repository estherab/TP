package tp.pr4.jugadores;


import tp.pr4.control.factorias.FactoriaJuego;
import tp.pr4.logica.Ficha;
import tp.pr4.logica.Tablero;
import tp.pr4.movimientos.Movimiento;
import tp.pr4.movimientos.MovimientoInvalido;

public interface Jugador {
	public Movimiento getMovimiento(FactoriaJuego factoria, Tablero tab, Ficha color) throws MovimientoInvalido;
}
