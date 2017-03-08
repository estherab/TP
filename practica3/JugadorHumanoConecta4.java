package tp.pr3.jugadores;

import java.util.InputMismatchException;
import java.util.Scanner;

import tp.pr3.control.factorias.FactoriaJuego;
import tp.pr3.juegos.ReglasConecta4;
import tp.pr3.logica.Ficha;
import tp.pr3.logica.Tablero;
import tp.pr3.movimientos.Movimiento;
import tp.pr3.movimientos.MovimientoInvalido;

public class JugadorHumanoConecta4 implements Jugador {
	Scanner sc;
	
	/**
	 * Constructor que recibe el scanner
	 * @param sc : scanner de entrada
	 */
	public JugadorHumanoConecta4(Scanner sc) {
		
		this.sc = sc;
	}
	
	/**
	 * Solicita al usuario la creación de un nuevo movimiento. 
	 * Si la columna introducida no es válida, lanza una excepción
	 * @param factoria : juego actual
	 * @param tab : tablero
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
			throw new MovimientoInvalido("Columna no valida. Tiene que ser un valor numerico entre 1 y " + ReglasConecta4.COLUMNAS_C4);
		}
		
		return factoria.creaMovimiento(-1, columna, color);
	}
}


