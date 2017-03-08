package tp.pr4.vistas.Swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import tp.pr4.control.ControlSwing;
import tp.pr4.logica.Ficha;
import tp.pr4.logica.Observador;
import tp.pr4.logica.TableroInmutable;

public class JPanelIzq extends JPanel implements Observador {
	private static final long serialVersionUID = 1L;
	ImageIcon iconoNegro = new ImageIcon("src/tp/pr4/vistas/Swing/iconos/negro.png");
	ImageIcon iconoBlanco = new ImageIcon("src/tp/pr4/vistas/Swing/iconos/blanco.png");

	private ControlSwing control;
	
	private JFrame vtnPrincipal;
	
	private JPanel pnlTab;
	private JPanel pnlFichas;
	private JPanel pnlTurno;
	private JPanel pnlRandom;
	
	private JLabel lblTurno;
	
	private JButton btnRandom;
	private MiBoton btnFicha;
	
	MiBoton[][] botonesTab;
	
	public JPanelIzq (JFrame ventana, ControlSwing c, int filas, int columnas) {
		vtnPrincipal = ventana;
		control = c;
		setLayout(new BorderLayout());
		System.out.println();
		
		/**
		 * CENTRO : Tablero
		 * 
		 * Creamos un Panel Tablero que contiene un Panel con las fichas y un JLabel donde
		 * poner el turno correspondiente
		 * 
		 */
		pnlTab = new JPanel();
		
		add(pnlTab, BorderLayout.CENTER);
		
		pnlTab.setLayout(new BorderLayout(0,40)); 
		pnlTab.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2,2,2,2), BorderFactory.createBevelBorder(BevelBorder.RAISED)));
		pnlTab.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2,2,2,2), BorderFactory.createEtchedBorder(BevelBorder.LOWERED)));

		creaTablero(filas, columnas);

		pnlTurno = new JPanel();
		pnlTurno.setBackground(Color.WHITE);
		pnlTurno.setOpaque(true);
		pnlTab.add(pnlTurno, BorderLayout.SOUTH); // Situacion dentro de Panel Tablero: Zona Sur
		pnlTurno.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2,2,2,2), BorderFactory.createBevelBorder(BevelBorder.RAISED)));
		pnlTurno.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2,2,2,2), BorderFactory.createEtchedBorder(BevelBorder.LOWERED)));
		
		lblTurno = new JLabel("Juegan blancas"); 
		lblTurno.setForeground(Color.BLUE); 
		pnlTurno.add(lblTurno);
		
		/**
		 * SUR : Poner Aleatorio
		 * 
		 * Creamos un Panel que contiene un JButton para crear un movimiento aleatorio
		 * 
		 */
		pnlRandom = new JPanel();
		
		add(pnlRandom, BorderLayout.SOUTH);
		btnRandom = new JButton(new ImageIcon(getClass().getResource("iconos/random.png"))); 
		btnRandom.setText("Poner Aleatorio");
		btnRandom.setPreferredSize(new Dimension(175, 50));
		btnRandom.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		    control.poner(); 
	    	}
    	});
		pnlRandom.add(btnRandom);
		
		setBorder(BorderFactory.createEmptyBorder(2,2,2,2)); 
	}
	
	public class MiBoton extends JButton implements ActionListener {
		private static final long serialVersionUID = 1L;
		private int fila, columna;
		
		public MiBoton(int i, int j) {
			fila = i;
			columna = j;
		}

		public void actionPerformed(ActionEvent e) {
			control.poner(fila, columna);	
		}
		
		public int getFila() {
			
			return fila;
		}
		
		public int getColumna() {
			
			return columna;
		}
	}


	public void onPartidaTerminada(TableroInmutable tabFin, Ficha ganador) {
		
		for (int i = tabFin.getFilas() - 1; i >= 0; i--) { 
			for (int j = 0; j < tabFin.getColumnas(); j++) { 
				botonesTab[i][j].setEnabled(false);
			}
		}
		
		btnRandom.setEnabled(false); 
	
		if (ganador == Ficha.BLANCA)
			lblTurno.setText("Ganan las blancas");

		else if (ganador == Ficha.NEGRA)
			lblTurno.setText("Ganan las negras");

		else
			lblTurno.setText("Partida en Tablas");

		dibujaTab(tabFin); 
	}

	// PRACTICA 5
	public void onMovimientoStart(Ficha turno) { }

	public void onMovimientoEnd(TableroInmutable tab) {
		
		dibujaTab(tab);
	}
	
	public void onUndo(TableroInmutable tab, boolean hayMas) {
		
		dibujaTab(tab);
	}

	public void onResetPartida(TableroInmutable tabIni, Ficha turno) {
		
		btnRandom.setEnabled(true); 
		
		for (int i = tabIni.getFilas() - 1; i >= 0; i--) { 
			for (int j = 0; j < tabIni.getColumnas(); j++) { 
				botonesTab[i][j].setEnabled(true); 
			}
		}
		
		dibujaTab(tabIni);
		estado(turno);
	}

	public void onUndoNotPossible() { }

	public void onCambioTurno(Ficha turno) {
		estado(turno);
	}

	public void onMovimientoIncorrecto(String explicacion) { }

	
	public void onCambioJuego(TableroInmutable tablero, Ficha turno) {
		int filas = tablero.getFilas(); 
		int cols = tablero.getColumnas(); /
		
		btnRandom.setEnabled(true);
		pnlTab.remove(pnlFichas);
		creaTablero(filas, cols);
		pnlTab.revalidate();
		vtnPrincipal.pack(); 
		
		lblTurno.setText("Juegan las blancas"); 
	}
	
	public void estado(Ficha turno) {
		if (turno == Ficha.BLANCA)
			lblTurno.setText("Juegan blancas");
		else 
			lblTurno.setText("Juegan negras");
	}
	
	public void dibujaTab(TableroInmutable tablero) {
		int filas = tablero.getFilas();
		int columnas = tablero.getColumnas();
		
		for (int i = filas - 1; i >= 0; i--) { 
			for (int j = 0; j < columnas; j++) { 
				
				if (tablero.getCasilla(i, j) == Ficha.VACIA)		
					botonesTab[i][j].setBackground(Color.LIGHT_GRAY);

				else if(tablero.getCasilla(i, j) == Ficha.NEGRA)
					botonesTab[i][j].setBackground(Color.BLACK);
				
				else if (tablero.getCasilla(i, j) == Ficha.BLANCA)
					botonesTab[i][j].setBackground(Color.WHITE);
				
				btnFicha.setOpaque(true);
			}
		}
	}
	
	public void creaTablero(int filas, int columnas) {
		pnlFichas = new JPanel();

		pnlFichas.setLayout(new GridLayout(filas,columnas,1,1)); 
		pnlFichas.setBackground(Color.DARK_GRAY); 
		
		botonesTab = new MiBoton[filas][columnas];
		
		for (int i = filas - 1; i >= 0; i--) { 
			for (int j = 0; j < columnas; j++) { 
				btnFicha = new MiBoton(i, j);
				btnFicha.addActionListener(btnFicha); 				
				
				btnFicha.setBackground(Color.LIGHT_GRAY);
				btnFicha.setOpaque(true);
				pnlFichas.add(btnFicha);
				
				botonesTab[i][j] = btnFicha;
			}
		}
		
		pnlTab.add(pnlFichas, BorderLayout.CENTER);
	}
}
