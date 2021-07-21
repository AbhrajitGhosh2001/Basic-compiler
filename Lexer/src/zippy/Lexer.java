import java.util.ArrayList;
import java.util.HashMap;

public class Lexer {

	// field variables
	private String line;
	
	//data structure for known words
	private HashMap<String, String> known = new HashMap<String, String>();
	
	/*
	 * Constructor can pass in String this way
	 */
	public Lexer(String line){
	this.line = line;
	}
	
	/*
	 * method used for tokenizing and giving each token a state
	 * returns a ArrayList of type Token
	 */
	public ArrayList<Token> lex(String line)
	{
		//Can pass in String this way aswell
		this.line = line;
		
		//Using The split method to put it in a String Array
		String[] myArray = null;
		myArray=line.split(" ");
		
		//Creating and Initializing myTokens as a Token Arraylist
		ArrayList<Token> myTokens;
		myTokens = new ArrayList<Token>();
		
		
				
		//Known Words or hash map library for statements
				known.put("PRINT", "PRINT");
				known.put("READ", "READ");
				known.put("INPUT", "INPUT");
				known.put("DATA", "DATA");
				known.put("RETURN", "RETURN");
				known.put("GOSUB", "GOSUB");
				known.put("FOR", "FOR");
				known.put("NEXT", "NEXT");
				known.put("IF", "IF");
				known.put("GOTO", "GOTO");
		//loop to iterate array and create appropriate tokens
		for(int j=0;j<myArray.length;j++) {
			
			//checks if String is a number
			boolean numeric = true;
			
			try {
				@SuppressWarnings("unused")
				Double num = Double.parseDouble(myArray[j]);
			} catch (NumberFormatException e) {
				numeric = false;
			}
			
			if (numeric) {
				myTokens.add(new Token(Token.myEnum.Number,myArray[j]));
			}
			
			else if(myArray[j].equals(",")) {
				myTokens.add(new Token(Token.myEnum.Comma,myArray[j]));
			}
			else if(myArray[j].equals("-")) {
				myTokens.add(new Token(Token.myEnum.Minus,myArray[j]));
			}
			
			else if(myArray[j].equals("+")) {
				myTokens.add(new Token(Token.myEnum.Plus,myArray[j]));
			}
			
			else if(myArray[j].equals("/")) {
				myTokens.add(new Token(Token.myEnum.Divide,myArray[j]));
			}
			
			else if(myArray[j].equals("*")) {
				myTokens.add(new Token(Token.myEnum.Times,myArray[j]));
			}
			
			else if(myArray[j].equals("=")) {
				myTokens.add(new Token(Token.myEnum.EQUALS,myArray[j]));
			}
			
			else if(myArray[j].equals("<")) {
				myTokens.add(new Token(Token.myEnum.Less,myArray[j]));
			}
			
			else if(myArray[j].equals("<=")) {
				myTokens.add(new Token(Token.myEnum.LessOrEqual,myArray[j]));
			}
			
			else if(myArray[j].equals(">")) {
				myTokens.add(new Token(Token.myEnum.Greater,myArray[j]));
			}
			
			else if(myArray[j].equals(">=")) {
				myTokens.add(new Token(Token.myEnum.GreaterOrEqual,myArray[j]));
			}
			
			else if(myArray[j].equals("<>")) {
				myTokens.add(new Token(Token.myEnum.NOTEQUALS,myArray[j]));
			}
			
			else if(myArray[j].equals("(")) {
				myTokens.add(new Token(Token.myEnum.LPAREN,myArray[j]));
			}
			
			else if(myArray[j].equals(")")) {
				myTokens.add(new Token(Token.myEnum.RPAREN,myArray[j]));
			}
			
			//Functions lexed here
			else if(myArray[j].equals("RANDOM")) {
				myTokens.add(new Token(Token.myEnum.RANDOM,myArray[j]));
			}
			else if(myArray[j].equals("LEFT$")) {
				myTokens.add(new Token(Token.myEnum.LEFT$,myArray[j]));
			}
			else if(myArray[j].equals("RIGHT$")) {
				myTokens.add(new Token(Token.myEnum.RIGHT$,myArray[j]));
			}
			else if(myArray[j].equals("MID$")) {
				myTokens.add(new Token(Token.myEnum.MID$,myArray[j]));
			}
			else if(myArray[j].equals("NUM$")) {
				myTokens.add(new Token(Token.myEnum.NUM$,myArray[j]));
			}
			else if(myArray[j].equals("VAL%")) {
				myTokens.add(new Token(Token.myEnum.VALper,myArray[j]));
			}
			else if(myArray[j].equals("VAL")) {
				myTokens.add(new Token(Token.myEnum.VAL,myArray[j]));
			}
			
			//Deals with  the words to lex
			else {
			
				//if in "" is a string
				if(myArray[j].contains("\"")) {
					;
					myTokens.add(new Token(Token.myEnum.STRING,myArray[j].replace("\"", "")));
				}
				
				else if (myArray[j].contains(":")) {
					;
						myTokens.add(new Token(Token.myEnum.LABEL,myArray[j].replaceAll(":","")));
					}
				
				//check if known by trying to get from hashmap
				else if(known.get(myArray[j])!=null) {
					if(myArray[j].equals("PRINT")||myArray[j].equals("print")) {
						myTokens.add(new Token(Token.myEnum.PRINT,"PRINT"));
					}
					else if(myArray[j].equals("READ")||myArray[j].equals("read")) {
						myTokens.add(new Token(Token.myEnum.READ,"READ"));
					}
					else if(myArray[j].equals("DATA")||myArray[j].equals("data")) {
						myTokens.add(new Token(Token.myEnum.DATA,"DATA"));
					}
					else if(myArray[j].equals("INPUT")||myArray[j].equals("input")) {
						myTokens.add(new Token(Token.myEnum.INPUT,"INPUT"));
					}
					else if(myArray[j].equals("RETURN")||myArray[j].equals("return")) {
						myTokens.add(new Token(Token.myEnum.RETURN,"RETURN"));
					}
					else if(myArray[j].equals("FOR")||myArray[j].equals("for")) {
						myTokens.add(new Token(Token.myEnum.FOR,"FOR"));
					}
					else if(myArray[j].equals("GOSUB")||myArray[j].equals("gosub")) {
						myTokens.add(new Token(Token.myEnum.GOSUB,"GOSUB"));
					}
					else if(myArray[j].equals("GOTO")||myArray[j].equals("goto")) {
						myTokens.add(new Token(Token.myEnum.GOTO,"GOTO"));
					}
					else if(myArray[j].equals("NEXT")||myArray[j].equals("NEXT")) {
						myTokens.add(new Token(Token.myEnum.NEXT,"NEXT"));
					}
					else if(myArray[j].equals("IF")||myArray[j].equals("if")) {
						myTokens.add(new Token(Token.myEnum.IF,"IF"));
					}
				}
				
				else if(myArray[j].equals("TO")) {
					myTokens.add(new Token(Token.myEnum.TO,"TO"));
				}
				else if(myArray[j].equals("STEP")) {
					myTokens.add(new Token(Token.myEnum.STEP,"STEP"));
				}
				
				else if(myArray[j].equals("THEN")||myArray[j].equals("then")) {
					myTokens.add(new Token(Token.myEnum.THEN,"THEN"));
				}
				
				else if(myArray[j].contains("%")) {
					myTokens.add(new Token(Token.myEnum.IDENTIFIER,myArray[j]));
				}
					
				else{
						myTokens.add(new Token(Token.myEnum.IDENTIFIER,myArray[j]));
					}
				}
				
		}
		//adds EndOfLine Token
		myTokens.add(new Token(Token.myEnum.EndOfLine,null));
		//returns Tokens
		return myTokens;
	}


}
