import java.util.ArrayList;

//Node for a for loop
public class ForNode extends StatementNode{
	private VariableNode A;
	private int i;
	private int j;
	private int step;
	private Node next;
	private Node after;
	ForNode(VariableNode A,int i,int j){
		this.A=A;
		this.i=i;
		this.j=j;
		step=1;
	}
	ForNode(VariableNode A,int i,int j,int step){
		this.A=A;
		this.i=i;
		this.j=j;
		this.step=step;
	}
	public int getStep() {
		return step;
	}
	
	public int getTo() {
		return j;
	}
	
	public int getVarValue() {
		return i;
	}
	
	public String getVariable() {
		return A.getVariable();
	}
	
	void setNext(Node next) {
		this.next=next;
	}
	
	Node getNext() {
		return next;
	}
	
	void setAfter(Node after){
		this.after=after;
	}
	
	Node getAfter() {
		return after;
	}
	
	@Override
	public String toString() {
		return "FOR:"+A.toString()+"="+i+" TO "+j+" STEP "+step;
	}
}
