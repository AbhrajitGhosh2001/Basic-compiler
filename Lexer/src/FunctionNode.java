import java.util.ArrayList;
//holds a function
public class FunctionNode extends Node{
	String function;
	ArrayList<Node> arguments;
	FunctionNode(String function,ArrayList<Node> arguments){
		this.function=function;
		this.arguments=arguments;
	}
	FunctionNode(String function){
		this.function=function;
	}
	String getFunction(){
		return function;
	}
	ArrayList<Node> getArgs(){
		return arguments;
	}
	
	@Override
	public String toString() {
		if(arguments!=null) {
		return "Function: "+function+" Arguments: "+ arguments.toString();}
		else {
			return "Function: "+function;
		}
	}

}
