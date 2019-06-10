package models.records;

public class NoNeed extends TransportStrategy{

	@Override
	public Order transport(Order order) {
		order.setWay("személyes átvétel");
		return order;
	}

}
