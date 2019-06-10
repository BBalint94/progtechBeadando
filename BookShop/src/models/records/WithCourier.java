package models.records;

public class WithCourier extends TransportStrategy{

	@Override
	public Order transport(Order order) {		
		int price = order.getSumPrice();
		price += 1000;
		order.setSumPrice(price);
		order.setWay("futár");
		return order;
	}

}
