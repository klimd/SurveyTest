package survey;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;

public class UserAnswers implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vector<Question> answeredQuestions; 
	private String filePath;
	private String fileName;
	private String option;
	
	public UserAnswers(String fileName, String option)
	{
		this.setAnsweredQuestions(new Vector<Question>());	
		this.fileName  = fileName;
		this.option = option;
	}
	
	public void addAnsweredQuestion(Question question)
	{
		this.getAnsweredQuestions().add(question);
	}
	
	public void saveAnswers() throws IOException{
		
		//Take the user's name in order to save the survey/test under that name;
		String username = IO.getAnswer("Please enter your name");
		String path = "." + File.separator + "output" + File.separator + option +"sTaken";
		this.filePath = path + File.separator + fileName + "_UserAnswers_" + username;	
		
		FileOutputStream fileOut = new FileOutputStream(this.filePath.trim());
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(this);
        out.close();
        fileOut.close();
        System.out.println("Your answers are saved in file: " + this.filePath);		
	}
	
	public void loadAnswers(String fileName, String option) {
		
		String path = "." + File.separator + "output" + File.separator + option +"sTaken";
		String filePath = path + File.separator + fileName;
		
		FileInputStream fileIn;
		try {
			fileIn = new FileInputStream(filePath.trim());
	        ObjectInputStream in = new ObjectInputStream(fileIn);
	        UserAnswers temp = (UserAnswers) in.readObject();
	        this.setAnsweredQuestions(temp.getAnsweredQuestions());
	        
			in.close();
	        fileIn.close();
	        
	        System.out.println("The answers for " + fileName + " are loaded ");	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	public Vector<Question> getAnsweredQuestions() {
		return answeredQuestions;
	}

	public void setAnsweredQuestions(Vector<Question> answeredQuestions) {
		this.answeredQuestions = answeredQuestions;
	}
}
