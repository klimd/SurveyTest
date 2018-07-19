package survey;

import java.io.Serializable;
import java.util.Vector;

public class Question implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String question;
	protected Boolean isGradable = false;
	protected String questionType;
	protected String userAnswer;
	
	public void setQuestion(String q)
	{
		question = q;
	}
	public Boolean isGradable()
	{
		return isGradable;
	}
	public void isGradable(Boolean b)
	{
		isGradable = b;
	}
	public void display(int num) {
		
	}
	public void modify() {
		
		modifyPrompt();
		modifyChoices();
		if(isGradable())
		{
			this.modifyCorrectAnswer();
		}
	}
	
	private void modifyPrompt() {
		String modifyPromptResponse = IO.getYesOrNoAnswer("Do you want to modify the prompt? Yes/No");					
		if (modifyPromptResponse.toLowerCase().equals("yes"))
		{
			String newPrompt = IO.getAnswer("Enter the new prompt for your question:");
			this.setQuestion(newPrompt);
		}
	}
	public void modifyChoices() {
		
	}
	
	public void modifyCorrectAnswer() {
		
	}
	
	public void getUserAnswer()
	{
		
	}
	//default is 0 points for a question
	//all question types except Essay override this
	public int gradeQuestion()
	{
		return 0;		
	}
	public String getQuestionType() {
		
		return questionType;
	}

	public String returnUserAnswer() {
		
		return "";
	}
	public Vector<Integer> returnChoicesSelected()
	{
		return null;
	}
}
