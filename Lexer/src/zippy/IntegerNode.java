//integer holder node
public class IntegerNode extends Node{

	private int number;
	
	public IntegerNode(String number) {
		this.number=Integer.parseInt(number);
	}
	
	@Override
	public String toString() {
		return "Int "+number;
	}
	
	public int getInt(){
		return number;
	}
	

}
