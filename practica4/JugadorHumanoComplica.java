package tp.pr4.jugadores;

import java.util.InputMismatchException;
import java.util.Scanner;

import tp.pr4.control.factorias.FactoriaJuego;
import tp.pr4.juegos.ReglasComplica;
import tp.pr4.logica.Ficha;
import tp.pr4.logica.Tablero;
import tp.pr4.movimientos.Movimiento;
import tp.pr4.movimientos.MovimientoInvalido;

public class JugadorHumanoComplica implements Jugador {
	Scanner sc;
	
	/**
	 * Constructor que recibe el scanner
	 * @param sc : scanner de entrada
	 */
	public JugadorHumanoComplica(Scanner sc) {
		
		this.sc = sc;
	}
	
	/**
	 * Solicita al usuario la creacion de un nuevo movimiento. Lanza una excepcion si la columna introducida
	 * no es v�lida
	 * @param factoria : juego actual
	 * @tab : tablero
	 * @param color : jugador actual
	 * @return movimiento
	 */
	public Movimiento getMovimiento(FactoriaJuego factoria, Tablero tab, Ficha color) throws MovimientoInvalido {
		int columna;
		System.out.print("Introduce la columna: ");
		
		try {
			columna = sc.nextInt() - 1;
		} catch (InputMismatchException e) {
			sc.nextLine();
			throw new MovimientoInvalido("Columna no valida. Tiene que ser un valor numerico entre 1 y " + ReglasComplica.COLUMNAS_CO);
		}
		
		return factoria.creaMovimiento(-1, columna, color);
	}
}
