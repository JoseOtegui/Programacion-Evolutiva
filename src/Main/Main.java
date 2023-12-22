package Main;

import java.util.ArrayList;

import Geneticos.*;
import View.GUI;

import javax.swing.*;
import org.math.plot.*;

public class Main {

	public static void main(String[] args) {
		
		
		AlgoritmoGenetico AG = new AlgoritmoGenetico();
		
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new GUI(AG);
			}
		});
	}


	
	
	
}
