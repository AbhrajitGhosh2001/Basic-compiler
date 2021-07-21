import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Interpreter {
	//all statementNodes
	private ArrayList<Node> nodes;
	//temp holder
	private ArrayList<Node> innernodes;
	//Comparable
	private ArrayList<Node> compareNode=new ArrayList<Node>();
	Interpreter(ArrayList<Node> nodes){
		this.nodes=nodes;
		change();
	}

	//had to add this in the loop because all of them were statements Nodes now they are comparable
	private void change() {
		for(int i=0;i<nodes.size();i++) {
			//takes Node out of statmentsNode
			StatementsNode wow=(StatementsNode)nodes.get(i);
			innernodes=(wow.getList());
			compareNode.add(innernodes.get(0));
		}
	}

	//variables
	HashMap<String, Integer> hashInt = new HashMap<String, Integer>();
	HashMap<String, Float> hashFloat = new HashMap<String, Float>();
	HashMap<String, String> hashString = new HashMap<String, String>();
	//label	 
	HashMap<String, Node> hashlabel = new HashMap<String, Node>();
	//Data collection
	private ArrayList<Node> Data = new ArrayList<Node>();

	void Intitialize() {
		System.out.println("Initializing");
		accept();
		for(int i=0;i<compareNode.size();i++) {
			//for methods that return I it interprets inside the method then skips in this for loop by giving the i value it should be on
			//checks what variable
			if(compareNode.get(i).getClass().equals(ForNode.class)) {
				i=loops(compareNode.get(i));
			}
			//for gosub
			else if(compareNode.get(i).getClass().equals(GosubNode.class)) {
				i=substituteJutsu(compareNode.get(i));
			}
			//for If
			else if(compareNode.get(i).getClass().equals(IfNode.class)) {
				i=whatIf(compareNode.get(i),i);
			}
			else if(compareNode.get(i).getClass().equals(LabeledStatementNode.class)) {
				LabeledStatementNode lblnode=(LabeledStatementNode) compareNode.get(i);
				interpret(lblnode.getStatement());
			}
			
			//Did not know if label with return is skiped until called this skips it so it only works when you call turn above off if so
			/*else if(compareNode.get(i).getClass().equals(LabeledStatementNode.class)) {
				for (int x=i+1;x<compareNode.size();x++) {
					if(compareNode.get(x).getClass().equals(LabeledStatementNode.class)) {
						break;
					}
					else if(compareNode.get(x).getClass().equals(ReturnNode.class)) {
						
						i=x;
					}
				}
			}*/
		
			else {
				interpret(compareNode.get(i));
			}
		}
	}

	//interpret function main function for interpreter
	void interpret(Node node) {

		//for Read
		if(node.getClass().equals(ReadNode.class)) {
			ReadNode read = (ReadNode) node;
			ArrayList<Node> list=read.getList();
			System.out.println();
			for(int i=0;i<list.size();i++) {
				if(list.get(i).getClass().equals(StringNode.class)) {
					for(int j=0;j<list.size();j++) {
						if(Data.get(j).getClass().equals(StringNode.class)) {
							StringNode putIn=(StringNode) list.get(i);
							StringNode putIn2=(StringNode) Data.get(j);
							System.out.print(putIn2.getWord()+" ");
							hashString.put(putIn.getWord(),putIn2.getWord());
						}
					}
				}
				else if(list.get(i).getClass().equals(VariableNode.class)) {
					VariableNode vnode=(VariableNode) list.get(i);
					String s=vnode.getVariable();
					if(s.contains("%")) {
						for(int j=0;j<list.size();j++) {
							if(Data.get(j).getClass().equals(FloatNode.class)) {
								FloatNode putIn2=(FloatNode) Data.get(j);
								System.out.print(putIn2.getFloat()+" ");
								hashFloat.put(s,putIn2.getFloat());
							}
						}
					}
					else if(s.contains("$")) {
						for(int j=0;j<list.size();j++) {
							if(Data.get(j).getClass().equals(StringNode.class)) {
								StringNode putIn2=(StringNode) Data.get(j);
								System.out.print(putIn2.getWord()+" ");
								hashString.put(s,putIn2.getWord());
							}
						}
					}
					else {
						for(int j=0;j<list.size();j++) {
							if(Data.get(j).getClass().equals(IntegerNode.class)) {
								IntegerNode putIn2=(IntegerNode) Data.get(j);
								System.out.println(putIn2.getInt());
								hashInt.put(s,putIn2.getInt());
							}
						}
					}
				}

			}

		}
		//Assignment
		else if(node.getClass().equals(AssignmentNode.class)) {

			AssignmentNode assign = (AssignmentNode) node;
			VariableNode varn=assign.getVariableNode();
			Node check =assign.getNode();
			if(check.getClass().equals(StringNode.class)) {
				StringNode str = (StringNode) check;
				hashString.put(varn.getVariable(),str.getWord());
			}
			else if(check.getClass().equals(FunctionNode.class)) {
				Node fnode = functionCall(check);
				if(fnode.getClass().equals(StringNode.class)) {
					StringNode str = (StringNode) fnode;
					hashString.put(varn.getVariable(),str.getWord());
				}
				else if(fnode.getClass().equals(MathOpNode.class)) {
					MathOpNode math = (MathOpNode) fnode;
					math.getLeft();
					if(math.getLeft().getClass().equals(FloatNode.class)) {
						hashFloat.put(varn.getVariable(),EvaluateFloatMathOp(math));
					}
					else {
						hashInt.put(varn.getVariable(),EvaluateIntMathOp(math));
					}
				}
				else if(fnode.getClass().equals(IntegerNode.class)) {
					IntegerNode inti = (IntegerNode) fnode ;
					hashInt.put(varn.getVariable(),inti.getInt());
				}
				else if(fnode.getClass().equals(FloatNode.class)) {
					FloatNode flit = (FloatNode) fnode;
					hashFloat.put(varn.getVariable(),flit.getFloat());
				}
			}
			else if(check.getClass().equals(MathOpNode.class)) {

				MathOpNode math = (MathOpNode) check;

				if(math.getLeft().getClass().equals(FloatNode.class)) {
					hashFloat.put(varn.getVariable(),EvaluateFloatMathOp(math));
				}
				else {
					EvaluateIntMathOp(math);
					hashInt.put(varn.getVariable(),EvaluateIntMathOp(math));
				}
			}
			else if(check.getClass().equals(IntegerNode.class)) {
				IntegerNode inti = (IntegerNode) check ;
				hashInt.put(varn.getVariable(),inti.getInt());
			}
			else if(check.getClass().equals(FloatNode.class)) {
				FloatNode flit = (FloatNode) check;
				hashFloat.put(varn.getVariable(),flit.getFloat());
			}
		}
		//Input
		else if(node.getClass().equals(InputNode.class)) {
			Scanner keyboard = new Scanner(System.in);
			InputNode inp = (InputNode) node;
			ArrayList<Node> list=inp.getList();
			for(int i=0;i<list.size();i++) {
				if(list.get(i).getClass().equals(StringNode.class)) {
					StringNode str=(StringNode) list.get(i);
					System.out.println(str.getWord()+"? Enter Below");
				}
				else if(list.get(i).getClass().equals(VariableNode.class)) {
					VariableNode vnode=(VariableNode) list.get(i);
					String s=vnode.getVariable();
					if(s.contains("%")) {
						float flit=keyboard.nextFloat();
						hashFloat.put(s,flit);
					}
					else if(s.contains("$")) {
						String str=keyboard.nextLine();
						hashString.put(s,str);
					}
					else {
						for(int j=0;j<list.size();j++) {
							int inti=keyboard.nextInt();
							hashInt.put(s,inti);
						}
					}
				}
			}
		}
		//Print
		else if(node.getClass().equals(PrintNode.class)) {
			PrintNode prnt = (PrintNode) node;
			ArrayList<Node>list=prnt.getNodelist();

			for(int i=0;i<list.size();i++) {
				if(list.get(i).getClass().equals(MathOpNode.class)){
					MathOpNode math=(MathOpNode) list.get(i);
					if(math.getLeft().getClass().equals(FloatNode.class)) {
						float ans = EvaluateFloatMathOp(list.get(i));
						System.out.print(ans);
					}
					else if(math.getLeft().getClass().equals(IntegerNode.class)) {
						int ans = EvaluateIntMathOp(list.get(i));
						System.out.print(ans);
					}

				}
				else if(list.get(i).getClass().equals(StringNode.class)) {
					StringNode str= (StringNode) list.get(i);
					System.out.print(str.getWord());
				}
				else if(list.get(i).getClass().equals(VariableNode.class)) {
					VariableNode vnode=(VariableNode) list.get(i);
					String s=vnode.getVariable();

					if(s.contains("$")) {
						System.out.print(hashString.get(s));
					}

					else if(s.contains("%")) {
						System.out.print(hashFloat.get(s));
					}
					else {
						System.out.print(hashInt.get(s));
					}
				}

			}
			System.out.println();
		}
	}
	
	//handles if
	int whatIf(Node node,int re) {
		IfNode ifner = (IfNode) node;
		ArrayList<Node> list=ifner.getList();
		if(EvaluateBooleanExpression(list)) {
			interpret(hashlabel.get(ifner.getThen()));
			for(int i=0;i<compareNode.size();i++) {
				if(compareNode.get(i).getClass().equals(LabeledStatementNode.class)) {
					LabeledStatementNode lblnode=(LabeledStatementNode) compareNode.get(i);
					if(ifner.getThen().equals(lblnode.getLabel())) {
						return i;
					}
				}
			}
		}
		return re;
	}
	
	//handles gosub
	int substituteJutsu(Node node){
		GosubNode gogo=(GosubNode) node;
		int x=0;
		int start=0,end=0;
		for(int i=0;i<compareNode.size();i++) {
			if(compareNode.get(i).equals(node)) {
				x=i;
			}
			if(compareNode.get(i).getClass().equals(LabeledStatementNode.class)) {
				LabeledStatementNode lblnode=(LabeledStatementNode) compareNode.get(i);
				if(gogo.getVariable().equals(lblnode.getLabel())) {
					interpret(lblnode.getStatement());
					start=i;
					for(int j=start;j<compareNode.size();j++) {
						if(compareNode.get(j).getClass().equals(ReturnNode.class)) {
							end=j;
						}
					}
				}
			}
		}
		for(int j=start;j<end;j++) {
			interpret(compareNode.get(j));
		}
		return x;
	}
	

	//handles for
	int loops(Node node) {
		//for for
		int remember=0,remember2=0;
		if(node.getClass().equals(ForNode.class)) {
			ForNode frnod = (ForNode) node;
			
			int start=frnod.getVarValue();
			int to=frnod.getTo();
			int increment=frnod.getStep();

			hashInt.put(frnod.getVariable(), frnod.getVarValue());
			for(int i=0;i<compareNode.size();i++) {
				if(node.equals(compareNode.get(i))) {
					remember=i+1;
				}
				if(frnod.getNext().equals(compareNode.get(i))) {
					remember2=i;
				}
			}

			for(int x=start-1;x<=to-1;) {
				x=x+increment;
				hashInt.put(frnod.getVariable(), x);
				for(int j=remember;j<=remember2;j++) {
					interpret(compareNode.get(j));
				}
			}
		}
		return remember2;
	}

	//handels basics build in functions
	Node functionCall(Node node) {
		FunctionNode fnode=(FunctionNode) node;
		String check=fnode.getFunction();
		String s = null;
		//returns random integer
		if(check.equals("RANDOM")) {
			int i = new Random().nextInt();
			s= String.valueOf(i);	
			return new StringNode(s);
		}
		//returns n letters from left
		else if(check.equals("LEFT$")) {
			ArrayList<Node>list=fnode.getArgs();
			String use = null;
			int from = 0;
			for(int i=0;i<list.size();i++) {
				if(list.get(i).getClass().equals(StringNode.class)) {
					StringNode str=(StringNode) list.get(i);
					use=str.getWord();
				}
				else if(list.get(i).getClass().equals(IntegerNode.class)) {
					IntegerNode inti=(IntegerNode) list.get(i);
					from=inti.getInt();
				}
			}

			char cArray[] = new char[use.length()];
			cArray=use.toCharArray();
			char cArray2[] = new char[from];
			for(int j=0;j<from;j++) {
				cArray2[j]=cArray[j];
			}
			String str = new String(cArray2);
			return  new StringNode(str);
		}
		//returns n letters from right
		else if(check.equals("RIGHT$")) {
			ArrayList<Node>list=fnode.getArgs();
			String use = null;
			int from = 0;
			for(int i=0;i<list.size();i++) {
				if(list.get(i).getClass().equals(StringNode.class)) {
					StringNode str=(StringNode) list.get(i);
					use=str.getWord();
				}
				else if(list.get(i).getClass().equals(IntegerNode.class)) {
					IntegerNode inti=(IntegerNode) list.get(i);
					from=inti.getInt();
				}
			}

			char cArray[] = new char[use.length()];
			cArray=use.toCharArray();
			char cArray2[] = new char[from];
			for(int j=from;j==0;j--){
				cArray2[j]=cArray[j];
			}
			String str = new String(cArray2);
			str=use.substring(use.length()-from);
			return new StringNode(str);
		}
		//returns middle of two integers
		else if(check.equals("MID$")) {
			ArrayList<Node>list=fnode.getArgs();
			String use = "";
			int first=0;
			int second=0;
			int diff=0;
			for(int i=0;i<list.size();i++) {
				if(list.get(i).getClass().equals(StringNode.class)) {
					StringNode str=(StringNode) list.get(i);
					use=str.getWord();
				}
				else if(list.get(i).getClass().equals(IntegerNode.class)&&diff==0) {
					diff++;
					IntegerNode inti=(IntegerNode) list.get(i);
					first=inti.getInt();
				}
				else if(list.get(i).getClass().equals(IntegerNode.class)&&diff==1) {
					IntegerNode inti=(IntegerNode) list.get(i);
					second=inti.getInt();
				}
			}

			String strin = new String();
			strin=use.substring(first, second);
			return new StringNode(strin);
		}
		//converts string to float or int
		else if(check.equals("NUM$")) {
			ArrayList<Node>list=fnode.getArgs();
			for(int i=0;i<list.size();i++) {
				if(list.get(i).getClass().equals(IntegerNode.class)) {
					IntegerNode inti= (IntegerNode) list.get(i);
					return new StringNode(String.valueOf(inti.getInt()));
				}
				if(list.get(i).getClass().equals(FloatNode.class)) {
					FloatNode floa= (FloatNode) list.get(i);
					return new StringNode(String.valueOf(floa.getFloat()));
				}
			}

		}
		else if(check.equals("VAL")) {
			ArrayList<Node>list=fnode.getArgs();
			StringNode inti =(StringNode) list.get(0);
			int val = 0;
			try {
				val = Integer.parseInt(inti.getWord());
			}
			catch (NumberFormatException e) {

			}
			return new IntegerNode(String.valueOf(val));
		}
		else if(check.equals("VAL%")) {
			ArrayList<Node>list=fnode.getArgs();
			StringNode floa =(StringNode) list.get(0);
			float val = 0;
			try {
				val = Float.parseFloat(floa.getWord());
			}
			catch (NumberFormatException e) {

			}
			return new FloatNode(String.valueOf(val));
		}
		return fnode;
	}

	private void accept() {
		for(int i=0;i<compareNode.size();i++) {
			if(compareNode.get(i).getClass().equals(DataNode.class)) {
				datar(compareNode.get(i),i);
			}
		}
		//loop to set special case Nodes Orders and References
		for(int i=0;i<compareNode.size();i++) {
			if(compareNode.get(i).getClass().equals((LabeledStatementNode.class))) {
				labeler(compareNode.get(i),i);
			}
			else if(compareNode.get(i).getClass().equals(ForNode.class)) {
				forer(compareNode.get(i),i);
			}
		}
		//loop to set default next excludes the special case goTo and goSub
		for(int i=0;i<compareNode.size()-1;i++) {
			if(compareNode.get(i).getClass().equals((GoToNode.class))||compareNode.get(i).getClass().equals(GosubNode.class)) {
			}
			else {
				StatementNode ok=(StatementNode)compareNode.get(i);
				ok.setNextStatement((StatementNode)compareNode.get(i+1));
				compareNode.set(i, ok);
			}

		}
		for(int i=0;i<compareNode.size();i++) {
			if(compareNode.get(i).getClass().equals(AssignmentNode.class)) {
				assigner(compareNode.get(i),i);
			}
		}
	}

	private void assigner(Node node, int j) {
		AssignmentNode gogo=(AssignmentNode)node;
		String same=(gogo.getVariableNode().toString());
		for(int i=0;i<compareNode.size();i++) {
			if(compareNode.get(i).getClass().equals((PrintNode.class))) {
				PrintNode prnt=(PrintNode) compareNode.get(i);
				String same2;
				if(prnt.getNodelist().get(0).toString().equals(same)) {
					gogo.setNextStatement(compareNode.get(i));
				}
			}
		}
		compareNode.set(j, gogo);
	}


	//handles data adds it to different array list and removes it
	private void datar(Node node, int i) {
		DataNode data = (DataNode)node;
		ArrayList<Node> datalist = data.getList();
		for(int j=0;j<datalist.size();j++) {
			Data.add(datalist.get(j));
		}
		compareNode.remove(i);
	}

	//handles finding label to go to
	private void goTo(Node node, int j) {
		StatementNode gogo=(StatementNode) node;
		String same=((GoToNode) node).getVariable();
		for(int i=0;i<compareNode.size();i++) {
			if(compareNode.get(i).getClass().equals((LabeledStatementNode.class))) {
				LabeledStatementNode lbSN=(LabeledStatementNode) compareNode.get(i);
				if(lbSN.getLabel().equals(same)) {
					gogo.setNextStatement(compareNode.get(i));
				}
			}
		}
		compareNode.set(j, gogo);
	}

	//handles finding label for goSub
	private void goSub(Node node, int j) {
		StatementNode gogo=(StatementNode) node;
		String same=((GosubNode) node).getVariable();
		for(int i=0;i<compareNode.size();i++) {
			if(compareNode.get(i).getClass().equals((LabeledStatementNode.class))) {
				LabeledStatementNode lbSN=(LabeledStatementNode) compareNode.get(i);
				if(lbSN.getLabel().equals(same)) {
					gogo.setNextStatement(compareNode.get(i));
				}
			}
		}
		compareNode.set(j, gogo);
	}

	//handles for finds its next makes them have references between each other
	private void forer(Node node, int j) {
		ForNode forNode= (ForNode)node;
		String same = forNode.getVariable();
		hashInt.put(forNode.getVariable(),forNode.getVarValue());
		for(int i=0;i<compareNode.size();i++) {
			if(compareNode.get(i).getClass().equals((NextNode.class))) {
				NextNode nextNode=(NextNode)compareNode.get(i);
				if(nextNode.getNext().equals(same)) {
					forNode.setNext(nextNode);
					nextNode.setFor(forNode);
					forNode.setAfter(compareNode.get(i+1));
					nextNode.setAfter(compareNode.get(i+1));
					compareNode.set(i, nextNode);
					compareNode.set(j, forNode);
				}
			}
		}
	}

	//handles labels calls their GoSub and GoTo node before removing label and replacing it with child node and inserting it into hash map
	private void labeler(Node node, int j) {
		LabeledStatementNode labelNode= (LabeledStatementNode)node;
		hashlabel.put(labelNode.getLabel(), labelNode.getStatement());
		for(int i=0;i<compareNode.size();i++) {
			if(compareNode.get(i).getClass().equals((GoToNode.class))) {
				goTo(compareNode.get(i),i);
			}
			else if(compareNode.get(i).getClass().equals(GosubNode.class)) {
				goSub(compareNode.get(i),i);
			}
		}
	}

	//Evaluate integers
	int EvaluateIntMathOp(Node node) {
		String symbol;
		int answer=0;
		
		
		if(node.getClass().equals(IntegerNode.class)) {
			IntegerNode inti=(IntegerNode) node;
			return inti.getInt();
		}
		if(node.getClass().equals(VariableNode.class)) {
			VariableNode vnode=(VariableNode) node;
			return hashInt.get(vnode.getVariable());
		}
		else if(node.getClass().equals(MathOpNode.class)) {
			MathOpNode math = (MathOpNode) node;
			symbol=math.getSign();
			int inti1= EvaluateIntMathOp(math.getLeft());
			int inti2= EvaluateIntMathOp(math.getRight());
			if(symbol.equals("+")) {
				answer=inti1+inti2;
			}
			else if(symbol.equals("-")) {
				answer=inti1-inti2;
			}
			else if(symbol.equals("*")) {
				answer=inti1*inti2;
			}
			else if(symbol.equals("/")) {
				answer=inti1/inti2;
			}
			return answer;
		}
		return answer;
	}

	//Evaluate Floats
	float EvaluateFloatMathOp(Node node) {
		String symbol;
		float answer=0;
		if(node.getClass().equals(FloatNode.class)) {
			FloatNode inti=(FloatNode) node;
			return inti.getFloat();
		}
		else if(node.getClass().equals(MathOpNode.class)) {
			MathOpNode math = (MathOpNode) node;
			symbol=math.getSign();
			float inti1= EvaluateIntMathOp(math.getLeft());
			float inti2= EvaluateIntMathOp(math.getRight());
			if(symbol.equals("+")) {
				answer=inti1+inti2;
			}
			else if(symbol.equals("-")) {
				answer=inti1-inti2;
			}
			else if(symbol.equals("*")) {
				answer=inti1*inti2;
			}
			else if(symbol.equals("/")) {
				answer=inti1/inti2;
			}
			return answer;


		}
		return answer;
	}

	//Evaluates Boolean Expression
	boolean EvaluateBooleanExpression(ArrayList<Node> list) {
		float one=0,two=0;
		int one1 = 0, two2=0;
		String bowlin = "";
		if(list.get(0).getClass().equals(VariableNode.class)) {
			VariableNode vnode=(VariableNode) list.get(0);
			if(vnode.getVariable().contains("%")) {;
			one=hashFloat.get(vnode.getVariable());
			}
			else {
				one1=hashInt.get(vnode.getVariable());
			}
		}
		if(list.get(2).getClass().equals(VariableNode.class)) {
			VariableNode vnode=(VariableNode) list.get(2);
			if(vnode.getVariable().contains("%")) {;
			two=hashFloat.get(vnode.getVariable());
			}
			else {
				two2=hashInt.get(vnode.getVariable());
			}
		}
		if(list.get(0).getClass().equals(FloatNode.class)) {
			FloatNode fltnode=(FloatNode) list.get(0);
			one=fltnode.getFloat();
		}
		if(list.get(2).getClass().equals(FloatNode.class)) {
			FloatNode fltnode=(FloatNode) list.get(2);
			two=fltnode.getFloat();
		}
		if(list.get(0).getClass().equals(IntegerNode.class)) {
			IntegerNode intinode=(IntegerNode) list.get(0);
			one1=intinode.getInt();
		}
		if(list.get(2).getClass().equals(IntegerNode.class)) {
			IntegerNode intinode=(IntegerNode) list.get(2);
			two2=intinode.getInt();
		}
		if(list.get(1).getClass().equals(BooleanOperationNode.class)) {
			BooleanOperationNode bowl=(BooleanOperationNode)list.get(1);
			bowlin=bowl.getOperation();
		}
		if(bowlin.equals("=")) {
			if(one==two||one1==two2){
				return true;
			}
		}
		if(bowlin.equals("<")) {
			if(one<two||one1<two2){
				return true;
			}
		}
		if(bowlin.equals("<=")) {
			if(one<=two||one1<=two2){
				return true;
			}
		}
		if(bowlin.equals(">")) {
			if(one>two||one1>two2){
				return true;
			}
		}
		if(bowlin.equals(">=")) {
			if(one>=two||one1>=two2){
				return true;
			}
		}
		if(bowlin.equals("<>")) {
			if(one!=two||one1!=two2){
				return true;
			}
		}

		return false;
	}
}
