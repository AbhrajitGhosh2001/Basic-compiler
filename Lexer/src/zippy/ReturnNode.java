//holds nothing just returns
public class ReturnNode extends StatementNode{
	private String retur;
	ReturnNode(String retur){
		this.retur=retur;
	}
	@Override
	public String toString() {
		if(retur!=null) {
		return "RETURN: "+retur;
		}
		else {
			return "RETURN";
		}
	}
	
}
