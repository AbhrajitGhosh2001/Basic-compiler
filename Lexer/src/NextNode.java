//Holds a string derives from node
public class NextNode extends StatementNode{
	String Next;
	Node For;
	Node after;
	NextNode(String Next){
		this.Next=Next;
	}
	
	void setFor(Node For) {
		this.For=For;
	}
	
	Node getFor() {
		return For;
	}
	
	String getNext() {
		return Next;
	}
	
	void setAfter(Node after){
		this.after=after;
	}

	@Override
	public String toString() {
		return "Next: "+Next;
	}
}
