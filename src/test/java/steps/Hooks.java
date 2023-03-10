package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;


import static utils.BaseClass.*;

public class Hooks{
	@Before
	public static void preCondition(){
		launchApplication.accept(driverFactory.get());
	}
	
	@After
	public static void postCondition(Scenario scenario){
		byte[] pic;
		if(scenario.isFailed()){
			//failed screenshot will be available inside failed folder
			pic = takeScreenshot("failed/" + scenario.getName());
		}else {
			pic = takeScreenshot("passed/" + scenario.getName());
		}
		
		//to attach the screenshot in our report
		scenario.attach(pic, "image/png", scenario.getName());
		quitBrowser();
		
	}
}
