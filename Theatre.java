/*
Jae Park
Mr. Rosen
January 15, 2019
This program will assist the user in formatting a theatre seating plan.
The user will be given choices to either purchase seats, and look at the information of a seat.
	      GLOBAL VARIABLES
    NAME            TYPE        DESCRIPTION
    ---------------------------------------------------------------------
    choice          char        This variable stores the user's choice to navigate the menu
    prices          double [][] This variable stores the prices of each seat in the seating map
    occupiedCheck   boolean[][] This variable determines whether or not a seat is occupied
    currentPrice    double      This variable stores the current price the user must pay
    colN            int         This variable stores the current column number of the seat the user selects
    rowC            char        This variable stores the current row number of the seat the user selects
    currentAvailable int        This variable stores the number of seats that are currently available
    f               font        This variable stores the font that is used in the splashscreen
    f1              font        This variable stores the font that is used for the legend
    f2              font        This varialbe stores the font that is used in the
    pw              PrintWriter This variable is the printwriter that will be used to write in the file
*/

// The "Theatre" class.
import java.awt.*;
import hsa.Console;
import hsa.Message;
import java.io.*;

public class Theatre
{
    static Console c;           // The output console

    // Variable declaration
    static char choice;
    double[] [] prices = new double [20] [30];
    boolean[] [] occupiedCheck = new boolean [20] [30];
    double currentPrice = 0;
    int colN;
    char rowC;
    int currentAvailable = 600;
    final Font f = new Font ("Comic Sans MS", Font.BOLD, 34);
    final Font f1 = new Font ("Comic Sans MS", Font.BOLD, 24);
    final Font f2 = new Font ("Courier Sans", Font.BOLD, 14);
    PrintWriter pw;


    public Theatre ()
    {
	c = new Console (30, 120, "Theatre Seating Map Controller");
    }


    /*
      This method centres the text it is given
      ----------------------------------------------------------
      Local Variables:
      NAME        TYPE        DESCRIPTION
      ----------------------------------------------------------
      s           String      This is a parameter variable that holds the string that is centred
      No input/loop/logic is used.
    */
    public void centre (String s)
    {
	c.print (' ', 60 - s.length () / 2);
	c.println (s);
    }


    /*
    This method centres and displays the title text at the top of the console.
    ------------------------------------------------------------
    Local Variables: none.
    Global Variables Used: none.
    ------------------------------------------------------------
    No input/logic/loop is used
    */
    public void title ()
    {
	c.clear ();
	centre ("TDSB CINEPLEX THEATRE SEATING");   // calls the method to centre the text
	c.println ();
    }


    /*
      This method pauses the program, by asking the user to press any key to continue
      ----------------------------------------------------------
      Local Variables:
      NAME        TYPE        DESCRIPTION
      ----------------------------------------------------------
      key           char        This variable stores a character.
      ----------------------------------------------------------
      getChar() is used to retrieve the key the user entered
     */
    public void pauseProgram ()
    {
	char key;
	centre ("Press any key to continue...");
	key = c.getChar ();
    }


    /*
      This method displays a popup message to the user, using the Message class
      ----------------------------------------------------------
      Local Variables:
      NAME        TYPE        DESCRIPTION
      ----------------------------------------------------------
      s           String      This parameter variable stores the string that is put in the message box.
      y           int         This variable stores an integer.
      ----------------------------------------------------------
      No input/logic/loop is used
    */
    public void errorTrap (String s)
    {
	new Message (s);
    }


    /*
      This method draws the animation in the splash screen, and initializes the prices in the seating map
      ----------------------------------------------------------
      Local Variables:
      NAME        TYPE        DESCRIPTION
      ----------------------------------------------------------
      temp        int         This variable stores the price of the seat during initialization.

      Global Variables:
      ----------------------------------------------------------
      c           Console     This console variable is passed onto the threadded class
      ----------------------------------------------------------
      for loops are used to initialize the prices
    */
    public void splashScreen ()
    {
	int temp = 30;  // this variable stores the temporary beginning price that is set into the prices array
	try
	{
	    pw = new PrintWriter (new FileWriter ("tickets.jae"));      // this opens a new printwriter into the tickets file, so that it can be freshly written to later in the program
	}
	catch (Exception e)
	{
	}
	for (int i = 0 ; i < 20 ; i++)
	{
	    for (int j = 0 ; j < 30 ; j++)
	    {
		prices [i] [j] = temp; // add prices to each row
	    }
	    temp--; // reduces price per row as it moves further away from the screen, by one
	}

	// ANIMATION
	Drawing dd = new Drawing (c);
	dd.run ();
	try
	{
	    Thread.sleep (1000);
	}
	catch (Exception e)
	{
	}
	pauseProgram ();
    }


    /*
    This method draws the seating map on the console screen for the user to observe
    ----------------------------------------------------------------------------------------
    Local Variables: String alpha - serves as a placeholder for all the row letters that will be displayed
    Global Variables Used: none.
    ----------------------------------------------------------------------------------------
    A for loop is used to traverse the occupied array
    */
    public void draw ()
    {
	title ();
	String alpha = "ABCDEFGHIJKLMNOPQRST";      // this string serves as a placeholder for all the row letters that will be displayed
	c.setColor (Color.black);
	c.fillRect (0, 150, 1000, 500);      // background black box
	c.setFont (f2);
	for (int i = 0 ; i < 30 ; i++)
	{
	    c.setColor (Color.white);
	    c.drawString ("" + (i + 1), i * 21, 200);       // draws the column numbers once

	}
	for (int i = 0 ; i < 20 ; i++)          // this loop goes through the rows of the occupied grid
	{
	    for (int j = 0 ; j < 30 ; j++)      // this loop goes through the columns of the occupied grid
	    {
		if (occupiedCheck [i] [j] == true)  // if the seat is occupied, set the colour to gray
		{
		    c.setColor (Color.gray);
		}
		else
		{
		    c.setColor (Color.yellow);      // else set the colour to yellow
		}
		c.fillOval (0 + j * 21, 200 + i * 20, 21, 20);  // draws the oval that represents one seat
		c.setColor (Color.white);
		c.drawString ("" + alpha.charAt (i), 635, 215 + i * 20);    // writes the row letter at the end of the row
	    }
	}
	c.drawString ("Screen", 280, 166);  // draws the word screen on the screen

	// Legend
	c.setFont (f);                  // new font
	c.setColor (Color.yellow);
	c.fillOval (700, 280, 20, 20);
	c.setColor (Color.gray);
	c.fillOval (700, 480, 20, 20);
	c.setColor (Color.white);
	c.drawString ("Legend", 730, 200);
	c.setFont (f1);
	c.drawString (" = Unoccupied", 720, 300);
	c.drawString (" = Occupied", 720, 500);
	c.drawString (currentAvailable + " seats available", 700, 400);     // this displays the amount of seats that are available to purchase to the user
    }


    /*
    This method displays the main menu options to the user, and updates the seating map diagram
    ----------------------------------------------------------------------------------------
    Local Variables: none.
    Global Variables Used: choice - serves as the choice of user input to navigate the program
    ----------------------------------------------------------------------------------------
    getChar() is used to get the value of choice.
    */
    public void mainMenu ()
    {
	title ();
	draw ();
	// List of options printed in the main menu
	centre ("(a)Look at a seat (b)Purchase tickets (c)Instructions");
	centre ("Press any other key exit");                // prompts the user for input
	choice = c.getChar ();                              // gets choice
    }


    /*
    This method displays the instructions to the user, on how the program functions
    ----------------------------------------------------------------------------------------
    Local Variables: none.
    Global Variables Used: none.
    ----------------------------------------------------------------------------------------
    No input/logic/loop is used
    */
    public void instructions ()
    {
	title ();
	centre ("This program will assist you in controlling the seating plan of the theatre.");        // instructions output
	c.println ();
	centre ("The theatre has 20 rows with 30 seats per row.");
	c.println ();
	centre ("While purchasing, you will be asked to enter an alphanumeric coordinate on the seating");
	centre ("map in form [letter, number] to navigate this program. Follow the given prompts.");
	c.println ();
	centre ("All purchases are non-refundable, and will be autosaved in a single file with a given file name");
	centre ("tickets.jae");
	c.println ();
	centre ("This file will be stored in the local folder of this program.");
	c.println ();
	centre ("Closing and re-running this program will clear the occupied seats and clear the map.");
	c.println ();
	pauseProgram ();        // pauses the program
    }


    /*
      This method prompts the user for data when they want to purchase a ticket
      ----------------------------------------------------------
      Local Variables:
      NAME        TYPE        DESCRIPTION
      ----------------------------------------------------------
      userin      boolean[][] This array determines whether or not the user has already selected this seat during the purchasing stage
      userInput   int[][]     This array stores the user input of row and column coordinates
      temp        String      This string stores the input for errortrapping
      n           int         This int stores the number of tickets they want to buy
      tempi       int         This int stores the numerical value of the row character the user selects
      a           boolean     This boolean is used to see if the program must direct the user straight to the main menu instead of being prompted to confirm a price

      Global Variables:
      NAME        TYPE        DESCRIPTION
      ----------------------------------------------------------
      occupiedCheck   boolean[][] This variable determines whether or not a seat is occupied
      currentPrice    double      This variable stores the current price the user must pay
      colN            int         This variable stores the current column number of the seat the user selects
      rowC            char        This variable stores the current row number of the seat the user selects
      currentAvailable int        This variable stores the number of seats that are currently available
      pw              PrintWriter This variable is the printwriter that will be used to write in the file
      choice          char        This variable stores the user's choice to navigate the menu
      ----------------------------------------------------------
      while loops are used to errortrap input
      a try catch structure is used for errortrapping and parsing strings to ints
      if statements are used for errortrapping valid input
      readLine() is used to collect user data input
      getChar() is used to collect user directory requests
      Message class is used to display messages to the user
      a printwriter is used to write to the ticket file
    */
    public void askData ()
    {
	c.setCursor (2, 1);
	c.println ();
	c.setCursor (3, 1);
	c.println ();
	c.setCursor (4, 1);
	c.println ();
	c.setCursor (5, 1);
	c.println ();
	c.setCursor (6, 1);
	c.println ();
	boolean[] [] userin = new boolean [20] [30];
	int[] [] userInput;
	String temp;
	int n;
	int tempi = 0;
	boolean a = false;

	// ERRORTRAP for number of tickets they want to purchase
	while (true)
	{
	    c.setCursor (2, 1);
	    c.println ();
	    c.setCursor (2, 1);
	    c.print ("Please enter the number of seats you wish to buy: "); // prompts the user
	    temp = c.readLine ();   // reads
	    try
	    {
		n = Integer.parseInt (temp);
		if (n <= currentAvailable && n > 0)     // only break if they enter a non-negative number that is not zero and if there are enough tickets for them to buy
		    break;
		else
		    errorTrap ("Please select a valid number of seats!");   // else error message
	    }
	    catch (Exception e)
	    {
		errorTrap ("Please enter an integer!");     // if the input cannot be parsed, prompt for integer input
	    }

	}
	userInput = new int [n] [2];    // initialize the user input array to the number of tickets they will buy (one pair of coordinates per ticket)

	// Asks for seat coordinates until the number of seats the user wants to buy have been selected
	for (int i = 0 ; i < n ; i++)
	{
	    // ERRORTRAP for character entry (to make sure it's in bounds) of row letter
	    while (true)
	    {
		c.setCursor (2, 1);
		c.println ();
		c.setCursor (2, 1);
		c.print ("Please enter the row letter of the seat: ");  // prompts for the row letter
		temp = c.readLine ();
		rowC = temp.toUpperCase ().charAt (0);
		if ((rowC > 'T' || rowC < 'A')) // checks if the letter is in proper range
		{
		    errorTrap ("Please enter a valid letter from A-T"); // range specification message
		}
		else
		{
		    if (temp.length () == 1)            // checks if the input has length of 1 (char)
			break;
		    else
			errorTrap ("Please enter a character!");    // else ask for a character
		}
	    }
	    tempi = rowC - 'A';             // finds the numerical value of the row letter and assigns it to tempi, by subtracting the ASCII value of capital A

	    // ERRORTRAP for checking if user input is an int of column input
	    while (true)
	    {
		c.setCursor (3, 1);
		c.println ();
		c.setCursor (3, 1);
		c.print ("Please enter the column number of the seat: ");   // prompts input
		temp = c.readLine ();              // reads user input
		try
		{
		    colN = Integer.parseInt (temp);
		    if (colN > 0 && colN < 31)          // checks if the row number is in bounds
		    {
			if (!occupiedCheck [tempi] [colN - 1])  // checks if the seat they want to buy is not occupied already
			{
			    if (!userin [tempi] [colN - 1])     // checks if the seat they chose was already chosen
			    {
				break;
			    }
			    else
			    {
				errorTrap ("You've already selected this seat!");   // error message for already having this seat selected in a previous selection
			    }
			}
			else
			{
			    errorTrap ("Please enter a seat that isn't occupied!");
			    pauseProgram ();
			    a = true;   // sets a to true, so that they won't be prompted the payment confirmation message
			    break;          // breaks the for loop so they are direct back to the main menu
			}
		    }
		    else
			errorTrap ("Please enter an integer between 1 and 30 inclusive!");
		}
		catch (Exception e)
		{
		    errorTrap ("Please enter an integer!");
		}
	    }
	    if (a)      // if they exited then they are not shown the seat selection confirmation
		break;
	    colN = colN - 1;    // subtracts one to colN to match the array index
	    // sets the new price the user has to pay
	    currentPrice = priceTotal (currentPrice, prices [tempi] [colN]);    // continues to add prices in the blackbox method
	    c.setCursor (4, 50);
	    c.println ("Do you wish to continue?");
	    centre ("Press 1 to continue.");
	    centre ("Press any other key to go to the main menu.");
	    choice = c.getChar ();
	    if (choice == '1')      // if they choose to select this seat
	    {
		userin [tempi] [colN] = true;   // add this seat to the temp map of their current selection
		userInput [i] [0] = tempi;  // add the row number coordinate
		userInput [i] [1] = colN;   // add the column number coordinate
		c.setColor (Color.green);   // sets selected seats as 'marked' with green in the UI
		c.fillOval (0 + (colN) * 21, 200 + tempi * 20, 21, 20);  // draw command for the above comment
	    }
	    else
	    {
		break;  // if they do not wish to purchase this ticket, they are returned to the main menu
	    }
	    c.setCursor (4, 50);    // clears the confirmation text
	    c.println ();
	    c.setCursor (5, 58);
	    c.println ();
	    c.setCursor (6, 40);
	    c.println ();
	}
	if (choice == '1' && !a)    // if they chose to continue for the final seat selection
	{
	    c.setCursor (2, 1);
	    c.println ();
	    c.setCursor (3, 1);
	    c.println ();
	    c.setCursor (4, 1);
	    c.println ();
	    c.setCursor (5, 1);
	    c.println ();
	    c.setCursor (6, 1);
	    c.println ();
	    c.setCursor (2, 40);
	    c.println ("THE FINAL COST OF THESE TICKETS ARE: $" + currentPrice);    // displays final price to the user
	    centre ("Do you wish to proceed?");
	    centre ("Press 1 to proceed to the transaction.");
	    centre ("Press any other key to go quit and to go to the main menu.");  // prompts input for directory
	    choice = c.getChar ();
	    if (choice == '1')
	    {
		centre ("Please enter the last name of the person you wish to save these tickets to: ");    // if they want to purchase, ask for name to save it under
		temp = c.readLine ();
		errorTrap (n + " ticket(s) have been successfully saved under the name of " + temp);    // output confirmation of saved tickets
		for (int i = 0 ; i < n ; i++)
		{
		    occupiedCheck [userInput [i] [0]] [userInput [i] [1]] = true;       // set the seats that they selected as occupied on the main grid
		    pw.println (temp);      // save the name of the person in the ticket file
		    pw.print (userInput [i] [0]); // save the row number
		    pw.println (userInput [i] [1]); // save the column number
		}
		pw.flush ();        // clears what I have in the print writer buffer, without actually closing the printwriter. This allows me to retain old information in the ticket file
		currentAvailable -= n;  // reduce the current amount of seats available in the theatre by the number of tickets bought
	    }
	}
	currentPrice = 0;       // regardless of a transaction or not, the price the user must pay is reset back to 0
    }


    /*
     This method views the file contents and looks for the information of a specific seat, whose coordinates are provided by the user
      ----------------------------------------------------------
      Local Variables:
      NAME        TYPE        DESCRIPTION
      ----------------------------------------------------------
      tempi       int         This variable stores the numerical value of the row letter
      String      name        This variable stores the String (name) we are looking for in the file
      String      temp        This variable stores the input for errortrapping

      Global Variables:
      NAME        TYPE        DESCRIPTION
      ----------------------------------------------------------
      colN            int         This variable stores the current column number of the seat the user selects
      rowC            char        This variable stores the current row number of the seat the user selects
      ----------------------------------------------------------
      While loops are used to errortrap input
      if structures are used in errortrapping input and checking whether or not the seat is occupied
    */
    public void viewSeat ()
    {
	String name, temp;      // stores the name String we are looking for in the file, and the temp String for errorTrapping input
	int tempi;              // stores the numerical value of the row letter
	c.setCursor (2, 1);     // clears the main menu text
	c.println ();
	c.setCursor (3, 1);
	c.println ();
	c.setCursor (4, 1);
	c.println ();
	c.setCursor (5, 1);
	c.println ();
	c.setCursor (6, 1);
	c.println ();

	// ERRORTRAP for character entry (to make sure it's in bounds) of row coordinate
	while (true)
	{
	    c.setCursor (2, 1);
	    c.println ();
	    c.setCursor (2, 1);
	    c.print ("Please enter the row letter of the seat: ");  // prompts input
	    temp = c.readLine ();       // reads user input
	    if (temp.length () > 1 || temp.length () == 0) // checks if it is not a character or blank
	    {
		errorTrap ("Please enter a character!");
	    }
	    else
	    {
		rowC = temp.toUpperCase ().charAt (0);  // finds the row character
		if ((rowC > 'T' || rowC < 'A'))     // checks if it is not a valid letter
		{
		    errorTrap ("Please enter a valid letter from A-T");
		}
		else
		    break;
	    }
	}
	// ERRORTRAP for checking if user input is an int of column input
	while (true)
	{
	    c.setCursor (3, 1);
	    c.println ();
	    c.setCursor (3, 1);
	    c.print ("Please enter the column number of the seat: ");   // prompts input
	    temp = c.readLine ();              // reads user input
	    try
	    {
		colN = Integer.parseInt (temp);
		if (colN > 0 && colN < 31)      // checks if it is in range
		{
		    break;
		}
		else
		    errorTrap ("Please enter an integer between 1 and 30 inclusive!");
	    }
	    catch (Exception e)
	    {
		errorTrap ("Please enter an integer!");
	    }
	}
	colN = colN - 1;            // assings the values for the row/column numbers so we can easily use them in the array (index value)
	tempi = rowC - 'A';
	c.setColor (Color.green);   // sets selected seats as 'marked' with green in the UI
	c.fillOval (0 + (colN) * 21, 200 + tempi * 20, 21, 20);  // draw command for the above comment
	if (!occupiedCheck [tempi] [colN])  // checks if the seat is occupied
	{
	    centre ("This seat is not occupied");
	    centre ("The price of this seat is $" + prices [tempi] [colN]);
	}
	else
	{
	    centre ("This seat is occupied by last name: " + current (tempi, colN));    // if it is occupied, we call the blackbox method to return the name of the occupant

	}
	pauseProgram ();        // pauses program so the user can read the message
    }


    /*
    This method displays the goodbye screen to the user, and the programmer's name
    ----------------------------------------------------------------------------------------
    Local Variables: none.
    Global Variables Used: none.
    ----------------------------------------------------------------------------------------
    No input/logic/loop is used
    */
    public void goodbye ()
    {
	c.clear ();
	centre ("Thanks for using this mapping program!");
	centre ("By: Jae Park");
	pauseProgram ();
	pw.close ();
	c.close ();
    }



    /*
    This private blackbox method finds the ticket information of a specific seat, whose coords are sent through parameters
    ----------------------------------------------------------------------------------------
    Local Variables:
      NAME        TYPE            DESCRIPTION
      ----------------------------------------------------------
      br          BufferedReader  This variable stores the BufferedReader we will use to read from the file
      String      name            This variable stores the String (name) we are looking for in the file
      String      input           This variable stores the input for errortrapping
      int         row             This parameter variable stores the row number we are looking for
      int         column          This parameter variable stores the column number we are looking for
    ----------------------------------------------------------------------------------------
    Global Variables Used: none.
    ----------------------------------------------------------------------------------------
    A try catch structure is used for file reading
    A forloop is used to traverse the ticket file, line by line
    An if structure is used to determine if the ticket holder has the proper ticket coordinates or not
    */
    private String current (int row, int column)
    {
	BufferedReader br;
	String name;        // stores the name of the ticketholder
	try
	{
	    br = new BufferedReader (new FileReader ("tickets.jae"));       // reads from the file tickets.jae
	    for (name = br.readLine () ; name != null ;)    // continues to read lines from the file, until there are no more lines
	    {
		String input = br.readLine ();  // this is used to store the ticket coordinates that were printed under the ticketholder's last name
		if (input.equals ("" + column + colN))  // if the coordinates match,
		{
		    return name;    // return this ticketholder name
		}
	    }

	}
	catch (Exception e)
	{
	}
	return "";
    }


    /*
    This method finds the total price by adding up the new ticket seat price (when the user wants to buy tickets)
    ----------------------------------------------------------------------------------------
	Local Variables:
      NAME        TYPE            DESCRIPTION
      ----------------------------------------------------------
      currSum     double          This parameter variable stores the current total price
      newSum      double          This parameter variable stores the to-be-added price
    ----------------------------------------------------------------------------------------
    Global Variables Used: none.
    ----------------------------------------------------------------------------------------
    No input/logic/loop is used
    */
    private double priceTotal (double currSum, double newSum)
    {
	return currSum + newSum;
    }


    /*
    This is the main method that directs the user to directories
    ----------------------------------------------------------------------------------------
    Local Variables: none.
    Global Variables Used: choice - This variable is used to see where the program should direct the user to
    ----------------------------------------------------------------------------------------
    A while loop is used to keep the program running until they choose to exit
    if structures are used to determine where the user should be taken next
    */
    public static void main (String[] args)
    {
	Theatre d = new Theatre ();
	d.splashScreen ();
	while (true)
	{
	    d.mainMenu ();
	    if (choice == 'a')
	    {
		d.viewSeat ();
	    }
	    else if (choice == 'b')
	    {
		d.askData ();
	    }
	    else if (choice == 'c')
	    {
		d.instructions ();
	    }
	    else
		break;
	}
	d.goodbye ();
    } // main method
} // Theatre class


