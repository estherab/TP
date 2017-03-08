package tp.pr3.jugadores;

import java.util.Random;

import tp.pr3.control.factorias.FactoriaJuego;
import tp.pr3.logica.Ficha;
import tp.pr3.logica.Tablero;
import tp.pr3.movimientos.Movimiento;

public class JugadorAleatorioGravity implements Jugador {

	/**
	 * Crea un movimiento válido (dentro del tablero). En el juego Gravity creamos una fila y columna ambas aleatorias
	 * @param factoria : juego actual
	 * @param tab : tablero
	 * @param color : jugador actual
	 * @return movimiento creado
	 */
	public Movimiento getMovimiento(FactoriaJuego factoria, Tablero tab, Ficha color) {
		Random rnd = new Random();
		int fila = -1, columna = -1;
		boolean movimientoValido = false;

		while (!movimientoValido) {
			columna = rnd.nextInt(tab.getAncho()); // Entre 0 y ancho - 1
			fila = rnd.nextInt(tab.getAlto()); // Entre 0 y alto - 1
			
			if (tab.fichaVacia(fila, columna)) // Si la casilla está ocupada se crea un nuevo movimiento
				movimientoValido = true;
		}

		return factoria.creaMovimiento(fila, columna, color);
	}
}
