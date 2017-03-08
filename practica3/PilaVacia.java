package tp.pr3.logica;

public class PilaVacia extends java.lang.Exception {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Llama a la constructora de Exception con el mensaje a lanzar por la excepción
	 * @param mensaje
	 */
	public PilaVacia(String mensaje) {
		
		super(mensaje);
	}
}
