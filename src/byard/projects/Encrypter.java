package byard.projects;
import java.util.Random;
/**
 * 
 * @author Isaac Byard
 * This class is designed to encrypt given text.
 * The encryption method works in this way:
 * 		1. It generates FLUFF amount of random characters that are added to the beginning
 * 		2. For every character which is input it moves that character's ordinal value down by OFFSET times.
 * 		3. It repeats this process until all the characters have been encoded. 
 * 		4. The last character in the encoded message is an encoded character, not a random character 
 * 			that may present a problem
 *
 */
public class Encrypter 
{
	//random number generator
	Random rand = new Random(); 
	
	//OFFSET is the constant integer for the ordinal amount that the character is encoded according to its ASII number
	private int OFFSET = 14;
	//FLUFF is the constant integer for the amount of random characters added between encoded text
	private int FLUFF = 48;
	
	/**
	 * @param char letter is converted into its ordinal value
	 * @return int letter the ordinal of the passed in letter
	 */
	private int charToOrd(char letter)
	{
		return (int)letter;
	} //end harToOrd()
	
	/**
	 * 
	 * @param int num is converted into a character
	 * @return char num as a character
	 */
	private char ordToChar(int num)
	{
		return (char)num;
	}//end ordToChar
	
	/**
	 * Encrypts the passed in ordinal number to the OFFSET value.
	 * @param int num the ordinal number being offset
	 * @return int num the offset ord
	 */
	private int ordEncrypt(int num)
	{
		return num - OFFSET;
	} //end ordEncrypt()
	
	/**
	 * Decrypts the passed in ordinal number according the to the OFFSET value.
	 * @param int num the ordinal value being reset to its original ord
	 * @return the decoded ordinal number
	 */
	private int ordDecrypt(int num)
	{
		return num + OFFSET;
	}//end ordDecrypt()
	
	/**
	 * Creates a string of random characters.
	 * @param int numberOfChars creates this many random characters
	 * @return String string of random characters
	 */
	private String randomChars(int numberOfChars)
	{
		String string = "";
		while (numberOfChars > 0)
		{
			string+=((char)(rand.nextInt(224) + 31));
			numberOfChars --;
		}
		return string;
	}//end randomChars()
	
	/**
	 * encrypts the passed in string
	 * @param String string 
	 * @return String
	 */
	public String encrypter(String string)
	{
		char tempChar;
		int tempOrd;
		String newString = "";
		for (int i = 0; i < string.length(); i++)
		{
			newString += randomChars(FLUFF);
			tempChar = string.charAt(i);
			tempOrd = charToOrd(tempChar);
			tempOrd = ordEncrypt(tempOrd);
			newString += ordToChar(tempOrd);
		}
		return newString;
	}//end encrypt()
	
	/**
	 * Decrypts the passed in string
	 * @param String string encoded string
	 * @return String decoded string
	 */
	public String decrypter(String string)
	{
		char tempChar;
		int tempOrd;
		String newString = "";
		for (int i = FLUFF; i <= string.length(); i+= FLUFF+1)
		{
			tempChar = string.charAt(i);
			tempOrd = charToOrd(tempChar);
			tempOrd = ordDecrypt(tempOrd);
			tempChar = ordToChar(tempOrd);
			newString += tempChar;
		}
		return newString;
	}//end decrypt()
}//end class
