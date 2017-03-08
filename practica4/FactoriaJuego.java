package tp.pr4.control.factorias;

import java.util.Scanner;

import tp.pr4.juegos.ReglasJuego;
import tp.pr4.jugadores.Jugador;
import tp.pr4.logica.Ficha;
import tp.pr4.movimientos.Movimiento;

public interface FactoriaJuego {
	
	public ReglasJuego creaReglas();
	public Movimiento creaMovimiento(int fila, int col, Ficha turno);
	public Jugador creaJugadorAleatorio();
	public Jugador creaJugadorHumano(Scanner sc);
}
