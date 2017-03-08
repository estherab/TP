package tp.pr3.control.factorias;

import java.util.Scanner;

import tp.pr3.juegos.ReglasJuego;
import tp.pr3.jugadores.Jugador;
import tp.pr3.logica.Ficha;
import tp.pr3.movimientos.Movimiento;

public interface FactoriaJuego {
	
	public ReglasJuego creaReglas();
	public Movimiento creaMovimiento(int fila, int col, Ficha turno);
	public Jugador creaJugadorAleatorio();
	public Jugador creaJugadorHumano(Scanner sc);
}
