package survey;

import java.util.Vector;

public class Essay extends Question{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int maxWords = 600;
	private Vector<String> userAnswers;
	private String questionType = "Essay" ;
	
	public Essay(String testSurvey)
	{
		String prompt = IO.getAnswer("Enter the prompt for your Essay question:");
		setQuestion(prompt);
		
		if(testSurvey.equals("Test"))
		{
			isGradable(true);
		}
	}
	
	public void display(int num)
	{
		System.out.println("Question " + num + ": " + question);
	}
	
	public void getUserAnswer()
	{
		userAnswers = new Vector<String>();
		//display the question
		System.out.println(this.question + "(Max " + maxWords + " words)");
		
		//ask the user how many paragraphs is their essay, it should be less than 10
		int paragNum = IO.getNumberLessThan("Please enter the number of paragraphs for your essay. ", 10);
		//if the answer is longer than maxLenght, only take it up to maxLenght-th character
		for(int i = 1 ; i <= paragNum ; i++)
		{
			String userInput = IO.getAnswer("Paragraph #" + i);
			userAnswers.add(userInput);
		}		
	}
	
	public String getQuestionType() {
		
		return "Essay";
	}
	public String returnUserAnswer() {
		
		String temp = "";
		for (String s: this.userAnswers)
			temp+=s;
		
		return temp;
	}
}
