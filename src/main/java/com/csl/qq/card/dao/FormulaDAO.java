package com.csl.qq.card.dao;

import java.io.Serializable;
import java.util.Collection;

import com.csl.qq.card.domain.Formula;
import com.csl.util.DAO.BaseDAO;

public interface FormulaDAO extends BaseDAO<Formula>{
	Collection<Formula> getFormulaByThemeId(Serializable themeId);
}
