import java.util.ArrayList;

//holds Node list
public class PrintNode extends StatementNode{

	ArrayList<Node> nodelist;
	PrintNode(ArrayList<Node> nodelist){
		this.nodelist=nodelist;
	}
	
	public ArrayList<Node> getNodelist(){
		return nodelist;
	}
	
	String getZero() {
		VariableNode var= (VariableNode) nodelist.get(0);
		return var.getVariable();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return("PRINT :"+nodelist.toString());
	}
	
}
