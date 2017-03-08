package tp.pr4.jugadores;

import java.util.Random;

import tp.pr4.control.factorias.FactoriaJuego;
import tp.pr4.juegos.ReglasComplica;
import tp.pr4.logica.Ficha;
import tp.pr4.logica.Tablero;
import tp.pr4.movimientos.Movimiento;

public class JugadorAleatorioComplica implements Jugador {

	/**
	 * Crea un movimiento aleatorio y válido (dentro del rango del tablero)
	 * @param factoria : factoria del juego crrespondiente
	 * @param tab : tablero
	 * @param color : jugador actual
	 * @return movimiento creado
	 */
	public Movimiento getMovimiento(FactoriaJuego factoria, Tablero tab, Ficha color) {
		Random rnd = new Random();
		int columna = -1;
		
		// Devuelve un entero entre 0 y COLUMNAS_CO - 1
		columna = rnd.nextInt(ReglasComplica.COLUMNAS_CO);

		return factoria.creaMovimiento(-1, columna, color);
	}
}
