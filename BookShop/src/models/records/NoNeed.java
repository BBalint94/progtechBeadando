package models.records;

public class NoNeed extends TransportStrategy{

	@Override
	public Order transport(Order order) {
		order.setWay("szem�lyes �tv�tel");
		return order;
	}

}
