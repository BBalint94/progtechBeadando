package models.records;

public class PackageAutomat extends TransportStrategy{

	@Override
	public Order transport(Order order) {
		int price = order.getSumPrice();
		price += 700;
		order.setSumPrice(price);
		order.setWay("csomag autómata");
		return order;
	}

}
