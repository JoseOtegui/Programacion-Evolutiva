package Geneticos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import Cruce.*;
import Mutacion.Mutacion;
import Mutacion.MutacionHeuristica;
import Mutacion.MutacionIntercambio;
import Mutacion.MutacionInversion;
import Mutacion.MutacionSimetrica;
import Seleccion.*;
import Viajante.IndividuoPermutaciones;

public class AlgoritmoGenetico {
	
	private int tamPoblacion;
	private ArrayList<Individuo<?>> poblacion;
	private int generacion;
	
	//fitness maximo y medio de cada generacion y presion selectiva
	private double[] fitness_maximo;
	private double[] fitness_medio;
	private double[] presion_selectiva;
	private double[] mejor_absoluto;
	
	private double[] mejor_absoluto_parametros;
	
	private int maxGeneraciones;
	private int numGeneracion;
	private double probCruce;
	private double probMutacion;
	private int tamTorneo;
	private double prob_torneo;
	
	private boolean elitismo;
	private double tam_elite;
	
	//el mejor individuo se asigna una vez se haya evaluado de la generacion
	private Individuo<?> elMejor;
	private int pos_mejor;
	
	//mejor absoluto
	private double fitness_mejor_absoluto;
	private double sol_mejor_absoluto;
	
	//elecciones en la GUI
	private String seleccion, tipoCruce, tipoMut;
	
	//sring con la solucion que se muestra en la GUI
	private String solucion = "";
	private String distancia_sol = "";
	
	
	
	
	public AlgoritmoGenetico() {
		iniciaParametros(0,0,0,0,false,"","","",0,0,0);
	}
	
	
	public void iniciaParametros(int tam,int maxGen, double pCruce, double pMut,  boolean elitismo, String seleccion, String tipoMut, String tipoCruce,double tam_elite, double prob_torneo, int tam_torneo) {
		tamPoblacion = tam;
		maxGeneraciones = maxGen;
		probCruce = pCruce;
		probMutacion = pMut;
		poblacion = new ArrayList<Individuo<?>>(tamPoblacion);
		this.prob_torneo = prob_torneo;
		this.tamTorneo = tam_torneo;
		
		fitness_maximo = new double[maxGeneraciones];	
		fitness_medio = new double[maxGeneraciones];
		presion_selectiva = new double[maxGeneraciones];
		mejor_absoluto = new double[maxGeneraciones];
				
		numGeneracion = 0;
		fitness_mejor_absoluto = 0;
				
		this.seleccion = seleccion;
		this.tipoMut = tipoMut;
		this.tipoCruce = tipoCruce;
	
		
		this.elitismo = elitismo;
		this.tam_elite = tam_elite;
				
		fitness_mejor_absoluto = Double.MIN_VALUE;
	}
	
	public void testParametrosMut(int tamPoblacion2, int maxGen, double pCruce,boolean elitismo2, String seleccion2, String tipoMut2,
			String tipoCruce2, double tam_elite2, double prob_torneo2, int p_torneo, String param, double min_p,
			double max_p) {
		
		int numIteraciones = (int) Math.ceil((max_p - min_p) / 0.1);
		
		mejor_absoluto_parametros = new double[numIteraciones];
		int k = 0;
		
		for(double i = min_p; i < max_p; i+= 0.1) {
			this.iniciaParametros(tamPoblacion2, maxGen, pCruce, i, elitismo2, seleccion2, tipoMut2, tipoCruce2, tam_elite2, prob_torneo2, p_torneo);
			this.runAG();
			mejor_absoluto_parametros[k] = sol_mejor_absoluto;
			k++;
		}
		
	}
	
	public void testParametrosCruce(int tamPoblacion2, int maxGen, double pMut, boolean elitismo2, String seleccion2, String tipoMut2,
			String tipoCruce2, double tam_elite2, double prob_torneo2, int p_torneo, String param, double min_p,
			double max_p) {
		
		int numIteraciones = (int) Math.ceil((max_p - min_p) / 0.1);
		
		mejor_absoluto_parametros = new double[numIteraciones+1];
		int k = 0;
		
		for(double i = min_p; i <= max_p; i+= 0.1) {
			this.iniciaParametros(tamPoblacion2, maxGen, i, pMut, elitismo2, seleccion2, tipoMut2, tipoCruce2, tam_elite2, prob_torneo2, p_torneo);
			this.runAG();
			mejor_absoluto_parametros[k] = sol_mejor_absoluto;
			k++;
		}
		
	}
	
	public void testParametrosTam(int maxGen, double pCruce, double pMut, boolean elitismo2, String seleccion2,
			String tipoMut2, String tipoCruce2, double tam_elite2, double prob_torneo2, int p_torneo, String param,
			int min_tam, int max_tam) {
		
		
		
		mejor_absoluto_parametros = new double[max_tam-min_tam];
		//recorremos el intervalo de parametros
		for(int i = min_tam; i < max_tam; i++) {
			this.iniciaParametros(i, maxGen, pCruce, pMut, elitismo2, seleccion2, tipoMut2, tipoCruce2, tam_elite2, prob_torneo2, p_torneo);
			this.runAG();
			mejor_absoluto_parametros[i-min_tam] = sol_mejor_absoluto;
		}
		
	}
	
	
	public void runAG() {
		
		ArrayList<Individuo<?>> new_poblacion = new ArrayList<Individuo<?>>();
		ArrayList<Individuo<?>> elite = new ArrayList<Individuo<?>>();
		
		//INICIALIZAMOS la poblacion de forma aleatoria y asignamos fitness a los individuos
		//dependiendo de si necesitamos individuos booleanos o reales
		
		 
		inicializa();
			
		
		
		
		//evaluamos la poblacion obteniendo la puntuacion y puntuacion acumulada
		//de cada individuo asi como el individuo mejor	
		
		//evaluacion
		evaluarPoblacion();
		
		
		incrementaGeneracion();
		//BUCLE PRINCIPAL DEL ALGORITMO GENETICO
		while(getNumGeneracion() < getMaxGeneraciones()) {
			
			//ELITISMO
			if(elitismo) {
				//ordenamos la poblacion por fitness para obtener los mejores individuos
				
				ordenaPoblacion();
				Elitismo.elitismoMejores(elite,tam_elite,this);
			}
			
			
			//SELECCION
			seleccion(new_poblacion);
			
			//la nueva poblacion del AG tras la seleccion se la asignamos y vacioamos_new_poblacion
			setPoblacion(new_poblacion);
			new_poblacion = new  ArrayList<Individuo<?>>();
			
			//CRUCE
			cruce();
			
			
			//MUTACION
			mutacion();
					
			
			//ELITISMO: volvemos a incluir la elite sustituyendo por los peores
			if(elitismo) {
				ordenaPoblacion();
				
				Elitismo.incluyeElite(this, elite);
				//borramos la elite
				elite = new ArrayList<Individuo<?>>();
			}
			
			
			//EVALUACION
			evaluarPoblacion();
			
			
			
			//siguiente generacion
			incrementaGeneracion();
		}
	}
	
	private void mutacion() {
		switch (tipoMut){
			case "Insercion":{
				break;
			}
			case "Intercambio":{
				Mutacion m = new MutacionIntercambio(poblacion,probMutacion,tamPoblacion);
				break;
			}
			case "Inversion":{
				Mutacion m = new MutacionInversion(poblacion,probMutacion,tamPoblacion);
				break;
			}
			case "Heuristica":{
				Mutacion m = new MutacionHeuristica(poblacion,probMutacion,tamPoblacion);
				break;
			}
			case "Simetrica":{
				Mutacion m = new MutacionSimetrica(poblacion,probMutacion,tamPoblacion);
				break;
			}
			default:{
				Mutacion m = new MutacionInversion(poblacion,probMutacion,tamPoblacion);
				break;
			}
		}
	}
	
	private void cruce() {
		switch (tipoCruce){
			case "PMX":{
				Cruce c = new CrucePMX(poblacion,probCruce,tamPoblacion);
				break;
			}
			case "OX":{
				Cruce c = new CruceOX(poblacion,probCruce,tamPoblacion);
				break;
			}
			case "OX V1":{
				Cruce c = new CruceOXPosPri(poblacion,probCruce,tamPoblacion);
				break;
			}
			case "OX V2":{
				Cruce c = new CruceOXOrdenPri(poblacion,probCruce,tamPoblacion);
				break;
			}
			case "CX":{
				Cruce c = new CruceCiclos(poblacion,probCruce,tamPoblacion);
				break;
			}
			case "ERX":{
				Cruce c = new CruceERX(poblacion,probCruce,tamPoblacion);
				break;
			}
			case "CO":{
				Cruce c = new CruceCO(poblacion, probCruce,tamPoblacion);
				break;
			}

			default:{
				Cruce c = new CrucePMX(poblacion,probCruce,tamPoblacion);
				break;
			}
		}
	}

	private void ordenaPAC() {
		Collections.sort(getPoblacion(), new Comparator<Individuo<?>>() {
			@Override
			public int compare(Individuo<?> p1, Individuo<?> p2) {
				if((p1.getPuntuacionAcumulada()) < (p2.getPuntuacionAcumulada())) return -1;
				else if (p1.getPuntuacionAcumulada() == (p2.getPuntuacionAcumulada())) return 0;
				else return 1;
			}
		});
		
	}


	private void ordenaPoblacion() {
		Collections.sort(getPoblacion(), new Comparator<Individuo<?>>() {
			@Override
			public int compare(Individuo<?> p1, Individuo<?> p2) {
				if((p1.getFitness()) < (p2.getFitness())) return -1;
				else if (p1.getFitness() == (p2.getFitness())) return 0;
				else return 1;
			}
		});
	}
	
	
	private void ordenaPoblacionDec() {
		Collections.sort(getPoblacion(), new Comparator<Individuo<?>>() {
			@Override
			public int compare(Individuo<?> p1, Individuo<?> p2) {
				if((p1.getFitness()) > (p2.getFitness())) return -1;
				else if (p1.getFitness() == (p2.getFitness())) return 0;
				else return 1;
			}
		});
	}
	
	private void seleccion(ArrayList<Individuo<?>> new_poblacion) {
		switch (seleccion){
		case "Ruleta":{
			ordenaPAC();
			Seleccion_Ruleta.SeleccionRuleta(poblacion, new_poblacion, tamPoblacion );
			break;
		}
		case "Torneo":{
			ordenaPAC();
			Seleccion_Torneo.SeleccionTorneo(poblacion, new_poblacion,  tamPoblacion, tamTorneo);
			break;
		}
		case "Torneo Probabilistico":{
			ordenaPAC();
			Seleccion_Torneo_Probabilista.SeleccionTorneoProbabilista(poblacion, new_poblacion, tamPoblacion, tamTorneo, prob_torneo);
			break;
		}
		case "Estocastico":{
			ordenaPAC();
			Seleccion_Estocastico.SeleccionEstocastico(poblacion, new_poblacion,  tamPoblacion);
			break;
		}
		case "Truncamiento":{
			ordenaPoblacion();
			Seleccion_Truncamiento.SeleccionTruncamiento(poblacion, new_poblacion,  tamPoblacion);
			break;
		}
		case "Restos":{
			Seleccion_Restos.SeleccionRestos(poblacion, new_poblacion, tamPoblacion);
			break;
		}
		case "Ranking":{
			ordenaPoblacionDec();
			Seleccion_Ranking.SeleccionRanking(poblacion, new_poblacion, tamPoblacion);
			break;
		}
		default:{
			ordenaPAC();
			Seleccion_Ruleta.SeleccionRuleta(getPoblacion(), new_poblacion, tamPoblacion);
			break;
		}
			
		}
	}
	
	//crea los individuos y los inicializa aleatoriamente
	//ademas, obtiene el fitness de cada individuo
	public void inicializa() {
		for (int i = 0; i < tamPoblacion; i++) {
			poblacion.add(i, new IndividuoPermutaciones());
			poblacion.get(i).inicializaCromosoma();
			poblacion.get(i).setFitness(poblacion.get(i).getFitness());
		}
	}
	

	
	//calcula mejor individuo, ademas se asigna puntuacion y puntuacion acumulada a los individuos
	public void evaluarPoblacion(){
		double punt_ac = 0;
		double mejor_fitness = Double.MIN_VALUE;
		double mejor_sol = Double.MAX_VALUE;
		double suma_fitness = 0, suma_distancias = 0;
		
		double aux, aux_sol;
		
	
		
		//obtenemos el mejor valor de fitness asi como la suma total y la posicion del individuo que la tiene
		for (int i = 0; i < tamPoblacion; i++) {
			aux = poblacion.get(i).getFitness();
			aux_sol = poblacion.get(i).getFenotipo();
			suma_fitness += aux;
			suma_distancias += aux_sol;
			if(aux > mejor_fitness) {
				pos_mejor = i;
				mejor_fitness = aux;
			}
			if(aux_sol < mejor_sol) {
				mejor_sol = aux_sol;
			}
		}
		
		//establecemos las puntuaciones y las puntuaciones acumuladas a cada individuo
		for (int i = 0; i < tamPoblacion; i++) {
			Individuo<?> ind = poblacion.get(i);
			ind.setPuntuacion(ind.getFitness()/suma_fitness);
			ind.setPuntuacionAc(ind.getPuntuacion()+ punt_ac);
			punt_ac += ind.getPuntuacion();
		}
		
		
		
		//si el mejor de esta generacion es mejor que el que ya teniamos lo actualizamos
		if (mejor_fitness > fitness_mejor_absoluto) {
			fitness_mejor_absoluto = mejor_fitness;
			sol_mejor_absoluto = mejor_sol;
			solucion = poblacion.get(pos_mejor).soltoString();
			distancia_sol = poblacion.get(pos_mejor).soltoString1();
		}
		
		//guardamos fitness maximo, medio, mejor absoluto y presion selectiva
		mejor_absoluto[numGeneracion] = sol_mejor_absoluto;
		fitness_maximo[numGeneracion] = mejor_sol;
		double media = suma_distancias/tamPoblacion;
		double mediafirness = suma_fitness/tamPoblacion;
		fitness_medio[numGeneracion] = media;
		presion_selectiva[numGeneracion] = mejor_fitness / media;
		
	}
	

	public String soltoString() {
		return solucion;
	}
	
	public String soltoString1() {
		return distancia_sol;
	}

	public void setPoblacion(ArrayList<Individuo<?>> new_poblacion) {
		poblacion = new_poblacion;
	}
	
	public void setTamTorneo(int t) {
		tamTorneo = t;
	}
	
	public void setPCruce(double t) {
		probCruce = t;
	}
	
	public void setPMutacion(double t) {
		probMutacion = t;
	}
	
	public double getPMutacion() {
		return probMutacion;
	}
	
	public double getPCruce() {
		return probCruce;
	}
	
	public int getTamTorneo() {
		return tamTorneo;
	}
	
	public void setPTorneo(double t) {
		prob_torneo = t;
	}
	
	public double getPtorneo() {
		return prob_torneo;
	}
	
	public int getTamPoblacion() {
		return tamPoblacion;
	}
	
	public int getMaxGeneraciones() {
		return maxGeneraciones;
	}
	public int getNumGeneracion() {
		return numGeneracion;
	}
	public void incrementaGeneracion() {
		numGeneracion++;
	}
	public ArrayList<Individuo<?>> getPoblacion(){
		return poblacion;
	}
	
	public double[] getFitnessMaxArray() {
		return fitness_maximo;
	}
	
	public double[] getFitnessMedioArray() {
		return fitness_medio;
	}
	
	public double[] getPSelectivaArray() {
		return presion_selectiva;
	}
	
	public double[] getMejorAbsArray() {
		return mejor_absoluto;
	}


	public double[] getMejorParametros() {
		return mejor_absoluto_parametros;
	}


	









}
