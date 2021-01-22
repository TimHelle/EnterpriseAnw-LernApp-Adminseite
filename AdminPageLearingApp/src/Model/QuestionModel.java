package Model;

import java.util.ArrayList;
import java.util.List;

public class QuestionModel {
	
	private int id;
	private String text;
	private String explanation;
	private CategoryModel category;
	private List<AnswerModel> answer = new ArrayList<AnswerModel>();
	
	public static List<QuestionModel> questionList = new ArrayList<QuestionModel>();
	
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
	public List<AnswerModel> getAnswer() {
		return answer;
	}
	public void setAnswer(AnswerModel answer) {
		this.answer.add(answer);
	}
	
	public static List<QuestionModel> getQuestionList(){
		return questionList;
	}
	public static boolean addQuestionToList(QuestionModel question) {
		try
		{
			questionList.add(question);
			return true;
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return false;
		}
	}
	public static boolean removeCategoryFromList(QuestionModel question) {
		try
		{
			questionList.remove(question);
			return true;
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return false;
		}
	}
	public static String ToStringCategoryList() {
		StringBuilder sb = new StringBuilder();
		StringBuilder answers = new StringBuilder();
		for(QuestionModel item : questionList) {
			sb.append("Id: " + item.getId() + "\n");
			sb.append("Text: " + item.getText() + "\n");
			sb.append("Explanation: " + item.getExplanation() + "\n");
			sb.append("Category: " + item.getCategory() + "\n");
			for(AnswerModel answerItem : item.getAnswer())
			{
				answers.append("Id: " + answerItem.getId() + "\n");
				answers.append("Text: " + answerItem.getText() + "\n");
				answers.append("Description: " + answerItem.getDescription() + "\n");
			}
			sb.append("Answer: " + answers.toString());
		}
		return sb.toString();
	}
}
