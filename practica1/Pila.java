package tp.pr1;

public class Pila {
	private int[] undoStack; // PILA
	private int numUndo;
	public static final int MAX_PILA = 10;
	
	public Pila() {
		numUndo = 0;
		undoStack = new int[MAX_PILA];
	}
	/**
	 * Indica si la pila est� llena.
	 * @return true si el n�mero de elementos de la pila (numUndo) es igual que el m�ximo (MAX_PILA)
	 */
	public boolean estaLlena() {
		
		return (this.numUndo == MAX_PILA);
	}

	/**
	 * Indica si la pila est� vacia
	 * @return true si el n�mero de elemento (numUndo) es cero
	 */
	public boolean estaVacia() {
		
		return (this.numUndo == 0);
	}

	/**
	 * M�todo para apilar movimientos. Introduce en el array pila las columnas donde se han hecho los �ltimos diez
	 * movimientos. Si la pila ya est� llena, todos los elementos se desplazan hacia la izquierda para borrar la
	 * primera columna que se insert� y el �ltimo movimiento se inserta al final de la pila.
	 * @param columna : ultimo movimiento para apilar
	 */
	public void apilar(int columna) {
		if (!estaLlena())
			undoStack[numUndo++] = columna;

		else {
			for (int i = 1; i < numUndo; i++)
				// Desplaza los elementos de la pila hacia la izquierda
				undoStack[i - 1] = undoStack[i];

			undoStack[numUndo - 1] = columna;
		}
	}

	/**
	 * Devuelve lo �ltimo que hay en la pila
	 * @return undoStack[numUndo - 1] : �ltima columna apilada
	 */
	public int obtenerUltimoMov() { 
		
		return undoStack[numUndo - 1];
	}
	
	/**
	 * Una vez que se ha obtenido el �ltimo elemento de la pila, procedemos a borrarlo decrementando el contador
	 */
	public void desapilar() {

		numUndo--;
	}
	
	/**
	 * Al resetear el tablero, tambi�n hay que resetear la pila
	 */
	public void resetPila() {
		numUndo = 0;
	}
}
