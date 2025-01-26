package com.surveyProject.project.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.surveyProject.project.domain.survey.category.Category;

@Mapper
public interface CategoryMapper {
	int saveCategoryCount(Category category);
	
	List<Category> getCategory(Map<String, Object> map);
}
