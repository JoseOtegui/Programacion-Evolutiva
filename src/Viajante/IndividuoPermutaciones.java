package Viajante;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import Geneticos.Individuo;

public class IndividuoPermutaciones extends Individuo<Integer>{

	private final int TAM = 27;
	//string con la solucion final
	private String solucion = "";
	
	private double dist_sol = 0;
	
	//vector de string de ciudades
	private final static String[] _CIUDADES = {"Alicante", "Almeria","Avila", "Badajoz","Barcelona", "Bilbao", "Burgos",
			"Caceres","Cadiz","Castellon", "Ciudad Real","Cordoba","A Coruna", "Cuenca","Gerona","Granada","Guadalajara",
			"Huelva", "Huesca","Jaen","Leon","Lerida","Logrono","Lugo","Madrid","Malaga","Murcia"};
	
	//matriz de enteros con los costes de ir de cada ciudad al resto
	private final static int[][] _DIST = {
			{},
			{171},
			{369,	294},
			{366,	537,	633},
			{525,	696,	604,	318},
			{540,	515,	809,	717,	1022},
			{646,	817,	958,	401,	694,	620},
			{488,	659,	800,	243,	536,	583,	158},
			{504,	675,	651,	229,	89,	918,	605,	447},
			{617,	688,	484,	618,	342,	1284,	1058,	900,	369},
			{256,	231,	525,	532,	805,	284,	607,	524,	701,	873},
			{207,	378,	407,	256,	318,	811,	585,	427,	324,	464,	463},
			{354,	525,	332,	457,	272,	908,	795,	637,	319,	263,	610,	201},
			{860,	1031,	1172,	538,	772,	1118,	644,	535,	683,	1072,	1026,	799,	995},
			{142,	313,	511,	282,	555,	562,	562,	404,	451,	708,	305,	244,	445,	776},
			{640,	615,	909,	817,	1122,	100,	720,	683,	1018,	1384,	384,	911,	1008,	1218,	662},
			{363,	353,	166,	534,	438,	868,	829,	671,	485,	335,	584,	278,	166,	1043,	479,	968},
			{309,	480,	621,	173,	459,	563,	396,	238,	355,	721,	396,	248,	458,	667,	136,	663,	492},
			{506,	703,	516,	552,	251,	1140,	939,	781,	323,	219,	856,	433,	232,	1006,	677,	1240,	350,	690},
			{495,	570,	830,	490,	798,	274,	322,	359,	694,	1060,	355,	587,	797,	905,	406,	374,	831,	339,	1029},
			{264,	415,	228,	435,	376,	804,	730,	572,	423,	367,	520,	179,	104,	944,	380,	904,	99,	393,	336,	732},
			{584,	855,	896,	255,	496,	784,	359,	201,	407,	796,	725,	511,	733,	334,	500,	884,	761,	391,	730,	560,	668},
			{515,	490,	802,	558,	866,	156,	464,	427,	762,	1128,	259,	655,	865,	973,	472,	256,	861,	407,	1097,	118,	779,	628},
			{578,	653,	899,	358,	676,	468,	152,	115,	595,	999,	455,	526,	736,	650,	464,	568,	770,	278,	968,	244,	671,	316,	312},
			{762,	933,	1074,	440,	674,	1020,	546,	437,	585,	974,	928,	696,	897,	98,	678,	1120,	945,	569,	908,	807,	846,	236,	875,	352},
			{251,	422,	563,	115,	401,	621,	395,	237,	297,	663,	417,	190,	400,	609,	167,	721,	434,	58,	632,	397,	335,	333,	465,	336,	551},
			{473,	482,	219,	644,	436,	997,	939,	781,	506,	265,	713,	388,	187,	1153,	615,	1097,	129,	602,	313,	941,	209,	877,	1009,	880,	1055,	544},
			{150,	75,	219,	516,	675,	590,	796,	638,	654,	613,	306,	357,	444,	1010,	292,	690,	278,	459,	628,	611,	340,	734,	583,	694,	912,	401,	407}
			};
	
	private static Double mejor_fitness_mut = new Double(0);
	
	public static double maxDIST = 0;
	
	public double getFenotipo() {
		return dist_sol;
	}
	
	public static double maxDistancia() {
		
		if (maxDIST > 0) return maxDIST;
		
		else {
			int maxfila = 0;
			
			for(int i = 0; i < _DIST.length; i++) {
				for(int j = 0;  j < _DIST[i].length; j++) {
					if (_DIST[i][j] > maxfila) {
						maxfila = _DIST[i][j];
					}
				}
				maxDIST += maxfila;
			}
			
			return maxDIST;
		}
	}

	public IndividuoPermutaciones() {
		//numero de enteros en la permutacion
		tamTotal = TAM;
		cromosoma = new Integer[tamTotal];
	}

	
	public double getFitness() {
		//calculamos la distancia recorrida en las ciudades del cromosoma
		double distancia = 0;
		
		int origen, destino;
		
		for(int i = 0; i < tamTotal-1; i++) {
			
			try {
			origen = cromosoma[i];
			destino = cromosoma[i+1];
		
			
				if(origen > destino) {
					distancia += _DIST[origen][destino];
				}
				else {
					distancia += _DIST[destino][origen];
				}
			}
			catch(Exception e) {
				throw e;
			}
			
			
		}
		
		//distancia entre la ultima ciudad y la primera
		origen = cromosoma[tamTotal-1];
		destino = cromosoma[0];
		
		if(origen > destino) {
			distancia += _DIST[origen][destino];
		}
		else {
			distancia += _DIST[destino][origen];
		}
		
		dist_sol = distancia;
		double fitness = 1 - (distancia/maxDistancia());
		return fitness;
	}
	
	
	

	//inicializa el cromosoma del individuo con valores enteros aleatorios sin repetir
	public void inicializaCromosoma() {
	
		
		for (int i = 0; i < tamTotal; i++) {
			//inicializamos con ciudades del 0 a tamTotal-1
			cromosoma[i] = i;
		}
		
		// mezclamos los elementos del array (SHUFFLE)
        Random rnd = new Random();
        for (int i = tamTotal-1; i >= 0; i--) {
            int index = rnd.nextInt(i + 1);
            int temp = cromosoma[index];
            cromosoma[index] = cromosoma[i];
            cromosoma[i] = temp;
        }
				
	}



	@Override
	protected void setCromosoma(int i, Integer aux) {
		cromosoma[i] = aux;
		
	}

	@Override
	protected String soltoString() {
		//buscamos Madrid
		int indiceM = -1, i =0;
		
		while(i < tamTotal && indiceM == -1) {
			if(cromosoma[i] == 24) {
				indiceM = i;
			}
			i++;
		}
		
		String camino = "RESULTADO: \n\n";
		camino += _CIUDADES[cromosoma[indiceM]] + " ->" + '\n';
		
		i = (indiceM+1) %tamTotal;
	try {	
		while(i != indiceM) {
			camino += _CIUDADES[cromosoma[i]] + " ->" + '\n';
			i = (i+1)%tamTotal;
		}
		
		camino += _CIUDADES[cromosoma[indiceM]];
	}
	catch(Exception e) {
		System.out.println(e.getStackTrace());
	}
		return camino;
	}
	
	protected String soltoString1() {
		return  "DISTANCIA: " + dist_sol;
	}

	@Override
	public Individuo<?> copia() {
		IndividuoPermutaciones f = new IndividuoPermutaciones();
		f.setPuntuacion(this.puntuacion);
		f.setCromosoma(this.cromosoma);
		f.setFitness(f.getFitness());
		f.setPuntuacionAc(this.puntuacion_ac);
		return f;
		
	}

	@Override
	public void cruzaPMX(Individuo<?> individuo2, int puntoCorte1, int puntoCorte2) {
		
		//mapa que contiene los valores intercambiados y los valores correspondientes para el hijo 1 y el 2
		Map<Integer, Integer> intercambiados = new HashMap<Integer,Integer>();
		Map<Integer,Integer> intercambiados2 = new HashMap<Integer,Integer>();
	
		//cromosoma auxiliar del segundo padre
		Integer[] cromosoma_aux = (Integer[]) individuo2.getCromosoma();
		IndividuoPermutaciones ind = (IndividuoPermutaciones)individuo2;
				
		//intercambiamos los cromosomas en los padres desde el punto de cruce al siguiente punto
		for(int i = puntoCorte1; i < puntoCorte2; i++) {
			int aux = cromosoma[i];
			cromosoma[i] = cromosoma_aux[i];
			
			
			intercambiados2.put(aux, cromosoma_aux[i]);
			intercambiados.put(cromosoma_aux[i], aux);
			
			cromosoma_aux[i] = aux;
		}
		
		//para los valores que faltan copiamos valores correspondientes de los padres
		for(int i = 0; i < puntoCorte1; i++) {
			int key1 = cromosoma[i];
			int key2 = cromosoma_aux[i];
			
			//usamos un while por el caso en el que en el mapa se asigne 
			//un valor que est� tambi�n entre los puntos de corte al haber sido tambi�n intercambiado
			while(intercambiados.containsKey(key1)) {
				key1 = intercambiados.get(key1);
				cromosoma[i] = key1;
			}
			while(intercambiados2.containsKey(key2)) {
				key2 = intercambiados2.get(key2);
				cromosoma_aux[i] = key2;
			}
			
		}
		
		for(int i = puntoCorte2; i < tamTotal; i++) {
			int key1 = cromosoma[i];
			int key2 = cromosoma_aux[i];
			while(intercambiados.containsKey(key1)) {
				key1 = intercambiados.get(key1);
				cromosoma[i] = key1;
			}
			while(intercambiados2.containsKey(key2)) {
				key2 = intercambiados2.get(key2);
				cromosoma_aux[i] = key2;
			}
		}
		
		ind.setCromosoma(cromosoma_aux);
	}
	
	public void cruzaCiclos(Individuo<?> individuo2) {
		
		//cromosoma auxiliar del segundo padre
		Integer[] cromosoma_aux = (Integer[]) individuo2.getCromosoma();
		IndividuoPermutaciones ind = (IndividuoPermutaciones)individuo2;
		
		Integer[] hijo1 = new Integer[tamTotal];
	    Integer[] hijo2 = new Integer[tamTotal];
	    
	    //indices que ta tienen un valor 
	    boolean[] visitados1 = new boolean[tamTotal];
	    boolean[] visitados2 = new boolean[tamTotal];
	    
	    //tomamos el primer valor del primer hijo que inicia el bucle y el valor correspondiente en el segundo
	    
	    //PRIMER HIJO
	    
	    int valor = cromosoma[0];
	    int siguiente = cromosoma_aux[0];
	    
	    //indice donde esta el siguiente valor
	    int indicesiguiente = -1;
	    
	    hijo1[0] = valor;
	    visitados1[0] = true;
	    
	    //hasta que no completemos el ciclo
	    while(siguiente != valor) {
	    	
	    	 for (int i = 0; i < tamTotal; i++) {
	             if (cromosoma[i] == siguiente) {
	            	 indicesiguiente = i;
	                 break;
	             }
	         }
	    		    	 
	    	 hijo1[indicesiguiente] = cromosoma[indicesiguiente];
	    	 visitados1[indicesiguiente] = true;
	    	 siguiente = cromosoma_aux[indicesiguiente];
	    	
	    }
	    
	    //rellenamos el resto con los del otro padre
	    for (int i = 0; i < tamTotal; i++) {
	        if (!visitados1[i]) {
	            hijo1[i] = cromosoma_aux[i];
	        }
	    }
	    
	    
	    //SEGUNDO HIJO
	    
	    valor = cromosoma_aux[0];
	    siguiente = cromosoma[0];
	    indicesiguiente = -1;
	    
	    hijo2[0] = valor;
	    visitados2[0] = true;
	    
	    while(siguiente != valor) {
	    	
	    	 for (int i = 0; i < tamTotal; i++) {
	             if (cromosoma_aux[i] == siguiente) {
	            	 indicesiguiente = i;
	                 break;
	             }
	         }
	    		    	 
	    	 hijo2[indicesiguiente] = cromosoma_aux[indicesiguiente];
	    	 visitados2[indicesiguiente] = true;
	    	 siguiente = cromosoma[indicesiguiente];
	    	
	    }
	    
	    //rellenamos el resto con los del otro padre
	    for (int i = 0; i < tamTotal; i++) {
	        if (!visitados2[i]) {
	            hijo2[i] = cromosoma[i];
	        }
	    }
	    
	    this.setCromosoma(hijo1);
	    ind.setCromosoma(hijo2);
	}

	public void cruzaERX(Individuo<?> individuo2) {
		
		int intentos = 50, contador = 0;
		
		//cromosoma auxiliar del segundo padre
		Integer[] cromosoma_aux = (Integer[]) individuo2.getCromosoma();
		IndividuoPermutaciones ind = (IndividuoPermutaciones)individuo2;
		
		Integer[] hijo1 = new Integer[tamTotal];
	    Integer[] hijo2 = new Integer[tamTotal];
		
		int ciudadizq,ciudadder;
		//mapa con listas de las ciudades conectadas
		Map<Integer,Set<Integer>> conectividades = new HashMap<Integer,Set<Integer>>();
		
		//conjunto para comprobar si los elementos ya han sido visitados
		Set<Integer> visitados1 = new HashSet<Integer>();
		Set<Integer> visitados2 = new HashSet<Integer>();
		
		//construimos la tabla de conectividades
		for(int i = 0; i < tamTotal; i++) {
			//primer padre
			ciudadizq = (i == 0) ? cromosoma[tamTotal-1] : cromosoma[i-1];
			ciudadder = cromosoma[(i+1)%tamTotal];
			
			if(conectividades.containsKey(cromosoma[i])) {
				conectividades.get(cromosoma[i]).add(ciudadizq);
				conectividades.get(cromosoma[i]).add(ciudadder);
			}
			else {
				Set<Integer> n = new HashSet<Integer>();
				n.add(ciudadizq);
				n.add(ciudadder);
				conectividades.put(cromosoma[i], n);
			}
			
			//segundo padre
			ciudadizq = (i == 0) ? cromosoma_aux[tamTotal-1] : cromosoma_aux[i-1];
			ciudadder = cromosoma_aux[(i+1)%tamTotal];
			
			if(conectividades.containsKey(cromosoma_aux[i])) {
				conectividades.get(cromosoma_aux[i]).add(ciudadizq);
				conectividades.get(cromosoma_aux[i]).add(ciudadder);
			}
			else {
				Set<Integer> n = new HashSet<Integer>();
				n.add(ciudadizq);
				n.add(ciudadder);
				conectividades.put(cromosoma_aux[i], n);
			};
		}
		
		boolean bloqueo1 = true;
		int primer_valor = 0;
		//PRIMER HIJO
		while(bloqueo1 && contador < intentos) {
			bloqueo1 = false;
			//valor que estamos estucdiando
			int valor1 = cromosoma[primer_valor];
			hijo1[0] = valor1;
			
			//lo a�adimos a los visitados
			visitados1.add(valor1);
			
			//individuo menos conectado
			int mejor1 = Integer.MAX_VALUE;
			
			for(int i = 1; i < tamTotal;i++) {
				Iterator<Integer> it= conectividades.get(valor1).iterator();
				
				while(it.hasNext()) {
					int siguiente = it.next();
					if(!visitados1.contains(siguiente)) {
						//si no estaba ya visitado y esta menos conectado que el que teniamos, es el nuevo valor
						if(conectividades.get(siguiente).size() < mejor1) {
							mejor1 = conectividades.get(siguiente).size();
							valor1 = siguiente;
						}
						//si son iguales tomamos uno al azar
						else if(conectividades.get(siguiente).size()== mejor1) {
							Random r = new Random();
							double p = r.nextDouble();
							if(p < 0.5) {
								mejor1 = conectividades.get(siguiente).size();
								valor1 = siguiente;
							}
						}
					}
				}
				
				hijo1[i] = valor1;
				visitados1.add(valor1);
				mejor1 =  Integer.MAX_VALUE;
				
				if(hijo1[i-1]== valor1) { 
					bloqueo1 = true;
					contador++;
					visitados1 =new HashSet<Integer>();
					break;
				}
			}
			primer_valor = (primer_valor+1)%tamTotal;
		}
		
		boolean bloqueo2 = true;
		primer_valor = 0;
		contador = 0;
		//SEGUNDO HIJO
		while(bloqueo2 && contador < intentos) {
			bloqueo2 = false;
			//valor que estamos estucdiando
			int valor2 = cromosoma_aux[primer_valor];
			hijo2[0] = valor2;
			
			//lo a�adimos a los visitados
			visitados2.add(valor2);
			
			//individuo menos conectado
			int mejor2 = Integer.MAX_VALUE;
			
			for(int i = 1; i < tamTotal;i++) {
				Iterator<Integer> it= conectividades.get(valor2).iterator();
				
				while(it.hasNext()) {
					int siguiente = it.next();
					if(!visitados2.contains(siguiente)) {
						//si no estaba ya visitado y esta menos conectado que el que teniamos, es el nuevo valor
						if(conectividades.get(siguiente).size() < mejor2) {
							mejor2 = conectividades.get(siguiente).size();
							valor2 = siguiente;
						}
						//si son iguales tomamos uno al azar
						else if(conectividades.get(siguiente).size()== mejor2) {
							Random r = new Random();
							double p = r.nextDouble();
							if(p < 0.5) {
								mejor2 = conectividades.get(siguiente).size();
								valor2 = siguiente;
							}
						}
					}
				}
				
				hijo2[i] = valor2;
				visitados2.add(valor2);
				mejor2 =  Integer.MAX_VALUE;
				
				if(hijo2[i-1]== valor2) { 
					bloqueo2 = true;
					contador++;
					visitados2 =new HashSet<Integer>();
					break;
				}
			}
			primer_valor = (primer_valor+1)%tamTotal;
		}
		
		//si superamos un numero de intentos, no cruzamos
		if(!bloqueo1 && !bloqueo2) {
			this.setCromosoma(hijo1);
			ind.setCromosoma(hijo2);
		}
		
	}

	@Override
	public void cruzaCO(Individuo<?> individuo2, int puntoCruce) {

		
		//cromosoma auxiliar del segundo padre
		Integer[] cromosoma_aux = (Integer[]) individuo2.getCromosoma();
		IndividuoPermutaciones ind = (IndividuoPermutaciones)individuo2;
		
		//Lista din�mica ordenada de las ciudades para codificar y otra auxiliar para decodificar
		ArrayList<Integer> L1 = new ArrayList<Integer>();
		ArrayList<Integer> L2 = new ArrayList<Integer>();
		
		//inicializamos las listas
		for (int i = 0; i < tamTotal; i++) {
		      L1.add(i);
		      L2.add(i);
		}
		
		Integer[] hijo1 = new Integer[tamTotal];
	    Integer[] hijo2 = new Integer[tamTotal];
		
	    int indice1, valor1, indice2,valor2;
	    
	    //bucle en el que codificamos y representamos los recorridos de los padres seg�n la codificaci�n ordinal
		for(int i = 0; i < tamTotal; i++) {
			valor1 = cromosoma[i];
			valor2 = cromosoma_aux[i];
			
			indice1 = L1.indexOf(valor1);
			L1.remove(indice1);
			hijo1[i] = indice1;
			
			indice2 = L2.indexOf(valor2);
			L2.remove(indice2);
			hijo2[i] = indice2;
		}
		
		//cruce monopunto entre las dos codificaciones
		//intercambiamos los cromosomas en los padres desde el punto de cruce al final
		for(int i = puntoCruce; i < tamTotal; i++) {
			Integer aux = hijo1[i];
			hijo1[i] = hijo2[i];
			hijo2[i] = aux;
		}
		
		
		//inicializamos las listas
		for (int i = 0; i < tamTotal; i++) {
			L1.add(i);
			L2.add(i);
		}
		
		//descodificamos y obtenemos recorridos factibles
		for(int i = 0; i < tamTotal; i++) {
			indice1 = hijo1[i];
			indice2 = hijo2[i];
			
			valor1 = L1.get(indice1);
			L1.remove(indice1);
			hijo1[i] = valor1;
			
			valor2 = L2.get(indice2);
			L2.remove(indice2);
			hijo2[i] = valor2;
		}
	}
	
	public void cruzaOX(Individuo<?> individuo2, int iniCorte, int finCorte) {


		Integer[] hijo1 = new Integer[tamTotal];
		Integer[] hijo2 = new Integer[tamTotal];
		
		Set<Integer> visitados1 = new HashSet<Integer>();
		Set<Integer> visitados2 = new HashSet<Integer>();
		//cromosoma auxiliar del segundo padre
		Integer[] cromosoma_aux = (Integer[]) individuo2.getCromosoma();
		IndividuoPermutaciones ind = (IndividuoPermutaciones)individuo2;
		
		//intercambiamos los cromosomas en los padres desde el punto de cruce al siguiente punto
		for(int i = iniCorte; i < finCorte; i++) {
			hijo1[i] = cromosoma_aux[i];
			hijo2[i] = cromosoma[i];
			
			visitados1.add(cromosoma_aux[i]);
			visitados2.add(cromosoma[i]);
		}
		//rellenado de hijo1
		int i = finCorte;
		int indiceHijo = finCorte;
		while ( indiceHijo != iniCorte) {
			if(visitados1.contains(cromosoma[i])) 
				i = (i + 1)%tamTotal;
				
			else {
				hijo1[indiceHijo] = cromosoma[i];
				indiceHijo = (indiceHijo + 1)%tamTotal;
				i = (i + 1)%tamTotal;
			}
		}
			
		//rellenado de hijo2
		int j = finCorte;
		int indiceHijo2 = finCorte;
		while ( indiceHijo2 != iniCorte) {//significa que lllegaste al inicio del segmento a copiar
			if(visitados2.contains(cromosoma_aux[j])) 
				j = (j + 1)%tamTotal;//cuando sale del rango se resetea a 0 el indice
			
			else {
				hijo2[indiceHijo2] = cromosoma_aux[j];
				indiceHijo2 = (indiceHijo2 + 1)%tamTotal;
				j = (j + 1)%tamTotal;
				}
			}

		ind.setCromosoma(hijo2);
		this.setCromosoma(hijo1);
	}	
	
	
	public void cruzaOXPosPri(Individuo<?> individuo2, Set<Integer> posiciones) {
		//cromosoma auxiliar del segundo padre
		Integer[] cromosoma_aux = (Integer[]) individuo2.getCromosoma();
		IndividuoPermutaciones ind = (IndividuoPermutaciones)individuo2;
		
		Integer[] hijo1 = new Integer[tamTotal];
		Integer[] hijo2 = new Integer[tamTotal];
		
		Set<Integer> visitados1 = new HashSet<Integer>();
		Set<Integer> visitados2 = new HashSet<Integer>();
		
		//intercambiamos las posiciones de "posiciones" entre cromosoma y cromosoma_aux
		Iterator<Integer> it = posiciones.iterator();
		int pos;
		int posUltimo = 0;
        while (it.hasNext()) {
        	pos = it.next();
        	if(pos > posUltimo)
        		posUltimo = pos;
        	
        	hijo1[pos] = cromosoma_aux[pos];
			hijo2[pos] = cromosoma[pos];
			
			visitados1.add(cromosoma_aux[pos]);
			visitados2.add(cromosoma[pos]);
        }
        //rellenado de hijo1
    	int i = (posUltimo + 1)%tamTotal;
		int indiceHijo = i;
		while (indiceHijo != posUltimo) {
			if(hijo1[indiceHijo] != null) {
				indiceHijo = (indiceHijo + 1)%tamTotal;
			}
			else if(visitados1.contains(cromosoma[i])) 
				i = (i + 1)%tamTotal;
				
			else {
				hijo1[indiceHijo] = cromosoma[i];
				indiceHijo = (indiceHijo + 1)%tamTotal;
				i = (i + 1)%tamTotal;
			}
		}
      			
      		//rellenado de hijo2
		i = (posUltimo + 1)%tamTotal;
		int indiceHijo2 = i;
		while ( indiceHijo2 != posUltimo) {
			if(hijo2[indiceHijo2] != null) {
				indiceHijo2 = (indiceHijo2 + 1)%tamTotal;
			}
			else if(visitados2.contains(cromosoma_aux[i])) 
				i = (i + 1)%tamTotal;
				
			else {
				hijo2[indiceHijo2] = cromosoma_aux[i];
				indiceHijo2 = (indiceHijo2 + 1)%tamTotal;
				i = (i + 1)%tamTotal;
			}
		}
      		

      		ind.setCromosoma(hijo2);
      		this.setCromosoma(hijo1);
	}	
	
	
	public void cruzaOXOrdenPri(Individuo<?> individuo2, Set<Integer> posiciones) {
		
		Integer[] hijo1 = new Integer[tamTotal];
		Integer[] hijo2 = new Integer[tamTotal];
		
		//cromosoma auxiliar del segundo padre
		Integer[] cromosoma_aux = (Integer[]) individuo2.getCromosoma();
		IndividuoPermutaciones ind = (IndividuoPermutaciones)individuo2;
		
		//posiciones en P2 que se corresponden con "posiciones" en P1
		Set<Integer> PosicionesH2 = new HashSet<Integer>();
		Set<Integer> PosicionesH1 = new HashSet<Integer>();

		
		//averiguamos el valor que tienen las posiciones dadas en el set
		Integer[] valoresPosiciones = new Integer[posiciones.size()];
		Integer[] valoresPosiciones1 = new Integer[posiciones.size()];
		
		
		//HIJO 2
		Iterator<Integer> it = posiciones.iterator();
		int pos;
		int valor;
		int j = 0;
        while (it.hasNext()) {
        	pos = it.next();
        	valor = cromosoma[pos];
        	valoresPosiciones[j] = valor;
        	boolean encontrado = false;
        	int i = 0;
        	while(!encontrado) {
        		if(cromosoma_aux[i] == valor) {
        			encontrado = true;
        			PosicionesH2.add(i);
        		}
        		i++;
        	}
        	j++;
        }
        
        for(int i = 0; i < tamTotal; i++) {
        	if(!PosicionesH2.contains(i)) {
        		hijo2[i] = cromosoma_aux[i];
        	}
        }
        it = PosicionesH2.iterator();
        int posValores = 0;
        while(it.hasNext()) {
        	pos = it.next();
        	hijo2[pos] = valoresPosiciones[posValores];
        	posValores++;
        }
        
        
        //HIJO 1
    	Iterator<Integer> it1 = posiciones.iterator();
		int pos1;
		int valor1;
		int j1 = 0;
        while (it1.hasNext()) {
        	pos1 = it1.next();
        	valor1 = cromosoma_aux[pos1];
        	valoresPosiciones1[j1] = valor1;
        	boolean encontrado = false;
        	int i = 0;
        	while(!encontrado) {
        		if(cromosoma[i] == valor1) {
        			encontrado = true;
        			PosicionesH1.add(i);
        		}
        		i++;
        	}
        	j1++;
        }
        
        for(int i = 0; i < tamTotal; i++) {
        	if(!PosicionesH1.contains(i)) {
        		hijo1[i] = cromosoma[i];
        	}
        }
        it1 = PosicionesH1.iterator();
        int posValores1 = 0;
        while(it1.hasNext()) {
        	pos1 = it1.next();
        	hijo1[pos1] = valoresPosiciones1[posValores1];
        	posValores1++;
        }
        
        
        
		
		ind.setCromosoma(hijo2);
		this.setCromosoma(hijo1);
	}
	
	public void mutaInsercion(Set<Integer> ciudades, Set<Integer> posiciones) {
		Iterator<Integer> itCiu = posiciones.iterator();
		Iterator<Integer> itPos = posiciones.iterator();

		int posCiu;
		int valorCiu;
		int posInser;
		
        while (itCiu.hasNext()) {
        	posCiu = itCiu.next();
        	posInser = itPos.next();
        	valorCiu = cromosoma[posCiu];
        	
        	for(int i = posCiu; i < posInser; i--) 
        		cromosoma[i] = cromosoma[i-1];
        		
        	cromosoma[posInser] = valorCiu;
        }
		
	}
	public void mutaInversion(int puntoCorte1, int puntoCorte2) {
		//lista de valores entre el punto 1 y el 2 a invertir
		ArrayList<Integer> invertidos = new ArrayList<Integer>();
		
		//tomamos esos valores
		for(int i = puntoCorte1; i < puntoCorte2;i++) {
			invertidos.add(cromosoma[i]);
		}
		
		int cont = invertidos.size()-1;		
		//los asignamos de forma invertida
		for(int i = puntoCorte1; i< puntoCorte2; i++) {
			cromosoma[i] = invertidos.get(cont);
			cont--;
		}
		
	}

	public void mutaIntercambio(int p1, int p2) {
		int aux = cromosoma[p1];
		cromosoma[p1] = cromosoma[p2];
		cromosoma[p2] = aux;
	}
	
	public void mutaSimetrica(int p1) {
		Integer[] cromosomamutado = new Integer[tamTotal];
		int contador = 0, i = p1;
		
		//desde el punto aleatorio al final los colocamos al principio
		while(contador < tamTotal) {
			cromosomamutado[contador] = cromosoma[i];
			contador++;
			i = (i+1)%tamTotal;
		}
		this.setCromosoma(cromosomamutado);
	}

	public void mutaHeuristica(int n) {
		
		Random r= new Random();
		//indices de las ciudades seleccionadas
		int[] indices = new int[n];
		//conjunto con los indices ya seleccionados para que no coincidan
		Set<Integer> set= new HashSet<Integer>();
		
		Integer[] ciudades_mejor = new Integer[n];
		Integer[] ciudades = new Integer[n];
		
		mejor_fitness_mut = 0.0;
		
 		int ind;
 		
		for(int i = 0; i < n; i++) {
			//numero aleatorio entre 0 y tama�o del cromosoma 
			 
		
			ind = r.nextInt(tamTotal);
			while(set.contains(ind)) {
				ind = r.nextInt(tamTotal);
			}
			set.add(ind);
			 
			indices[i] = ind;
			ciudades[i] = cromosoma[ind];
			 
		}
		
		//hacemos todas las permutaciones de las ciudades con esos indices
		// caso base
        if (ciudades == null || ciudades.length == 0) {
            return;
        }
 
        permutations(ciudades,ciudades_mejor, indices, this, 0);
		
        //rellenamos el individuo con los valores de la permutacion en los indices correspondientes
    	for(int i = 0; i < indices.length;i++) {
    		this.setCromosoma(indices[i], ciudades_mejor[i]);
    	}
    	//el fitness lo pone despues
        //this.setFitness(mejor_fitness_mut);
	}
	

	   // Funci�n recursivo para generar todas las permutaciones de un array y que guarde en mejor la que de mejor fitness al individuo
	    private static void permutations(Integer[] c, Integer[] mejor,int[] indices, IndividuoPermutaciones ind, int currentIndex)
	    {
	        if (currentIndex == c.length - 1) {
	            //rellenamos el individuo con los valores de la permutacion en los indices correspondientes
	        	for(int i = 0; i < indices.length;i++) {
	        		ind.setCromosoma(indices[i], c[i]);
	        	}
	        	double f = ind.getFitness();
	        	if(f > mejor_fitness_mut) {
	        		mejor_fitness_mut = f;
	        		//rellenamos el mejor con los valores de esta permutacion si es la mejor
	        		for(int j = 0; j < c.length;j++) {
	        			mejor[j] = c[j];
	        		}
	        	}
	        }
	 
	        for (int i = currentIndex; i < c.length; i++)
	        {
	            swap(c, currentIndex, i);
	            permutations(c,mejor,indices, ind, currentIndex + 1);
	            swap(c, currentIndex, i);
	        }
	    }
	    
	    // Funci�n de utilidad para intercambiar dos caracteres en una array
	    private static void swap(Integer[] c, int i, int j)
	    {
	        int temp = c[i];
	        c[i] = c[j];
	        c[j] = temp;
	    }
	
	
}
