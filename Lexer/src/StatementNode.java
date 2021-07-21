
public class StatementNode extends Node{

	public StatementNode nextStatement;
    
	public void setNextStatement(Node nextStatement) {
		this.nextStatement=(StatementNode) nextStatement;
	}
	
	public Node getNextStatement() {
		return nextStatement;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
}
