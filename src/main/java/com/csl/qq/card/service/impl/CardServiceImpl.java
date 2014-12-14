package com.csl.qq.card.service.impl;

import com.csl.qq.card.dao.CardDAO;
import com.csl.qq.card.domain.Card;
import com.csl.qq.card.service.CardService;

public class CardServiceImpl implements CardService {
		private CardDAO cardDAO;
		public CardDAO getCardDAO() {
			return cardDAO;
		}
		public void setCardDAO(CardDAO cardDAO) {
			this.cardDAO = cardDAO;
		}
		public void saveCard(Card card) {
				cardDAO.saveEntry(card);
		}
		public Card findCardByName(String name,Integer themeId) {
			return cardDAO.findCardByName(name,themeId);
		}
		@Override
		public void updateCard(Card card) {
			cardDAO.updateEntry(card);
		}
		@Override
		public Card findCardByID(String id) {
			return cardDAO.findCardByID(id);
		}
}
