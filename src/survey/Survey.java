package survey;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

public class Survey implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String option = "Survey";
	protected String name;
	protected Vector<Question> questions;
	private Boolean isEmpty;
	
	public Survey() {
		questions = new Vector<Question>();
	}

	public void create()
	{
		Boolean done = false;
		while(!done)
		{
			IO.displayMenu3();
			try 
			{
				int option = Integer.parseInt(IO.getInput().trim());	
				switch(option)
				{
				//chosing type of question
					case 1:{
						Question question = new TrueFalse(this.getOption());
						questions.add(question);
						this.isEmpty = false;
						break;
					}
					case 2:{
						Question question = new MC(this.getOption());
						questions.add(question);
						this.isEmpty = false;
						break;
					}
					case 3:{
						Question question = new ShortAnswer(this.getOption());
						questions.add(question);
						this.isEmpty = false;
						break;
					}
					case 4:{
						Question question = new Essay(this.getOption());
						questions.add(question);
						this.isEmpty = false;
						break;
					}
					case 5:{
						Question question = new RankChoices(this.getOption());
						questions.add(question);
						this.isEmpty = false;
						break;
					}
					case 6:{
						Question question = new Matching(this.getOption());
						questions.add(question);
						this.isEmpty = false;
						break;
					}
					case 7: {
						done = true;
						break;
					}
					
					default: break;
				}
			}
			catch(NumberFormatException | IOException e)
			{
				System.out.println("Please select a valid option");
			}	
		}
		
	}
	
	public void display()
	{
		System.out.println("Displaying "+ this.getOption() + ": " +  this.getName());
		int numQuestions = questions.size();
		
		//if the survey has no questions yet
		if(numQuestions < 0)
		{
			System.out.println("The " + this.getOption() + " is empty.");
			this.isEmpty = true;
		}
		else
		{
			int num = 1;
			for (Question question : questions)
			{
				//display the question, each question type has its own overridden method
				question.display(num);
				num++;
			}
		}
	}
	
	public void load() throws IOException
	{
		System.out.println("Loading Survey: " +  this.getName());
		
		String fileName = this.getName(); //this is the title/name of the Survey
		String pathName = "output" + File.separator + "Surveys" + File.separator; //location where to be loaded
		String path = pathName + fileName;
		
		Survey temp = null;
		FileInputStream fileIn = new FileInputStream(path);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        try {
			temp = (Survey) in.readObject();
			this.questions = temp.getQuestions();
			for (Question question : this.questions)
			{
				question.isGradable = false;
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
        
        //display the newly loaded survey
        temp.display();
	}
	
	//load the file to get the questions/answers but don't print them in the console
	public void loadWithOutDisplay()throws IOException
	{
		System.out.println("Loading " + this.getOption() +  ": " +  this.getName());
		
		String fileName = this.getName(); //this is the title/name of the Survey
		String pathName = "output" + File.separator + this.getOption() + "s" + File.separator; //location where to be loaded
		String path = pathName + fileName;
		
		Survey temp = null;
		FileInputStream fileIn = new FileInputStream(path);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        try {
			temp = (Survey) in.readObject();
			this.questions = temp.getQuestions();
			for (Question question : this.questions)
			{
				question.isGradable = false;
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
	
	public void save() 
	{		
		String fileName = this.getName(); //this is the title/name of the Survey
		String pathName = "output" + File.separator + this.getOption() + "s"  + File.separator; //location where to be saved
		String path = pathName + fileName;
		
		
        ObjectOutputStream out;
		try {
			FileOutputStream fileOut = new FileOutputStream(path);
			out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
		    out.close();
		    fileOut.close();
		    System.out.println("The " + this.getOption() + " " + fileName + " is saved!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void modify()
	{
		//display current surveys
		String fileName = IO.pickSurveyOrTest(this.getOption(), "modify");
		
		//display the questions
		try {
			setName(fileName);
			this.load();	
			
			//if the survey is empty it cannot be modified
			if(this.questions.size() == 0)
			{
				System.out.println("This " + this.getOption() + " cannot be modified because it is empty!");
			}
			else
			{
				Boolean modifyMore = true;
				while(modifyMore)
				{
					int questNum = IO.getNumberLessThan("Which question number do you wish to modify?", this.questions.size());
					this.getQuestions().get(questNum-1).modify();
					
					String modifyMoreResponse = IO.getYesOrNoAnswer("Do you want to modify another question? Yes/No");					
					if (modifyMoreResponse.toLowerCase().equals("no"))
					{
						modifyMore = false;
					}					
				}
				this.save();
			}

		} catch (IOException e) {
			// e.printStackTrace();
			System.out.println("Could not load " + fileName + " to modify.");
		}		
	}  
	
	public void take()
	{
		//display current surveys
		String fileName = IO.pickSurveyOrTest(this.getOption(), "take");
		
		UserAnswers userAnswers = new UserAnswers(fileName, this.getOption());
		
		//display the questions
		try {
			setName(fileName);
			this.loadWithOutDisplay();	
			
			//if the survey is empty it cannot be modified
			if(this.questions.size() == 0)
			{
				System.out.println("This " + this.getOption() + " cannot be taken because it is empty!");
			}
			else
			{
				System.out.println("You are taking the " + this.getOption() + " " + fileName);
				int questionNum = 1;
				for (Question question : this.questions)
				{
					System.out.print(questionNum + ") ");
					question.getUserAnswer();
					userAnswers.addAnsweredQuestion(question);
					questionNum++;
				}
				//serialize the answers
				userAnswers.saveAnswers();
			}

		} catch (IOException e) {
			// e.printStackTrace();
			System.out.println("Could not save taken " + this.getOption() + " " + fileName);
		}		
				
	}
	
	public void tabulate(String option)
	{		
		Vector<String> allUserAnswersFiles = IO.getFilesToTabulate(option);
		//integer is for the number of the question in the survey
		//Vector<Question> will store the answers for that particular question from all survey/test takers
		HashMap<Integer, Vector<Question>> answersHash = new HashMap<Integer, Vector<Question>>();
		System.out.println("Current user answers: ");
		for(String file : allUserAnswersFiles)
		{
			System.out.println(file);
			UserAnswers userAnswers = new UserAnswers(file, option);	
			userAnswers.loadAnswers(file, option); 
			
			int num = 1;
			for (Question answeredQuestion : userAnswers.getAnsweredQuestions())
			{
				Vector<Question> temp = new Vector<Question>();
				if(!answersHash.containsKey(num))
				{
					temp = new Vector<Question>();
				}
				else
				{
					temp = answersHash.get(num);
				}
				temp.add(answeredQuestion);
				answersHash.put(num, temp);
				num++;
			}
		} 
		Iterator it = answersHash.entrySet().iterator();
		int tempNum = 1;
	    while (it.hasNext()) {
	        HashMap.Entry pair = (HashMap.Entry)it.next();
	        int questionNum = (int) pair.getKey();
	        Vector<Question> answers =  (Vector<Question>) pair.getValue();
	      
	        //print the question
	        answers.get(0).display(tempNum);
	        tempNum++;
	        tabulateHelper(answers);	        
	        
	        it.remove();
	    }
	}
	
	public void tabulateHelper(Vector<Question> answeredQuestions)
	{
		String questionType = answeredQuestions.get(0).getQuestionType();
		if(questionType.equals("TrueFalse"))
		{
			//For T/F just count how many reponses are true out of all of them and print the results
			System.out.println("Replies: ");
			int trueNum = 0;
			for (Question q: answeredQuestions)
			{
				System.out.println(q.returnUserAnswer());
				if(q.returnUserAnswer().equals("T"))
					trueNum++;
			}
			System.out.println("Tabulate: ");
			int temp = answeredQuestions.size() - trueNum;
			System.out.println("T: " + trueNum + " F: " + temp);
		}
		if(questionType.equals("MC"))
		{
			System.out.println("Replies: ");
			Vector<Integer> totals = new Vector<Integer>();
			for(int i = 0 ; i < answeredQuestions.get(0).returnChoicesSelected().size() ; i ++)
				totals.add(0);
			//go trough each MC question and map the possible choices with 0 and 1s depending 
			//on whether they were not or were selected and then just add the numbers of 0 and 1s for each choice
			for (Question q: answeredQuestions)
			{
				System.out.println(q.returnUserAnswer());
				int temp = 0;
				for (Integer timesSelected : q.returnChoicesSelected())
				{
					int old = totals.get(temp);
					totals.remove(temp);
					totals.add(temp,old+timesSelected);
					temp++;
				}				
			}
			System.out.println("Tabulate: ");
			for (int i = 1 ; i <= totals.size() ; i++)
				System.out.println("Choice #" + i + " was selected " + totals.get(i-1) + " times.");
		}
		
		if(questionType.equals("SA"))
		{
			//Just hash all the possible responses and keep a track of how many times each appears
			HashMap<String, Integer> temp = new HashMap();
			
			System.out.println("Replies: ");
			for (Question q: answeredQuestions)
			{
				System.out.println(q.returnUserAnswer());
				if(!temp.containsKey(q.returnUserAnswer()))
				{
					temp.put(q.returnUserAnswer(), 1);
				}
				else
				{
					temp.put(q.returnUserAnswer(), temp.get(q.returnUserAnswer())+1 );				
				}
			}
			System.out.println("Tabulate: ");
			Iterator it = temp.entrySet().iterator();
			while (it.hasNext()) {
				HashMap.Entry pair = (HashMap.Entry)it.next();
				System.out.println("Answer: " + pair.getKey() + " Times: " + pair.getValue());
				it.remove(); 
			}
		}
		if(questionType.equals("Essay"))
		{
			//Just print all answers
			System.out.println("Replies: ");
			for (Question q: answeredQuestions)
			{
				System.out.println(q.returnUserAnswer());
			}
		}
		
		if(questionType.equals("Rank"))
		{
			//again, create a string of each ranking, store it and see whether it matches to any other string
			//of a different survey/test, hence whether there has been another exact ranking
			HashMap<String, Integer> temp = new HashMap();
			
			System.out.println("Replies: ");
			for (Question q: answeredQuestions)
			{
				System.out.println(q.returnUserAnswer());
				if(!temp.containsKey(q.returnUserAnswer()))
					temp.put(q.returnUserAnswer(), 1);
				else
					temp.put(q.returnUserAnswer(), temp.get(q.returnUserAnswer())+1 );
			}
			System.out.println("Tabulate: ");
			Iterator it = temp.entrySet().iterator();
			while (it.hasNext()) {
				HashMap.Entry pair = (HashMap.Entry)it.next();
				System.out.println("Ranking: " + pair.getKey() + " Times: " + pair.getValue());
				it.remove(); 
			}
		}
		
		if(questionType.equals("Matching"))
		{
			//again, create a string of each matching, store it and see whether it matches to any other string
			//of a different survey/test, hence whether there has been another exact match
			
			HashMap<String, Integer> temp = new HashMap();
			
			System.out.println("Replies: ");
			for (Question q: answeredQuestions)
			{
				System.out.println(q.returnUserAnswer());
				if(!temp.containsKey(q.returnUserAnswer()))
					temp.put(q.returnUserAnswer(), 1);
				else
					temp.put(q.returnUserAnswer(), temp.get(q.returnUserAnswer())+1 );
			}
			System.out.println("Tabulate: ");
			Iterator it = temp.entrySet().iterator();
			while (it.hasNext()) {
				HashMap.Entry pair = (HashMap.Entry)it.next();
				System.out.println("Matching: " + pair.getKey() + " Times: " + pair.getValue());
				it.remove();
			}
		}
	}
		
	public String getOption()
	{
		return option;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}
	public Vector<Question> getQuestions()
	{
		return this.questions;
	}
}
