package survey;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class MC extends Question{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Set<Integer> correctAnswers;
	private Vector<String> choicesToSelect; 
	private Set<Integer> userAnswers;
	private String questionType = "MC" ;
	
	public MC(String testSurvey)
	{
		String prompt = IO.getAnswer("Enter the prompt for your MC question:");
		setQuestion(prompt);
		setChoices();
		
		if(testSurvey.equals("Test"))
		{
			isGradable(true);
			setCorrectResponse();
		}
	}
	
	public void display(int num)
	{
		System.out.println("Question " + num + ": " + question);
		int numChoice = 1;
		//display answer choices
		for (String choice : choicesToSelect)
		{
			System.out.print(numChoice + ") " + choice + " " );
			numChoice++;
		}
		System.out.println();
		
		if(isGradable)
		{
			System.out.println("The correct answers are: " );
			for (int numQ : correctAnswers)
			{
				System.out.println(numQ + ") " );
			}
		}
	}
	//set the correct responses for the MC question
	public void setCorrectResponse()
	{
		int num = 1;
		System.out.print("Current choices are: ");
		for(String choice: choicesToSelect)
		{
			System.out.print(num + ") " + choice + " ");
			num++;
		}
		System.out.println();
		
		correctAnswers = new HashSet<Integer>();
		//this is the number of correct choices, not the number for the correct choice
		int numCorrect = IO.getNumInput("Enter NUMBER of correct choices:");
		
		//if the 'admin' enters a number bigger than the total number of choices
		while(numCorrect > choicesToSelect.size())
		{
			System.out.println("Try again. The number of correct choices must be <= " + choicesToSelect.size());	
			numCorrect = IO.getNumInput("Enter NUMBER of correct choices:");
		}
		
		for (int i=0 ; i<numCorrect ; i++)
		{
			int temp = i+1;
			int correctChoice = IO.getNumInput("Enter the NUMBER of correct choice #" + temp);	
			//if the 'admin' enters a number bigger than the total number of choices
			while (correctChoice  > choicesToSelect.size())
			{
				System.out.println("Try again. The correct choice must be a number <= " + choicesToSelect.size());
				correctChoice = IO.getNumInput("Enter the NUMBER correct choice #" + temp);			
			}
			correctAnswers.add(correctChoice);
		}
	}
	//set the possible responses for the MC question
	private void setChoices()
	{
		choicesToSelect = new Vector<String>();
		int num = IO.getNumInput("Enter the number of choices for your multiple choice question.");
		
		for (int i=0 ; i<num ; i++)
		{
			int choiceNum = i+1;
			String choice = IO.getAnswer("Enter choice #" + choiceNum);
			choicesToSelect.add(choice);
		}
	}
	
	public void modifyChoices()
	{
		String modifyChoices = IO.getYesOrNoAnswer("Do you wish to change the choices? Yes/No");					
		if (modifyChoices.toLowerCase().equals("yes"))
		{
			int numChoice = 1;
			//display answer choices
			for (String choice : choicesToSelect)
			{
				System.out.print(numChoice + ") " + choice + "  ");
				numChoice++;
			}
			System.out.println();
			
			int choiceToModify = IO.getNumberLessThan("Which choice do you want to modify? Enter the number before it", choicesToSelect.size());
			String newChoiceValue = IO.getAnswer("Enter new value for choice " + choiceToModify);
			choicesToSelect.set(choiceToModify-1, newChoiceValue);			
		}
	}
	
	public void modifyCorrectAnswer() {
		
		String modifyCorrectAnswer = IO.getYesOrNoAnswer("Do you want to modify the correct answer? Yes/No");					
		if (modifyCorrectAnswer.toLowerCase().equals("yes"))
		{
			setCorrectResponse();
		}
	}
	
	public void getUserAnswer()
	{
		userAnswers = new HashSet<Integer>();
		
		System.out.println(this.question);
		//display answer choices
		int numChoice = 1;
		for (String choice : choicesToSelect)
		{
			System.out.print(numChoice + ") " + choice + " " );
			numChoice++;
		}
		System.out.println();
		
		//if it is a test the user can only enter as many choices as there are correct options
		//if it is a survey the user can choose how many options they want to enter
		int num;
		if(isGradable)
		{
			System.out.println("Please give " + correctAnswers.size() + " choices: ");
			num = correctAnswers.size();
		}
		else
		{
			num = IO.getNumberLessThan("Enter the number of how many choices you want to select", choicesToSelect.size());
		}
		
		for(int i = 1 ; i <=num ; i++)
		{
			int userInput = IO.getNumberLessThan("Enter the NUMBER in front of your choice #" + i, choicesToSelect.size());
			userAnswers.add(userInput);
		}			
	}
	
	public int gradeQuestion()
	{
		Set<Integer> tempCorrectAnswers = correctAnswers;
		Set<Integer> tempUserAnswers = userAnswers;
		
		//if there is no difference between the two sets then the user got all answers correct
		tempCorrectAnswers.removeAll(tempUserAnswers);
		if(tempCorrectAnswers.isEmpty())
			return 10;
		else return 0;
	}
	
	public String getQuestionType() {
		
		return "MC";
	}
	public String returnUserAnswer() {
		
		String temp = "";
		for(Integer i: userAnswers)
		{
			temp += choicesToSelect.get(i-1) + " ";
		}
		return temp;
	}
	
	//if the choice was selected by the use return 1 otherwise return 0 for the total count
	public Vector<Integer> returnChoicesSelected()
	{
		Vector<Integer> temp = new Vector<Integer>();
		int num = 1;
		for (String choice: choicesToSelect)
		{
			if(this.userAnswers.contains(num))
				temp.add(1);
			else 
				temp.add(0);
			num++;
		}		
		return temp;
	}	
}
