/*
Jae Park
Mr. Rosen
January 15, 2019
This class will draw the animation of the seating map on the splash screen
*/

// The "Drawing" class.
import java.awt.*;
import hsa.Console;

public class Drawing extends Thread
{
    private Console c;           // The output console

    // Global Variables
    private Font f = new Font ("Comic Sans MS", Font.BOLD, 34);


    public Drawing (Console con)
    {
	c = con;
    }


    /*
    This method draws the splash screen
    ----------------------------------------------------------------------------------------
    Local Variables: none.
    Global Variables Used: none.
    ----------------------------------------------------------------------------------------
    fNo input/logic/loop is used    */
    public void draw1 ()
    {
	// Initial Drawing
	c.setColor (Color.black);
	c.fillRect (0, 150, 1000, 500);      // background fill
	c.setColor (Color.yellow);
	for (int i = 0 ; i < 20 ; i++)
	{
	    for (int j = 0 ; j < 30 ; j++)
	    {
		c.fillOval (150 + j * 21, 200 + i * 20, 21, 20);
		c.fillOval (760 - j * 21, 580 - i * 20, 21, 20);
		try
		{
		    sleep (10);
		}
		catch (Exception e)
		{
		}
	    }
	    if (i > 8)
		break;
	}
	c.setFont (f);                  // new font
	c.setColor (Color.red);
	c.drawString ("TDSB CINEPLEX Cinema Seating Controller", 110, 75);
	c.setColor (Color.white);
	c.drawString ("Screen", 400, 180);
    }


    public void run ()
    {
	draw1 ();
    }
} // Drawing class
