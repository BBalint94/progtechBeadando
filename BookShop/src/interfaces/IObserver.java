package interfaces;

public interface IObserver {

	void onSuccess(String message);
	
	void onFailed(String message);
}
