package tp.pr1;

import java.util.Scanner;

public class Controlador {
	private Partida partida;
	private Scanner cin;
	
	private static final String PONER = "PONER";
	private static final String DESHACER = "DESHACER";
	private static final String REINICIAR = "REINICIAR";
	private static final String SALIR = "SALIR";

	/**
	 * Constructora de Controlador que crea el scanner de los flujos de entrada y una nueva partida con las dimensiones
	 * introducidas
	 */
	public Controlador() {
		cin = new Scanner(System.in);
		this.partida = new Partida();
	}

	/**
	 * Conduce todo el desarrollo del juego. Pide al usuario todos los datos para efectuar una jugada y dibuja el tablero.
	 * Si hay un ganador los muestra a través de mostrarGanador() y si no lo hay, cambia el turno para seguir jugando
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
				columna = cin.nextInt() - 1; // Se le resta -1, ya que va des 0 <= columna < ancho
				if (!partida.ejecutaMovimiento(partida.getTurno(), columna)) 
					System.out.println("Movimiento incorrecto");
			}

			else if (ejecucion.equalsIgnoreCase(DESHACER)) {
				if (!partida.deshacer())
					System.out.println("Imposible deshacer");
			}

			else if (ejecucion.equalsIgnoreCase(REINICIAR)) {
				partida.reset();
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
	 * Indica de quien es el turno actual y solicita la jugada a realizar
	 */
	public void estado() {

		if (partida.getTurno() == Ficha.NEGRA) 
			System.out.println("Juegan negras \n¿Qué quieres hacer?");
		 
		else if (partida.getTurno() == Ficha.BLANCA) 
			System.out.println("Juegan blancas \n¿Qué quieres hacer?");
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
