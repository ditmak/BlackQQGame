package com.csl.qq.card.service.impl;

import com.csl.qq.card.dao.OperatorDAO;
import com.csl.qq.card.domain.Operator;
import com.csl.qq.card.service.OperatorService;

public class OperatorServiceImpl implements OperatorService {
		private OperatorDAO operatorDAO;
		public OperatorDAO getOperatorDAO() {
			return operatorDAO;
		}
		public void setOperatorDAO(OperatorDAO operatorDAO) {
			this.operatorDAO = operatorDAO;
		}
		public void saveOperator(Operator operator) {
				operatorDAO.saveEntry(operator);
		}
}
