//holds a Boolean Operation >, >=, <, <=, <>, =
public class BooleanOperationNode extends Node{
	String operation;
	BooleanOperationNode(String operation){
		this.operation=operation;
	}
	
	public String getOperation() {
		return operation;
	}
	
	@Override
	public String toString() {
		return "BooleanOpNode: " + operation;
	}

}
