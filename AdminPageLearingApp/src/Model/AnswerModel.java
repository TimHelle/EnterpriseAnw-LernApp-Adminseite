package Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"text","isCorrect"})
public class AnswerModel {	

	private String text;
	private boolean isCorrect;
	
	@JsonProperty("text")
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	@JsonProperty("isCorrect")
	public boolean isCorrect() {
		return isCorrect;
	}
	public void setIsCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}
	
	
	
}
