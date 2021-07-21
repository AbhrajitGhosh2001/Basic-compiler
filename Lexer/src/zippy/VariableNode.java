//holds variable name derives from node

public class VariableNode extends StatementNode{

	private String variableNode;
	
	VariableNode(String variableNode){
		this.variableNode=variableNode;
	}
	
	public String getVariable() {
		return variableNode;
	}
	
	@Override
	public String toString() {
		return "VariableNode: "+variableNode;
	}
	
}
