//derives from node holds a string variable
public class GosubNode extends StatementNode{

	private String variable;
	
	GosubNode(String variable){
		this.variable=variable;
	}
	
	String getVariable() {
		return variable;
	}

	@Override
	public String toString() {
		
		return "GosubNode: "+variable;
	}
	
	
}
