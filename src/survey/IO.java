package survey;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Vector;

public class IO {
	
	//this function calls its helper function until the user enters a positive integer
	public static int getNumInput(String prompt) {
	
		int num = getNumInputHelper(prompt);
		while(num == -1)
		{
			num = getNumInputHelper(prompt);
		}
		return num;
	}
	//this helper functions converts the user response to a prompt into an int
	public static int getNumInputHelper(String prompt) {
		String input = getAnswer(prompt).trim();
		try {
			int num = Integer.parseInt(input);
			return num;
		}
		catch(Exception e)
		{
			System.out.println("You must enter an integer. Try again");
		}
		return -1;
	}
	//this helper functions just takes the user response after displaying a prompt
	public static String getAnswer(String prompt) {
		
		System.out.println(prompt);
		InputStream is = System.in;
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader buff = new BufferedReader(isr); 	
		String line = null;
		try {
			line = buff.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return line;
	}
	//this helper functions just takes the user input
	public static String getInput() throws IOException{
		InputStream is = System.in;
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader buff = new BufferedReader(isr); 	
		String line = buff.readLine();		
		return line;
	}
	
	public static void displayMenu1() {
		System.out.println("Menu 1:");
		System.out.println("1) Test ");
		System.out.println("2) Survey ");
		System.out.println("3) Quit ");
		//System.out.println("Please select the number for one of the options above");		
	}
	
	public static void displayMenu2(String option) {
		
		System.out.println("Menu 2:");
		System.out.println("1) Create a new " + option);
		System.out.println("2) Display a " + option);
		System.out.println("3) Load a " + option);
		System.out.println("4) Save a " + option);
		System.out.println("5) Modify an existing " + option);
		System.out.println("6) Take a " + option);
		System.out.println("7) Tabulate a " + option);
		if (option.equals("Survey"))
		{
			System.out.println("8) Quit");
		}
		else
		{
			System.out.println("8) Grade a " + option);
			System.out.println("9) Quit");
		}
		//System.out.println("Please select the number for one of the options above");
	}
	
	public static void displayMenu3() {
		System.out.println("Menu 3:");
		System.out.println("1) Add a new T/F question ");
		System.out.println("2) Add a new multiple choice question ");
		System.out.println("3) Add a new short answer question ");
		System.out.println("4) Add a new essay question ");
		System.out.println("5) Add a new ranking question");
		System.out.println("6) Add a new matching question ");
		System.out.println("7) Go back to previous menu ");
		System.out.println("Please select the number for one of the options above");
	}
	//loads the survey/test when chosen from menu 2
	//option is either Survey or Test, and is passed as an argument in the function, because there 
	//are separate folders for storing surveys and tests
	public static void loadMenu(String option) {
		
		String path = "." + File.separator + "output" + File.separator + option +"s";
		File fileNames = new File(path);
		Vector<String> files = new Vector<String>();
		
		int num = 1;
		//display all files in the directory
		for(String fileName : fileNames.list()) 
		{
			System.out.println(num + ") " + fileName);
			files.add(fileName);
			num ++;
		}
		//select the number of the displayed file to load
		String toLoad = IO.getAnswer("Please select a " + option + " to load (enter the number only): ");
		
		try {
			
			int fileNum = Integer.parseInt(toLoad.trim()) - 1;
			if (fileNum < files.size())
			{
				// loading for Survey and Test is implemented separately
				// actually it is almost identical, except for the type
				if(option.equals("Survey"))
				{
					Survey surveyToLoad = new Survey();
					surveyToLoad.setName(files.get(fileNum));
					surveyToLoad.load();	
				}
				else
				{
					Test surveyToLoad = new Test();
					surveyToLoad.setName(files.get(fileNum));
					surveyToLoad.load();
				}
			}
			else
			{
				System.out.println("You must select a positive number less than " + files.size());
			}			
		}
		catch (NumberFormatException | IOException e){
			System.out.println("Your input needs to be a number");
		}							
	}

	//this displays all the available surveys/tests and asks the user to pick one for modifying, taking, grading etc
	public static String pickSurveyOrTest(String option, String action)
	{
		String path = "." + File.separator + "output" + File.separator + option +"s";
		File fileNames = new File(path);
		Vector<String> files = new Vector<String>();		
		
		int num = 1;
		//display all files in the directory
		for(String fileName : fileNames.list()) 
		{
			System.out.println(num + ") " + fileName);
			files.add(fileName);
			num ++;
		}
		if(num == 1)
		{
			System.out.println("There are no " + option.toLowerCase() + "s currently");
			return "";
		}
		else
		{
			int surveyNum = getNumberLessThan("Please select the number of the " + option + " you want to " + action, num);
			return files.get(surveyNum-1);
		}
	}
	//this displays the taken tests and ask the user to pick one for grading
	public static String pickTestToGrade()
	{
		String path = "." + File.separator + "output" + File.separator + "TestsTaken";
		File fileNames = new File(path);
		Vector<String> files = new Vector<String>();		
		
		int num = 1;
		//display all files in the directory
		for(String fileName : fileNames.list()) 
		{
			System.out.println(num + ") " + fileName);
			files.add(fileName);
			num ++;
		}
		if(num == 1)
		{
			System.out.println("There are no tests currently");
			return "";
		}
		else
		{
			int testNum = getNumberLessThan("Please select the number of the test you want to grade", num);
			return files.get(testNum-1);
		}
	}
	
	//returns all the tests or surveys taken for tabulation
	public static Vector<String> getFilesToTabulate(String option)
	{
		String path = "." + File.separator + "output" + File.separator + option + "sTaken";
		File fileNames = new File(path);
		Vector<String> files = new Vector<String>();		
		
		int num = 1;
		//display all files in the directory
		for(String fileName : fileNames.list()) 
		{
			files.add(fileName);
			num ++;
		}
		if(num == 1)
		{
			System.out.println("There are no tests currently");
		}
		return files;
	}
	
	//name says it all - it ask the user to answer with either 'yes' or 'no'
	public static String getYesOrNoAnswer(String prompt)
	{
		String answer = getAnswer(prompt);
		while(!answer.toLowerCase().equals("yes") && !answer.toLowerCase().equals("no"))
		{
			answer = IO.getAnswer("Please answer with Yes or No");
		}	
		return answer;
	}
	
	//since it turned out I will be using this a lot, I pulled it out in a separate functions
	//this functions ensures the user selects an existing option 
	public static int getNumberLessThan(String prompt, int num)
	{
		int numToReturn = IO.getNumInput(prompt);
		while(numToReturn < 0 || numToReturn > num)
		{
			numToReturn = IO.getNumInput("You must select a number between 1 and " + num);
		}
		return numToReturn;
	}
}
