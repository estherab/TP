package tp.pr4.logica;

public enum TipoJuego {
	CONECTA4, COMPLICA, GRAVITY;

	public static boolean esRedimensionable(TipoJuego juego) {

		if (juego == TipoJuego.GRAVITY)
			return true;

		else
			return false;
	}
}