package survey;

import java.util.ArrayList;
import java.util.Vector;

public class RankChoices extends Question{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Integer> correctRanking;
	private Vector<String> selectChoices;
	private ArrayList<Integer> userAnswers;
	private String questionType = "Rank" ;
	
	public RankChoices(String testSurvey)
	{
		String prompt = IO.getAnswer("Enter the prompt for your ranking question:");
		setQuestion(prompt);
		setChoices();
		
		if(testSurvey.equals("Test"))
		{
			isGradable(true);
			setCorrectRanking();
		}
	}
	
	public void display(int num)
	{
		System.out.println("Question " + num + ": " + question );
		
		//TODO: Display user answer 
		int numChoice = 1;
		for (String choice : selectChoices)
		{
			System.out.print(numChoice + ") " + choice + " " );
			numChoice++;
		}
		System.out.println();
		
		if(isGradable)
		{
			System.out.println("The correct ranking is: " );
			int count = 1;
			for (int rank : correctRanking)
			{
				System.out.println(selectChoices.get(rank-1));
				count++;
			}
		}
	}
	
	public void setCorrectRanking()
	{
		correctRanking = new ArrayList<Integer>();
			
		for (int i=0 ; i < selectChoices.size() ; i++)
		{
			int temp = i+1;
			int correctRank = IO.getNumInput("Enter the NUMBER of the choice for rank #" + temp);
			while (correctRank > selectChoices.size())
			{
				System.out.println("Biggest rank number must be <= " + selectChoices.size());
				correctRank = IO.getNumInput("Enter the NUMBER of the choice for rank #" + temp);
			}
			correctRanking.add(correctRank);
		}
	}
	//set the choices for the ranking question
	private void setChoices()
	{
		selectChoices = new Vector<String>();
		int num = IO.getNumInput("Enter the NUMBER of choices for your ranking question.");
		
		for (int i=0 ; i<num ; i++)
		{
			int choiceNum = i+1;
			String choice = IO.getAnswer("Enter choice #" + choiceNum);
			selectChoices.add(choice);
		}
	}
	
	public void modifyChoices() {
		String modifyChoices = IO.getYesOrNoAnswer("Do you wish to change the ranking choices? Yes/No");					
		if (modifyChoices.toLowerCase().equals("yes"))
		{
			int numChoice = 1;
			//display answer choices
			for (String choice : selectChoices)
			{
				System.out.print(numChoice + ") " + choice + "  ");
				numChoice++;
			}
			
			int choiceToModify = IO.getNumberLessThan("Which choice do you want to modify? Select the number before it.", selectChoices.size());
			String newChoiceValue = IO.getAnswer("Enter new value for choice " + choiceToModify);
			selectChoices.set(choiceToModify-1, newChoiceValue);			
		}
	}
	
	public void modifyCorrectAnswer() {
		String modifyCorrectAnswer = IO.getYesOrNoAnswer("Do you want to modify the correct ranking? Yes/No");					
		if (modifyCorrectAnswer.toLowerCase().equals("yes"))
		{
			setCorrectRanking();
		}
	}
	
	public void getUserAnswer()
	{
		userAnswers = new ArrayList<Integer>();
				
		System.out.println(this.question);
		//display answer choices
		int numChoice = 1;
		for (String choice : selectChoices)
		{
			System.out.print(numChoice + ") " + choice + " " );
			numChoice++;
		}
		System.out.println();
				
		for(int i = 1 ; i <= selectChoices.size(); i++)
		{
			int userInput = IO.getNumberLessThan("Enter the NUMBER in front of your choice for rank #" + i, selectChoices.size());
			userAnswers.add(userInput);
		}			
	}
	public int gradeQuestion()
	{
		String tempUserAnswer = "";
		String tempCorrectAnswer = "";
		
		//I am creating strings for both ranking sequences to compare more easily the two strings
		//instead of comparing the elements one by one
		for(int i : userAnswers)
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
		
		return "Rank";
	}
	public String returnUserAnswer() {
		
		String temp = "";
		for(Integer i: userAnswers)
		{
			temp += selectChoices.get(i-1) + " ";
		}
		return temp;
	}
}
