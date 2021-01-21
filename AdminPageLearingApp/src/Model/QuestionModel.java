package Model;

public class QuestionModel {
	
	private int id;
	private String text;
	private String explanation;
	private CategoryModel category;
	private AnswerModel answer[];
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public CategoryModel getCategory() {
		return category;
	}
	public void setCategory(CategoryModel category) {
		this.category = category;
	}
	public AnswerModel[] getAnswer() {
		return answer;
	}
	public void setAnswer(AnswerModel[] answer) {
		this.answer = answer;
	}
	
	
	
}
