package Model;

import java.util.ArrayList;
import java.util.List;

public class QuestionModel {
	
	private int id;
	private String text;
	private String explanation;
	private String hash;	
	private SendCategoryModel category;
	private CategoryModel category_1;	
	private List<AnswerModel> answers = new ArrayList<AnswerModel>();
	
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
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public CategoryModel getCategory() {
		return category_1;
	}
	public void setCategory(CategoryModel category) {
		this.category_1 = category;
	}
	public SendCategoryModel getSendCategory() {
		return category;
	}
	public void setSendCategory(SendCategoryModel category) {
		this.category = category;
	}
	public List<AnswerModel> getAnswers() {
		return answers;
	}
	public void setAnswer(AnswerModel answer) {
		this.answers.add(answer);
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
	public static String ToStringQuestionList() {
		StringBuilder sb = new StringBuilder();		
		for(QuestionModel item : questionList) {
			StringBuilder answers = new StringBuilder();
			sb.append("Text: " + item.getText() + "\n");
			sb.append("Explanation: " + item.getExplanation() + "\n");
			sb.append("Hashcode: " + item.getHash() + "\n");
			sb.append("Category: " + item.getCategory().getTitle() + "\n");
			for(AnswerModel answerItem : item.getAnswers())
			{
				answers.append("\t Text: " + answerItem.getText() + "\n");
			}
			sb.append("Answer: \n" + answers.toString());
		}
		return sb.toString();
	}
}
