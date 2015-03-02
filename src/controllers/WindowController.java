package controllers;

import controllers.Main;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * WindowsController control all the windows
 * *author Gruppe 23
 */
public class WindowController {

	private static Stage thisStage;
	private static Main program;
	
	
	public static void setProgram(Main p){
		program = p;
	
}
	
	public static void setStage(Stage stage){
		thisStage=stage;
	}
	
	public static void showStage(){
		thisStage.centerOnScreen();
		thisStage.setResizable(false);
		thisStage.show();
	}
	
	private static Parent replaceSceneContent(String fxml, int width, int height) throws Exception{
		Parent page = (Parent) FXMLLoader.load(
				program.getClass().getResource(fxml), null, 
				new JavaFXBuilderFactory());
		Scene scene = new Scene(page, width, height);
		thisStage.hide();
		thisStage.setScene(scene);
		thisStage.centerOnScreen();
		thisStage.setResizable(false);
		thisStage.show();
		if(scene == null){
			scene = new Scene(page);
			thisStage.setScene(scene);
		}
		else{
			thisStage.getScene().setRoot(page);
		}
		return page;
	}
	
	public static void goToLogin(){
		try {
			replaceSceneContent("../views/loginView.fxml", 600, 450 );
		}
		catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			
		}
	}
	
	public static void goToCalendarView(){
		try{
			replaceSceneContent("../views/calendarFinalView.fxml", 1500 , 987);
		}
		catch (Exception e){
			System.out.println(e);
			e.printStackTrace();
		}
	}
	
	public static void goToEventView(){
		try{
			Stage eventWindows = new Stage();
			Parent page;
			FXMLLoader loader = new FXMLLoader(program.getClass().getResource("../views/eventView.fxml"), null, new JavaFXBuilderFactory());
			page = (Parent) loader.load();
			EventController controller = loader.getController();
			controller.setStage(eventWindows);
			Scene scene = new Scene(page, 494, 712);
			eventWindows.setScene(scene);
			eventWindows.centerOnScreen();
			eventWindows.setResizable(false);
			eventWindows.show();
		}
		catch(Exception e){
			System.out.println(e);
			e.printStackTrace();
		}
	}
	
}
