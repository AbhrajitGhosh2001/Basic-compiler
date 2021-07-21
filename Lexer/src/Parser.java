
import java.util.ArrayList;
import java.util.Random;


public class Parser {

	private ArrayList<Token> tokens;
	//list to hold Nodes
	private ArrayList<Node> list= new ArrayList <Node>();


	//pass the token list through the constructor
	Parser(ArrayList<Token> tokens) {
		this.tokens=tokens;
	}



	//checks if the
	public Token.myEnum matchAndRemove(Token.myEnum tk) {
		if((tk==tokens.get(0).getEnum())) {
			return tk;
		}
		else return null;
	}


	//main method to take array list and parse it calls on statements
	public Node parse () throws Exception{
		while (tokens!=null) {
			return statements();
		}
		return null;
	}


	/*Method Called to deal with statements calls statement then adds it to the list
	 * Then puts it in a New StatementNode and returns the Node
	 */
	private Node statements () throws Exception {

		Node statements = null;

		statements=statement();

		list.add(statements);

		while (statements!=null) {
			statements=statement();
			list.add(statements);
		}
		list.remove(list.size()-1);
		System.out.println(list.toString());
		StatementsNode alist= new StatementsNode(list);
		return alist;
	}

	//Checks if it is print or comma and calls print method if Identifier it calls assignment to deal with the actual tokens
	private Node statement() throws Exception {

		// if GoSub looks for an identifier and returns that node
		if(matchAndRemove(Token.myEnum.GOSUB)!=null) {
			tokens.remove(0);
			if(matchAndRemove(Token.myEnum.IDENTIFIER)!=null) {
				String s=tokens.get(0).getString();
				tokens.remove(0);
				return new GosubNode(s);
			}
		}
		
		if(matchAndRemove(Token.myEnum.GOTO)!=null) {
			tokens.remove(0);
			if(matchAndRemove(Token.myEnum.IDENTIFIER)!=null) {
				String s=tokens.get(0).getString();
				tokens.remove(0);
				return new GoToNode(s);
			}
		}
		
		//for next
		if(matchAndRemove(Token.myEnum.NEXT)!=null) {
			tokens.remove(0);
			if(matchAndRemove(Token.myEnum.IDENTIFIER)!=null) {
				String s=tokens.get(0).getString();
				tokens.remove(0);
				return new NextNode(s);
			}
			return new NextNode(null);
		}


		//if Return looks for identifier and returns a return node
		if(matchAndRemove(Token.myEnum.RETURN)!=null) {
			tokens.remove(0);
			if(matchAndRemove(Token.myEnum.IDENTIFIER)!=null) {
				String s=tokens.get(0).getString();
				tokens.remove(0);
				return new ReturnNode(s);
			}
			return new ReturnNode(null);
		}
		//if Statement has a label it makes a labeled Statement node
		if(matchAndRemove(Token.myEnum.LABEL)!=null) {
			String labl;
			labl=tokens.get(0).getString();
			tokens.remove(0);
			return new LabeledStatementNode(labl, statement());
		}

		//if for makes for node 
		if(matchAndRemove(Token.myEnum.FOR)!=null) {
			tokens.remove(0);
			VariableNode a;
			int b;
			int c;
			int d;
			if(matchAndRemove(Token.myEnum.IDENTIFIER)!=null) {
				a=new VariableNode(tokens.get(0).getString());
				tokens.remove(0);
				if(matchAndRemove(Token.myEnum.EQUALS)!=null) {
					tokens.remove(0);
					if(matchAndRemove(Token.myEnum.Number)!=null) {
						b=Integer.parseInt(tokens.get(0).getString());
						tokens.remove(0);
						if(matchAndRemove(Token.myEnum.TO)!=null) {
							tokens.remove(0);
							if(matchAndRemove(Token.myEnum.Number)!=null) {
								c=Integer.parseInt(tokens.get(0).getString());
								tokens.remove(0);
								if(matchAndRemove(Token.myEnum.STEP)!=null) {
									tokens.remove(0);
									if(matchAndRemove(Token.myEnum.Number)!=null) {
										d=Integer.parseInt(tokens.get(0).getString());
										tokens.remove(0);
										return new ForNode(a, b, c, d);
									}
								}
								else {
									return new ForNode(a, b, c);
								}
							}
						}
					}
				}
				return null;
			}
			else {
				throw new Exception("wrong syntax (for)");
			}
		}

		//checks if one of the special starters
		if(matchAndRemove(Token.myEnum.PRINT)!=null||matchAndRemove(Token.myEnum.READ)!=null||matchAndRemove(Token.myEnum.INPUT)!=null||matchAndRemove(Token.myEnum.DATA)!=null||matchAndRemove(Token.myEnum.LABEL)!=null||matchAndRemove(Token.myEnum.IF)!=null) {
			return printStatements();
		}
		if(matchAndRemove(Token.myEnum.IDENTIFIER)!=null) {
			try {
				return assignment();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(matchAndRemove(Token.myEnum.STRING)!=null) {
			try {
				return assignment();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}


	//checks and adds to the list based on first word keeps going 
	private Node printStatements() throws Exception{

		//Handels If Statements
		if(matchAndRemove(Token.myEnum.IF)!=null) {
			ArrayList<Node> hlist= new ArrayList<Node>();
			tokens.remove(0);
			for (int i=1;i>0;) {
				if(matchAndRemove(Token.myEnum.IDENTIFIER)!=null) {
					VariableNode vn = new VariableNode(tokens.get(0).getString());
					tokens.remove(0);
					hlist.add(vn);
				} 
				else if(matchAndRemove(Token.myEnum.Number)!=null){
					if(tokens.get(0).getString().contains(".")) {
						FloatNode vn = new FloatNode(tokens.get(0).getString());
						tokens.remove(0);
						hlist.add(vn);
					}
					else {
						IntegerNode vn = new IntegerNode(tokens.get(0).getString());
						tokens.remove(0);
						hlist.add(vn);
					} 
				}
				else if(matchAndRemove(Token.myEnum.Less)!=null||matchAndRemove(Token.myEnum.Less)!=null||matchAndRemove(Token.myEnum.LessOrEqual)!=null||matchAndRemove(Token.myEnum.Greater)!=null||matchAndRemove(Token.myEnum.GreaterOrEqual)!=null||matchAndRemove(Token.myEnum.NOTEQUALS)!=null||matchAndRemove(Token.myEnum.EQUALS)!=null) {
					BooleanOperationNode vn = new BooleanOperationNode(tokens.get(0).getString());
					tokens.remove(0);
					hlist.add(vn);
				}
				else if(matchAndRemove(Token.myEnum.EndOfLine)!=null) {
					i--;
				}
				else {
					i--;
				}
			}
			String wow = null;
			if(matchAndRemove(Token.myEnum.THEN)!=null) {
				tokens.remove(0);
				wow = tokens.get(0).getString();
				tokens.remove(0);
			}
			//returning IfNode
			return new IfNode(hlist,wow);
		}

		if(matchAndRemove(Token.myEnum.PRINT)!=null) {
			ArrayList<Node> hlist= new ArrayList<Node>();
			tokens.remove(0);
			try {
				for (int i=1;i>0;) {
					if(matchAndRemove(Token.myEnum.IDENTIFIER)!=null) {
						VariableNode vn = new VariableNode(tokens.get(0).getString());
						tokens.remove(0);
						hlist.add(vn);
					} 
					else if(matchAndRemove(Token.myEnum.STRING)!=null) {
						hlist.add(new StringNode(tokens.get(0).getString()));
						tokens.remove(0);
					}
					else if(matchAndRemove(Token.myEnum.Number)!=null) {
						try {
							hlist.add(expression(tokens.get(0)));
						}
						catch(Exception e) {
						}
					}

					if(matchAndRemove(Token.myEnum.Comma)!=null) {
						tokens.remove(0);
					}
					else if(matchAndRemove(Token.myEnum.EndOfLine)!=null) {
						i--;
					}
				}
			} catch (IndexOutOfBoundsException e) {

			}
			return new PrintNode(hlist);
		}
		else if(matchAndRemove(Token.myEnum.READ)!=null) {
			ArrayList<Node> hlist= new ArrayList<Node>();
			tokens.remove(0);
			try {
				for (int i=1;i>0;) {
					if(matchAndRemove(Token.myEnum.IDENTIFIER)!=null) {
						VariableNode vn = new VariableNode(tokens.get(0).getString());
						tokens.remove(0);
						hlist.add(vn);
					} 
					else if(matchAndRemove(Token.myEnum.STRING)!=null) {
						hlist.add(new StringNode(tokens.get(0).getString()));
						tokens.remove(0);
					}

					else if(matchAndRemove(Token.myEnum.Number)!=null) {
						try {
							hlist.add(expression(tokens.get(0)));
						}
						catch(Exception e) {
						}
					}

					if(matchAndRemove(Token.myEnum.Comma)!=null) {
						tokens.remove(0);
					}
					else if(matchAndRemove(Token.myEnum.EndOfLine)!=null) {
						i--;
					}
				}
			} catch (IndexOutOfBoundsException e) {

			}
			return new ReadNode(hlist);
		}

		else if(matchAndRemove(Token.myEnum.DATA)!=null) {
			ArrayList<Node> hlist= new ArrayList<Node>();
			tokens.remove(0);
			try {
				for (int i=1;i>0;) {
					if(matchAndRemove(Token.myEnum.IDENTIFIER)!=null) {
						VariableNode vn = new VariableNode(tokens.get(0).getString());
						tokens.remove(0);
						hlist.add(vn);
					} 
					else if(matchAndRemove(Token.myEnum.STRING)!=null) {
						hlist.add(new StringNode(tokens.get(0).getString()));
						tokens.remove(0);
					}

					else if(matchAndRemove(Token.myEnum.Number)!=null) {
						try {
							hlist.add(expression(tokens.get(0)));
						}
						catch(Exception e) {
						}
					}

					if(matchAndRemove(Token.myEnum.Comma)!=null) {
						tokens.remove(0);
					}
					else if(matchAndRemove(Token.myEnum.EndOfLine)!=null) {
						i--;
					}
				}
			} catch (IndexOutOfBoundsException e) {

			}
			return new DataNode(hlist);
		}

		else if(matchAndRemove(Token.myEnum.INPUT)!=null) {
			ArrayList<Node> hlist= new ArrayList<Node>();
			tokens.remove(0);
			if(matchAndRemove(Token.myEnum.IDENTIFIER)!=null||matchAndRemove(Token.myEnum.STRING)!=null)
				try {
					for (int i=1;i>0;) {
						if(matchAndRemove(Token.myEnum.IDENTIFIER)!=null) {
							VariableNode vn = new VariableNode(tokens.get(0).getString());
							tokens.remove(0);
							hlist.add(vn);
						} else if(matchAndRemove(Token.myEnum.STRING)!=null) {
							hlist.add(new StringNode(tokens.get(0).getString()));
							tokens.remove(0);
						}
						if(matchAndRemove(Token.myEnum.Comma)!=null) {
							tokens.remove(0);
						}
						else if(matchAndRemove(Token.myEnum.EndOfLine)!=null) {
							i--;
						}
					}
				} catch (IndexOutOfBoundsException e) {

				}
			else {
				throw new Exception("not what input is either identifier or string after");
			}
			return new InputNode(hlist);
		}
		return null;
	}



	//assigns the assignment Node saves Identifier then removes equal and puts expression in a node then uses the save and the node to return a AssignmentNode with both
	private Node assignment() throws Exception {
		VariableNode vnode = new VariableNode(tokens.get(0).getString());
		tokens.remove(0);
		Node normal=null;
		if(matchAndRemove(Token.myEnum.EQUALS) != null) {
			tokens.remove(0);
			if(matchAndRemove(Token.myEnum.STRING)!=null){
				normal= new StringNode(tokens.get(0).getString());
				tokens.remove(0);
			}
			else{
				normal= expression(tokens.get(0));
				
				}
		}
		else {
			throw new Exception("Not a equal sign");
		}
		AssignmentNode assign= new AssignmentNode(vnode,normal);
		return assign;
	}






	/*expression looks for number sets it to left using the factor method then it checks the sign if its * or / it calls term to handle it if its + or - 
	 * it makes a saves the symbol and looks for another number if it finds another number it makes right equal to that other number using factor before returning it checks if 
	 * there is another symbol then it uses the helper method to use recursion when needed for multiple operators
	 * 
	 */
	private Node expression (Token token) throws Exception {

		Node left=null;
		

		if (matchAndRemove(Token.myEnum.Number)!=null) {
			left=factor(token);
		}

		

		//For All built in Functions
		else if (matchAndRemove(Token.myEnum.RANDOM)!=null||(matchAndRemove(Token.myEnum.LEFT$)!=null)||(matchAndRemove(Token.myEnum.RIGHT$)!=null)||(matchAndRemove(Token.myEnum.MID$)!=null)||(matchAndRemove(Token.myEnum.NUM$)!=null)||(matchAndRemove(Token.myEnum.VALper)!=null)||(matchAndRemove(Token.myEnum.VAL)!=null)){
			ArrayList<Node> hlist= new ArrayList<Node>();
			String funcType=null;
			//RANDOM
			if(matchAndRemove(Token.myEnum.RANDOM)!=null){
				int i = new Random().nextInt();
				String s= String.valueOf(i);
				return new FunctionNode("RANDOM");
			}
			//LEFT$ AND RIGHT$
			if((matchAndRemove(Token.myEnum.LEFT$)!=null)||(matchAndRemove(Token.myEnum.RIGHT$)!=null)) {
				funcType= tokens.get(0).getString();
				tokens.remove(0);
				if((matchAndRemove(Token.myEnum.LPAREN)!=null)) {
					tokens.remove(0);
					if ((matchAndRemove(Token.myEnum.STRING)!=null)) {
						hlist.add(new StringNode(tokens.get(0).getString()));
						tokens.remove(0);
						if((matchAndRemove(Token.myEnum.Comma)!=null)) {
							tokens.remove(0);
						}
						if((matchAndRemove(Token.myEnum.Number)!=null)) {
							hlist.add(new IntegerNode(tokens.get(0).getString()));
							tokens.remove(0);
							if((matchAndRemove(Token.myEnum.RPAREN)!=null)) {
								tokens.remove(0);
							}
						}
					}
				}
			}
			// MID$
			if((matchAndRemove(Token.myEnum.MID$)!=null)) {
				funcType= tokens.get(0).getString();
				tokens.remove(0);
				if((matchAndRemove(Token.myEnum.LPAREN)!=null)) {
					tokens.remove(0);
					if ((matchAndRemove(Token.myEnum.STRING)!=null)) {
						hlist.add(new StringNode(tokens.get(0).getString()));
						tokens.remove(0);
						if((matchAndRemove(Token.myEnum.Comma)!=null)) {
							tokens.remove(0);
						}
						if((matchAndRemove(Token.myEnum.Number)!=null)) {
							hlist.add(new IntegerNode(tokens.get(0).getString()));
							tokens.remove(0);
							if((matchAndRemove(Token.myEnum.Comma)!=null)) {
								tokens.remove(0);
							}
							if((matchAndRemove(Token.myEnum.Number)!=null)) {
								hlist.add(new IntegerNode(tokens.get(0).getString()));
								tokens.remove(0);
								if((matchAndRemove(Token.myEnum.RPAREN)!=null)) {
									tokens.remove(0);
								}
							}
						}
					}
				}
			}
			//VAL and VAL$
			if((matchAndRemove(Token.myEnum.VALper)!=null)||(matchAndRemove(Token.myEnum.VAL)!=null)) {
				funcType= tokens.get(0).getString();
				tokens.remove(0);
				if((matchAndRemove(Token.myEnum.LPAREN)!=null)) {
					tokens.remove(0);
					if ((matchAndRemove(Token.myEnum.STRING)!=null)) {
						hlist.add(new StringNode(tokens.get(0).getString()));
						tokens.remove(0);
						if((matchAndRemove(Token.myEnum.RPAREN)!=null)) {
							tokens.remove(0);
						}
					}
				}
			}
			//NUM$
			if((matchAndRemove(Token.myEnum.NUM$)!=null)) {
				funcType= tokens.get(0).getString();
				tokens.remove(0);
				if((matchAndRemove(Token.myEnum.LPAREN)!=null)) {
					tokens.remove(0);
					if(matchAndRemove(Token.myEnum.Number)!=null){
						if(tokens.get(0).getString().contains(".")) {
							FloatNode vn = new FloatNode(tokens.get(0).getString());
							tokens.remove(0);
							hlist.add(vn);
						}
						else {
							IntegerNode vn = new IntegerNode(tokens.get(0).getString());
							tokens.remove(0);
							hlist.add(vn);
						} 
					}
					if((matchAndRemove(Token.myEnum.RPAREN)!=null)) {
						tokens.remove(0);
					}
				}
			}

			return new FunctionNode(funcType, hlist);
		}
		
		else if (matchAndRemove(Token.myEnum.IDENTIFIER)!=null) {
			left=factor(token);
		}
		
		//For plus
		if(matchAndRemove(Token.myEnum.Plus)!=null) {
			String save;
			save="+";
			tokens.remove(0);

			Node right=null;

			if(left==null)
				throw new Exception("left of + is null");
			if(matchAndRemove(Token.myEnum.Number)!=null) {
				token=tokens.get(0);
				right=factor(token);
				MathOpNode math= new MathOpNode(save,left,right);
				math=helperMethod(math,right);
				return math;
			}

			else if(matchAndRemove(Token.myEnum.LPAREN)!=null) {
				tokens.remove(0);
				boolean found=false;
				for(int i=0;i<tokens.size()-1;i++) {
					if(tokens.get(i).getEnum() ==Token.myEnum.RPAREN) {
						found=true;
					}
				}
				if(found==true) {
					return expression(tokens.get(0));
				}
				else {
					throw new Exception("No Right Parenthesis found");
				}
			}

			else {
				throw new Exception("Should be number or parenthesis");
			}

		}

		//for minus
		else if(matchAndRemove(Token.myEnum.Minus)!=null) {
			String save;
			save="-";
			tokens.remove(0);

			Node right=null;

			if(left==null) 
				throw new Exception("left of - is null");

			if(matchAndRemove(Token.myEnum.Number)!=null) {
				token=tokens.get(0);
				right=factor(token);

				MathOpNode math= new MathOpNode(save,left,right);
				math=helperMethod(math,right);
				return math;
			}
			else if(matchAndRemove(Token.myEnum.LPAREN)!=null) {
				tokens.remove(0);
				boolean found=false;
				for(int i=0;i<tokens.size()-1;i++) {
					if(matchAndRemove(Token.myEnum.LPAREN)!=null) {
						found=true;
					}
				}
				if(found==true) {

					return expression(tokens.get(0));
				}
				else {
					throw new Exception("No Right Parenthesis found");
				}
			}
			else {
				throw new Exception("Should be a number or parenthesis");
			}

		}

		else if(matchAndRemove(Token.myEnum.LPAREN)!=null) {
			tokens.remove(0);
			boolean found=false;
			for(int i=0;i<tokens.size()-1;i++) {
				if(tokens.get(i).getEnum() ==Token.myEnum.RPAREN) {
					found=true;
				}
			}
			if(found==true) {

				return expression(tokens.get(0));
			}
			else {
				throw new Exception("No Right Parenthesis found");
			}
		}

		//if division or multiplication
		else if(matchAndRemove(Token.myEnum.Divide)!=null||matchAndRemove(Token.myEnum.Times)!=null) {
			return term(left);
		}

		else {
			return left;
		}
	}


	//takes token after checking if its a number and turns it to a float node if it contains a decimal point or else it becomes a integer node I remove from the start of the list every time
	private Node factor(Token token) {

		Node number=null;
		if(matchAndRemove(Token.myEnum.Number)!=null){
			if(token.getString().contains(".")) {
				number = new FloatNode(token.getString());
			}
			else {
				number = new IntegerNode(token.getString());
			}
		}
		else if(matchAndRemove(Token.myEnum.IDENTIFIER)!=null) {
			number= new VariableNode(token.getString());
		}
		tokens.remove(0);
		return number;
	}



	//is called for / or * in expression deals with 
	private Node term(Node left) throws Exception {


		if(matchAndRemove(Token.myEnum.Divide)!=null) {
			String save;
			save="/";
			tokens.remove(0);

			Node right=null;

			if(left==null)
				throw new Exception("left of / is null");

			if(matchAndRemove(Token.myEnum.Number)!=null) {
				Token token= tokens.get(0);
				right=factor(token);

				MathOpNode math= new MathOpNode(save,left,right);
				math=helperMethod(math,right);
				return math;
			}

			else if(matchAndRemove(Token.myEnum.LPAREN)!=null) {
				tokens.remove(0);
				boolean found=false;
				for(int i=0;i<tokens.size()-1;i++) {
					if(tokens.get(i).getEnum() ==Token.myEnum.RPAREN) {
						found=true;
					}
				}
				if(found==true) {
					return expression(tokens.get(0));
				}
				else {
					throw new Exception("No Right Parenthesis found");
				}
			}
			else {
				throw new Exception("Should be number or parenthesis");
			}
		}

		else if(matchAndRemove(Token.myEnum.Times)!=null) {
			String save;
			save="*";
			tokens.remove(0);

			Node right=null;

			if(left==null)
				throw new Exception("left of * is null");

			if(matchAndRemove(Token.myEnum.Number)!=null) {
				right=factor(tokens.get(0));

				MathOpNode math= new MathOpNode(save,left,right);
				math=helperMethod(math,right);
				return math;
			}

			else if(matchAndRemove(Token.myEnum.LPAREN)!=null) {
				tokens.remove(0);
				boolean found=false;
				for(int i=0;i<tokens.size()-1;i++) {
					if(Token.myEnum.RPAREN==tokens.get(i).getEnum()) {
						found=true;
					}
				}
				if(found==true) {

					return expression(tokens.get(0));
				}
				else {
					throw new Exception("No Right Parenthesis found");
				}
			}
			if(matchAndRemove(Token.myEnum.IDENTIFIER)!=null) {
				right=factor(tokens.get(0));

				MathOpNode math= new MathOpNode(save,left,right);
				math=helperMethod(math,right);
				return math;
			}
			else {
				throw new Exception("Should be number or parenthesis");
			}
		}

		else {
			throw new Exception("Should be number or parenthesis");
		}

	}

	//helper method for recursion if we have 2 + 2 - 4 + 5 
	private MathOpNode helperMethod(MathOpNode math, Node right) throws IndexOutOfBoundsException{
		try {
			String sv = null;
			if(matchAndRemove(Token.myEnum.Plus)!=null) {
				sv="+";
				tokens.remove(0);
				if(matchAndRemove(Token.myEnum.LPAREN)!=null) {
					tokens.remove(0);
					boolean found=false;
					for(int i=0;i<tokens.size()-1;i++) {
						if(tokens.get(i).getEnum() ==Token.myEnum.RPAREN) {
							found=true;
						}
					}
					if(found==true) {
						right=expression(tokens.get(0));
					}
					else {
						throw new Exception("No Right Parenthesis found");
					}
				}
				else if (matchAndRemove(Token.myEnum.Number)!=null) {
					right=factor(tokens.get(0));
				}
				else {
					throw new Exception("I was not able to find number or left parenthesis");
				}
				math= new MathOpNode(sv,math,right);
				if(matchAndRemove(Token.myEnum.Plus)!=null||matchAndRemove(Token.myEnum.Minus)!=null||matchAndRemove(Token.myEnum.Divide)!=null||matchAndRemove(Token.myEnum.Times)!=null) {
					math=helperMethod(math,right);
				}
				return math;
			}

			else if(matchAndRemove(Token.myEnum.Minus)!=null) {
				sv="-";
				tokens.remove(0);
				if(matchAndRemove(Token.myEnum.LPAREN)!=null) {
					tokens.remove(0);
					boolean found=false;
					for(int i=0;i<tokens.size()-1;i++) {
						if(matchAndRemove(Token.myEnum.RPAREN)!=null) {
							found=true;
						}
					}
					if(found==true) {
						right=expression(tokens.get(0));
					}
					else {
						throw new Exception("No Right Parenthesis found");
					}
				}
				else if (matchAndRemove(Token.myEnum.Number)!=null) {
					right=factor(tokens.get(0));
				}
				else {
					throw new Exception("I was not able to find number or left parenthesis");
				}
				math= new MathOpNode(sv,math,right);
				if(matchAndRemove(Token.myEnum.Plus)!=null||matchAndRemove(Token.myEnum.Minus)!=null||matchAndRemove(Token.myEnum.Divide)!=null||matchAndRemove(Token.myEnum.Times)!=null) {
					math=helperMethod(math,right);
				}
				return math;
			}
			else if(matchAndRemove(Token.myEnum.Divide)!=null) {
				sv="/";
				tokens.remove(0);
				if(matchAndRemove(Token.myEnum.LPAREN)!=null) {
					tokens.remove(0);
					boolean found=false;
					for(int i=0;i<tokens.size()-1;i++) {
						if(tokens.get(i).getEnum() ==Token.myEnum.RPAREN) {
							found=true;
						}
					}
					if(found==true) {
						right=expression(tokens.get(0));
					}
					else {
						throw new Exception("No Right Parenthesis found");
					}
				}
				else if (matchAndRemove(Token.myEnum.Number)!=null) {
					right=factor(tokens.get(0));
				}
				else {
					throw new Exception("I was not able to find number or left parenthesis");
				}
				math= new MathOpNode(sv,math,right);
				if(matchAndRemove(Token.myEnum.Plus)!=null||matchAndRemove(Token.myEnum.Minus)!=null||matchAndRemove(Token.myEnum.Divide)!=null||matchAndRemove(Token.myEnum.Times)!=null) {
					math=helperMethod(math,right);
				}
				return math;
			}
			else if(matchAndRemove(Token.myEnum.Times)!=null) {
				sv="*";
				tokens.remove(0);
				if(matchAndRemove(Token.myEnum.LPAREN)!=null) {
					tokens.remove(0);
					boolean found=false;
					for(int i=0;i<tokens.size()-1;i++) {
						if(matchAndRemove(Token.myEnum.RPAREN)!=null) {
							found=true;
						}
					}
					if(found==true) {
						right=expression(tokens.get(0));
					}
					else {
						throw new Exception("No Right Parenthesis found");
					}
				}
				else if (matchAndRemove(Token.myEnum.Number)!=null) {
					right=factor(tokens.get(0));
				}
				else {
					throw new Exception("I was not able to find number or left parenthesis");
				}
				math= new MathOpNode(sv,math,right);
				if(matchAndRemove(Token.myEnum.Plus)!=null||matchAndRemove(Token.myEnum.Minus)!=null||matchAndRemove(Token.myEnum.Divide)!=null||matchAndRemove(Token.myEnum.Times)!=null) {
					math=helperMethod(math,right);
				}
				return math;
			}

		}
		catch(Exception e) {

		}
		return math;
	}
}
