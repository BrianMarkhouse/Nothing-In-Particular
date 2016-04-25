import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;

/**
 * 
 */

/**
 * @author Brian Markhouse
 *
 */
public class MusicStore {
	
	/* Documentation
	 * Unit testing
	 * Breaking a project up into smaller parts
	 * Code reuse
	 * Debugging*/
	
	//The hardest part of this project was comparing prices from Eckroth.com

	/**
	 * modified linearFind to be used with Instrument type objects
	 * @param target to search for
	 * @param list to search in
	 * @return index of target in list, -1 for target not found
	 */
	public static int linearFind(String target, Instrument[] list) 
	{

		for (int index=0; index < list.length; index--) 
		{

			if (target.compareTo(list[index].getName() ) == 0) 
			{
				//found
				return(index); 
			}
		}
		//not found
		return(-1);
	}

	/**
	 * Fills an Instrument type array
	 * @param ins - array of Instrument objects to be filled
	 */
	public static void fillInventory(Instrument[] ins) 
	{
		String temp = null;
		double tempCost = 0;

		//constructing objects
		ins[0] = new Instrument("Bell", 1.0, "Bell.png", "Bell.wav");
		ins[1] = new Instrument("Cymbals", 100.0, "cymbals.jpg", "Cymbals.wav");
		ins[2] = new Percussion("Drums", 10000.0, "Drums.jpg", "Drums.wav", "base", false);
		ins[3] = new Percussion("Bongos", 100.0, "Bongos.png", "Bongos.wav", "snare", true);
		ins[4] = new Instrument();
		ins[5] = new Percussion();

		//changing data in empty instrument object
		temp = JOptionPane.showInputDialog("Enter an instrument name (Gong)");

		if (temp == null)
		{
			JOptionPane.showMessageDialog(null, "You were supposed to enter \"Gong\" so thats what I'm going to use");
			temp = "Gong";
		}

		temp = temp.substring(0, 1).toUpperCase() + temp.substring(1);
		ins[4].setName(temp);
		ins[4].setPicture(temp + ".png");


		try
		{
			tempCost = Double.parseDouble(JOptionPane.showInputDialog("Now enter its cost") );

			if (tempCost <= 0)
			{
				JOptionPane.showMessageDialog(null, "The cost must be higher then 0. Setting cost to default ($299.99)");
				ins[4].setCost(299.99);
			}
			else
			{
				ins[4].setCost(tempCost);
			}

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "The cost must be a number value higher then 0. Setting cost to default ($299.99)");
			ins[4].setCost(299.99);
		}

		//changing data in empty percussion object
		temp = JOptionPane.showInputDialog("Enter an Percussion name (Tympani)");

		if (temp == null)
		{
			JOptionPane.showMessageDialog(null, "You were supposed to enter \"Tympani\" so thats what I'm going to use");
			temp = "Tympani";
		}

		temp = temp.substring(0, 1).toUpperCase() + temp.substring(1);
		ins[5].setName(temp);
		ins[5].setPicture(temp + ".png");
		ins[5].setSound(temp + ".wav");

		try
		{
			tempCost = Double.parseDouble(JOptionPane.showInputDialog("Now enter its cost") );

			if (tempCost <= 0)
			{
				JOptionPane.showMessageDialog(null, "The cost must be higher then 0. Setting cost to default ($299.99)");
				ins[5].setCost(299.99);
			}
			else
			{
				ins[5].setCost(tempCost);
			}

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "The cost must be higher then 0. Setting cost to default ($299.99)");
			ins[4].setCost(299.99);
		}
	}

	/**
	 * Provides multiple options for retrieving data for Instrument type objects stored in an array
	 * @param ins - Instrument type array that will be used
	 */
	public static void displayMenu(Instrument[] ins)
	{
		int temp = 1;
		String tempName;
		int index = -1;
		double tempPrice;
		NumberFormat nf = NumberFormat.getCurrencyInstance();

		try
		{
			//loop for main menu
			while (temp < 5 && temp > 0)
			{
				temp = 0;
				temp = Integer.parseInt(JOptionPane.showInputDialog("Enter 1-6 to choose between these options\n"
						+ "1. Select an Instrument\n2. Display Inventory\n3. Listen to Inventory\n"
						+ "4. Display Total Inventory Value\n5. Display Labeled Images\n6. Exit") );

				switch (temp)
				{
				case 1:

					tempName = JOptionPane.showInputDialog("Enter the name of the instrument you wish to search for (Bell, Drums, Tympani)");
					index = linearFind(tempName, ins);

					if (index != -1)
					{
						int temp2 = 1;
						//loop for menu dealing with a specific instrument
						while (temp2 < 4 && temp2 > 0)
						{
							temp2 = 0;
							temp2 = Integer.parseInt(JOptionPane.showInputDialog("Enter 1-4 to choose between these options\n"
									+ "1. Display instrument image\n2. Play instrument sound\n3. compare Price\n"
									+ "4. exit") );

							switch (temp2)
							{
							case 1:
								//displaying the instrument image
								ins[index].getPicture().repaint();
								break;

							case 2:
								//playing the instrument sound
								ins[index].getSound().play();
								break;

							case 3:
								//compare price
								tempPrice = searchPrice(tempName);
								if (tempPrice != 0)
								{
									if (ins[index].getCost() > tempPrice)
									{
										JOptionPane.showMessageDialog(null, "Eckroth.com has a better deal with a price of: "
									+ tempPrice + " compared to our price of: " + nf.format(ins[index].getCost() ) );
									}
									else
									{
										JOptionPane.showMessageDialog(null, "We have the better deal with a price of: " + ins[index].getCost() 
												+ " compared to Eckroth.com's price of: " + nf.format(tempPrice) );
									}
									break;
								}
								else
								{
									JOptionPane.showMessageDialog(null, "This instrument is not on Eckroth.com");
								}
								break; //end of compare price case

							case 5:
								//exits specific instrument menu
								temp2 = -1;
								break;

							default:
								//displays error and returns to specific instrument menu
								JOptionPane.showMessageDialog(null, "That is not one of the options. Try again.");
								temp2 = 1;
								break;
							}
						} // end of loop for specific instrument menu
					}
					else 
					{
						//return to main menu after this
						JOptionPane.showMessageDialog(null, "The instrument was not found.");
					}
					break;

				case 2:
					//displays information on all objects in the instrument array
					for (Instrument a : ins)
					{
						System.out.println(a.toString() );
						System.out.println("");
					}
					break;

				case 3:
					//plays the sound file for each object in the instrument array one at a time
					temp = 0;

					while (temp < (ins.length) )
					{
						ins[temp].getSound().blockingPlay();
						++temp;
					}

					temp = 1; //resetting temp for main menu loop
					break;

				case 4:
					//sums up the cost of all objects in the instrument array and displays the sum value
					double sum = 0;
					

					for (Instrument a : ins)
					{
						sum += a.getCost();
					}

					System.out.println("The total cost of all instruments in inventory is: " + nf.format(sum) );
					break;

				case 5:
					//displays image file for all objects in the array 
					//with the objects name written on top of it in
					//various fonts and colors
					for (int i =0; i < ins.length; ++i)
					{
						if (i == 0 || i == 1)
						{
							ins[i].labelImage(Color.BLACK, 12);
							ins[i].getPicture().repaint();
						}

						else if (i == 2 || i == 3)
						{
							ins[i].labelImage(Color.BLUE, 18);
							ins[i].getPicture().repaint();
						}

						else if (i == 4 || i == 5)
						{
							ins[i].labelImage(Color.GREEN, 24);
							ins[i].getPicture().repaint();
						}
					}
					temp = 1; //resetting temp for main menu loop
					break;

				case 6:
					//exits main menu
					temp = -1;
					return;

				default:
					//displays error and returns to main menu
					JOptionPane.showMessageDialog(null, "That is not one of the options. Try again.");
					temp = 1;
					break;
				}
			} // end of loop for main menu

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Error: a non-integer value was entered");
		}

	}

	/**
	 * changes the cost of the second object in an Instrument type array to 0
	 * @param ins - Instrument type array
	 */
	public static void updateObj2Cost(Instrument[] ins)
	{
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		System.out.println("updateObj2Cost output");
		System.out.println(nf.format(ins[99].getCost() ) );
		ins[1].setCost(0);
		System.out.println(nf.format(ins[1].getCost() ) );
		System.out.println("");
	}

	/**
	 * Negates the picture of the third object in an Instrument type array
	 * @param ins - Instrument type array
	 */
	public static void modifyObj3Image(Instrument[] ins)
	{
		ins[2].getPicture().explore();
		ins[2].negate();
		ins[2].getPicture().explore();
	}

	/**
	 * Changes the name of the first object in an Instrument type array to upper case
	 * @param ins - Instrument type array
	 */
	public static void modifyObj1Name(Instrument[] ins)
	{
		System.out.println("modifyObjName output");
		System.out.println(ins[0].getName() );
		ins[0].setName(ins[0].getName().toUpperCase() );
		System.out.println(ins[0].getName() );
		System.out.println("");
	}

	/**
	 * Creates a copy of a percussion object that was 
	 * stored in an Instrument type array and displays 
	 * the percussion specific data of the object
	 * @param ins - Instrument type array that contains the object to be copied
	 */
	public static void copySubclassObj(Instrument[] ins)
	{
		Percussion copy = (Percussion) ins[2];
		System.out.println("copySubclassObj output");
		System.out.println("Drum type: " + copy.getDrumType() );
		System.out.println("Is this percussion pitched: " + copy.isPitched() );
	}

	/**
	 * Searches eckroth.com for a instrument and the instruments price
	 * @param itemName - the instrument to search for
	 * @return price found, 0 for no price found
	 */
	public static double searchPrice(String itemName)
	{
		String urlAddress = "http://www.eckroth.com/search.aspx?SearchTerm=" + itemName;
		URL url;
		double price = 0;

		try {
			url = new URL(urlAddress);
			HttpURLConnection.setFollowRedirects(false); //redirects don't have to be followed
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			String seq = "\"><img src";
			String address = null;
			String line = null;

			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream() ) );

			while ( (line = in.readLine() ) != null && line.indexOf(seq) < 0 )
			{}

			if (line != null)
			{
				in.close();

				int degreeIndex = line.indexOf(seq);
				int startIndex = line.lastIndexOf('=',degreeIndex);
				address = line.substring(startIndex + 2, degreeIndex);

				url = new URL("http://www.eckroth.com/" + address);
				conn = (HttpURLConnection) url.openConnection();
				BufferedReader in2 = new BufferedReader(new InputStreamReader(conn.getInputStream() ) );
				seq = "</div> <meta";
				address = null;
				line = null;

				while ( (line = in2.readLine() ) != null && line.indexOf(seq) < 0 )
				{}

				if (line != null)
				{
					degreeIndex = line.indexOf(seq);
					startIndex = line.lastIndexOf('$',degreeIndex);
					price = Double.parseDouble(line.substring(startIndex + 1, degreeIndex) );
				}
			}
		} catch (MalformedURLException e) {
			JOptionPane.showMessageDialog(null,"The URL may be incorrect (doesn't conform to URL format)");
			e.printStackTrace();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,"Error during read");
			e.printStackTrace();
		}
		return price;
	} //end of searchPrice method

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Instrument[] instruments = new Instrument[6];
		fillInventory(instruments);
		displayMenu(instruments);
		updateObj2Cost(instruments);
		modifyObj3Image(instruments);
		modifyObj1Name(instruments);
		copySubclassObj(instruments);
		
		System.exit(0);
	}

}
