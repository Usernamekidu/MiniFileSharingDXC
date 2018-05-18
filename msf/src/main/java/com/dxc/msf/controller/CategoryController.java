//Nhut Lam 
package com.dxc.msf.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import com.dxc.msf.model.CategoryDTO;
import com.dxc.msf.service.CategoryService;
import com.google.gson.Gson;

@Controller
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	@RequestMapping(value = "/category/create", method = RequestMethod.POST, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody String eventCreate(@RequestBody CategoryDTO category) {
		boolean success = categoryService.CreateCategory(category);
		if (success) {
			return "{\"status\": \"OK\"}";
		} else {
			return "{\"status\": \"Failed\"}";
		}
	}

	@RequestMapping(value = "/category/list", method = RequestMethod.GET, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody String getListUser() {
		List<CategoryDTO> list = categoryService.getListCategory();
		String json = new Gson().toJson(list);
		return json;
	}

	@RequestMapping(value = "/category/edit", method = RequestMethod.POST, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody String eventEdit(@RequestBody CategoryDTO cateDTO) {
		boolean success = categoryService.UpdateCategory(cateDTO);
		if (success) {
			return "{\"status\": \"OK\"}";
		} else {
			return "{\"status\": \"Failed\"}";
		}
	}

	@RequestMapping(value = "/category/delete", method = RequestMethod.POST, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody String eventDelete(@RequestBody int categoryID) {
		boolean success = categoryService.DeleteCategory(categoryID);
		if (success) {
			return "{\"status\": \"OK\"}";
		} else {
			return "{\"status\": \"Failed\"}";
		}
	}

	@RequestMapping(value = "/category/getCategory", method = RequestMethod.POST,produces = { "application/json; charset=UTF-8" })
	public @ResponseBody String find_categoryByID(@RequestBody CategoryDTO categoryDTO) {
		String json = new Gson().toJson(categoryService.getCategoryByID(categoryDTO.getCategoryID()));
		return json;
	}
}
