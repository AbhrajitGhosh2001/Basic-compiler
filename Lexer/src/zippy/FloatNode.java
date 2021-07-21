//float holder node
public class FloatNode extends Node{
	
	private float number;
	
	public FloatNode(String number) {
		this.number=Float.parseFloat(number);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "FLOAT "+number;
	}
	
	public float getFloat(){
		return number;
	}

}
