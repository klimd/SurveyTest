package survey;

import java.util.Vector;

public class TrueFalse extends Question {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String correctAnswer;
	private Vector<String> choicesToSelect; 
	protected String userAnswer;
	private String questionType = "TrueFalse" ;
	
	public TrueFalse(String testSurvey)
	{
		String prompt = IO.getAnswer("Enter the prompt for your True/False question:");
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
			System.out.println("The correct answer is " + correctAnswer);
		}
	}
	
	//set whether the correct answer for the question is true or false (only for tests)
	public void setCorrectResponse()
	{
		String correct = IO.getAnswer("Enter correct choice:");
		
		while(!correct.equals("T") && !correct.equals("F"))
		{
			System.out.println("The correct answer can only be either 'T' or 'F' ");
			correct = IO.getAnswer("Enter correct choice:");			
		}
		correctAnswer = correct;
	}

	//set the options of possible responses/answers to choose from
	private void setChoices()
	{
		choicesToSelect = new Vector<String>();
		choicesToSelect.add("T");
		choicesToSelect.add("F");
	}
	
	//TrueFalse has no modifyChoices, but has a modify Correct answer
	public void modifyCorrectAnswer() 
	{		
		String modifyCorrectAnswer = IO.getYesOrNoAnswer("Do you want to modify the correct answer? Yes/No");					
		if (modifyCorrectAnswer.toLowerCase().equals("yes"))
		{
			setCorrectResponse();
		}
	}
	
	public void getUserAnswer()
	{
		System.out.println(this.question);
		//display answer choices
		int numChoice = 1;
		for (String choice : choicesToSelect)
		{
			System.out.print(numChoice + ") " + choice + " " );
			numChoice++;
		}
		System.out.println();
		
		String input = IO.getAnswer("Enter your choice (T/F): ");
		while(!input.equals("T") && !input.equals("F"))
		{
			System.out.println("Your answer can only be either 'T' or 'F' ");
			input = IO.getAnswer("Enter your choice (T/F): ");			
		}
		userAnswer = input;		
	}
	public int gradeQuestion()
	{
		if(userAnswer.equals(correctAnswer))
			return 10;
		else return 0;
	}
	
	public String getQuestionType() {
		
		return "TrueFalse";
	}
	public String returnUserAnswer() {
		
		return this.userAnswer;
	}
}
