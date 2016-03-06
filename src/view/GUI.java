package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

import domain.Jucator;
import domain.Judet;

public class GUI {

	private JFrame frame;
	public Jucator jucatorAlbastru;
	public Jucator jucatorRosu;
	private JTextField textTrupeRosu;
	private JTextField textTrupeAlbastru;
	private JButton timisoara;
	private JButton varna;
	private JButton bucuresti;
	private JButton sofia;
	private JButton constanta;
	private JButton balcic;
	private JButton turnAlbastru;
	private JButton turnRosu;
	private JScrollPane scrollPane;
	private JProgressBar graficTrupe;
	private JTextArea consola;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {

		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// Initiam jucatorii
		jucatorAlbastru = new Jucator();
		jucatorAlbastru.listaJudete = new LinkedHashMap<String, Judet>();
		jucatorAlbastru.listaJudete.put("Bucuresti", new Judet("Bucuresti Capitala", 180, false, jucatorAlbastru));
		jucatorAlbastru.listaJudete.put("Timisoara", new Judet("Timisoara", 80, false, jucatorAlbastru));
		jucatorAlbastru.listaJudete.put("Constanta", new Judet("Constanta", 80, false, jucatorAlbastru));
		jucatorRosu = new Jucator();
		jucatorRosu.listaJudete = new LinkedHashMap<String, Judet>();
		jucatorRosu.listaJudete.put("Sofia", new Judet("Sofia Capitala", 180, false, jucatorRosu));
		jucatorRosu.listaJudete.put("Varna", new Judet("Varna", 80, false, jucatorRosu));
		jucatorRosu.listaJudete.put("Balcic", new Judet("Balcic", 80, false, jucatorRosu));

		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 255, 255));
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);

		turnAlbastru = new JButton("End Turn Albastru");
		turnAlbastru.setForeground(Color.GREEN);
		turnAlbastru.setBackground(Color.BLUE);
		GridBagConstraints gbc_btnNewButton_6 = new GridBagConstraints();
		gbc_btnNewButton_6.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_6.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_6.gridx = 0;
		gbc_btnNewButton_6.gridy = 0;
		frame.getContentPane().add(turnAlbastru, gbc_btnNewButton_6);
		turnAlbastru.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Incepe turul jucatorului Rosu, s-a terminat tura jucatorului
				// Albastru
				consola.setText(consola.getText() + "\nIncepe tura jucatorului ROSU");
				timisoara.setEnabled(false);
				bucuresti.setEnabled(false);
				constanta.setEnabled(false);
				activeazaJudeteRos();
				turnAlbastru.setEnabled(false);
				turnRosu.setEnabled(true);
				// Adaugam trupe jucatorului ce a terminat tura
				consola.setText(consola.getText() + "\nJucatorul Albastru primeste trupe");
				adaugaTrupe(jucatorAlbastru);
				// Actualizam datele din frame
				actualizeazaDateFrame();
				textTrupeAlbastru.setText("Trupe Albastru: " + trupeAlbastru());
				// setam text label si bara progress
				graficTrupe.setValue(seteazaValoareBara());
				castigator();
			}
		});

		turnRosu = new JButton("End Turn Rosu");
		turnRosu.setForeground(Color.GREEN);
		turnRosu.setBackground(Color.RED);
		GridBagConstraints gbc_btnNewButton_7 = new GridBagConstraints();
		gbc_btnNewButton_7.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_7.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_7.gridx = 2;
		gbc_btnNewButton_7.gridy = 0;
		frame.getContentPane().add(turnRosu, gbc_btnNewButton_7);
		turnRosu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Incepe turul jucatorului Albastru, s-a terminat tura
				// jucatorului Rosu, se activeaza judetele numai daca nu sunt
				// cucerite
				consola.setText(consola.getText() + "\nIncepe tura jucatorului ALBASTRU");
				activeazaJudeteAlbastru();
				sofia.setEnabled(false);
				varna.setEnabled(false);
				balcic.setEnabled(false);
				turnAlbastru.setEnabled(true);
				turnRosu.setEnabled(false);
				// Adaugam trupe jucatorului ce a terminat tura
				consola.setText(consola.getText() + "\nJucatorul Rosu primeste trupe");
				// System.out.println(seteazaValoareBara()+"alb"+trupeAlbastru()+"ros"+trupeRosu());
				adaugaTrupe(jucatorRosu);
				// Actualizam datele din frame
				actualizeazaDateFrame();
				textTrupeRosu.setText("Trupe Rosu: " + trupeRosu());
				// setam text label si bara progress
				graficTrupe.setValue(seteazaValoareBara());
				castigator();
			}
		});

		textTrupeAlbastru = new JTextField();
		textTrupeAlbastru.setForeground(Color.GREEN);
		textTrupeAlbastru.setBackground(Color.BLUE);
		textTrupeAlbastru.setText("Trupe Albastru: " + trupeAlbastru());
		GridBagConstraints gbc_txtTrupeAlbastru = new GridBagConstraints();
		gbc_txtTrupeAlbastru.insets = new Insets(0, 0, 5, 5);
		gbc_txtTrupeAlbastru.fill = GridBagConstraints.BOTH;
		gbc_txtTrupeAlbastru.gridx = 3;
		gbc_txtTrupeAlbastru.gridy = 0;
		frame.getContentPane().add(textTrupeAlbastru, gbc_txtTrupeAlbastru);
		textTrupeAlbastru.setColumns(10);
		textTrupeAlbastru.setEditable(false);

		graficTrupe = new JProgressBar();
		graficTrupe.setBackground(Color.RED);
		graficTrupe.setForeground(Color.BLUE);
		graficTrupe.setStringPainted(true);
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.fill = GridBagConstraints.BOTH;
		gbc_progressBar.insets = new Insets(0, 0, 5, 5);
		gbc_progressBar.gridx = 4;
		gbc_progressBar.gridy = 0;
		frame.getContentPane().add(graficTrupe, gbc_progressBar);
		graficTrupe.setValue(seteazaValoareBara());

		textTrupeRosu = new JTextField();
		textTrupeRosu.setForeground(Color.GREEN);
		textTrupeRosu.setBackground(Color.RED);
		textTrupeRosu.setText("Trupe Rosu: " + trupeRosu());
		GridBagConstraints gbc_txtTrupeRosu = new GridBagConstraints();
		gbc_txtTrupeRosu.insets = new Insets(0, 0, 5, 0);
		gbc_txtTrupeRosu.fill = GridBagConstraints.BOTH;
		gbc_txtTrupeRosu.gridx = 5;
		gbc_txtTrupeRosu.gridy = 0;
		frame.getContentPane().add(textTrupeRosu, gbc_txtTrupeRosu);
		textTrupeRosu.setColumns(10);
		textTrupeRosu.setEditable(false);

		bucuresti = new JButton(
				"Bucuresti Capitala Trupe: " + jucatorAlbastru.listaJudete.get("Bucuresti").getNumarTrupe());
		bucuresti.setForeground(Color.GREEN);
		bucuresti.setBackground(Color.BLUE);
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.gridx = 0;
		gbc_btnNewButton_1.gridy = 1;
		frame.getContentPane().add(bucuresti, gbc_btnNewButton_1);

		bucuresti.addActionListener(new ActionListener() {
			// TODO Auto-generated method stub
			@Override
			public void actionPerformed(ActionEvent e) {
				bucuresti.setEnabled(false);
				JFrame intrebare = new JFrame();
				intrebare.setVisible(true);
				intrebare.setSize(300, 200);
				intrebare.getContentPane().setBackground(Color.red);
				intrebare.setLocationRelativeTo(null);
				JButton butonTransfer = new JButton("Muta 10 trupe in judetele Aliate !");
				JButton butonAtac = new JButton("Ataca judet inamic !");
				butonAtac.setSize(200, 200);
				butonTransfer.setSize(200, 200);
				intrebare.getContentPane().add(butonTransfer, BorderLayout.NORTH);
				intrebare.getContentPane().add(butonAtac, BorderLayout.SOUTH);
				butonTransfer.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						mutaTrupe("Bucuresti", "Timisoara", jucatorAlbastru);
						mutaTrupe("Bucuresti", "Constanta", jucatorAlbastru);
						timisoara.setText(
								"Timisoara Trupe: " + jucatorAlbastru.listaJudete.get("Timisoara").getNumarTrupe());
						bucuresti.setText("Bucuresti Capitala Trupe: "
								+ jucatorAlbastru.listaJudete.get("Bucuresti").getNumarTrupe());
						constanta.setText(
								"Constanta Trupe: " + jucatorAlbastru.listaJudete.get("Constanta").getNumarTrupe());
						intrebare.setVisible(false);
					}
				});
				butonAtac.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JButton butonAtacSofia = new JButton("Ataca judet Sofia!");
						JButton butonAtacVarna = new JButton("Ataca judet Varna!");
						JButton butonAtacBalcic = new JButton("Ataca judet Balcic!");
						intrebare.setVisible(false);
						JFrame intrebare2 = new JFrame();
						intrebare2.setVisible(true);
						intrebare2.setSize(300, 200);
						intrebare2.getContentPane().setBackground(Color.red);
						intrebare2.setLocationRelativeTo(null);
						if (!jucatorRosu.listaJudete.get("Sofia").isCucerit()) {
							intrebare2.getContentPane().add(butonAtacSofia, BorderLayout.NORTH);
						}
						if (!jucatorRosu.listaJudete.get("Varna").isCucerit()) {
							intrebare2.getContentPane().add(butonAtacVarna, BorderLayout.SOUTH);
						}
						if (!jucatorRosu.listaJudete.get("Balcic").isCucerit()) {
							intrebare2.getContentPane().add(butonAtacBalcic, BorderLayout.CENTER);
						}
						butonAtacSofia.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								consola.setText(consola.getText() + "Ai atacat Sofia!");
								calculeazaRezultatAtacAlbastru("Bucuresti", "Sofia", jucatorAlbastru, jucatorRosu);
								if (jucatorRosu.listaJudete.get("Sofia").isCucerit()) {
									sofia.setEnabled(false);
									sofia.setText("TERITORIU CUCERIT !");
								} else {
									sofia.setText("Sofia Capiatala Trupe: "
											+ jucatorRosu.listaJudete.get("Sofia").getNumarTrupe());
								}
								bucuresti.setText(("Bucuresti Trupe: "
										+ jucatorAlbastru.listaJudete.get("Bucuresti").getNumarTrupe()));
								textTrupeAlbastru.setText("Trupe Albastru: " + trupeAlbastru());
								textTrupeRosu.setText("Trupe Rosu: " + trupeRosu());
								graficTrupe.setValue(seteazaValoareBara());
								intrebare2.setVisible(false);
							}
						});
						butonAtacVarna.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								consola.setText(consola.getText() + "Ai atacat Varna!");
								calculeazaRezultatAtacAlbastru("Bucuresti", "Varna", jucatorAlbastru, jucatorRosu);
								if (jucatorRosu.listaJudete.get("Varna").isCucerit()) {
									varna.setEnabled(false);
									varna.setText("TERITORIU CUCERIT !");
								} else {
									varna.setText(
											"Varna Trupe: " + jucatorRosu.listaJudete.get("Varna").getNumarTrupe());
								}
								bucuresti.setText(("Bucuresti Trupe: "
										+ jucatorAlbastru.listaJudete.get("Bucuresti").getNumarTrupe()));
								textTrupeAlbastru.setText("Trupe Albastru: " + trupeAlbastru());
								textTrupeRosu.setText("Trupe Rosu: " + trupeRosu());
								graficTrupe.setValue(seteazaValoareBara());
								intrebare2.setVisible(false);
							}
						});
						butonAtacBalcic.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								consola.setText(consola.getText() + "Ai atacat Balcic!");
								calculeazaRezultatAtacAlbastru("Bucuresti", "Balcic", jucatorAlbastru, jucatorRosu);
								if (jucatorRosu.listaJudete.get("Balcic").isCucerit()) {
									balcic.setEnabled(false);
									balcic.setText("TERITORIU CUCERIT !");
								} else {
									balcic.setText(
											"Balcic Trupe: " + jucatorRosu.listaJudete.get("Balcic").getNumarTrupe());
								}
								bucuresti.setText(("Bucuresti Trupe: "
										+ jucatorAlbastru.listaJudete.get("Bucuresti").getNumarTrupe()));
								textTrupeAlbastru.setText("Trupe Albastru: " + trupeAlbastru());
								textTrupeRosu.setText("Trupe Rosu: " + trupeRosu());
								graficTrupe.setValue(seteazaValoareBara());
								intrebare2.setVisible(false);
							}
						});
					}
				});

			}
		});
		sofia = new JButton("Sofia Capitala Trupe: " + jucatorRosu.listaJudete.get("Sofia").getNumarTrupe());
		sofia.setForeground(Color.GREEN);
		sofia.setBackground(Color.RED);
		GridBagConstraints gbc_btnNewButton_4 = new GridBagConstraints();
		gbc_btnNewButton_4.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_4.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_4.gridx = 2;
		gbc_btnNewButton_4.gridy = 1;
		frame.getContentPane().add(sofia, gbc_btnNewButton_4);

		sofia.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sofia.setEnabled(false);
				JFrame intrebare = new JFrame();
				intrebare.setVisible(true);
				intrebare.setSize(300, 200);
				intrebare.getContentPane().setBackground(Color.red);
				intrebare.setLocationRelativeTo(null);
				JButton butonTransfer = new JButton("Muta 10 trupe in judetele Aliate !");
				JButton butonAtac = new JButton("Ataca judet inamic !");
				butonAtac.setSize(200, 200);
				butonTransfer.setSize(200, 200);
				intrebare.getContentPane().add(butonTransfer, BorderLayout.NORTH);
				intrebare.getContentPane().add(butonAtac, BorderLayout.SOUTH);
				butonTransfer.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						mutaTrupe("Sofia", "Balcic", jucatorRosu);
						mutaTrupe("Sofia", "Varna", jucatorRosu);
						varna.setText("Varna Trupe: " + jucatorRosu.listaJudete.get("Varna").getNumarTrupe());
						sofia.setText("Sofia Trupe: " + jucatorRosu.listaJudete.get("Sofia").getNumarTrupe());
						balcic.setText("Balcic Trupe: " + jucatorRosu.listaJudete.get("Balcic").getNumarTrupe());
						intrebare.setVisible(false);
					}
				});
				butonAtac.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JButton butonAtacBucuresti = new JButton("Ataca judet Bucuresti!");
						JButton butonAtacTimisoara = new JButton("Ataca judet Timisoara!");
						JButton butonAtacConstanta = new JButton("Ataca judet Constanta!");
						intrebare.setVisible(false);
						JFrame intrebare2 = new JFrame();
						intrebare2.setVisible(true);
						intrebare2.setSize(300, 200);
						intrebare2.getContentPane().setBackground(Color.red);
						intrebare2.setLocationRelativeTo(null);
						if (!jucatorAlbastru.listaJudete.get("Bucuresti").isCucerit()) {
							intrebare2.getContentPane().add(butonAtacBucuresti, BorderLayout.NORTH);
						}
						if (!jucatorAlbastru.listaJudete.get("Timisoara").isCucerit()) {
							intrebare2.getContentPane().add(butonAtacTimisoara, BorderLayout.SOUTH);
						}
						if (!jucatorAlbastru.listaJudete.get("Constanta").isCucerit()) {
							intrebare2.getContentPane().add(butonAtacConstanta, BorderLayout.CENTER);
						}
						butonAtacBucuresti.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								consola.setText(consola.getText() + "Ai atacat Bucuresti!");
								calculeazaRezultatAtacAlbastru("Sofia", "Bucuresti", jucatorRosu, jucatorAlbastru);// atacator,
																													// aparator,
																													// jucator
																													// ce
																													// ataca
																													// si
																													// jucator
																													// ce
																													// ataca
								if (jucatorAlbastru.listaJudete.get("Bucuresti").isCucerit()) {
									bucuresti.setEnabled(false);
									bucuresti.setText("TERITORIU CUCERIT !");
								} else {
									bucuresti.setText("Bucuresti Capiatala Trupe: "
											+ jucatorAlbastru.listaJudete.get("Bucuresti").getNumarTrupe());
								}
								sofia.setText(("Sofia Capitala Trupe: "
										+ jucatorRosu.listaJudete.get("Sofia").getNumarTrupe()));
								textTrupeAlbastru.setText("Trupe Albastru: " + trupeAlbastru());
								textTrupeRosu.setText("Trupe Rosu: " + trupeRosu());
								graficTrupe.setValue(seteazaValoareBara());
								intrebare2.setVisible(false);
							}
						});
						butonAtacTimisoara.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								consola.setText(consola.getText() + "Ai atacat Timisoara!");
								calculeazaRezultatAtacAlbastru("Sofia", "Timisoara", jucatorRosu, jucatorAlbastru);
								if (jucatorAlbastru.listaJudete.get("Timisoara").isCucerit()) {
									timisoara.setEnabled(false);
									timisoara.setText("TERITORIU CUCERIT !");
								} else {
									timisoara.setText("Timisoara Trupe: "
											+ jucatorAlbastru.listaJudete.get("Timisoara").getNumarTrupe());
								}
								sofia.setText(("Sofia Capitala Trupe: "
										+ jucatorRosu.listaJudete.get("Sofia").getNumarTrupe()));
								textTrupeAlbastru.setText("Trupe Albastru: " + trupeAlbastru());
								textTrupeRosu.setText("Trupe Rosu: " + trupeRosu());
								graficTrupe.setValue(seteazaValoareBara());
								intrebare2.setVisible(false);
							}
						});
						butonAtacConstanta.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								consola.setText(consola.getText() + "Ai atacat Constanta!");
								calculeazaRezultatAtacAlbastru("Sofia", "Constanta", jucatorRosu, jucatorAlbastru);
								if (jucatorAlbastru.listaJudete.get("Constanta").isCucerit()) {
									constanta.setEnabled(false);
									constanta.setText("TERITORIU CUCERIT !");
								} else {
									constanta.setText("Constanta Trupe: "
											+ jucatorAlbastru.listaJudete.get("Constanta").getNumarTrupe());
								}
								sofia.setText(("Sofia Capitala Trupe: "
										+ jucatorRosu.listaJudete.get("Sofia").getNumarTrupe()));
								textTrupeAlbastru.setText("Trupe Albastru: " + trupeAlbastru());
								textTrupeRosu.setText("Trupe Rosu: " + trupeRosu());
								graficTrupe.setValue(seteazaValoareBara());
								intrebare2.setVisible(false);
							}
						});
					}
				});

			}
		});
		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setViewportBorder(
				new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(128, 128, 0)));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridheight = 3;
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.gridx = 3;
		gbc_scrollPane.gridy = 1;
		frame.getContentPane().add(scrollPane, gbc_scrollPane);

		consola = new JTextArea();
		consola.setForeground(new Color(0, 0, 128));
		consola.setBackground(new Color(152, 251, 152));
		consola.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		consola.setEditable(false);
		scrollPane.setViewportView(consola);
		consola.setText("Consola");

		constanta = new JButton("Constanta Trupe: " + jucatorAlbastru.listaJudete.get("Constanta").getNumarTrupe());
		constanta.setForeground(Color.GREEN);
		constanta.setBackground(Color.BLUE);
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_2.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_2.gridx = 0;
		gbc_btnNewButton_2.gridy = 2;
		frame.getContentPane().add(constanta, gbc_btnNewButton_2);

		constanta.addActionListener(new ActionListener() {
			// TODO Auto-generated method stub
			@Override
			public void actionPerformed(ActionEvent e) {
				constanta.setEnabled(false);
				JFrame intrebare = new JFrame();
				intrebare.setVisible(true);
				intrebare.setSize(300, 200);
				intrebare.getContentPane().setBackground(Color.red);
				intrebare.setLocationRelativeTo(null);
				JButton butonTransfer = new JButton("Muta 10 trupe in judetele Aliate !");
				JButton butonAtac = new JButton("Ataca judet inamic !");
				butonAtac.setSize(200, 200);
				butonTransfer.setSize(200, 200);
				intrebare.getContentPane().add(butonTransfer, BorderLayout.NORTH);
				intrebare.getContentPane().add(butonAtac, BorderLayout.SOUTH);
				butonTransfer.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						mutaTrupe("Constanta", "Bucuresti", jucatorAlbastru);
						mutaTrupe("Constanta", "Timisoara", jucatorAlbastru);
						timisoara.setText(
								"Timisoara Trupe: " + jucatorAlbastru.listaJudete.get("Timisoara").getNumarTrupe());
						bucuresti.setText("Bucuresti Capitala Trupe: "
								+ jucatorAlbastru.listaJudete.get("Bucuresti").getNumarTrupe());
						constanta.setText(
								"Constanta Trupe: " + jucatorAlbastru.listaJudete.get("Constanta").getNumarTrupe());
						intrebare.setVisible(false);
					}
				});
				butonAtac.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JButton butonAtacSofia = new JButton("Ataca judet Sofia!");
						JButton butonAtacVarna = new JButton("Ataca judet Varna!");
						JButton butonAtacBalcic = new JButton("Ataca judet Balcic!");
						intrebare.setVisible(false);
						JFrame intrebare2 = new JFrame();
						intrebare2.setVisible(true);
						intrebare2.setSize(300, 200);
						intrebare2.getContentPane().setBackground(Color.red);
						intrebare2.setLocationRelativeTo(null);
						if (!jucatorRosu.listaJudete.get("Sofia").isCucerit()) {
							intrebare2.getContentPane().add(butonAtacSofia, BorderLayout.NORTH);
						}
						if (!jucatorRosu.listaJudete.get("Varna").isCucerit()) {
							intrebare2.getContentPane().add(butonAtacVarna, BorderLayout.SOUTH);
						}
						if (!jucatorRosu.listaJudete.get("Balcic").isCucerit()) {
							intrebare2.getContentPane().add(butonAtacBalcic, BorderLayout.CENTER);
						}
						butonAtacSofia.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								consola.setText(consola.getText() + "Ai atacat Sofia!");
								calculeazaRezultatAtacAlbastru("Constanta", "Sofia", jucatorAlbastru, jucatorRosu);
								if (jucatorRosu.listaJudete.get("Sofia").isCucerit()) {
									sofia.setEnabled(false);
									sofia.setText("TERITORIU CUCERIT !");
								} else {
									sofia.setText("Sofia Capiatala Trupe: "
											+ jucatorRosu.listaJudete.get("Sofia").getNumarTrupe());
								}
								constanta.setText(("Constanta Trupe: "
										+ jucatorAlbastru.listaJudete.get("Constanta").getNumarTrupe()));
								textTrupeAlbastru.setText("Trupe Albastru: " + trupeAlbastru());
								textTrupeRosu.setText("Trupe Rosu: " + trupeRosu());
								graficTrupe.setValue(seteazaValoareBara());
								intrebare2.setVisible(false);
							}
						});
						butonAtacVarna.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								consola.setText(consola.getText() + "Ai atacat Varna!");
								calculeazaRezultatAtacAlbastru("Constanta", "Varna", jucatorAlbastru, jucatorRosu);
								if (jucatorRosu.listaJudete.get("Varna").isCucerit()) {
									varna.setEnabled(false);
									varna.setText("TERITORIU CUCERIT !");
								} else {
									varna.setText(
											"Varna Trupe: " + jucatorRosu.listaJudete.get("Varna").getNumarTrupe());
								}
								constanta.setText(("Constanta Trupe: "
										+ jucatorAlbastru.listaJudete.get("Constanta").getNumarTrupe()));
								textTrupeAlbastru.setText("Trupe Albastru: " + trupeAlbastru());
								textTrupeRosu.setText("Trupe Rosu: " + trupeRosu());
								graficTrupe.setValue(seteazaValoareBara());
								intrebare2.setVisible(false);
							}
						});
						butonAtacBalcic.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								consola.setText(consola.getText() + "Ai atacat Balcic!");
								calculeazaRezultatAtacAlbastru("Constanta", "Balcic", jucatorAlbastru, jucatorRosu);
								if (jucatorRosu.listaJudete.get("Balcic").isCucerit()) {
									balcic.setEnabled(false);
									balcic.setText("TERITORIU CUCERIT !");
								} else {
									balcic.setText(
											"Balcic Trupe: " + jucatorRosu.listaJudete.get("Balcic").getNumarTrupe());
								}
								constanta.setText(("Constanta Trupe: "
										+ jucatorAlbastru.listaJudete.get("Constanta").getNumarTrupe()));
								textTrupeAlbastru.setText("Trupe Albastru: " + trupeAlbastru());
								textTrupeRosu.setText("Trupe Rosu: " + trupeRosu());
								graficTrupe.setValue(seteazaValoareBara());
								intrebare2.setVisible(false);
							}
						});
					}
				});

			}
		});
		balcic = new JButton("Balcic Trupe: " + jucatorRosu.listaJudete.get("Balcic").getNumarTrupe());
		balcic.setForeground(Color.GREEN);
		balcic.setBackground(Color.RED);
		GridBagConstraints gbc_btnNewButton_5 = new GridBagConstraints();
		gbc_btnNewButton_5.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_5.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_5.gridx = 2;
		gbc_btnNewButton_5.gridy = 2;
		frame.getContentPane().add(balcic, gbc_btnNewButton_5);

		balcic.addActionListener(new ActionListener() {
			// TODO Auto-generated method stub
			@Override
			public void actionPerformed(ActionEvent e) {
				balcic.setEnabled(false);
				JFrame intrebare = new JFrame();
				intrebare.setVisible(true);
				intrebare.setSize(300, 200);
				intrebare.getContentPane().setBackground(Color.red);
				intrebare.setLocationRelativeTo(null);
				JButton butonTransfer = new JButton("Muta 10 trupe in judetele Aliate !");
				JButton butonAtac = new JButton("Ataca judet inamic !");
				butonAtac.setSize(200, 200);
				butonTransfer.setSize(200, 200);
				intrebare.getContentPane().add(butonTransfer, BorderLayout.NORTH);
				intrebare.getContentPane().add(butonAtac, BorderLayout.SOUTH);
				butonTransfer.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						mutaTrupe("Balcic", "Sofia", jucatorRosu);
						mutaTrupe("Balcic", "Varna", jucatorRosu);
						varna.setText("Varna Trupe: " + jucatorRosu.listaJudete.get("Varna").getNumarTrupe());
						sofia.setText("Sofia Trupe: " + jucatorRosu.listaJudete.get("Sofia").getNumarTrupe());
						balcic.setText("Balcic Trupe: " + jucatorRosu.listaJudete.get("Balcic").getNumarTrupe());
						intrebare.setVisible(false);
					}
				});
				butonAtac.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JButton butonAtacBucuresti = new JButton("Ataca judet Bucuresti!");
						JButton butonAtacTimisoara = new JButton("Ataca judet Timisoara!");
						JButton butonAtacConstanta = new JButton("Ataca judet Constanta!");
						intrebare.setVisible(false);
						JFrame intrebare2 = new JFrame();
						intrebare2.setVisible(true);
						intrebare2.setSize(300, 200);
						intrebare2.getContentPane().setBackground(Color.red);
						intrebare2.setLocationRelativeTo(null);
						if (!jucatorAlbastru.listaJudete.get("Bucuresti").isCucerit()) {
							intrebare2.getContentPane().add(butonAtacBucuresti, BorderLayout.NORTH);
						}
						if (!jucatorAlbastru.listaJudete.get("Timisoara").isCucerit()) {
							intrebare2.getContentPane().add(butonAtacTimisoara, BorderLayout.SOUTH);
						}
						if (!jucatorAlbastru.listaJudete.get("Constanta").isCucerit()) {
							intrebare2.getContentPane().add(butonAtacConstanta, BorderLayout.CENTER);
						}
						butonAtacBucuresti.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								consola.setText(consola.getText() + "Ai atacat Bucuresti!");
								calculeazaRezultatAtacAlbastru("Balcic", "Bucuresti", jucatorRosu, jucatorAlbastru);
								if (jucatorAlbastru.listaJudete.get("Bucuresti").isCucerit()) {
									bucuresti.setEnabled(false);
									bucuresti.setText("TERITORIU CUCERIT !");
								} else {
									bucuresti.setText("Bucuresti Capiatala Trupe: "
											+ jucatorAlbastru.listaJudete.get("Bucuresti").getNumarTrupe());
								}
								balcic.setText(
										("Balcic Trupe: " + jucatorRosu.listaJudete.get("Balcic").getNumarTrupe()));
								textTrupeAlbastru.setText("Trupe Albastru: " + trupeAlbastru());
								textTrupeRosu.setText("Trupe Rosu: " + trupeRosu());
								graficTrupe.setValue(seteazaValoareBara());
								intrebare2.setVisible(false);
							}
						});
						butonAtacTimisoara.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								consola.setText(consola.getText() + "Ai atacat Timisoara!");
								calculeazaRezultatAtacAlbastru("Balcic", "Timisoara", jucatorRosu, jucatorAlbastru);
								if (jucatorAlbastru.listaJudete.get("Timisoara").isCucerit()) {
									timisoara.setEnabled(false);
									timisoara.setText("TERITORIU CUCERIT !");
								} else {
									timisoara.setText("Timisoara Trupe: "
											+ jucatorAlbastru.listaJudete.get("Timisoara").getNumarTrupe());
								}
								balcic.setText(
										("Balcic Trupe: " + jucatorRosu.listaJudete.get("Balcic").getNumarTrupe()));
								textTrupeAlbastru.setText("Trupe Albastru: " + trupeAlbastru());
								textTrupeRosu.setText("Trupe Rosu: " + trupeRosu());
								graficTrupe.setValue(seteazaValoareBara());
								intrebare2.setVisible(false);
							}
						});
						butonAtacConstanta.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								consola.setText(consola.getText() + "Ai atacat Constanta!");
								calculeazaRezultatAtacAlbastru("Balcic", "Constanta", jucatorRosu, jucatorAlbastru);
								if (jucatorAlbastru.listaJudete.get("Constanta").isCucerit()) {
									constanta.setEnabled(false);
									constanta.setText("TERITORIU CUCERIT !");
								} else {
									constanta.setText("Constanta Trupe: "
											+ jucatorAlbastru.listaJudete.get("Constanta").getNumarTrupe());
								}
								balcic.setText(
										("Balcic Trupe: " + jucatorRosu.listaJudete.get("Balcic").getNumarTrupe()));
								textTrupeAlbastru.setText("Trupe Albastru: " + trupeAlbastru());
								textTrupeRosu.setText("Trupe Rosu: " + trupeRosu());
								graficTrupe.setValue(seteazaValoareBara());
								intrebare2.setVisible(false);
							}
						});
					}
				});

			}
		});
		timisoara = new JButton("Timisoara Trupe: " + jucatorAlbastru.listaJudete.get("Timisoara").getNumarTrupe());
		timisoara.setForeground(Color.GREEN);
		timisoara.setBackground(Color.BLUE);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 3;
		frame.getContentPane().add(timisoara, gbc_btnNewButton);

		timisoara.addActionListener(new ActionListener() {
			// TODO Auto-generated method stub
			@Override
			public void actionPerformed(ActionEvent e) {
				timisoara.setEnabled(false);
				JFrame intrebare = new JFrame();
				intrebare.setVisible(true);
				intrebare.setSize(300, 200);
				intrebare.getContentPane().setBackground(Color.red);
				intrebare.setLocationRelativeTo(null);
				JButton butonTransfer = new JButton("Muta 10 trupe in judetele Aliate !");
				JButton butonAtac = new JButton("Ataca judet inamic !");
				butonAtac.setSize(200, 200);
				butonTransfer.setSize(200, 200);
				intrebare.getContentPane().add(butonTransfer, BorderLayout.NORTH);
				intrebare.getContentPane().add(butonAtac, BorderLayout.SOUTH);
				butonTransfer.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						mutaTrupe("Timisoara", "Bucuresti", jucatorAlbastru);
						mutaTrupe("Timisoara", "Constanta", jucatorAlbastru);
						timisoara.setText(
								"Timisoara Trupe: " + jucatorAlbastru.listaJudete.get("Timisoara").getNumarTrupe());
						bucuresti.setText("Bucuresti Capitala Trupe: "
								+ jucatorAlbastru.listaJudete.get("Bucuresti").getNumarTrupe());
						constanta.setText(
								"Constanta Trupe: " + jucatorAlbastru.listaJudete.get("Constanta").getNumarTrupe());
						intrebare.setVisible(false);
					}
				});
				butonAtac.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JButton butonAtacSofia = new JButton("Ataca judet Sofia!");
						JButton butonAtacVarna = new JButton("Ataca judet Varna!");
						JButton butonAtacBalcic = new JButton("Ataca judet Balcic!");
						intrebare.setVisible(false);
						JFrame intrebare2 = new JFrame();
						intrebare2.setVisible(true);
						intrebare2.setSize(300, 200);
						intrebare2.getContentPane().setBackground(Color.red);
						intrebare2.setLocationRelativeTo(null);
						if (!jucatorRosu.listaJudete.get("Sofia").isCucerit()) {
							intrebare2.getContentPane().add(butonAtacSofia, BorderLayout.NORTH);
						}
						if (!jucatorRosu.listaJudete.get("Varna").isCucerit()) {
							intrebare2.getContentPane().add(butonAtacVarna, BorderLayout.SOUTH);
						}
						if (!jucatorRosu.listaJudete.get("Balcic").isCucerit()) {
							intrebare2.getContentPane().add(butonAtacBalcic, BorderLayout.CENTER);
						}
						butonAtacSofia.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								consola.setText(consola.getText() + "Ai atacat Sofia!");
								calculeazaRezultatAtacAlbastru("Timisoara", "Sofia", jucatorAlbastru, jucatorRosu);
								if (jucatorRosu.listaJudete.get("Sofia").isCucerit()) {
									sofia.setEnabled(false);
									sofia.setText("TERITORIU CUCERIT !");
								} else {
									sofia.setText("Sofia Capiatala Trupe: "
											+ jucatorRosu.listaJudete.get("Sofia").getNumarTrupe());
								}
								timisoara.setText(("Timisoara Trupe: "
										+ jucatorAlbastru.listaJudete.get("Timisoara").getNumarTrupe()));
								textTrupeAlbastru.setText("Trupe Albastru: " + trupeAlbastru());
								textTrupeRosu.setText("Trupe Rosu: " + trupeRosu());
								graficTrupe.setValue(seteazaValoareBara());
								intrebare2.setVisible(false);
							}
						});
						butonAtacVarna.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								consola.setText(consola.getText() + "Ai atacat Varna!");
								calculeazaRezultatAtacAlbastru("Timisoara", "Varna", jucatorAlbastru, jucatorRosu);
								if (jucatorRosu.listaJudete.get("Varna").isCucerit()) {
									varna.setEnabled(false);
									varna.setText("TERITORIU CUCERIT !");
								} else {
									varna.setText(
											"Varna Trupe: " + jucatorRosu.listaJudete.get("Varna").getNumarTrupe());
								}
								timisoara.setText(("Timisoara Trupe: "
										+ jucatorAlbastru.listaJudete.get("Timisoara").getNumarTrupe()));
								textTrupeAlbastru.setText("Trupe Albastru: " + trupeAlbastru());
								textTrupeRosu.setText("Trupe Rosu: " + trupeRosu());
								graficTrupe.setValue(seteazaValoareBara());
								intrebare2.setVisible(false);
							}
						});
						butonAtacBalcic.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								consola.setText(consola.getText() + "Ai atacat Balcic!");
								calculeazaRezultatAtacAlbastru("Timisoara", "Balcic", jucatorAlbastru, jucatorRosu);
								if (jucatorRosu.listaJudete.get("Balcic").isCucerit()) {
									balcic.setEnabled(false);
									balcic.setText("TERITORIU CUCERIT !");
								} else {
									balcic.setText(
											"Balcic Trupe: " + jucatorRosu.listaJudete.get("Balcic").getNumarTrupe());
								}
								timisoara.setText(("Timisoara Trupe: "
										+ jucatorAlbastru.listaJudete.get("Timisoara").getNumarTrupe()));
								textTrupeAlbastru.setText("Trupe Albastru: " + trupeAlbastru());
								textTrupeRosu.setText("Trupe Rosu: " + trupeRosu());
								graficTrupe.setValue(seteazaValoareBara());
								intrebare2.setVisible(false);
							}
						});
					}
				});

			}
		});
		varna = new JButton("Varna Trupe: " + jucatorRosu.listaJudete.get("Varna").getNumarTrupe());
		varna.setForeground(Color.GREEN);
		varna.setBackground(Color.RED);
		GridBagConstraints gbc_btnNewButton_3 = new GridBagConstraints();
		gbc_btnNewButton_3.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_3.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_3.gridx = 2;
		gbc_btnNewButton_3.gridy = 3;
		frame.getContentPane().add(varna, gbc_btnNewButton_3);

		varna.addActionListener(new ActionListener() {
			// TODO Auto-generated method stub
			@Override
			public void actionPerformed(ActionEvent e) {
				varna.setEnabled(false);
				JFrame intrebare = new JFrame();
				intrebare.setVisible(true);
				intrebare.setSize(300, 200);
				intrebare.getContentPane().setBackground(Color.red);
				intrebare.setLocationRelativeTo(null);
				JButton butonTransfer = new JButton("Muta 10 trupe in judetele Aliate !");
				JButton butonAtac = new JButton("Ataca judet inamic !");
				butonAtac.setSize(200, 200);
				butonTransfer.setSize(200, 200);
				intrebare.getContentPane().add(butonTransfer, BorderLayout.NORTH);
				intrebare.getContentPane().add(butonAtac, BorderLayout.SOUTH);
				butonTransfer.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						mutaTrupe("Varna", "Sofia", jucatorRosu);
						mutaTrupe("Varna", "Balcic", jucatorRosu);
						varna.setText("Varna Trupe: " + jucatorRosu.listaJudete.get("Varna").getNumarTrupe());
						sofia.setText("Sofia Trupe: " + jucatorRosu.listaJudete.get("Sofia").getNumarTrupe());
						balcic.setText("Balcic Trupe: " + jucatorRosu.listaJudete.get("Balcic").getNumarTrupe());
						intrebare.setVisible(false);
					}
				});
				butonAtac.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JButton butonAtacBucuresti = new JButton("Ataca judet Bucuresti!");
						JButton butonAtacTimisoara = new JButton("Ataca judet Timisoara!");
						JButton butonAtacConstanta = new JButton("Ataca judet Constanta!");
						intrebare.setVisible(false);
						JFrame intrebare2 = new JFrame();
						intrebare2.setVisible(true);
						intrebare2.setSize(300, 200);
						intrebare2.getContentPane().setBackground(Color.red);
						intrebare2.setLocationRelativeTo(null);
						if (!jucatorAlbastru.listaJudete.get("Bucuresti").isCucerit()) {
							intrebare2.getContentPane().add(butonAtacBucuresti, BorderLayout.NORTH);
						}
						if (!jucatorAlbastru.listaJudete.get("Timisoara").isCucerit()) {
							intrebare2.getContentPane().add(butonAtacTimisoara, BorderLayout.SOUTH);
						}
						if (!jucatorAlbastru.listaJudete.get("Constanta").isCucerit()) {
							intrebare2.getContentPane().add(butonAtacConstanta, BorderLayout.CENTER);
						}
						butonAtacBucuresti.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								consola.setText(consola.getText() + "Ai atacat Bucuresti!");
								calculeazaRezultatAtacAlbastru("Varna", "Bucuresti", jucatorRosu, jucatorAlbastru);
								if (jucatorAlbastru.listaJudete.get("Bucuresti").isCucerit()) {
									bucuresti.setEnabled(false);
									bucuresti.setText("TERITORIU CUCERIT !");
								} else {
									bucuresti.setText("Bucuresti Capiatala Trupe: "
											+ jucatorAlbastru.listaJudete.get("Bucuresti").getNumarTrupe());
								}
								varna.setText(("Varna Trupe: " + jucatorRosu.listaJudete.get("Varna").getNumarTrupe()));
								textTrupeAlbastru.setText("Trupe Albastru: " + trupeAlbastru());
								textTrupeRosu.setText("Trupe Rosu: " + trupeRosu());
								graficTrupe.setValue(seteazaValoareBara());
								intrebare2.setVisible(false);
							}
						});
						butonAtacTimisoara.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								consola.setText(consola.getText() + "Ai atacat Timisoara!");
								calculeazaRezultatAtacAlbastru("Varna", "Timisoara", jucatorRosu, jucatorAlbastru);
								if (jucatorAlbastru.listaJudete.get("Timisoara").isCucerit()) {
									timisoara.setEnabled(false);
									timisoara.setText("TERITORIU CUCERIT !");
								} else {
									timisoara.setText("Timisoara Trupe: "
											+ jucatorAlbastru.listaJudete.get("Timisoara").getNumarTrupe());
								}
								varna.setText(("Varna Trupe: " + jucatorRosu.listaJudete.get("Varna").getNumarTrupe()));
								textTrupeAlbastru.setText("Trupe Albastru: " + trupeAlbastru());
								textTrupeRosu.setText("Trupe Rosu: " + trupeRosu());
								graficTrupe.setValue(seteazaValoareBara());
								intrebare2.setVisible(false);
							}
						});
						butonAtacConstanta.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								consola.setText(consola.getText() + "Ai atacat Constanta!");
								calculeazaRezultatAtacAlbastru("Varna", "Constanta", jucatorRosu, jucatorAlbastru);
								if (jucatorAlbastru.listaJudete.get("Constanta").isCucerit()) {
									constanta.setEnabled(false);
									constanta.setText("TERITORIU CUCERIT !");
								} else {
									constanta.setText("Constanta Trupe: "
											+ jucatorAlbastru.listaJudete.get("Constanta").getNumarTrupe());
								}
								varna.setText(("Varna Trupe: " + jucatorRosu.listaJudete.get("Varna").getNumarTrupe()));
								textTrupeAlbastru.setText("Trupe Albastru: " + trupeAlbastru());
								textTrupeRosu.setText("Trupe Rosu: " + trupeRosu());
								graficTrupe.setValue(seteazaValoareBara());
								intrebare2.setVisible(false);
							}
						});
					}
				});

			}
		});

	}

	public void castigator() {
		if (jucatorAlbastru.listaJudete.get("Bucuresti").isCucerit()
				&& jucatorAlbastru.listaJudete.get("Timisoara").isCucerit()
				&& jucatorAlbastru.listaJudete.get("Constanta").isCucerit()) {
			consola.setText("Jocul s-a terminat\nJUCATORUL ROSU CASTIGA ! ");

		} else if (jucatorRosu.listaJudete.get("Sofia").isCucerit() && jucatorRosu.listaJudete.get("Balcic").isCucerit()
				&& jucatorRosu.listaJudete.get("Varna").isCucerit()) {
			consola.setText("Jocul s-a terminat\nJUCATORUL ALBASTRU CASTIGA ! ");
		}
	}

	public void adaugaTrupe(Jucator jucator) {
		// Se aloca resurse fiecarui jucator in parte in functie de jucator, si
		// in fuctie de faptul daca judetul a fost sau nu cucerit, daca e
		// cucerit nu primeste resurse
		if (jucator.equals(jucatorAlbastru)) {
			for (String den : jucatorAlbastru.listaJudete.keySet()) {
				if (!jucatorAlbastru.listaJudete.get(den).isCucerit()) {
					jucatorAlbastru.listaJudete.get(den)
							.setNumarTrupe(jucatorAlbastru.listaJudete.get(den).getNumarTrupe() + 10);
				}
			}
		} else {
			for (String den : jucatorRosu.listaJudete.keySet()) {
				if (!jucatorRosu.listaJudete.get(den).isCucerit()) {
					jucatorRosu.listaJudete.get(den)
							.setNumarTrupe(jucatorRosu.listaJudete.get(den).getNumarTrupe() + 10);
				}
			}

		}
	}

	public void mutaTrupe(String sursa, String destinatie, Jucator jucator) {
		// Se realizeaz mutarea de trupe daca si numai daca sunt suficiente
		// trupe
		int disponibilitati = 0;
		if (jucator.equals(jucatorAlbastru)) {
			disponibilitati = jucatorAlbastru.listaJudete.get(sursa).getNumarTrupe();
			if (disponibilitati > 10) {
				jucatorAlbastru.listaJudete.get(sursa)
						.setNumarTrupe(jucatorAlbastru.listaJudete.get(sursa).getNumarTrupe() - 10);
				jucatorAlbastru.listaJudete.get(destinatie)
						.setNumarTrupe(jucatorAlbastru.listaJudete.get(destinatie).getNumarTrupe() + 10);
			}
		}
		if (jucator.equals(jucatorRosu)) {
			disponibilitati = jucatorRosu.listaJudete.get(sursa).getNumarTrupe();
			if (disponibilitati > 10) {
				jucatorRosu.listaJudete.get(sursa).setNumarTrupe(disponibilitati - 10);
				jucatorRosu.listaJudete.get(destinatie)
						.setNumarTrupe(jucatorRosu.listaJudete.get(destinatie).getNumarTrupe() + 10);
			}
		}
	}

	public void activeazaJudeteRos() {
		// activam jutdetele rosu daca nu sunt cucerite
		if (!jucatorRosu.listaJudete.get("Sofia").isCucerit()) {
			sofia.setEnabled(true);
		}
		if (!jucatorRosu.listaJudete.get("Varna").isCucerit()) {
			varna.setEnabled(true);
		}
		if (!jucatorRosu.listaJudete.get("Balcic").isCucerit()) {
			balcic.setEnabled(true);
		}
	}

	public void activeazaJudeteAlbastru() {
		// activam jutdetele Albastru daca nu sunt cucerite
		if (!jucatorAlbastru.listaJudete.get("Timisoara").isCucerit()) {
			timisoara.setEnabled(true);
		}
		if (!jucatorAlbastru.listaJudete.get("Bucuresti").isCucerit()) {
			bucuresti.setEnabled(true);
		}
		if (!jucatorAlbastru.listaJudete.get("Constanta").isCucerit()) {
			constanta.setEnabled(true);
		}
	}

	public double calculeazaBonusAtac() {
		// Metoda ce realizeaza un calcul simplu bazat pe functia random a unui
		// modificator de atac
		Random random = new Random();
		int i = random.nextInt(100);
		if (i > 80) {
			// bonus trupe + 40%
			// De afisat in consola ce bonus s-a obtinut in atac
			consola.setText(consola.getText() + "\nbonus trupe + 40%");
			return 1.4;
		} else if (i > 60) {
			// bonus trupe + 20%
			consola.setText(consola.getText() + "\nbonus trupe + 20%");
			return 1.2;
		} else if (i > 40) {
			// bonus neutru
			consola.setText(consola.getText() + "\nbonus neutru");
			return 1;
		} else if (i > 20) {
			// penalizare -20%
			consola.setText(consola.getText() + "\npenalizare -20%");
			return 0.8;
		} else {
			// penalizare -40%
			consola.setText(consola.getText() + "\npenalizare -40%");
			return 0.6;
		}
	}

	public void actualizeazaDateFrame() {
		// se verifica daca este cucerit teritoriul si se actualizeaza datele
		// din frame
		if (!jucatorRosu.listaJudete.get("Sofia").isCucerit()) {
			sofia.setText("Sofia Trupe: " + jucatorRosu.listaJudete.get("Sofia").getNumarTrupe());
		}
		if (!jucatorRosu.listaJudete.get("Varna").isCucerit()) {
			varna.setText("Varna Trupe: " + jucatorRosu.listaJudete.get("Varna").getNumarTrupe());
		}
		if (!jucatorRosu.listaJudete.get("Balcic").isCucerit()) {
			balcic.setText("Balcic Trupe: " + jucatorRosu.listaJudete.get("Balcic").getNumarTrupe());
		}
		if (!jucatorAlbastru.listaJudete.get("Timisoara").isCucerit()) {
			timisoara.setText(("Timisoara Trupe: " + jucatorAlbastru.listaJudete.get("Timisoara").getNumarTrupe()));
		}
		if (!jucatorAlbastru.listaJudete.get("Constanta").isCucerit()) {
			constanta.setText(("Constanta Trupe: " + jucatorAlbastru.listaJudete.get("Constanta").getNumarTrupe()));
		}
		if (!jucatorAlbastru.listaJudete.get("Bucuresti").isCucerit()) {
			bucuresti.setText(
					("Bucuresti Capitala Trupe: " + jucatorAlbastru.listaJudete.get("Bucuresti").getNumarTrupe()));
		}

	}

	public void calculeazaRezultatAtacAlbastru(String atac, String aparare, Jucator jucatorAtac,
			Jucator jucatorAparare) {
		int rezultatBonus = (int) (jucatorAtac.listaJudete.get(atac).getNumarTrupe() * calculeazaBonusAtac());
		int rezultat = jucatorAparare.listaJudete.get(aparare).getNumarTrupe() - rezultatBonus;
		if (rezultat > 0) {
			consola.setText(consola.getText() + "\nAtac Esuat!");
			jucatorAparare.listaJudete.get(aparare).setNumarTrupe(rezultat);
			jucatorAtac.listaJudete.get(atac).setNumarTrupe(1);
		} else if (rezultat == 0) {
			consola.setText(consola.getText() + "\nRemiza!");
			jucatorAtac.listaJudete.get(atac).setNumarTrupe(1);
			jucatorAparare.listaJudete.get(aparare).setNumarTrupe(1);
		} else if (rezultat < 0) {
			consola.setText(consola.getText() + "\nAtac reusit, asezare cucerita!");
			jucatorAparare.listaJudete.get(aparare).setCucerit(true);
			jucatorAparare.listaJudete.get(aparare).setJucator(jucatorAlbastru);
			jucatorAparare.listaJudete.get(aparare).setNumarTrupe(0);
			jucatorAtac.listaJudete.get(atac).setNumarTrupe(-rezultat);
		}
	}

	public int trupeAlbastru() {
		int totalTrupeAlbastru = 0;
		for (String den : jucatorAlbastru.listaJudete.keySet()) {
			totalTrupeAlbastru += jucatorAlbastru.listaJudete.get(den).getNumarTrupe();
		}
		return totalTrupeAlbastru;
	}

	public int trupeRosu() {
		int totalTrupeRosu = 0;
		for (String den : jucatorRosu.listaJudete.keySet()) {
			totalTrupeRosu += jucatorRosu.listaJudete.get(den).getNumarTrupe();
		}
		return totalTrupeRosu;
	}

	public int seteazaValoareBara() {
		int totalTrupejoc = 0;
		int totalTrupeAlbastru = trupeAlbastru();
		int totalTrupeRosu = trupeRosu();
		totalTrupejoc = totalTrupeRosu + totalTrupeAlbastru;
		int procent = totalTrupeAlbastru * 100 / totalTrupejoc;
		return procent;
	}

}
