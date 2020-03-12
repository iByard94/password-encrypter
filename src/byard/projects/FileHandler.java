package byard.projects;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 * 
 * @author Isaac Byard
 * This class is responsible for maintaining and handling files. 
 * It particularly works with two files, "data.enc" and "description.enc"
 * "data.enc" is responsible for holding encrypted text.
 * "description.enc" is responsible for holding the titles (or descriptions) of the input
 * encoded messages.
 * 
 * They are synchronized to each other by keeping the title and the encrypted message on the same
 * line number in both text files. For example: if one encrypted their Yahoo password "pass," "Yahoo"
 * would be stored in "description.enc" on line 4, and "pass" would be saved to "data.enc" at line 4
 * as well. 
 *
 */
public class FileHandler 
{
	//assign the designated files to the objects
	File dataFile = new File("data.enc");
	File descriptionFile = new File("description.enc");
	//instantiate the encrypter
	Encrypter encrypter = new Encrypter();
	/**
	 * Write to encoded file, however, this method itself does not encode. 
	 * @param content
	 */
	public void writeToFile(String content)
		{
		try 
		{
			if (!dataFile.exists())//if the file does not exist, create a blank file
			{
				dataFile.createNewFile();
			} 
			FileWriter fw = new FileWriter(dataFile.getAbsoluteFile(),true); //instantiate new file writer
			BufferedWriter bw = new BufferedWriter(fw); //instantiate new buffered writer
			bw.write(content); //write the content to "data.enc", content will not be encrypted
			bw.newLine(); //create a new line for the next time's use
			bw.close(); //close buffer
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Write to description file
	 * @param content
	 */
	public void writeToDescriptionFile(String content)
	{
	try 
	{
		if (!descriptionFile.exists())
		{
			descriptionFile.createNewFile();
		} 
		FileWriter fw = new FileWriter(descriptionFile.getAbsoluteFile(),true); //instantiate new file writer
		BufferedWriter bw = new BufferedWriter(fw); //instantiate new buffered writer
		bw.write(content);  //write content to "description.enc", content will not be encrypted
		bw.newLine(); //create a new line for next time's use
		bw.close(); //close buffer
	}
	catch (IOException e) 
	{
		e.printStackTrace();
	}
}
	/**
	 * encryptToFile()
	 * This will encrypt content into the "data.enc" file
	 * @param content
	 */
	public void encryptToFile(String content)
	{
	try 
	{
		if (!dataFile.exists())
		{
			dataFile.createNewFile();
		} 
		FileWriter fw = new FileWriter(dataFile.getAbsoluteFile(),true); 
		FileReader fr = new FileReader(dataFile.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		BufferedReader br = new BufferedReader(fr);
		bw.write(encrypter.encrypter(content)); //write the enctypted content into the "data.enc" file
		bw.newLine(); //create a new line for next time's use
		
		br.readLine(); //moves the buffer to the next line
		
		
		br.close();
		bw.close();
	}
	catch (IOException e) 
	{
		e.printStackTrace();
	}
	}
	/**
	 * deletePassword()
	 * Deletes a given line from both the "data.enc" and the "description.enc" files.
	 * @param lineNumber int the line number to be removed from the file
	 */
	public void deletePassword(int lineNumber)
	{
		try 
		{
			if (!dataFile.exists()) //if the file does not exist then create a new file
			{
				dataFile.createNewFile(); //the new file
			} 
			FileReader fr = new FileReader(dataFile.getAbsoluteFile()); //instantiate reader for the data.enc
			BufferedReader br = new BufferedReader(fr); //instantiate a reader for the data.enc
			FileReader fr2 = new FileReader(descriptionFile.getAbsoluteFile()); //instantiate reader for the description.enc
			BufferedReader br2 = new BufferedReader(fr2); //instantiate reader for the description.enc
			
			int curr = 0; //stands for current line number
			String content = ""; //this represents the content from "data.enc"
			String content2=""; //this represents the content from "description.enc"
			String line; //the current line from data.enc
			String line2; //the current line from description.enc
			while((line = br.readLine())!= null && (line2= br2.readLine())!=null)//while line && line2 can equal a readable line:
			{
				curr++;//the current line number increases each iteration
				if (curr!=lineNumber) //if current line does not equal the line number to be deleted, then:
				{
					content += line+System.getProperty("line.separator"); //add all the content to content plus the current line
					content2 += line2 + System.getProperty("line.separator");
				}//else if current line number DOES equal the line number to be deleted, do not write that particular line to the new copy of the file
			}
			FileWriter fw = new FileWriter(dataFile.getAbsoluteFile()); //new writer
			BufferedWriter bw = new BufferedWriter(fw);
			FileWriter fw2 = new FileWriter(descriptionFile.getAbsoluteFile());
			BufferedWriter bw2 = new BufferedWriter(fw2);
			bw.write(content); //write all the content are re written to file minus the deleted line
			bw2.write(content2);
			
			bw.close();
			br.close();
			bw2.close();
			br2.close();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	/**
	 * clearFile()
	 * This simply erases all the text in the file
	 */
	public void clearFile()
	{
		try 
		{
			if (!dataFile.exists())
			{
				dataFile.createNewFile();
			} 
			FileWriter fw = new FileWriter(dataFile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("");
			bw.close();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	/**
	 * readLine()
	 * Reads the line desired to be read from the file
	 * @param lineNumber
	 * @return String lineIWant the encrypted string in "data.enc"
	 */
	public String readLine(int lineNumber)
	{
		try 
		{
			if (!dataFile.exists())
			{
				dataFile.createNewFile();
			} 
			FileReader fr = new FileReader(dataFile.getAbsoluteFile());
			BufferedReader br = new BufferedReader(fr);
			for(int i = 0; i < lineNumber-1; ++i) //transcends the file starting from the top, until it is reading the desired line
				  br.readLine();
			String lineIWant = br.readLine();
			
			br.close();
			return lineIWant;
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * readDescription()
	 * Reads the desired lie from "description.enc"
	 * @param lineNumber
	 * @return String lineIWant the desired line in "description.enc" 
	 */
	public String readDescriptionLine(int lineNumber)
	{
		try 
		{
			if (!descriptionFile.exists())
			{
				descriptionFile.createNewFile();
			} 
			FileReader fr = new FileReader(descriptionFile.getAbsoluteFile());
			BufferedReader br = new BufferedReader(fr);
			for(int i = 0; i < lineNumber-1; ++i) //transcends the file starting from the top, until it is reading the desired line
				  br.readLine();
			String lineIWant = br.readLine();
			
			br.close();
			return lineIWant;
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * getDescriptionNumLines()
	 * @return int number of used lines in "description.enc"
	 */
	public int getDescriptionNumLines()
	{
		int counter = 0;

		try 
		{
			if (!dataFile.exists())
			{
				dataFile.createNewFile(); //if there is no file, create one
			} 
			FileReader fr = new FileReader(dataFile.getAbsoluteFile());
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			while (line!= null) //as long as the line contains data, keep iterating
			{
				 line = br.readLine(); //move to the next line
				 counter ++; //add one to the counter
			}
			br.close();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return counter-1;
	}
	
	/**
	 * arrayFromDescriptionFile()
	 * creates an array from the lines in the description file
	 * @return String[] newArray
	 */
	public String[] arrayFromDescriptionFile()
	{
		try 
		{
			if (!descriptionFile.exists())
			{
				descriptionFile.createNewFile(); //if the file does not exist, create a new file
			} 
			FileReader fr = new FileReader(descriptionFile.getAbsoluteFile());
			BufferedReader br = new BufferedReader(fr);
			String[] newArray = new String[getDescriptionNumLines()]; //creates an array with as many available spaces as there are lines in the description.enc
			for(int i = 0; i < getDescriptionNumLines(); ++i)
				  newArray[i] = br.readLine();	//add every line to the array
			br.close();
			return newArray;
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * isEmpty()
	 * Checks if "data.enc" is empty. (If yes, then description.enc ought also to be empty.)
	 * @return boolean
	 */
	boolean isEmpty()
	{
		if (readLine(1) == null)
			return true;
		else
			return false;
	}
}//end class
