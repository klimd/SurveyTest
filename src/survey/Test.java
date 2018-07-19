package survey;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Vector;

public class Test extends Survey implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String option = "Test";
	private int points;
	private Boolean isGraded; 
	
	public Test() {
		questions = new Vector<Question>();		
		isGraded = false;
	}
	
	public void load() throws IOException
	{
		System.out.println("Loading Test: " + this.getName());
		
		String fileName = this.getName(); //this is the title/name of the Survey
		String pathName = "output" + File.separator + "Tests" + File.separator; //location where to be loaded
		String path = pathName + fileName;
		
		Test temp = null;
		FileInputStream fileIn = new FileInputStream(path);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        try {
			temp = (Test) in.readObject();
			this.questions = temp.getQuestions();
			for (Question question : this.questions)
			{
				question.isGradable = true;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        in.close();
        fileIn.close();
        temp.display();
	}
	
	public void grade()
	{
		String fileName = IO.pickTestToGrade();
		
		//if there is a file picked, else it cannot grade cause there are no tests taken yet
		if (!fileName.equals(""))
		{
			UserAnswers userAnswers = new UserAnswers(fileName, this.option);	
			userAnswers.loadAnswers(fileName, "Test");

			points = 0;
			for(Question question : userAnswers.getAnsweredQuestions())
			{
				points += question.gradeQuestion();
			}
			
			int totalPoints = 10 * userAnswers.getAnsweredQuestions().size();			
			System.out.println("Score: " + points + "/" + totalPoints);		
		}
		
	}
	//load the file to get the questions/answers but don't print them in the console
		public void loadWithOutDisplay()throws IOException
		{
			System.out.println("Loading Test: " +  this.getName());
			
			String fileName = this.getName(); //this is the title/name of the Survey
			String pathName = "output" + File.separator + "Tests" + File.separator; //location where to be loaded
			String path = pathName + fileName;
			
			Survey temp = null;
			FileInputStream fileIn = new FileInputStream(path);
	        ObjectInputStream in = new ObjectInputStream(fileIn);
	        try {
				temp = (Survey) in.readObject();
				this.questions = temp.getQuestions();
				for (Question question : this.questions)
				{
					question.isGradable = true;
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        in.close();
	        fileIn.close();
		}
	
	public String getOption()
	{
		return option;
	}
}
