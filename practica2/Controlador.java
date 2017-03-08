package tp.pr2.control;

import java.util.Scanner;

import tp.pr2.juegos.Movimiento;
import tp.pr2.juegos.MovimientoComplica;
import tp.pr2.juegos.MovimientoConecta4;
import tp.pr2.juegos.ReglasComplica;
import tp.pr2.juegos.ReglasConecta4;
import tp.pr2.logica.Ficha;
import tp.pr2.logica.Partida;

public class Controlador {
	private Partida partida;
	private Scanner cin;
	TipoJuego jugandoA;

	private static final String PONER = "PONER";
	private static final String JUGAR = "JUGAR";
	private static final String DESHACER = "DESHACER";
	private static final String REINICIAR = "REINICIAR";
	private static final String SALIR = "SALIR";

	/**
	 * Constructora de Controlador que crea el scanner de los flujos de entrada 
	 * y una nueva partida con las reglas del juego correspondiente
	 */
	public Controlador() {
		this.jugandoA = TipoJuego.CONECTA4;
		cin = new Scanner(System.in);
		partida = new Partida(new ReglasConecta4());
	}

	/**
	 * Conduce todo el desarrollo del juego. Pide al usuario todos los datos para efectuar una jugada y dibuja el tablero.
	 * Si hay un ganador lo muestra a traves de mostrarGanador() y si no lo hay, cambia el turno para seguir jugando
	 */
	public void run() {
		String ejecucion;
		int columna;

		System.out.println(partida.toString()); // Dibujo el tablero
		estado();
		ejecucion = cin.next(); 

		while (!ejecucion.equalsIgnoreCase(SALIR) && !partida.getTerminada()) {

			if (ejecucion.equalsIgnoreCase(PONER)) {
				System.out.println("Introduce la columna: ");
				columna = cin.nextInt() - 1;
				System.out.println();
				
				if (this.jugandoA == TipoJuego.CONECTA4) {
					Movimiento movC4 = new MovimientoConecta4(columna, this.partida.getTurno());
					if (!partida.ejecutaMovimiento(movC4))
						System.out.println("Movimiento incorrecto");
				} 
				
				else {
					Movimiento movCo = new MovimientoComplica(columna, this.partida.getTurno());
					if (!partida.ejecutaMovimiento(movCo))
						System.out.println("Movimiento incorrecto");
				}
			}

			else if (ejecucion.equalsIgnoreCase(JUGAR)) {
				ejecucion = cin.next();
				
				if (ejecucion.equalsIgnoreCase("C4")) {
					this.jugandoA = TipoJuego.CONECTA4;
					partida.reset(new ReglasConecta4());
				} 
				
				else if (ejecucion.equalsIgnoreCase("CO")) {
					this.jugandoA = TipoJuego.COMPLICA;
					partida.reset(new ReglasComplica());
				} 
				
				else
					System.out.println("No te entiendo");
			}

			else if (ejecucion.equalsIgnoreCase(DESHACER)) {
				if (!partida.deshacer()) {
					System.out.println("Imposible deshacer");
				}
			}

			else if (ejecucion.equalsIgnoreCase(REINICIAR)) {
				if (this.jugandoA == TipoJuego.CONECTA4)
					partida.reset(new ReglasConecta4());
				
				else
					partida.reset(new ReglasComplica());
				System.out.println("Partida reiniciada");
			}
			
			else 
				System.out.println("Opción incorrecta. Inténtalo de nuevo...");
			
			System.out.println(partida.toString());
			
			if (partida.getTerminada())
				mostrarGanador();
			
			else {
				estado();
				ejecucion = cin.next(); 				
			}
		}
		
		System.exit(0);
	}
	
	/**
	 * Indica de quién es el turno actual y solicita la jugada a realizar
	 */
	public void estado() {

		if (partida.getTurno() == Ficha.NEGRA) 
			System.out.println("Juegan negras \n¿Que quieres hacer?");
		 
		else if (partida.getTurno() == Ficha.BLANCA) 
			System.out.println("Juegan blancas \n¿Que quieres hacer?");
	}
	
	/**
	 * Informa sobre el ganador de la partida si lo ha habido. En caso contrario indica que la partida ha quedado en tablas
	 */
	public void mostrarGanador() {
		if (partida.getGanador() == Ficha.BLANCA)
			System.out.println("Ganan las blancas");

		else if (partida.getGanador() == Ficha.NEGRA)
			System.out.println("Ganan las negras");
		
		else
			System.out.println("Partida en tablas");
	}
}
