package com.csl.qq.card.service;

import java.util.List;

import com.csl.qq.card.domain.Theme;

public interface ThemeService {
	void saveTheme(Theme theme);
	List<Theme>  getALLTheme();
}
