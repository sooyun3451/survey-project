package com.surveyProject.project.web.controller;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.surveyProject.project.service.survey.category.CategoryService;
import com.surveyProject.project.web.dto.CMRespDto;
import com.surveyProject.project.web.dto.category.CategoryRespDto;
import com.surveyProject.project.web.dto.category.UpdateCategoryReqDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/survey")
public class CategoryController {

	private final CategoryService categoryService;
	private static final String CATEGORYINSERT = "/update";
	private static final String CATEGORYLIST = "/category";

	@PutMapping(CATEGORYINSERT)
	public ResponseEntity<?> categoryinsert(@RequestBody UpdateCategoryReqDto updateCategoryReqDto) {
		boolean status = false;

		try {
			status = categoryService.saveCategoryCount(updateCategoryReqDto);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new CMRespDto<>(-1, "Failed", status));
		}
		return ResponseEntity.ok().body(new CMRespDto<>(1, "Success", status));
	}

	@GetMapping(CATEGORYLIST)
	public ResponseEntity<?> categoryList(@RequestParam int page, int categoryCount) {
		List<CategoryRespDto> list = null;

		try {
			list = categoryService.geyCategory(page, categoryCount);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "Failed", null));
		}
		return ResponseEntity.ok().body(new CMRespDto<>(1, "Success", list));
	}
}
