package tp.pr3.logica;

import tp.pr3.movimientos.Movimiento;
import tp.pr3.movimientos.MovimientoInvalido;
import tp.pr3.control.factorias.FactoriaJuego;
import tp.pr3.juegos.ReglasJuego;
import tp.pr3.jugadores.Jugador;

public class Partida {
	private ReglasJuego reglas;
	private Tablero tablero;
	private Ficha turno;
	private Ficha ganador;
	private boolean terminada;
	private Pila pila;

	/**
	 * Constructora de Partida. Crea el tablero, inicializa la pila y asigna
	 * BLANCA como turno inicial
	 * 
	 * @param reg : reglas del juego correspondiente
	 */
	public Partida(ReglasJuego reg) {
		pila = new Pila();
		this.terminada = false;
		this.reglas = reg;
		this.tablero = reg.inicializaTablero();
		this.turno = Ficha.BLANCA;
	}

	public Movimiento creaMov(FactoriaJuego factoria, Jugador tipoJug) throws MovimientoInvalido {

		return tipoJug.getMovimiento(factoria, tablero, turno);
	}

	/**
	 * Método que a través de tablero.toString() devuelve todo el tablero en una
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
	 * Resetea el tablero. Pone todas las casillas VACIAS, el turno es de las
	 * BLANCAS, y vacia la pila
	 * 
	 * @param reg
	 *            : reglas del juego correspondiente
	 */
	public void reset(ReglasJuego reg) {
		this.tablero = reg.inicializaTablero(); 
		this.reglas = reg;
		tablero.inicializaTablero();
		turno = Ficha.BLANCA;
		pila.resetPila();
	}

	/**
	 * Introduce una ficha del turno actual en la casilla correspondiente. Por
	 * último, apila el movimiento, comprueba si hay un ganador y cambia el
	 * turno
	 * 
	 * @param mov
	 *            : movimiento a ejecutar
	 * @return ok : true si se ha insertado la ficha
	 */
	public void ejecutaMovimiento(Movimiento mov) throws MovimientoInvalido {
		Ficha color;

		reglas.movimientoValido(tablero, mov);
		mov.ejecutaMovimiento(tablero);

		pila.apilar(mov); 
		color = reglas.comprobarGanador(tablero, mov);

		if ((color == Ficha.VACIA) && !reglas.hayTablas(tablero, mov))
			turno = reglas.SiguienteTurno(tablero, getTurno());

		else {
			terminada = true;
			ganador = color;
		}
	}

	/**
	 * Método para saber de quién es el turno actual.
	 * 
	 * @return turno
	 */
	public Ficha getTurno() {

		return turno;
	}

	/**
	 * Método que indica si la partida se ha terminado
	 * 
	 * @return terminada : true si la partida ha terminado
	 */
	public boolean getTerminada() {

		return terminada;
	}

	/**
	 * Método para obtener el ganador.
	 * 
	 * @return ganador : BLANCA, NEGRA o VACIA
	 */
	public Ficha getGanador() {

		return ganador;
	}

	/**
	 * Deshace el último movimiento en caso de que la pila tenga elementos y
	 * pone la correspondiente ficha VACIA. Por último, borra el movimiento de
	 * la pila y cambia el turno
	 * 
	 * @return ok : true si se ha podido desapilar el movimiento
	 */
	public void deshacer() throws PilaVacia {

		pila.estaVacia();
		Movimiento mov = pila.obtenerUltimoMov();
		mov.undo(tablero);
		pila.desapilar();
		turno = mov.getJugador();
	}
}
