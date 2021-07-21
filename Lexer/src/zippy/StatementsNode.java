import java.util.ArrayList;

//Holds list of nodes ReadNode and Data Node derives from it
public class StatementsNode extends Node{
	
	ArrayList<Node> list;
	Node next;
	StatementsNode(ArrayList<Node> list){
		this.list=list;
	}
	
	public ArrayList<Node> getList(){
		return list;
	}
	
	void setNext(Node next){
		this.next=next;
	}
	
	@Override
	public String toString() {
		return list.toString();
	}
}
