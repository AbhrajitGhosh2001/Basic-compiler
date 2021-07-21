//holds a statement as a Node  and a string Label
public class LabeledStatementNode extends StatementNode{
	
	Node Node;
	String Label;
	LabeledStatementNode(String Label,Node Node){
		this.Label=Label;
		this.Node=Node;
	}
	
	String getLabel() {
		return Label;
	}
	Node getStatement() {
		return Node;
	}
	
	@Override
	public String toString() {
		return "LabeledStatementNode:"+Label+": "+Node.toString();
	}
}
