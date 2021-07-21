
//last class used holds a VariableNode node and a Node node
public class AssignmentNode extends StatementNode{
	
	private Node node;
	private VariableNode vnode;
	
	AssignmentNode(VariableNode vnode,Node node){
		this.node=node;
		this.vnode=vnode;
	}
	
	public Node getNode() {
		return node;
	}
	public VariableNode getVariableNode() {
		return vnode;
	}
	@Override
	public String toString(){
		return "AssignmentNode("+vnode.getVariable()+", "+node.toString()+")";
	}

}
