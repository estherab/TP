package tp.pr3.control.factorias;

import java.util.Scanner;

import tp.pr3.juegos.ReglasConecta4;
import tp.pr3.juegos.ReglasJuego;
import tp.pr3.jugadores.Jugador;
import tp.pr3.jugadores.JugadorAleatorioConecta4;
import tp.pr3.jugadores.JugadorHumanoConecta4;
import tp.pr3.logica.Ficha;
import tp.pr3.movimientos.Movimiento;
import tp.pr3.movimientos.MovimientoConecta4;

public class FactoriaJuegoConecta4 implements FactoriaJuego {
	
	/**
	 * Crea las reglas para el juego Conecta 4
	 * @return reglas
	 */
	public ReglasJuego creaReglas() {
		
		return new ReglasConecta4();
	}
	
	/**
	 * Crea un movimiento para Conecta 4. Inicializa los atributo de Movimiento con los valores de los argumentos.
	 * @param fila, col : coordenadas del movimiento
	 * @param turno : color del jugador actual
	 * @return movimiento
	 */
	public Movimiento creaMovimiento(int fila, int col, Ficha turno) {
		
		return new MovimientoConecta4(fila, col, turno);
	}

	/**
	 * Devuelve la variable jugador con un jugador para Conecta 4 de tipo aleatorio
	 * @return jugador
	 */
	public Jugador creaJugadorAleatorio() {

		return new JugadorAleatorioConecta4();
	}

	/**
	 * Devuelve la variable jugador con un jugador para Conecta 4 de tipo humano
	 * @param sc : scanner de entrada
	 * @return jugador 
	 */
	public Jugador creaJugadorHumano(Scanner sc) {

		return new JugadorHumanoConecta4(sc);
	}
}
