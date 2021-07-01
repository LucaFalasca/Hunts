package logic.state.draw;

import javafx.scene.canvas.GraphicsContext;
import logic.state.draw.states.RectangleState;
import logic.state.draw.states.State;

public class DrawMachine {
private State state;

	public DrawMachine() {
		this.state = RectangleState.getInstance();
	}

	public DrawMachine(State state) {
		this.state = state;
	}
	
	public boolean draw(GraphicsContext gc, double startX, double startY, double endX, double endY) {
		return state.draw(gc, startX, startY, endX, endY);
	}
	
	public void clean(GraphicsContext gc) {
		gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
	}
	
	public void clean(GraphicsContext gc, double startX, double startY, double endX, double endY) {
		state.clean(gc, startX, startY, endX, endY);
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public State getCurrentState() {
		return this.state;
	}
	
	@Override
	public String toString() {
		return state.toString();
	}
}
