package com.csl.qq.card.dao;

import com.csl.qq.card.domain.Card;
import com.csl.util.DAO.BaseDAO;

public interface CardDAO extends BaseDAO<Card>{
	Card findCardByName(String name,Integer themeId);
	Card findCardByID(String ID);
}
