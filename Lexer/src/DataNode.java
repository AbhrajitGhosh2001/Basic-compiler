import java.util.ArrayList;
//holds the data to be read by the readNode also holds Node List

public class DataNode extends StatementNode {
	ArrayList<Node> list;
	
	DataNode(ArrayList<Node> list){
		this.list=list;
	}
	ArrayList<Node> getList(){
		return list;
	}
	
	@Override
	public String toString() {
		return "DATA: " +list.toString();
	}

}
