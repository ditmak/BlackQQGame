package com.csl.qq.card.service;

import java.io.Serializable;
import java.util.List;

import com.csl.qq.card.domain.Formula;

public interface FormulaService {
	void saveFormula(Formula formula);
	List<Formula> getFormulaByThemeId(Serializable themeId);
}
