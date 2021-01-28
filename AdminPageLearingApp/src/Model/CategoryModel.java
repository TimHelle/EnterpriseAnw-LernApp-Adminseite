package Model;

import java.util.ArrayList;
import java.util.List;

public class CategoryModel {
	
	private String description;
	private String title;
	private String hash;
	public static List<CategoryModel> categoryList = new ArrayList<CategoryModel>();
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public static List<CategoryModel> getCategoryList() {
		return categoryList;
	}
	public static boolean addCategoryToList(CategoryModel category) {
		try
		{
			categoryList.add(category);
			return true;
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return false;
		}
	}
	public static boolean removeCategoryFromList(CategoryModel category) {
		try
		{
			categoryList.remove(category);
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
		for(CategoryModel item : categoryList) {
			sb.append("Description: " + item.getDescription() + "\n");
			sb.append("Title: " + item.getTitle() + "\n");
			sb.append("Hashcode: "+ item.getHash() + "\n");
		}
		return sb.toString();
	}
	
}
