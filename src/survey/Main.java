package survey;

import java.io.IOException;

public class Main {

	public static void main(String[] args) {		
		
		Boolean quit = false;		
		while(!quit)
		{
			IO.displayMenu1();
			int option = IO.getNumberLessThan("Please select the number for one of the options above", 3);
			//Test
			if(option == 1)
			{
				Test testSurvey = new Test();
				Boolean quit1 = false;
				while (!quit1) 
				{
					IO.displayMenu2("Test");
					int option2 = IO.getNumberLessThan("Please select the number for one of the options above", 9);	
					if(option2 == 1)
					{
						String title =IO.getAnswer("Title of the Test : "); 
						testSurvey.create();
						testSurvey.setName(title);
					}
					else if(option2 == 2)
					{
						if(testSurvey.getName() == null)
							System.out.println("You need to create a test before you can display it!");				
						else testSurvey.display();	
					}
					else if(option2 == 3)
						IO.loadMenu("Test");						
					else if(option2 == 4)
					{
						if(testSurvey.getName() == null)
							System.out.println("You need to create a test before you can save it!");
						else testSurvey.save();
					}
					else if(option2 == 5)				
						testSurvey.modify();
					else if(option2 == 6)
						testSurvey.take();
					else if(option2 == 7)
						testSurvey.tabulate(testSurvey.getOption());
					else if(option2 == 8)
						testSurvey.grade();
					else if(option2 == 9)
					{
						quit1 = true;
						quit = true;
					}					
					else System.out.println("Please select a valid option!");
				}
			}
			//Survey
			else if(option == 2)
			{
				Survey testSurvey = new Survey();
				Boolean quit1 = false;
				while (!quit1) 
				{
					IO.displayMenu2("Survey");
					int option2 = IO.getNumberLessThan("Please select the number for one of the options above", 8);	
					if(option2 == 1)
					{
						String title =IO.getAnswer("Title of the Survey : "); 
						testSurvey.create();
						testSurvey.setName(title);
					}
					else if(option2 == 2)
					{
						if(testSurvey.getName() == null)
							System.out.println("You need to create a survey before you can display it!");				
						else testSurvey.display();	
					}
					else if(option2 == 3)
						IO.loadMenu("Survey");						
					else if(option2 == 4)
					{
						if(testSurvey.getName() == null)
							System.out.println("You need to create a survey before you can save it!");
						else testSurvey.save();
					}
					else if(option2 == 5)				
						testSurvey.modify();
					else if(option2 == 6)
						testSurvey.take();
					else if(option2 == 7)
						testSurvey.tabulate("Survey");
					else if(option2 == 8)
					{
						quit1 = true;
						quit = true;
					}
					else System.out.println("Please select a valid option!");
				}
			}
			//Exit
			else if(option == 3)
			{
				quit = true;
			}		
			else System.out.println("Please select a valid option!");
		}
		//exiting
		System.out.println("Thanks, bye!");		
	}

}
