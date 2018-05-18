package com.dxc.msf.service;

import java.util.List;

import com.dxc.msf.model.CategoryDTO;
import com.dxc.msf.model.FileDTO;

public interface CategoryService {
	// Nhut Lam
	public boolean CreateCategory(CategoryDTO category);
	public boolean UpdateCategory(CategoryDTO categoryDTO);
	public boolean DeleteCategory(int categoryID);
	public List<CategoryDTO> getListCategory();
	public CategoryDTO getCategoryByID(int categoryID);	

	
}
