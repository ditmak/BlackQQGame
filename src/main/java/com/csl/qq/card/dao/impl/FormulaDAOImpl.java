package com.csl.qq.card.dao.impl;

import java.io.Serializable;
import java.util.Collection;

import com.csl.qq.card.dao.FormulaDAO;
import com.csl.qq.card.domain.Formula;
import com.csl.util.DAO.impl.BaseDAOImpl;

public class FormulaDAOImpl extends BaseDAOImpl<Formula> implements FormulaDAO{
	public Collection<Formula> getFormulaByThemeId(Serializable themeId) {
		return 	getSession().createQuery("from Formula f where f.theme.id='"+themeId+"'").list();
	}
}
