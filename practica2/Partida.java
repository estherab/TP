package tp.pr2.logica;

import tp.pr2.juegos.Movimiento;
import tp.pr2.juegos.ReglasJuego;

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

	/**
	 * Método que a través de tablero.toString() devuelve todo el tablero en
	 * una sola cadena para mostrarlo en Controlador
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
	 * @param reg : reglas del juego correspondiente
	 */
	public void reset(ReglasJuego reg) {
		this.tablero = reg.inicializaTablero(); // Se pone con las dimesiones nuevas
		this.reglas = reg;
		tablero.inicializaTablero(); 
		turno = Ficha.BLANCA;
		pila.resetPila();
	}

	/**
	 * Introduce una ficha del turno actual en la casilla correspondiente. 
	 * Por último, apila el movimiento, comprueba si hay un ganador y cambia el turno
	 * 
	 * @param mov : movimiento a ejecutar
	 * @return ok : true si se ha insertado la ficha
	 */
	public boolean ejecutaMovimiento(Movimiento mov) {
		boolean ok = false;
		Ficha color;

		if (reglas.movimientoValido(tablero, mov)) {
			if (mov.ejecutaMovimiento(tablero)) {
				ok = true;
				pila.apilar(mov); 
				color = reglas.comprobarGanador(tablero, mov);

				if ((color == Ficha.VACIA) && !reglas.hayTablas(tablero, mov)) 
					turno = reglas.SiguienteTurno(tablero, getTurno());
				
				else {
					terminada = true;
					ganador = color;
				}
			}
		}

		return ok;
	}

	/**
	 * Método para saber de quien es el turno actual. 
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
	public boolean deshacer() {
		boolean ok = false;
		if (!pila.estaVacia()) {
			ok = true;
			// Asignamos a mov el ultimo movimiento encontrado en la pila
			Movimiento mov = pila.obtenerUltimoMov();
			mov.undo(tablero);
			pila.desapilar();
			turno = mov.getJugador();
		}
		
		return ok;
	}
}
