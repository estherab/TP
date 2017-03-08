package tp.pr4.logica;

import java.util.ArrayList;

import tp.pr4.movimientos.Movimiento;
import tp.pr4.movimientos.MovimientoInvalido;
import tp.pr4.control.factorias.FactoriaJuego;
import tp.pr4.juegos.ReglasJuego;
import tp.pr4.jugadores.Jugador;

public class Partida {
	private ReglasJuego reglas;
	private Tablero tablero;
	private Ficha turno;
	private Ficha ganador;
	private boolean terminada;
	private Pila pila;
	private ArrayList<Observador> observadores;

	/**
	 * Constructora de Partida. Crea el tablero, inicializa la pila y asigna
	 * BLANCA como turno inicial
	 * 
	 * @param reg
	 *            : reglas del juego correspondiente
	 */
	public Partida(ReglasJuego reg) {
		pila = new Pila();
		this.terminada = false;
		this.reglas = reg;
		this.tablero = reg.inicializaTablero();
		this.turno = Ficha.BLANCA;
		observadores = new ArrayList<Observador>();
	}

	public Movimiento creaMov(FactoriaJuego factoria, Jugador tipoJug) {
		Movimiento mov = null;

		try {
			mov = tipoJug.getMovimiento(factoria, tablero, turno);
		} catch (MovimientoInvalido e) {
			for (Observador ob : observadores) {
				ob.onMovimientoIncorrecto(e.getMessage());
				ob.onMovimientoEnd(tablero);
				ob.onCambioTurno(turno);
			}
			
		}

		return mov;
	}

	/**
	 * M�todo que a trav�s de tablero.toString() devuelve todo el tablero en una
	 * sola cadena para mostrarlo en Controlador
	 * 
	 * @return cadena : contiene todo el tablero
	 */
	public String toString() {
		String cadena = "";

		cadena = tablero.toString();

		return cadena;
	}

	/**
	 * Cambiar el tipo de Juego. Pone todas las casillas VACIAS, el turno es de
	 * las BLANCAS, y vacia la pila
	 * 
	 * @param reg
	 *            : reglas del juego correspondiente
	 */
	public void cambioJuego(ReglasJuego reg) {
		this.tablero = reg.inicializaTablero(); 
		this.reglas = reg;
		tablero.inicializaTablero(); // Se inicializa las fichas a vacio
		turno = Ficha.BLANCA;
		pila.resetPila();
		for (Observador ob : observadores)
			ob.onCambioJuego(tablero, turno);
	}

	/**
	 * Reinicia el Juego.
	 * 
	 */
	public void reset() {
		this.tablero.inicializaTablero(); // Se inicializa las fichas a vacio
		turno = Ficha.BLANCA;
		pila.resetPila();
		for (Observador ob : observadores)
			ob.onResetPartida(tablero, turno);
	}

	/**
	 * Introduce una ficha del turno actual en la casilla correspondiente. Por
	 * �ltimo, apila el movimiento, comprueba si hay un ganador y cambia el
	 * turno
	 * 
	 * @param mov
	 *            : movimiento a ejecutar
	 * @return ok : true si se ha insertado la ficha
	 */
	public void ejecutaMovimiento(Movimiento mov) {
		Ficha color;

		try {
			reglas.movimientoValido(tablero, mov);
			mov.ejecutaMovimiento(tablero);

			pila.apilar(mov);
			color = reglas.comprobarGanador(tablero, mov);

			if ((color == Ficha.VACIA) && !reglas.hayTablas(tablero, mov)) {
				turno = reglas.SiguienteTurno(tablero, getTurno());
			
				for (Observador ob : observadores) {
					ob.onMovimientoEnd(tablero);
					ob.onCambioTurno(turno);
				}
			}

			else {
				terminada = true;
				ganador = color;
				for (Observador ob : observadores)
					ob.onPartidaTerminada(tablero, ganador);
			}

		} catch (MovimientoInvalido e) {
			for (Observador ob : observadores) {
				ob.onMovimientoIncorrecto(e.getMessage());
				ob.onMovimientoEnd(tablero);
				ob.onCambioTurno(turno);
			}
		}

	}
	
	public void creaJugador() {
		for (Observador ob : observadores)
			ob.onCambioTurno(turno);		
	}

	/**
	 * M�todo para saber de qui�n es el turno actual.
	 * 
	 * @return turno
	 */
	public Ficha getTurno() {

		return turno;
	}

	/**
	 * M�todo que indica si la partida se ha terminado
	 * 
	 * @return terminada : true si la partida ha terminado
	 */
	public boolean getTerminada() {

		return terminada;
	}

	/**
	 * M�todo para obtener el ganador.
	 * 
	 * @return ganador : BLANCA, NEGRA o VACIA
	 */
	public Ficha getGanador() {

		return ganador;
	}
	
	public boolean setTerminada() {
		return terminada = true;
	}

	/**
	 * Deshace el �ltimo movimiento en caso de que la pila tenga elementos y
	 * pone la correspondiente ficha VACIA. Por �ltimo, borra el movimiento de
	 * la pila y cambia el turno
	 * 
	 * @return ok : true si se ha podido desapilar el movimiento
	 */
	public void deshacer() {

		boolean hayMas = false;

		try {
			hayMas = pila.hayMovimientos();

			Movimiento mov = pila.obtenerUltimoMov();
			mov.undo(tablero);
			pila.desapilar();
			turno = mov.getJugador();
			if (pila.getNumUndo() == 0)
				hayMas = false;

		} catch (PilaVacia e) {
			for (Observador ob : observadores) 
				ob.onUndoNotPossible(); // Notifica que no se puede deshacer
		}
		
		finally {
			for (Observador ob : observadores) {
				ob.onUndo(tablero, hayMas); // Notifica deshace
				ob.onCambioTurno(turno); // Notifica cambia turno
			}
		}
	}

	public void addObserver(Observador o) {
		observadores.add(o);
	}

	public void removeObserver(Observador o) {
		observadores.remove(o);
	}
}
