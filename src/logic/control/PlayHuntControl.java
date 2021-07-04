package logic.control;

import logic.bean.AnswerBean;

public class PlayHuntControl {

	public boolean answer(AnswerBean bean) {
		String trueAnswer = bean.getRiddleAnswer();
		String userAnswer = bean.getUserAnswer();
		
		if(trueAnswer.toLowerCase().equals(userAnswer.toLowerCase())) return true;
		
		return false;
	}
	
}
