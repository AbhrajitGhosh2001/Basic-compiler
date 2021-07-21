import java.util.ArrayList;
//should read from DataNode takes Node list and stores it

public class ReadNode extends StatementNode{
	ArrayList<Node> list;
	
	ReadNode(ArrayList<Node> list){
		this.list=list;
	}
	
	ArrayList<Node> getList(){
		return list;
	}
	
	@Override
	public String toString() {
		return "READ: "+list.toString();
	}

	
}
