import java.util.ArrayList;

public class InputNode extends StatementNode{
	ArrayList<Node> list;
	InputNode(ArrayList<Node> list){
		this.list=list;
	}
	
	ArrayList<Node> getList(){
		return list;
	}
	
	@Override
	public String toString() {
			return "InputNode: "+ list.toString();
		}
	}


