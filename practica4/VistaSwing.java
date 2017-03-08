package tp.pr4.vistas.Swing;

import java.awt.GridLayout;

import javax.swing.JFrame;

import tp.pr4.control.ControlSwing;

public class VistaSwing extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private ControlSwing control;
	private JPanelDer pnlDer;
	private JPanelIzq pnlIzq;
	
	public VistaSwing(ControlSwing c, int filas, int columnas) {
		super("Practica 4 - TP");
		this.control = c;
	
		
		this.setLayout(new GridLayout(1,2,0,0));
		
		// PANEL IZQUIERDO
		pnlIzq = new JPanelIzq(this, c, filas, columnas);
		
		this.add(pnlIzq);
		control.addObserver(pnlIzq);
		
		
		// PANEL DERECHO
		pnlDer = new JPanelDer(c); 
		
		this.add(pnlDer);
		control.addObserver(pnlDer);
		
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
