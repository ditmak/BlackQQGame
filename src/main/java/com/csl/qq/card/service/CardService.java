package com.csl.qq.card.service;

import com.csl.qq.card.domain.Card;

public interface CardService {
	void saveCard(Card card);
	Card findCardByName(String name,Integer themeId);
	void updateCard(Card card);
	Card findCardByID(String id);
}
