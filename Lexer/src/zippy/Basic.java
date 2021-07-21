import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Abhrajit Ghosh
 * This class holds main
 */

public class Basic {

	public static void main(String[] args)  {

		//Finds the number of arguments and checks 
		int count = args.length;
		if (count>1) {
			System.out.println("too many arguments");
			System.exit(0);
		}
		else if(count==0) {
			System.out.println("No file typed in argument");
			System.exit(0);
		}
		


		ArrayList<Token> myToks = new ArrayList<>();
		ArrayList<Node> myNodes = new ArrayList<Node>();
		try {
			// Uses read all String
			List<String> readAllLines = Files.readAllLines(Paths.get(args[0]));
			//for loop to read every line
			for(String line : readAllLines) {
					//initiating one instance of Lexer class
					Lexer lecer = new Lexer(line);
					
					//String to print output
					String translate = "";
					//set arraylist to lexed line
					myToks=lecer.lex(line);
					
					for(int i=0;i<myToks.size();i++) {
						//concats tokens of the Lexed line together
						translate=translate.concat(lecer.lex(line).get(i).toString());
					}
				
					Parser parcer=new Parser(myToks);
					try {
						myNodes.add(parcer.parse());
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			Interpreter inting = new Interpreter(myNodes);
			inting.Intitialize();
		}
		//checks if something went wrong reading
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error Reading");
		}
		System.out.println("DONE");
	}
}


