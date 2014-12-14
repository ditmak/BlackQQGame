package com.csl.qq.card.service.impl;

import java.io.Serializable;
import java.util.List;

import com.csl.qq.card.dao.FormulaDAO;
import com.csl.qq.card.domain.Formula;
import com.csl.qq.card.service.FormulaService;

public class FormulaServiceImpl implements FormulaService {
		private FormulaDAO formulaDAO;
		public FormulaDAO getFormulaDAO() {
			return formulaDAO;
		}
		public void setFormulaDAO(FormulaDAO formulaDAO) {
			this.formulaDAO = formulaDAO;
		}
		public void saveFormula(Formula formula) {
				formulaDAO.saveEntry(formula);
		}
		public List<Formula> getFormulaByThemeId(Serializable themeId) {
		return (List<Formula>) formulaDAO.getFormulaByThemeId(themeId);
		}
}
