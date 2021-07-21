
public class Token {
	//private fields
	private myEnum state;

	private String strValue;
	
	 
	//constructors
	public Token(String s) {
		setString(s);
	}

	public Token( myEnum stite,String s) {
		setString(s);
		setEnum(stite);
	}

	//Enum types
	public enum myEnum {

		Comma,
		Number,
		Minus,
		Plus,
		Divide,
		Times,
		EndOfLine,
		EQUALS,
		NOTEQUALS,
		Less,
		LessOrEqual,
		Greater,
		GreaterOrEqual,
		LPAREN,
		RPAREN,
		STRING,
		IDENTIFIER,
		LABEL,
		PRINT,
		READ,
		INPUT,
		DATA,
		RETURN,
		GOSUB,
		FOR,
		STEP,
		NEXT,
		TO,
		IF,
		THEN,
		RANDOM,
		LEFT$,
		RIGHT$,
		MID$,
		NUM$,
		VALper,
		VAL, 
		GOTO
	}	
	
//accessors for String
	public String getString() { 
		return strValue; 
		}

	public void setString(String strValue) {
		this.strValue = strValue; 
	}
	
	
	//accessors for State
	public myEnum getEnum() { 
		return state; 
		}

	public void setEnum(myEnum stite) {
		this.state = stite; 
	}

	//Turns Token into String and used to output
	@Override
	public String toString() {
	
		if(this.state==myEnum.Number) {
			return state + "(" + strValue + ") ";
		}
		
		else
			return state + " ";
	}
}
