package tp.pr4.control;

import java.util.Scanner;

import tp.pr4.control.factorias.FactoriaJuego;
import tp.pr4.jugadores.Jugador;
import tp.pr4.logica.*;
import tp.pr4.movimientos.Movimiento;

public class ControlConsola {
	private Partida partida;
	private Scanner sc;
	private FactoriaJuego factoria;
	private Jugador jugador1, jugador2;
	
	// COMANDOS
	public static final String BLANCAS = "BLANCAS";
	public static final String NEGRAS = "NEGRAS";
	public static final String ALEATORIO = "ALEATORIO";
	public static final String HUMANO = "HUMANO";

	
	public ControlConsola(Partida p, FactoriaJuego factoria, Scanner sc) {
		this.sc = sc;
		this.partida = p; 
		this.factoria = factoria;
		this.jugador1 = factoria.creaJugadorHumano(sc);
		this.jugador2 = factoria.creaJugadorHumano(sc);
	} 
	
	/**
	 * Metodo que pide un movimiento al usuario y ejecuta una jugada con dicho movimiento
	 */
	public void poner() {
		Movimiento mov = null;
		
		if (partida.getTurno() == Ficha.BLANCA)
			mov = partida.creaMov(factoria, jugador1);
		else if (partida.getTurno() == Ficha.NEGRA)
			mov = partida.creaMov(factoria, jugador2); 
		if (mov != null) 
			partida.ejecutaMovimiento(mov);	
	}
	
	/**
	 * Metodo para reiniciar la partida
	 */
	public void reiniciar() {
		
		partida.reset();
	}
	
	/**
	 * Deshacer una jugada
	 */
	public void undo() {
		
		partida.deshacer();
	}
	
	/**
	 * Metodo para cambiar de juego
	 * @param f : factoria del juego al que se quiere cambiar
	 */
	public void reset(FactoriaJuego f) {
		this.factoria = f;
		partida.cambioJuego(factoria.creaReglas());
		jugador1 = factoria.creaJugadorHumano(sc);
		jugador2 = factoria.creaJugadorHumano(sc);
	}
	
	public boolean finalizada() {
						
		return partida.getTerminada();
	}
	
	public boolean finalizar() {
		
		return partida.setTerminada();
	}
	
	public Ficha getTurno() {
		
		return partida.getTurno();
	}
	
	public void addObserver(Observador o) {
		
		partida.addObserver(o);
	}

	public void removeObserver(Observador o) {
		
		partida.removeObserver(o);
	}
	
	/**
	 * Establece el tipo de jugador (humano o aleatorio) para los dos jugadores
	 * de la partida.
	 * 
	 * @param color : blancas (jugador1) o negras( jugador2)
	 * @param tipoJug : introducido por el usuario (humano o aleatorio)
	 */
	public void eligeJugador (String color, String tipoJug) {

		// Suponiendo que el jugador 1 siempre tiene ficha blanca
		if (color.equalsIgnoreCase(BLANCAS)) {
			if (tipoJug.equalsIgnoreCase(HUMANO))
				jugador1 = factoria.creaJugadorHumano(sc);

			else
				jugador1 = factoria.creaJugadorAleatorio();
		}

		// Suponiendo que el jugador 2 siempre tiene ficha negra
		else {
			if (tipoJug.equalsIgnoreCase(HUMANO))
				jugador2 = factoria.creaJugadorHumano(sc);

			else 
				jugador2 = factoria.creaJugadorAleatorio();
		} 
		
		partida.creaJugador();		
	}		
}
