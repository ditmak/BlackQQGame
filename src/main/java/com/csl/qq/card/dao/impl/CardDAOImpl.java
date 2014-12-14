package com.csl.qq.card.dao.impl;

import org.hibernate.Query;

import com.csl.qq.card.dao.CardDAO;
import com.csl.qq.card.domain.Card;
import com.csl.util.DAO.impl.BaseDAOImpl;

public class CardDAOImpl extends BaseDAOImpl<Card> implements CardDAO{
	public Card findCardByName(String name,Integer themeId) {
		Query query =  getSession().createQuery("from Card c where c.name=? and c.theme.id=?");
		query.setString(0, name);
		query.setInteger(1,themeId);
		return (Card) query.uniqueResult();
	}

	@Override
	public Card findCardByID(String ID) {
		Query query = getSession().createQuery("from Card c where c.cardID=?");
		query.setString(0, ID);
		return (Card)query.uniqueResult();
	}
}
