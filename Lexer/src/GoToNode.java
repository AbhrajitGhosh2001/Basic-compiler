
//derives from node holds a string variable
public class GoToNode extends StatementNode{

	private String variable;
	
	GoToNode(String variable){
		this.variable=variable;
	}

	String getVariable() {
		return variable;
	}
	
	@Override
	public String toString() {
		
		return "GoToNode: "+variable;
	}
	
	
}