package tp.pr3.control;

import java.util.Scanner;

import tp.pr3.movimientos.Movimiento;
import tp.pr3.movimientos.MovimientoInvalido;
import tp.pr3.control.factorias.FactoriaJuego;
import tp.pr3.control.factorias.FactoriaJuegoComplica;
import tp.pr3.control.factorias.FactoriaJuegoConecta4;
import tp.pr3.control.factorias.FactoriaJuegoGravity;
import tp.pr3.jugadores.Jugador;
import tp.pr3.logica.Ficha;
import tp.pr3.logica.Partida;
import tp.pr3.logica.PilaVacia;

public class Controlador {
	private Partida partida;
	private Scanner sc;
	private FactoriaJuego factoria;
	private Jugador jugador1, jugador2;

	// COMANDOS
	private static final String BLANCAS = "BLANCAS";
	private static final String NEGRAS = "NEGRAS";
	private static final String ALEATORIO = "ALEATORIO";
	private static final String HUMANO = "HUMANO";

	// OPCIONES
	private static final String PONER = "PONER";
	private static final String JUGAR = "JUGAR";
	private static final String DESHACER = "DESHACER";
	private static final String REINICIAR = "REINICIAR";
	private static final String AYUDA = "AYUDA";
	private static final String JUGADOR = "JUGADOR";
	private static final String SALIR = "SALIR";

	/**
	 * Constructora de Controlador que crea el scanner de los flujos de entrada
	 * y una nueva partida con las reglas del juego correspondiente
	 */
	public Controlador(FactoriaJuego factoria, Partida part, Scanner in) {
		this.sc = in;
		this.factoria = factoria;
		this.partida = part;
		this.jugador1 = factoria.creaJugadorHumano(sc); // TURNO BLANCA: JUGADOR 1
		this.jugador2 = factoria.creaJugadorHumano(sc); // TURNO NEGRA: JUGADOR 2
	}

	/**
	 * Conduce todo el desarrollo del juego. Pide al usuario todos los datos
	 * para efectuar una jugada y dibuja el tablero. Si hay un ganador lo
	 * muestra a traves de mostrarGanador() y si no lo hay, cambia el turno para
	 * seguir jugando
	 */
	public void run() {
		String ejecucion;
		Movimiento mov = null;

		System.out.println(partida.toString()); // Dibujo el tablero
		estado();
		ejecucion = sc.next(); 

		while (!ejecucion.equalsIgnoreCase(SALIR) && !partida.getTerminada()) {

			if (ejecucion.equalsIgnoreCase(PONER)) {
				try {
					if (partida.getTurno() == Ficha.BLANCA)
						mov = partida.creaMov(factoria, jugador1);

					else if (partida.getTurno() == Ficha.NEGRA) 
						mov = partida.creaMov(factoria, jugador2);

					partida.ejecutaMovimiento(mov);

				} catch (MovimientoInvalido e) {
					System.err.println(e.getMessage() + "\n");
				}
			}

			else if (ejecucion.equalsIgnoreCase(JUGAR)) {

				boolean ok = true;
				ejecucion = sc.nextLine();
				ejecucion = ejecucion.trim();
				String arrayEjecucion[] = ejecucion.split(" ");

				if (!arrayEjecucion[0].equalsIgnoreCase("C4")
						&& !arrayEjecucion[0].equalsIgnoreCase("CO")
						&& !arrayEjecucion[0].equalsIgnoreCase("GR"))
					System.err.println("No te entiendo");

				else {
					if (arrayEjecucion[0].equalsIgnoreCase("C4")) 
						factoria = new FactoriaJuegoConecta4(); 
					
					else if (arrayEjecucion[0].equalsIgnoreCase("CO")) 
						factoria = new FactoriaJuegoComplica();
					
					else if (arrayEjecucion[0].equalsIgnoreCase("GR")) {
						// Se comienza con las dimensaiones por defecto
						if (arrayEjecucion.length == 1) 
							factoria = new FactoriaJuegoGravity();

						// Se comienza con las dimensiones introducias
						else if (arrayEjecucion.length == 3) {
							int f = 0, c = 0;

							f = Integer.parseInt(arrayEjecucion[1]);
							c = Integer.parseInt(arrayEjecucion[2]);

							if ((f <= 0) || (c <= 0)) {
								System.err.println("Las dimensiones de Gravity tienen que ser positivas.\n");
							ok = false;
							}
							
							else
								factoria = new FactoriaJuegoGravity(f, c);

						}

						else if ((arrayEjecucion.length != 1) || (arrayEjecucion.length != 3)) {
							ok = false;
							System.err.println("No te entiendo: introduce gr [tamX tamY] o gr.\n");
						}
					}

					if (ok) {
						partida.reset(factoria.creaReglas()); 
						jugador1 = factoria.creaJugadorHumano(sc);
						jugador2 = factoria.creaJugadorHumano(sc);
					}
				}
			}

			else if (ejecucion.equalsIgnoreCase(DESHACER)) {
				try {
					partida.deshacer();
				} catch (PilaVacia e) {
					System.err.println(e.getMessage() + "\n");
				}
			}

			else if (ejecucion.equalsIgnoreCase(REINICIAR)) {
				partida.reset(factoria.creaReglas());
				System.out.println("Partida reiniciada");
			}

			else if (ejecucion.equalsIgnoreCase(AYUDA))
				ayuda();

			else if (ejecucion.equalsIgnoreCase(JUGADOR)) {
				String color = sc.next(); 
				String tipoJug = sc.next(); 
				eligeJugador(color, tipoJug);
			}

			else
				System.err.println("Opcion incorrecta. Intentalo de nuevo...\n");

			System.out.println(partida.toString());

			if (partida.getTerminada())
				mostrarGanador();

			else {
				estado();
				ejecucion = sc.next();
			}
		}

		System.exit(0);
	}

	/**
	 * Indica de quien es el turno actual y solicita la jugada a realizar
	 */
	public void estado() {
		if (partida.getTurno() == Ficha.NEGRA)
			System.out.print("Juegan negras \nQué quieres hacer? ");

		else if (partida.getTurno() == Ficha.BLANCA)
			System.out.print("Juegan blancas \nQué quieres hacer? ");
	}

	/**
	 * Informa sobre el ganador de la partida si lo ha habido. En caso contrario
	 * indica que la partida ha quedado en tablas
	 */
	public void mostrarGanador() {
		if (partida.getGanador() == Ficha.BLANCA)
			System.out.println("Ganan las blancas");

		else if (partida.getGanador() == Ficha.NEGRA)
			System.out.println("Ganan las negras");

		else
			System.out.println("Partida en tablas");
	}

	/**
	 * Muestra un texto de ayuda si el usuario introduce el comando "AYUDA"
	 */
	public void ayuda() {
		System.out.println("Los comandos disponibles son:\n");
		System.out.println(PONER + ": utilizalo para poner la siguiente ficha.");
		System.out.println(DESHACER + ": deshace el ultimo movimiento hecho en la partida.");
		System.out.println(JUGAR + " [c4|co|gr] [tamX tamY]: cambia el tipo de juego.");
		System.out.println(JUGADOR + "[blancas|negras] [humano|aleatorio]: cambia el tipo de jugador.");
		System.out.println(SALIR + ": termina la aplicacion.");
		System.out.println(AYUDA + ": muestra esta ayuda.\n");
	}

	/**
	 * Establece el tipo de jugador (humano o aleatorio) para los dos jugadores
	 * de la partida.
	 * 
	 * @param color
	 *            : blancas (jugador1) o negras(jugador2)
	 * @param tipoJug
	 *            : introducido por el usuario (humano o aleatorio)
	 */
	void eligeJugador(String color, String tipoJug) {

		// Suponiendo que el jugador 1 siempre tiene ficha blanca
		if (color.equalsIgnoreCase(BLANCAS)) {
			if (tipoJug.equalsIgnoreCase(HUMANO))
				jugador1 = factoria.creaJugadorHumano(sc);

			else if (tipoJug.equalsIgnoreCase(ALEATORIO))
				jugador1 = factoria.creaJugadorAleatorio();

			else
				System.err.println("Tipo de Jugador incorrecto. (Solo se puede humano o aleatorio)");
		}

		// Suponiendo que el jugador 2 siempre tiene ficha negra
		else if (color.equalsIgnoreCase(NEGRAS)) {
			if (tipoJug.equalsIgnoreCase(HUMANO))
				jugador2 = factoria.creaJugadorHumano(sc);

			else if (tipoJug.equalsIgnoreCase(ALEATORIO))
				jugador2 = factoria.creaJugadorAleatorio();

			else
				System.err.println("Tipo de Jugador incorrecto. (Solo se puede humano o aleatorio)\n");
		} else
			System.err.println("Color incorrecto. (Solo se puede negras o blancas)\n");
	}
}
