package View;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.NumberFormatter;

import org.math.plot.Plot2DPanel;

import Geneticos.AlgoritmoGenetico;

public class GUI extends JFrame {
	
	
	//variables a obtener con sus valores por defecto
	private int tamPoblacion = 100, maxGen= 100;
	private double pCruce = 0.6 ,pMut = 0.05;
	private String seleccion= "", tipoMut = "", tipoCruce = "";
	
	private boolean elitismo = false;
	private double tam_elite = 0;
	
	private int p_torneo = 2;
	private double prob_torneo = 0.7;
	
	private AlgoritmoGenetico AG;
	
	//estudio de parametros
	private boolean valido = true;
	private String param = "% Cruce";
	private int min_tam = 0, max_tam = 100;
	private double min_p = 0.0, max_p= 1.0;
	
	//spinners para elegir valores de poblacion, generacion y error
	private JSpinner spinner_pob, spinner_gen, spinner_err;
	int Time0 = 100;
	
	public GUI(AlgoritmoGenetico AG) {
		super("Pï¿½ctica Programacion Evolutiva");
		this.AG = AG;
		initGUI();	
		
	}
	
	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
        this.setContentPane(mainPanel);
        
        Dimension psm = new Dimension(1000, 700);
        mainPanel.setPreferredSize(psm);
        
        
        //panel restante con 1 filas y dos columnas
        JPanel viewsPanel = new JPanel();
        viewsPanel.setLayout(new BoxLayout(viewsPanel, BoxLayout.X_AXIS));
        mainPanel.add(viewsPanel, BorderLayout.CENTER);
        
        
        //panel de la izquierda con opciones del algoritmo
        JPanel opcionesPanel = new JPanel();
        

        Dimension psop = new Dimension(150, 800);
        opcionesPanel.setPreferredSize(psop);
        
        opcionesPanel.setLayout(new BoxLayout(opcionesPanel, BoxLayout.Y_AXIS));
        viewsPanel.add(opcionesPanel);
        
        //panel de la grafica solucion
        
        JPanel graficassol = new JPanel();
        graficassol.setLayout(new BoxLayout(graficassol, BoxLayout.X_AXIS));
        
        //panel de la grafica params
        
        JPanel graficasparam = new JPanel();
        
        
        JPanel p = new JPanel();
		p.setBorder(BorderFactory.createTitledBorder("Grafica"));
		CardLayout cl = new CardLayout();
        p.setLayout(cl);
              
       
        graficassol.add(p);
        
        //panel para el resultado
        JPanel pres = new JPanel();
        pres.setLayout(new BoxLayout(pres, BoxLayout.Y_AXIS));
        
        Dimension dmpres = new Dimension(10,800);
        
        pres.setPreferredSize(dmpres);
        
        //panel abajo , poner su layout y cosas
        JPanel resetPanel = new JPanel();   
        
        JTextArea resultado = new JTextArea("");
        resultado.setOpaque(false);
        
        JTextArea resultado1 = new JTextArea("");
        resultado1.setOpaque(false);
        
        graficassol.add(pres);
        
        viewsPanel.add(graficassol);        
        //boton abajo
        JButton ejecButton = addButton(null, null);
        ejecButton.setPreferredSize(new Dimension(100, 50)); 
        ejecButton.setText("Ejecutar");
        ejecButton.addActionListener(actionEvent -> {
        	AG.iniciaParametros(tamPoblacion,maxGen,pCruce, pMut, elitismo, seleccion, tipoMut, tipoCruce, tam_elite,prob_torneo, p_torneo ); 
        	
        	AG.runAG();
        	
        	JPanel graficaPanel = createGraficas(AG);
            resultado.setText(AG.soltoString()); 
        	resultado1.setText(AG.soltoString1());
            
        	p.add(graficaPanel);
        	pres.add(resultado);
        	pres.add(Box.createVerticalGlue());
        	pres.add(resultado1);
        	cl.next(p);
        });
        
       
        //boton parametros
        JButton paramButton = addButton(null, null);
        paramButton.setPreferredSize(new Dimension(100, 50));
        paramButton.setText("Test");
        paramButton.addActionListener(actionEvent -> {
        	testParametros();
        	
        	if (valido) {
        		if(param == "% Cruce") {
            		AG.testParametrosCruce(tamPoblacion,maxGen,pMut, elitismo, seleccion, tipoMut, tipoCruce, tam_elite,prob_torneo, p_torneo, param, min_p,max_p);
			    }
        		else if ( param == "% Mutacion") {
        			AG.testParametrosCruce(tamPoblacion,maxGen,pCruce, elitismo, seleccion, tipoMut, tipoCruce, tam_elite,prob_torneo, p_torneo, param, min_p,max_p);
        		}
			    else if(param == "Tam. Poblacion") {
	        		AG.testParametrosTam(maxGen,pCruce,pMut, elitismo, seleccion, tipoMut, tipoCruce, tam_elite,prob_torneo, p_torneo, param, min_tam, max_tam);

			    }
        		
    
        		
        		resultado.setText("");
        		resultado1.setText("");
        		
        		JPanel graficaPanel = createGraficasParam(AG);
        		
            	p.add(graficaPanel);
            	cl.next(p);
        	}
        });
        
      
        resetPanel.add(ejecButton);
        resetPanel.add(paramButton);
        
        
        mainPanel.add(resetPanel, BorderLayout.PAGE_END);
        
        
      
        //panel con spinners
        Dimension ps = new Dimension(100, 50);
        JPanel optionsPanel =
                createOptionsPanel(ps);
        opcionesPanel.add(optionsPanel);
        
        //panel para seleccion
        Dimension ps2 = new Dimension(100, 50);
        JPanel seleccionPanel = createSelPanel(ps2);
        opcionesPanel.add(seleccionPanel);
        
        //panel para cruce
        Dimension ps3 = new Dimension(100, 50);
        JPanel crucePanel = createCrucePanel(ps3);
        opcionesPanel.add(crucePanel);
        
        
        //panel para mutacion
        Dimension ps4 = new Dimension(100, 50);
        JPanel mutPanel = createMutacionPanel(ps4);
        opcionesPanel.add(mutPanel);
        
       
        //panel para elitismo
        Dimension ps5 = new Dimension(100, 50);
        JPanel elitPanel = createElitismoPanel(ps5);
        opcionesPanel.add(elitPanel);
        
        
        
       
       
        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
	}
	

	private void testParametros() {
		
			param = "% Cruce";
		
		    String[] parametros = {"% Cruce", "% Mutacion", "Tam. Poblacion"};
		    JComboBox<String> comboBox = new JComboBox<>(parametros);
		    JTextField minField = new JTextField("0.0", 5);
		    JTextField maxField = new JTextField("1.0", 5);

		    JPanel panel = new JPanel();
		    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		    panel.add(comboBox);
		    panel.add(Box.createVerticalStrut(10));
		    panel.add(new JLabel("Min:"));
		    panel.add(minField);
		    panel.add(Box.createVerticalStrut(5));
		    panel.add(new JLabel("Max:"));
		    panel.add(maxField);
		    
		    comboBox.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					param = (String) comboBox.getSelectedItem();
				}
			});
		    
		    

		    int result = JOptionPane.showConfirmDialog(null, panel, "Ingresa el Parï¿½metro y el Intervalo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		    if (result == JOptionPane.OK_OPTION) {
		    	
		    try {
			    	if(param == "% Cruce" || param == "% Mutacion") {
			         min_p = Double.parseDouble(minField.getText());
			         max_p = Double.parseDouble(maxField.getText());
			         
			         valido = true;
			    	}
			    	else if(param == "Tam. Poblacion") {
			    		min_tam = Integer.parseInt(minField.getText());
			    		max_tam = Integer.parseInt(maxField.getText());
			    		
			    		valido = true;
			    	}
			    	
			        if (min_p < 0 || max_p > 1 || min_p >= max_p || min_tam < 0 ||min_tam >= max_tam) {
			            valido = false;
			            min_p = 0.0; max_p = 0.1; min_tam = 0; max_tam = 100;
			        	JOptionPane.showMessageDialog(null, "El intervalo ingresado no es valido.", "Error", JOptionPane.ERROR_MESSAGE);
			        }
		    }
		    catch(NumberFormatException e) {
		    	valido = false;
		    	JOptionPane.showMessageDialog(null, "El formato de los valores no es valido", "Error", JOptionPane.ERROR_MESSAGE);
		    }
		    }
	}
	
	
	private JPanel createGraficasParam(AlgoritmoGenetico aG2) {
		if(param == "Tam. Poblacion") {
			//Gï¿½ficas
			double[] parametros = new double[max_tam-min_tam];
			
			for(int i = min_tam; i < max_tam; i++) {
				parametros[i-min_tam] = i;
			}

			// create your PlotPanel (you can use it as a JPanel)
			Plot2DPanel plot = new Plot2DPanel();

			
			//Dimension d = new Dimension(600,600);
			
			//plot.setPreferredSize(d);
			
			// define the legend position
			plot.addLegend("SOUTH");
			String s = "Mejor Valor con parametro "+ param;
			plot.addLinePlot(s, parametros, AG.getMejorParametros());
			
			return plot;
			
		}
		else {
			int numIteraciones = (int) Math.ceil((max_p - min_p) / 0.1);
			double[] parametros = new double[numIteraciones+1];
			int k = 0;
			
			for(double i = min_p; i <= max_p; i+= 0.1) {
				parametros[k] = i;
				k++;
			}
			// create your PlotPanel (you can use it as a JPanel)
			Plot2DPanel plot = new Plot2DPanel();

						
			//Dimension d = new Dimension(600,600);
						
			//plot.setPreferredSize(d);
						
			// define the legend position
			plot.addLegend("SOUTH");
			String s = "Mejor Valor con parametro "+ param;
			plot.addLinePlot(s, parametros, AG.getMejorParametros());
						
			return plot;
		}
	}

	private JPanel createGraficas(AlgoritmoGenetico AG) {
		//Gï¿½ficas
		double[] generaciones = new double[AG.getMaxGeneraciones()];
		
		for(int i = 0; i < generaciones.length; i++) {
			generaciones[i] = i;
		}

		// create your PlotPanel (you can use it as a JPanel)
		Plot2DPanel plot = new Plot2DPanel();

		
		//Dimension d = new Dimension(600,600);
		
		//plot.setPreferredSize(d);
		
		// define the legend position
		plot.addLegend("SOUTH");

		// add a line plot to the PlotPanel
		plot.addLinePlot("Mejor Absoluto", generaciones, AG.getMejorAbsArray());
		plot.addLinePlot("Mejor Generacion", generaciones, AG.getFitnessMaxArray());
		plot.addLinePlot("Media Generacion", generaciones, AG.getFitnessMedioArray());
		plot.addLinePlot("Presion Selectiva", generaciones, AG.getPSelectivaArray());
		
		
		return plot;
	}
	
	private JButton addButton(String iconName, ActionListener listener) {
        JButton button = new JButton("");
        button.setIcon(new ImageIcon("icons/" + iconName));
        button.setFocusPainted(false);
        if (listener != null) button.addActionListener(listener);
        return button;
    }
	
	private JPanel createElitismoPanel(Dimension preferredSize) {
		
		JPanel p = new JPanel();
		p.setBorder(BorderFactory.createTitledBorder("Elitismo"));
        p.setLayout(new GridLayout(2, 1));
        p.setPreferredSize(preferredSize);
        
        
        JTextField doubleField = new JTextField(10);
        doubleField.setHorizontalAlignment(JTextField.RIGHT);
        doubleField.setText("0.0");
        
        doubleField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					tam_elite = Double.parseDouble(doubleField.getText());
					elitismo = (tam_elite > 0);
			}
			
		});
        
        JLabel pcr = new JLabel("% de elite: ");
        pcr.setLabelFor(doubleField);
        
        p.add(pcr);
        p.add(doubleField);
        
        return p;
	}
	
	
	
	
	private JPanel createMutacionPanel(Dimension preferredSize) {
		
		JPanel p = new JPanel();
		p.setBorder(BorderFactory.createTitledBorder("Mutacion"));
        p.setLayout(new GridLayout(4, 1));
        p.setPreferredSize(preferredSize);
        
        JLabel cr = new JLabel("Tipo de mutacion: ");
        
        String[] options = {"Insercion", "Intercambio", "Inversion", "Heuristica", "Simetrica"};
        JComboBox<String> comboBox = new JComboBox<>(options);
        
        cr.setLabelFor(comboBox);
       
        comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tipoMut = (String) comboBox.getSelectedItem();
			}
		});
        
        
        
        JTextField doubleField = new JTextField(10);
        doubleField.setHorizontalAlignment(JTextField.RIGHT);
        doubleField.setText("0.05");
        
        doubleField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					pMut = Double.parseDouble(doubleField.getText());
			}
			
		});
        JLabel pcr = new JLabel("% de mutacion: ");
        pcr.setLabelFor(doubleField);
        
        
        p.add(cr);
        p.add(comboBox);
        p.add(pcr);
        p.add(doubleField);
        
        return p;
	}
	
	
	private JPanel createCrucePanel(Dimension preferredSize) {
		
		JPanel p = new JPanel();
		p.setBorder(BorderFactory.createTitledBorder("Cruce"));
        p.setLayout(new GridLayout(4, 1));
        p.setPreferredSize(preferredSize);
        
        JLabel cr = new JLabel("Tipo de cruce: ");
        
        String[] options = {"PMX", "OX","OX V1","OX V2", "CX", "ERX", "CO"};
        JComboBox<String> comboBox = new JComboBox<>(options);
        
        cr.setLabelFor(comboBox);
       
        comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tipoCruce = (String) comboBox.getSelectedItem();
			}
		});
        
        
        
        JTextField doubleField = new JTextField(10);
        doubleField.setHorizontalAlignment(JTextField.RIGHT);
        doubleField.setText("0.6");
        
        doubleField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					pCruce = Double.parseDouble(doubleField.getText());
			}
			
		});
        
        JLabel pcr = new JLabel("% de cruce: ");
        pcr.setLabelFor(doubleField);
        
        
        p.add(cr);
        p.add(comboBox);
        p.add(pcr);
        p.add(doubleField);
        
        return p;
	}
	
	private JPanel createSelPanel(Dimension preferredSize) {
		
		JPanel p = new JPanel();
		p.setBorder(BorderFactory.createTitledBorder("Seleccion"));
        p.setLayout(new GridLayout(2, 1));
        p.setPreferredSize(preferredSize);
        
        JLabel sel = new JLabel("Tipo de seleccion: ");
        
        String[] options = {"Ruleta", "Torneo", "Torneo Probabilistico", "Estocastico", "Truncamiento", "Restos","Ranking"};
        JComboBox<String> comboBox = new JComboBox<>(options);
        
        sel.setLabelFor(comboBox);
       
        comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				seleccion = (String) comboBox.getSelectedItem();
			}
		});
        
        p.add(sel);
        p.add(comboBox);
        
        return p;
	}
	
	private JPanel createOptionsPanel(Dimension preferredSize) {
		
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
		p.setPreferredSize(preferredSize);
		
		// 1000 es el maximo, 0 es el mï¿½mimo y el incremento es 1 y el valor
		// inicial lo cojo de Time0
		spinner_pob = new JSpinner(new SpinnerNumberModel(Time0, 0, 1000, 1));
		
		Dimension ps = new Dimension(300, 20);
		spinner_pob.setPreferredSize(ps);
		
		spinner_pob.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				tamPoblacion = (int) spinner_pob.getValue();
			}
		});
		
		
		spinner_gen = new JSpinner(new SpinnerNumberModel(Time0, 0, 1000, 1));
		spinner_gen.setPreferredSize(ps);
		
		
		spinner_gen.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				maxGen = (int) spinner_gen.getValue();
			}
		});
        
        
		JLabel pob = new JLabel("Tamaño Poblacion: ");
		JLabel gen = new JLabel("Numero generaciones: ");
		
		pob.setLabelFor(spinner_pob);
		gen.setLabelFor(spinner_gen);
		
		p.add(pob);
		p.add(spinner_pob);
		p.add(gen);
		p.add(spinner_gen);
	
		return p;
	}
	
	
}
