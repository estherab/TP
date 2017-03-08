package tp.pr4.jugadores;

import tp.pr4.control.factorias.FactoriaJuego;
import tp.pr4.logica.Ficha;
import tp.pr4.logica.Tablero;
import tp.pr4.movimientos.Movimiento;
import tp.pr4.juegos.ReglasConecta4;

import java.util.Random;

public class JugadorAleatorioConecta4 implements Jugador {

	/**
	 * Crea un movimiento aleatorio y v�lido (dentro del tablero)
	 * @param factoria : juego correspondiente
	 * @param tab : tablero
	 * @param color : jugador actual
	 * @return movimiento creado
	 */
	public Movimiento getMovimiento(FactoriaJuego factoria, Tablero tab, Ficha color) {
		Random rnd = new Random();
		boolean movimientoValido = false;
		int columna = -1;

		while (!movimientoValido) { 
			columna = rnd.nextInt(ReglasConecta4.COLUMNAS_C4);

			if (tab.fichaVacia(ReglasConecta4.FILAS_C4 - 1, columna))
				movimientoValido = true;
		}

		return factoria.creaMovimiento(-1, columna, color);
	}
}
