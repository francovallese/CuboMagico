package mypackage;

import render.Material;

public final class Materials 
{
	public static final Material VETRO =
			new Material().setDiffuse(1, 1, 1, 1).setSpecular(1, 1, 1, 10).setAmbient(0, 0, 0).setDoubleSided(true);
		
	
	
	
		
	public static final Material MATTE_CYAN =
		 new Material().setAmbient(0.0, 1.0, 1.0).setDoubleSided(false);
	public static final Material METALLIC_ALUMINUM =
			new Material().setDiffuse(0.50754, 0.50754, 0.50754).setSpecular(.508273, .508273, .508273, 10).setAmbient(0.19225, 0.19225, 0.19225).setDoubleSided(false);
		
	
	
	
	static double spec = 2;
	
		
	
	
	
	// FRANC
	
	//public static final Material METALLIC_BLACK =
	//	new Material().setDiffuse(0, 0, 0,5).setSpecular(.8,.8,.8, 5).setAmbient(0, 0, 0).setDoubleSided(false);
		
	public static final Material METALLIC_BLACK =
			new Material().setDiffuse(0, 0, 0,1).setSpecular(1, 1, 1, 10);
	
	//public static final Material METALLIC_RED = 
	//		new Material().setDiffuse(1, 0, 0).setSpecular(1, 0, 0, spec).setAmbient(0.5,0,0).setChar('R').setDoubleSided(false);
	public static final Material METALLIC_RED =
			new Material().setDiffuse(0.5, 0.025, 0,1).setSpecular(1, 0.2, 0, 5).setAmbient(0.500,0.025,0).setChar('R');
	
	
	//public static final Material METALLIC_BLUE = 
	//		new Material().setDiffuse(0, 0, 1).setSpecular(0, 0, 1, spec).setAmbient(0,0,0.5).setChar('F').setDoubleSided(false);
	
	public static final Material METALLIC_BLUE = 
			new Material().setDiffuse(0, 0.025, 0.5,1).setSpecular(0, 0.2, 1, 5).setAmbient(0,0.025,0.5).setChar('F');
	
	//public static final Material METALLIC_GREEN =
		//	new Material().setDiffuse(0, 1, 0).setSpecular(0, 1, 0, spec).setAmbient(0,0.5,0).setChar('B').setDoubleSided(false);
	public static final Material METALLIC_GREEN =
			new Material().setDiffuse(0, 0.5, 0.025,1).setSpecular(0, 1, 0.2, 5).setAmbient(0,0.5,0.025).setChar('B');
	
	//public static final Material METALLIC_ORANGE =
		//	new Material().setDiffuse(1, 0.5, 0).setSpecular(1, 0.5, 0, spec).setAmbient(0.75,0.35,0).setChar('L').setDoubleSided(false);	
	public static final Material METALLIC_ORANGE =
		  //new Material().setDiffuse(1,0.6, 0,1).setSpecular(1,0.6,  0, 5).setAmbient(0.6,0.36,0).setChar('L');	
			new Material().setDiffuse(1,0.45, 0,1).setSpecular(1,0.45,  0, 5).setAmbient(1,0.45,0).setChar('L');
	
	//public static final Material METALLIC_WHITE =
		//	new Material().setDiffuse(1, 1, 1).setSpecular(1, 1, 1, spec).setAmbient(0.5,0.5,0.5).setChar('U').setDoubleSided(false);
	public static final Material METALLIC_WHITE =
			new Material().setDiffuse(1, 1, 1,1).setSpecular(1, 1, 1, 5).setAmbient(0.65,0.65,0.65).setChar('U');
			
	//public static final Material METALLIC_YELLOW =
	//		new Material().setDiffuse(1, 1, 0).setSpecular(1, 1, 0, spec).setAmbient(0.65,0.65,0).setChar('D').setDoubleSided(false);
	public static final Material METALLIC_YELLOW =
			new Material().setDiffuse(1, 1, 0,1).setSpecular(1, 1, 0, 5).setAmbient(0.8,0.8,0).setChar('D');
			
	
	/* DEFAULT
	   protected double[] diffuse  = {1, 1, 1, 1};
	 
	   protected double[] specular = {0, 0, 0, 1};

	   protected double ambient[] = { 0, 0, 0 };
	*/
	
	
	

}