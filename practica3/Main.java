package tp.pr3.control;

import java.util.Scanner;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import tp.pr3.control.factorias.FactoriaJuego;
import tp.pr3.control.factorias.FactoriaJuegoComplica;
import tp.pr3.control.factorias.FactoriaJuegoConecta4;
import tp.pr3.control.factorias.FactoriaJuegoGravity;
import tp.pr3.logica.Partida;

public class Main {

	public static void main(String[] args) {

	// Si no se introduce nada se empieza con CONECTA 4
		FactoriaJuego factoria = new FactoriaJuegoConecta4();
		Partida	partida = new Partida(factoria.creaReglas());
		String juego = null;
		Options options = new Options();
		CommandLineParser parser;
		CommandLine cmdLine = null;

		options.addOption("g", "game", true, "Tipo de juego (c4, co, gr). Por defecto, c4.");
		options.addOption("h", "help", false, "Muestra esta ayuda.");
		options.addOption("x", "tamX", true, "Numero de columnas del tablero (solo para Gravity). Por defecto, 10.");
		options.addOption("y", "tamY", true, "Numero de filas del tablero (solo para Gravity). Por defecto, 10.");

		parser = new BasicParser();

		try {
			cmdLine = parser.parse(options, args);
			String argsNoEntendidos[] = cmdLine.getArgs();
			// PARA MOSTRAR LA AYUDA
			if (argsNoEntendidos.length == 0) {
				if (cmdLine.hasOption("h")) {
					new HelpFormatter().printHelp(Main.class.getCanonicalName(), options); // Muestra options
					System.exit(-1); 
				}

				// PARA JUGAR
				else if (cmdLine.hasOption("g")) {
					juego = cmdLine.getOptionValue("g");

					if (juego.equalsIgnoreCase("c4")) {
						factoria = new FactoriaJuegoConecta4();
						partida = new Partida(factoria.creaReglas());
					}

					else if (juego.equalsIgnoreCase("co")) {
						factoria = new FactoriaJuegoComplica();
						partida = new Partida(factoria.creaReglas());
					}

					else if (juego.equalsIgnoreCase("gr")) {
						if (cmdLine.hasOption("y") && cmdLine.hasOption("x")) {
							int f = 0, c = 0;
							try {
								f = Integer.parseInt(cmdLine.getOptionValue("y"));
								c = Integer.parseInt(cmdLine.getOptionValue("x"));
							} catch (NumberFormatException e) {
								System.err.println("Las dimensiones de Gravity tienen que ser valores numericos");
								System.err.println("Use -h|--help para mas detalles.");
								System.exit(-1);
							}

							if ((f < 0) || (c < 0))
								throw new ParseException("Las dimensiones de Gravity tienen que ser positivas");

							factoria = new FactoriaJuegoGravity(f, c);
							partida = new Partida(factoria.creaReglas());
						}

						else if ((cmdLine.hasOption("y") && !cmdLine.hasOption("x")) || 
								!(cmdLine.hasOption("y") && cmdLine.hasOption("x"))) {

							throw new ParseException(
									"En el juego Gravity hay que introducir 2 dimensiones, 'x' e 'y'");
						}

						else {
							factoria = new FactoriaJuegoGravity();
							partida = new Partida(factoria.creaReglas());
						}
					}

					else
						throw new ParseException("Juego '" + juego + "' incorrecto.");
				}
			}

			else {
				String noEntendidos = "";
				for (int i = 0; i < argsNoEntendidos.length; i++)
					noEntendidos += argsNoEntendidos[i] + " ";
				throw new ParseException("Argumentos no entendidos: "
						+ noEntendidos);
			}

		} catch (org.apache.commons.cli.ParseException e) {
			System.err.println("Uso incorrecto: " + e.getMessage());
			System.err.println("Use -h|--help para mas detalles.");
			System.exit(-1); // PARA SALIR
		}

		Scanner sc = new Scanner(System.in);

		Controlador cont = new Controlador(factoria, partida, sc);

		cont.run();
	}
}
