package tp.pr4.logica;

public interface Observador {
	
	void onPartidaTerminada(TableroInmutable tabFin, Ficha ganador);
	void onMovimientoStart(Ficha turno);
	void onMovimientoEnd(TableroInmutable tab);
	void onUndo(TableroInmutable tab, boolean hayMas);
	void onResetPartida(TableroInmutable tabIni, Ficha turno);
	void onUndoNotPossible();
	void onCambioTurno(Ficha turno);
	void onMovimientoIncorrecto(String explicacion);
	void onCambioJuego(TableroInmutable tablero, Ficha turno);		
}
