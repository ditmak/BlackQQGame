package com.csl.qq.card.service.impl;

import java.util.List;

import com.csl.qq.card.dao.ThemeDAO;
import com.csl.qq.card.domain.Theme;
import com.csl.qq.card.service.ThemeService;

public class ThemeServiceImpl implements ThemeService {
		private ThemeDAO themeDAO;
		public ThemeDAO getThemeDAO() {
			return themeDAO;
		}
		public void setThemeDAO(ThemeDAO themeDAO) {
			this.themeDAO = themeDAO;
		}
		public void saveTheme(Theme theme) {
				themeDAO.saveEntry(theme);
		}
		public List<Theme> getALLTheme(){
			return (List<Theme>) themeDAO.findAllEntry();
		}
}
