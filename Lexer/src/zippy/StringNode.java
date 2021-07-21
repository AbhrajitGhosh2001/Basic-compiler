
public class StringNode extends StatementNode{

	String blah;
	StringNode(String blah){
		this.blah=blah;
	}
	
	String getWord() {
		return blah;
	}
	@Override
	public String toString() {
		return "STRING:"+ blah;
	}

}
