package Model;

import java.util.ArrayList;
import java.util.List;

public class SendQuestionModel {

	private String text;
	private String explanation;
	private String hash;	
	private List<AnswerModel> answers = new ArrayList<AnswerModel>();
	private SendCategoryModel category;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getExplanation() {
		return explanation;
	}
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public List<AnswerModel> getAnswers() {
		return answers;
	}
	public void setAnswers(List<AnswerModel> answers) {
		this.answers = answers;
	}
	public void setAnswer(AnswerModel answer) {
		this.answers.add(answer);
	}
	public SendCategoryModel getCategory() {
		return category;
	}
	public void setCategory(SendCategoryModel category) {
		this.category = category;
	}	
}
