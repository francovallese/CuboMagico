package mypackage;



import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public interface Constants
{
	Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int W_TOT = ss.width; //Math.round((float)ss.width*0.8F); 
	public static final int H_TOT = ss.height; //Math.round((float)ss.height*0.8F); 
	public static final Cursor NO_CURSOR = no_cursor();
	public static final double LENTE = 288.0;
	public static final double PROSPETTIVA = 0.3;
	public static final float [] RGB_SFONDO = {0.1F,0.1F,0.2F};
	public static final Color SFONDO = new Color(RGB_SFONDO[0],RGB_SFONDO[1],RGB_SFONDO[2]); //0.1F,0.1F,0.2F);
	public static final Color COLORE_BORDO = new Color(102,204,204);
	public static final String FILE_PUNTI = "records";
	static Cursor no_cursor() 
	{
		// Transparent 16 x 16 pixel cursor image.
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        // Create a new blank cursor.
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
            cursorImg, new Point(0, 0), "blank cursor");
        return blankCursor;
	}
}