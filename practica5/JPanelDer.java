package tp.pr5.vistas.Swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import tp.pr5.control.ControlSwing;
import tp.pr5.jugadores.TipoTurno;
import tp.pr5.logica.Ficha;
import tp.pr5.logica.Observador;
import tp.pr5.logica.TableroInmutable;
import tp.pr5.logica.TipoJuego;


public class JPanelDer extends JPanel implements Observador {
	private static final long serialVersionUID = 1L;

	private ControlSwing control;
	private VistaSwing vtnPrincipal;

	private JPanel pnlNorte;
	private JPanel pnlPart;
	private JPanel pnlJugadores;
	private JPanel pnlCambioJ;
	private JPanel pnlAceptoCambio;
	private JPanel pnlSalir;
	private JPanel pnlGravity;

	private JButton btnUndo;
	private JButton btnReset;
	private JButton btnCambiar;
	private JButton btnSalir;

	JComboBox<TipoTurno> cboJugBlancas;
	JComboBox<TipoTurno> cboJugNegras;
	JComboBox<TipoJuego> cboJuego;

	private JLabel jNegras;
	private JLabel jBlancas;
	private JLabel filas;
	private JLabel cols;

	private JTextField txtF;
	private JTextField txtC;

	public JPanelDer(VistaSwing ventana, ControlSwing c) {
		control = c;
		vtnPrincipal = ventana;
		
		Dimension dimBtn = new Dimension (150,50);
		Dimension dimTxt = new Dimension (50,20);  
		
		setLayout(new BorderLayout()); 
	
		/** NORTE VENTANA
		 * 
		 * Contiene: panel de partida (pnlPart) y panel de gesti�n de jugadores (pnlJugadores)
		 * Va a constar de un borderLayout para situar los dos paneles que contiene  en dos zonas distinas
		 * 
		 */
		pnlNorte = new JPanel();  
		this.add(pnlNorte, BorderLayout.NORTH);  
		pnlNorte.setLayout(new BorderLayout());   
		
		/**NORTE PANEL NORTE
         *
		 * Contiene: bot�n deshacer (btnUndo) y bot�n reiniciar (btnReset)
		 */
		pnlPart = new JPanel();
		pnlPart.setBorder(BorderFactory.createTitledBorder("Partida")); 
		
		pnlPart.setLayout(new FlowLayout());
		
		btnUndo = new JButton(new ImageIcon(getClass().getResource("iconos/undo.png"))); 
		btnUndo.setText("Deshacer");
		btnUndo.setPreferredSize(dimBtn);
		btnUndo.setEnabled(false); 
        btnUndo.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		    control.undo();
	    	}
    	});
        
		btnReset = new JButton(new ImageIcon(getClass().getResource("iconos/reiniciar.png"))); 
		btnReset.setText("Reiniciar");  
		btnReset.setPreferredSize(dimBtn); 
		btnReset.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		    control.reiniciar();
	    	}
    	});

		pnlPart.add(btnUndo); 
		pnlPart.add(btnReset);
		pnlNorte.add(pnlPart, BorderLayout.NORTH); 
				
		/**SUR PANEL NORTE
		 * 
		 * Contiene: las dos etiquetas con jugador blancas y negras y sus respectivos comboBox
		 * 
		 * Creamos el panel de gesti�n de jugadores
		 * 
		 */
		pnlJugadores = new JPanel();
		
		pnlJugadores.setBorder(BorderFactory.createTitledBorder("Gestion de jugadores")); 
		this.add(pnlJugadores, BorderLayout.AFTER_LAST_LINE); 
		pnlJugadores.setLayout(new GridLayout(2, 2));
		
		jBlancas = new JLabel ("Jugador de blancas");
		cboJugBlancas = new JComboBox<TipoTurno>(TipoTurno.values()); 
		jNegras = new JLabel ("Jugador de negras");
		cboJugNegras = new JComboBox<TipoTurno>(TipoTurno.values());
		
		pnlJugadores.add(jBlancas); 
		pnlJugadores.add(cboJugBlancas);
		
		cboJugBlancas.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		    if  (((TipoTurno)cboJugBlancas.getSelectedItem()) == TipoTurno.AUTOMATICO) 
	    		    	 vtnPrincipal.pongoModoJuego(Ficha.BLANCA, TipoTurno.AUTOMATICO);
	    		    
	    		    else 
	    		    	vtnPrincipal.pongoModoJuego(Ficha.BLANCA, TipoTurno.HUMANO);
	    	}
    	});
		
		pnlJugadores.add(jNegras); 
		pnlJugadores.add(cboJugNegras); 
		
		cboJugNegras.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		    if  (((TipoTurno)cboJugNegras.getSelectedItem()) == TipoTurno.AUTOMATICO) 
	    		    	 vtnPrincipal.pongoModoJuego(Ficha.NEGRA, TipoTurno.AUTOMATICO);
	    		    
	    		    else 
	    		    	vtnPrincipal.pongoModoJuego(Ficha.NEGRA, TipoTurno.HUMANO);
	    	}
    	});
		
		pnlNorte.add(pnlJugadores, BorderLayout.SOUTH); 

		/**CENTRO VENTANA
		 * 
		 * Contiene: panel cambio de juego con un desplegable con todos los juegos y un panel 
		 * con un bot�n para la confirmaci�n del cambio solicitado por el usuario
		 * 
		 */
		pnlCambioJ = new JPanel();
	
		pnlCambioJ.setLayout(new GridLayout(3, 1));
		pnlCambioJ.setBorder(BorderFactory.createTitledBorder("Cambio de Juego")); 
		this.add(pnlCambioJ, BorderLayout.CENTER); 
		
		cboJuego = new JComboBox<TipoJuego>(TipoJuego.values());
		pnlCambioJ.add(cboJuego); 

		pnlGravity = new JPanel();
		
		pnlGravity.setLayout(new FlowLayout());
		
		filas = new JLabel("Filas"); 
		cols = new JLabel("Columnas"); 
		
		txtF = new JTextField(); 
		txtF.setPreferredSize(dimTxt);
		txtC = new JTextField();
		txtC.setPreferredSize(dimTxt);
		
		pnlGravity.add(filas);
		pnlGravity.add(txtF);
		pnlGravity.add(cols);
		pnlGravity.add(txtC);
		
		pnlGravity.setVisible(false);
		
		pnlCambioJ.add(pnlGravity); 
		
		cboJuego.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		    if (TipoJuego.esRedimensionable((TipoJuego) cboJuego.getSelectedItem()))
	    		    	pnlGravity.setVisible(true); 
	    		    else
	    		    	pnlGravity.setVisible(false);
	    	}
    	});
		
		pnlAceptoCambio = new JPanel();
		
		btnCambiar = new JButton(new ImageIcon(getClass().getResource("iconos/aceptar.png"))); 
		btnCambiar.setText("Cambiar"); 
		btnCambiar.setPreferredSize(dimBtn);
		btnCambiar.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		if (TipoJuego.esRedimensionable((TipoJuego) cboJuego.getSelectedItem())) {
	    			int nFi, nCols;
	    			try {
	    				nFi = Integer.parseInt(txtF.getText());
	    				nCols = Integer.parseInt(txtC.getText());
	    			} catch (NumberFormatException exc) {
	    				nFi = 0;
	    				nCols = 0;
	    			}
	    			
	    			if ((nFi > 0) && (nCols > 0))
	    		    	control.reset((TipoJuego) cboJuego.getSelectedItem(), nFi, nCols);
	    			else 
	    				control.reset((TipoJuego) cboJuego.getSelectedItem(), 0, 0);
	    		}
	    		
	    		else
	    			control.reset((TipoJuego) cboJuego.getSelectedItem(), 0, 0);	  
	    	}
    	});
		pnlAceptoCambio.add(btnCambiar); 
		pnlCambioJ.add(pnlAceptoCambio); 
		
		setBorder(BorderFactory.createEmptyBorder(5,20,5,0)); 
		
		/**SUR VENTANA
	     *
		 * Contiene: panel salir con el bot�n salir, para finalizar la jugada y cerrar la aplicaci�n
		 * 
		 */
		pnlSalir = new JPanel();
		pnlSalir.setBorder(BorderFactory.createEmptyBorder(40,30,0,20));
		
		btnSalir = new JButton(new ImageIcon(getClass().getResource("iconos/exit.png"))); 
		btnSalir.setText("Salir"); 
		btnSalir.setPreferredSize(dimBtn); 
		btnSalir.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
    		    quit();
    	}
		});
		pnlSalir.add(btnSalir);
		this.add(pnlSalir, BorderLayout.SOUTH); 
	}

	/**
	 * La partida notifica a los observadores que ha terminado la partida
	 * llamando a este m�todo
	 * 
	 * @param tabFin : tablero final de la partida
	 * @param ganador
	 */
	public void onPartidaTerminada(TableroInmutable tabFin, Ficha ganador) {
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	btnUndo.setEnabled(false);
		    }
		});
	}
 
	/**
	 * Notifica a los observadores que se ha iniciado la ejecuci�n de un
	 * movimiento
	 * 
	 * @param turno
	 */
	public void onMovimientoStart(Ficha turno) { 
		
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	cboJugBlancas.setEnabled(false);
				cboJugNegras.setEnabled(false);
				btnUndo.setEnabled(false);
		    }
		  });
	}

	/**
	 * Notifica a los obsrvadores que se ha terminado de realizar un movimiento
	 * 
	 * @param tab : tablero
	 */
	public void onMovimientoEnd(TableroInmutable tab, Ficha turnoAnterior, Ficha turnoSiguiente) {
		
		vtnPrincipal.terminarModo(turnoAnterior); // Termina la hebra
		vtnPrincipal.comenzarModo(turnoSiguiente); // Comienza la hebra
		
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	
				cboJugBlancas.setEnabled(true);
				cboJugNegras.setEnabled(true);
				btnUndo.setEnabled(true); 
		    }
		});
	}

	/**
	 * Notifica a los observadores que se ha deshecho un movimiento. Proporciona
	 * el estado final del tablero y si hay mas de un movimiento a deshacer o no
	 * 
	 * @param tab : tablero
	 * @param hayMas : true si hay m�s movimientos para deshacer
	 */
	public void onUndo(TableroInmutable tab, boolean hayMas, Ficha turno) {
		
		if (hayMas) { // Si se pueden deshacer mas movimientos, se habilita el boton deshacer
			btnUndo.setEnabled(true);
			vtnPrincipal.deshacerModo(turno);
		}

		else {
			btnUndo.setEnabled(false);
			vtnPrincipal.comenzarModo(turno);
		}
	}

	/**
	 * Notifica que se ha reiniciado la partida, proporcionando a los
	 * observadores el estado inicial del tablero y el color que se pone
	 * primero
	 * 
	 * @param tabIni : tablero inicial
	 * @param turno
	 */
	public void onResetPartida(TableroInmutable tabIni, Ficha turno) {
		
		vtnPrincipal.terminarModo(Ficha.BLANCA); 
		vtnPrincipal.terminarModo(Ficha.NEGRA);
		
		vtnPrincipal.inicializoModoJuego(); 
		
		cboJugBlancas.setSelectedItem(TipoTurno.HUMANO); 
		cboJugNegras.setSelectedItem(TipoTurno.HUMANO);
		
		cboJugBlancas.setEnabled(true);
		cboJugNegras.setEnabled(true);
		btnUndo.setEnabled(false); 
	}
	
	/**
	 * Notifica a los observadores que una operaci�n deshacer no ha tenido �xito
	 */
	public void onUndoNotPossible(Ficha turno) {
		
		btnUndo.setEnabled(false);
		vtnPrincipal.comenzarModo(turno);
	}

	public void onCambioTurno(Ficha turno) { }	

	public void onMovimientoIncorrecto(String explicacion) { }

	/**
	 * Notifica a los observadores que se ha cambiado de juego
	 * 
	 * @param tab : tablero
	 * @param turno
	 */
	public void onCambioJuego(TableroInmutable tablero, Ficha turno) {
		
		vtnPrincipal.terminarModo(Ficha.BLANCA); 
		vtnPrincipal.terminarModo(Ficha.NEGRA);
		
		vtnPrincipal.inicializoModoJuego(); 
		
		cboJugBlancas.setSelectedItem(TipoTurno.HUMANO); 
		cboJugNegras.setSelectedItem(TipoTurno.HUMANO);
		
		cboJugBlancas.setEnabled(true); 
		cboJugNegras.setEnabled(true);
		btnUndo.setEnabled(false); 
	}

	/**
	 * Ventana para salir
	 */
	private void quit() {
		int n = JOptionPane.showOptionDialog(new JFrame(),
				"�Estas seguro de que deseas salir?", "Quit",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				null, null);

		if (n == 0) {
			System.exit(0);
		}
	}
}
