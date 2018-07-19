package survey;

import java.util.Vector;

public class ShortAnswer extends Question{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vector<String> correctAnswers;
	private String userAnswer;
	private int maxLength = 100;
	private String questionType = "SA" ;
	
	public ShortAnswer(String testSurvey)
	{
		String prompt = IO.getAnswer("Enter the prompt for your SA question:");
		setQuestion(prompt);
		
		if(testSurvey.equals("Test"))
		{
			isGradable(true);
			setCorrectResponses();
		}
	}
	
	public void display(int num)
	{
		System.out.println("Question " + num + ": " + question);
		
		//TODO for next part of hw: Display user answer 
		
		if(isGradable)
		{
			System.out.println("The correct answers are: " );
			int count = 1;
			for (String correct : correctAnswers)
			{
				System.out.println(count + ") " + correct);
				count++;
			}
		}
	}
	
	//set possible correct answers for ShortAnswer question
	public void setCorrectResponses()
	{
		correctAnswers = new Vector<String>();
		int numCorrect = IO.getNumInput("Enter NUMBER of correct short answers:");
				
		for (int i=0 ; i<numCorrect ; i++)
		{
			int temp = i+1;
			String correctChoice = IO.getAnswer("Enter correct short answer #" + temp);		
			correctAnswers.add(correctChoice);
		}
	}

	//we only modify the correct answers for ShortAnswer, because there are no correct choices for a Survey
	public void modifyCorrectAnswer()
	{
		//We only have to change correct answers when it is a test
		if(this.isGradable())
		{
			String modifyChoices = IO.getYesOrNoAnswer("Do you wish to change the correct answers? Yes/No");					
			if (modifyChoices.toLowerCase().equals("yes"))
			{
				int numChoice = 1;
				//display answer choices
				for (String choice : correctAnswers)
				{
					System.out.print(numChoice + ") " + choice + "  ");
					numChoice++;
				}
				
				int choiceToModify = IO.getNumberLessThan("Which choice do you want to modify?", correctAnswers.size());
				String newChoiceValue = IO.getAnswer("Enter new value for choice " + choiceToModify);
				correctAnswers.set(choiceToModify-1, newChoiceValue);			
			}			
		}
	}
	
	public void getUserAnswer()
	{
		String input = IO.getAnswer(this.question + " (Max " + maxLength + " characters) ");
		userAnswer = input;
				
		//if the answer is longer than maxLenght, only take it up to maxLenght-th character
		if(userAnswer.length() > maxLength)
		{
			userAnswer = input.substring(0,  maxLength);
		}
	}
	
	public int gradeQuestion()
	{
		if(correctAnswers.contains(userAnswer))
			return 10;
		else return 0;
	}
	
	public String getQuestionType() {
		
		return "SA";
	}
	public String returnUserAnswer() {
		
		return this.userAnswer;
	}
}
