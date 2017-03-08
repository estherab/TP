package tp.pr4.vistas.Swing;

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

import tp.pr4.control.ControlSwing;
import tp.pr4.logica.Ficha;
import tp.pr4.logica.Observador;
import tp.pr4.logica.TableroInmutable;
import tp.pr4.logica.TipoJuego;

public class JPanelDer extends JPanel implements Observador {
	private static final long serialVersionUID = 1L;

	private ControlSwing control;

	private JPanel pnlPart;
	private JPanel pnlCambioJ;
	private JPanel pnlAceptoCambio;
	private JPanel pnlSalir;
	private JPanel pnlGravity;

	private JButton btnUndo;
	private JButton btnReset;
	private JButton btnCambiar;
	private JButton btnSalir;

	JComboBox<TipoJuego> cboJuego;

	private JLabel filas;
	private JLabel cols;

	private JTextField txtF;
	private JTextField txtC;

	public JPanelDer(ControlSwing c) {
		control = c;
		
		Dimension dimBtn = new Dimension (150,50); 
		Dimension dimTxt = new Dimension (50,20);
		
		setLayout(new BorderLayout());
		
	
		/**
		 * NORTE : Panel Partida
		 * 
		 * Creamos el Panel Partida, el cual contiene dos botones: Deshacer y reiniciar
		 */
		
		pnlPart = new JPanel();
		
		pnlPart.setBorder(BorderFactory.createTitledBorder("Partida"));
		this.add(pnlPart, BorderLayout.NORTH); 
		
		//COMPONENTES
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
				
		/**
		 * CENTRO : Panel Cambio de Juego
		 * 
		 * Creamos el Panel Cambio de Juego que contiene un desplegable,
		 * para cambiar de juego con los tres tipos de
		 * Juego existentes hasta ahora. Y otro panel con un boton para la confirmacion del 
		 * cambio solicitado por el usuario.
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
	    			else // Dimensiones por defecto
	    				control.reset((TipoJuego) cboJuego.getSelectedItem(), 0, 0);
	    		}
	    		
	    		else
	    			control.reset((TipoJuego) cboJuego.getSelectedItem(), 0, 0);	  
	    	}
    	});
		pnlAceptoCambio.add(btnCambiar); 
		pnlCambioJ.add(pnlAceptoCambio); 
		
		setBorder(BorderFactory.createEmptyBorder(5,20,5,0)); 
		
		/**
		 * Sur : Panel Salir
		 * 
		 * Creamos el Boton salir, para finalizar la jugada y cerrar la aplicacion
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

	public void onPartidaTerminada(TableroInmutable tabFin, Ficha ganador) {
		btnUndo.setEnabled(false); 
	}

	// PRACTICA 5
	public void onMovimientoStart(Ficha turno) { }

	public void onMovimientoEnd(TableroInmutable tab) {

		btnUndo.setEnabled(true); 
	}

	public void onUndo(TableroInmutable tab, boolean hayMas) {

		if (hayMas) 
			btnUndo.setEnabled(true);

		else
			btnUndo.setEnabled(false);
	}

	public void onResetPartida(TableroInmutable tabIni, Ficha turno) {

		btnUndo.setEnabled(false); 
	}

	public void onUndoNotPossible() { }

	public void onCambioTurno(Ficha turno) { }

	public void onMovimientoIncorrecto(String explicacion) { }

	public void onCambioJuego(TableroInmutable tablero, Ficha turno) {
		btnUndo.setEnabled(false);
	}

	// Ventana para salir
	private void quit() {
		int n = JOptionPane.showOptionDialog(new JFrame(),
				"¿Estas seguro de que quieres salir?", "Quit",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				null, null);

		if (n == 0) {
			System.exit(0);
		}
	}
}
