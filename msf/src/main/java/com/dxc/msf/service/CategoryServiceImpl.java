// Nhut Lam
package com.dxc.msf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxc.msf.dao.CategoryDAO;
import com.dxc.msf.model.CategoryDTO;
import com.dxc.msf.model.UserDTO;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryDAO categoryDAO;

	// create
	@Override
	public boolean CreateCategory(CategoryDTO category) {
		if (!category.getCategoryName().isEmpty()) {
			boolean success = categoryDAO.CreateCategory(category);
			if (success) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}

	@Override
	public List<CategoryDTO> getListCategory() {
		return categoryDAO.getListCategory();
	}


	@Override
	public boolean UpdateCategory(CategoryDTO categoryDTO) {
		return categoryDAO.UpdateCategory(categoryDTO);
	}

	@Override
	public boolean DeleteCategory(int categoryID) {
		return categoryDAO.DeleteCategory(categoryID);

	}

	@Override
	public CategoryDTO getCategoryByID(int categoryID) {
		return categoryDAO.getCategoryByID(categoryID);
	}
}
