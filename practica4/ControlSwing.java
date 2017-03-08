package tp.pr4.control;
 
import tp.pr4.control.factorias.*;
import tp.pr4.jugadores.Jugador;
import tp.pr4.logica.*;

import tp.pr4.movimientos.Movimiento;
 
public class ControlSwing {
    private Partida partida;
    private FactoriaJuego factoria;
    private Jugador JugMovAleatorio;
    TipoJuego jugandoA;
     
    public ControlSwing(Partida p, FactoriaJuego factoria) {
        this.partida = p; 
        this.factoria = factoria;
    }
     
    /**
     * Poner una ficha de forma aleatoria. Obtiene un movimiento despues de la creacion de 
     * un jugador aleatoria y ejecuta una jugada con el movimiento obtenido
     */
    public void poner() {
    	Movimiento mov; 
		
    	JugMovAleatorio = factoria.creaJugadorAleatorio();
		mov = partida.creaMov(factoria, JugMovAleatorio); 
		partida.ejecutaMovimiento(mov);
    }
    
    /**
     * Para poner una ficha en las coordenadas indicadas
     * por el usuario con el raton.
     */ 
    public void poner(int fila, int col) {
        Movimiento mov = factoria.creaMovimiento(fila, col, partida.getTurno());
        partida.ejecutaMovimiento(mov);         
    }
    
    /**
     * Reiniciar la partida (en un mismo juego)
     */
	public void reiniciar() {
		
		partida.reset();
	}
     
	/**
	 * Deshacer un movimiento
	 */
	public void undo() {
		
		partida.deshacer();
	}
	
	/**
	 * Cambio de juego
	 * @param t : juego al que se quiere cambiar
	 * @param filas, cols : tamaño del tablero de Gravity elegido por el usuario
	 */
    public void reset(TipoJuego t, int filas, int cols) {

    	if (t == TipoJuego.CONECTA4)
			this.factoria =  new FactoriaJuegoConecta4();
		else if (t == TipoJuego.COMPLICA)
			this.factoria =  new FactoriaJuegoComplica();
		else 
			if ((filas > 0) && (cols > 0))
				this.factoria = new FactoriaJuegoGravity(filas, cols);
			else // dimension por defecto 10x10
				this.factoria = new FactoriaJuegoGravity();
				
    	partida.cambioJuego(factoria.creaReglas()); 
    }
    
    public TipoJuego getTipoJuego() {
    	
    	return jugandoA;
    }

	/**
	 * Añade observadores
	 */
	public void addObserver(Observador o) {
		
		partida.addObserver(o);
	}
    
	public void removeObserver(Observador o) {
		
		partida.removeObserver(o);
	} 
}