import java.util.ArrayList;

//Handels
public class IfNode extends StatementNode{
	ArrayList<Node> list;
	String then;
	IfNode(ArrayList<Node> list,String then){
		this.list=list;
		this.then=then;
	}

	@Override
	public String toString() {
		return "IfNode: "+list+"THEN "+then;
	}

	public String getThen(){
		return then;
	}
	
	public ArrayList<Node> getList() {
		// TODO Auto-generated method stub
		return list;
	}

}
