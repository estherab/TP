package tp.pr3.jugadores;


import tp.pr3.control.factorias.FactoriaJuego;
import tp.pr3.logica.Ficha;
import tp.pr3.logica.Tablero;
import tp.pr3.movimientos.Movimiento;
import tp.pr3.movimientos.MovimientoInvalido;

public interface Jugador {
	public Movimiento getMovimiento(FactoriaJuego factoria, Tablero tab, Ficha color) throws MovimientoInvalido;
}
