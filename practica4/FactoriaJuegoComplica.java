package tp.pr4.control.factorias;

import java.util.Scanner;

import tp.pr4.juegos.ReglasComplica;
import tp.pr4.juegos.ReglasJuego;
import tp.pr4.jugadores.*;
import tp.pr4.logica.Ficha;
import tp.pr4.movimientos.Movimiento;
import tp.pr4.movimientos.MovimientoComplica;

public class FactoriaJuegoComplica implements FactoriaJuego {
	
	/**
	 * Crea las reglas para el juego Complica
	 * @return reglas 
	 */
	public ReglasJuego creaReglas() {
		
		return new ReglasComplica();
	}
	
	/**
	 * Crea el movimiento para el juego Complica. Inicializa los atributos de Movimiento con los valores de 
	 * los argumentos.
	 * @param fila, columna : coordenadas del movimiento
	 * @param turno : color del jugador actual
	 * @return movimiento
	 */
	public Movimiento creaMovimiento(int fila, int col, Ficha turno) {
		
		return new MovimientoComplica(fila, col, turno);
	}

	/**
	 * Devuelve la variable jugador con un jugador de Complica de tipo aleatorio
	 * @return jugador
	 */
	public Jugador creaJugadorAleatorio() {
		
		return new JugadorAleatorioComplica();
	}

	/**
	 * Devuelve la variable jugador con un jugador de Complica de tipo humano
	 * @param sc : scanner de entrada
	 * @return jugador
	 */
	public Jugador creaJugadorHumano(Scanner sc) {

		return new JugadorHumanoComplica(sc);
	}
}
