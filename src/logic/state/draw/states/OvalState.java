package logic.state.draw.states;

import javafx.scene.canvas.GraphicsContext;

public class OvalState implements State{

	private static OvalState instance = new OvalState();
	
	private OvalState() {
		
	}
	
	public static OvalState getInstance() {
		return instance;
	}
	
	@Override
	public boolean draw(GraphicsContext gc, double startX, double startY, double endX, double endY) {
		double w = (endX - startX);
		double h = (endY- startY);
		
		gc.fillOval(startX, startY, w, h);
		return false;
	}
	
	@Override
	public void clean(GraphicsContext gc, double startX, double startY, double endX, double endY) {
		double width = endX - startX;
    	double height = endY - startY;
    	
		gc.clearRect(startX, startY, width, height);
	}
	
	@Override
	public String toString() {
		return "OvalState";
	}
	

}
