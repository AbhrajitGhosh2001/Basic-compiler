//MathOpNode is the main Node with left and right references
public class MathOpNode extends Node{
	
	Node left;
	Node right;
	String symbol;

	public enum mySign{
		Add, Subtract, Multiply, Divide
	}
	
	private mySign sign;
	
	//constructor takes string
	public MathOpNode(String symbol, Node left, Node right) {
		this.symbol=symbol;
		setSign();
		this.left=left;
		this.right=right;
		setSign();
	}
	

	
	
	@Override
	public String toString() {
		
		return "MathNode("+symbol+","+left+","+right+")";
		
	}
	
	String getSign() {
		 return symbol;
	 }
	
	Node getLeft() {
		return left;
	}
	
	Node getRight() {
		return right;
	}
	
	//sets sign
	private void setSign() {
		if(symbol.equals("+")) {
			sign=mySign.Add;
		}
		else if(symbol.equals("-")) {
			sign=mySign.Subtract;
		}
		else if(symbol.equals("*")) {
			sign=mySign.Multiply;
		}
		else if(symbol.equals("/")) {
			sign=mySign.Divide;
		}
	}
	

}
