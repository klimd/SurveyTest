package survey;

import java.util.ArrayList;

public class Matching extends Question{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Integer> correctRanking;
	private ArrayList<String> sideA;
	private ArrayList<String> sideB;
	private ArrayList<Integer> userRanking;
	private String questionType = "Matching" ;
	
	public Matching(String testSurvey)
	{
		String prompt = IO.getAnswer("Enter the prompt for your matching question:");
		setQuestion(prompt);
		setSides();
		
		if(testSurvey.equals("Test"))
		{
			isGradable(true);
			setCorrectRanking();
		}
	}
	
	public void display(int num)
	{
		System.out.println("Question " + num + ": " + question);
		 
		System.out.print("Side A choices: ");
		for (int i=0 ; i < sideA.size() ; i++)
		{
			int temp = i+1;
			System.out.print(temp + ") " + sideA.get(i) + " ");
		}
		System.out.println();
		
		System.out.print("Side B choices: ");
		for (int i=0 ; i < sideB.size() ; i++)
		{
			int temp = i+1;
			System.out.print(temp + ") " + sideB.get(i) + " ");
		}
		System.out.println();
		
		if(isGradable)
		{
			System.out.println("The correct matching is: " );
			for(int i = 0 ; i < sideA.size() ; i++)
			{
				System.out.println(sideA.get(i) + " -> " + sideB.get(correctRanking.get(i)-1));
			}
		}
	}
	
	public void setCorrectRanking()
	{
		correctRanking = new ArrayList<Integer>();
			
		for (int i=0 ; i < sideA.size() ; i++)
		{
			int correctRank = IO.getNumInput("Enter the NUMBER of the correct match for " + sideA.get(i));	
			while (correctRank > sideA.size())
			{
				System.out.println("Biggest rank number must be <= " + sideA.size());
				correctRank = IO.getNumInput("Enter the NUMBER of the correct match for " + sideA.get(i));
			}
			correctRanking.add(correctRank);
		}
	}
	//setting the options for both sides of the matching
	private void setSides()
	{
		int num = IO.getNumInput("Enter the NUMBER of options for your matching question.");
		
		sideA = new ArrayList<String>();
		System.out.println("Entering options for side A");
		for (int i=0 ; i<num ; i++)
		{
			int choiceNum = i+1;
			String choice = IO.getAnswer("Enter choice #" + choiceNum);
			sideA.add(choice);
		}
		
		sideB = new ArrayList<String>();
		System.out.println("Entering options for side B");
		for (int i=0 ; i<num ; i++)
		{
			int choiceNum = i+1;
			String choice = IO.getAnswer("Enter choice #" + choiceNum);
			sideB.add(choice);
		}
	}
	
	private ArrayList<String> modifySide(ArrayList<String> side, String AorB)
	{
		ArrayList<String> newSide = side;
		System.out.println("Side " + AorB + " choices: ");
		for (int i=0 ; i < sideA.size() ; i++)
		{
			int temp = i+1;
			System.out.print(temp + ") " + newSide.get(i) + " ");
		}
		
		Boolean modifyMore = true;		
		while(modifyMore)
		{			
			int choiceToModify = IO.getNumberLessThan("Which choice do you want to modify? Select the number before it.", side.size());
			String newChoiceValue = IO.getAnswer("Enter new value for choice " + choiceToModify);
			newSide.set(choiceToModify-1, newChoiceValue);
			
			if(IO.getYesOrNoAnswer("Do you wish to change more choices for side " + AorB + " ? Yes/No").toLowerCase().equals("no"))
			{
				modifyMore = false;
			}
		}
		return newSide;
	}
	
	public void modifyChoices() {
		String modifyChoices = IO.getYesOrNoAnswer("Do you wish to change the matching choices? Yes/No");					
		if (modifyChoices.toLowerCase().equals("yes"))
		{
			String modifyNumChoices = IO.getYesOrNoAnswer("Do you wish to change the NUMBER of matching choices? Yes/No");					
			if (modifyNumChoices.toLowerCase().equals("yes"))
			{
				setSides();						
			}
			else
			{
				String modifyAChoices = IO.getYesOrNoAnswer("Do you wish to change the choices for side A? Yes/No");					
				if (modifyAChoices.toLowerCase().equals("yes"))
				{
					sideA = modifySide(sideA, "A");						
				}
				String modifyBChoices = IO.getYesOrNoAnswer("Do you wish to change the choices for side B? Yes/No");					
				if (modifyBChoices.toLowerCase().equals("yes"))
				{
					sideB = modifySide(sideB, "B");							
				}
			}					
		}
	}
	
	public void modifyCorrectAnswer() {
		String modifyCorrectAnswer = IO.getYesOrNoAnswer("Do you want to modify the correct matching? Yes/No");					
		if (modifyCorrectAnswer.toLowerCase().equals("yes"))
		{
			setCorrectRanking();
		}
	}
	

	public void getUserAnswer()
	{
		userRanking = new ArrayList<Integer>();
		System.out.println(this.question);
		
		//display matching choices		
		for(int i = 1 ; i <= sideA.size() ; i++)
		{
			System.out.println("A" + i + ") " + sideA.get(i-1) + "\t" + "B"+ i + ") " + sideB.get(i-1));
		}
		
		for (int i = 1 ; i <= sideA.size() ; i++)
		{
			int userRank = IO.getNumberLessThan("Enter the NUMBER(without the B) of the correct match for " + sideA.get(i-1), sideA.size());	
			userRanking.add(userRank);
		}
	}
	
	public int gradeQuestion()
	{
		String tempUserAnswer = "";
		String tempCorrectAnswer = "";
		
		//I am creating strings for both ranking sequences to compare more easily the two strings
		//instead of comparing the elements one by one
		for(int i : userRanking)
		{
			tempUserAnswer += i;
		}
		
		for(int i : correctRanking)
		{
			tempCorrectAnswer += i;
		}
		
		if(tempCorrectAnswer.equals(tempUserAnswer))
			return 10;
		else return 0;
	}
	
	public String getQuestionType() {
		
		return "Matching";
	}
	public String returnUserAnswer() {
		
		String temp = "";
		int t = 0;
		for(Integer i: userRanking)
		{
			temp += sideA.get(t) + " -> " + sideB.get(i-1) + " | ";
			t++;
		}
		return temp;
	}
}
