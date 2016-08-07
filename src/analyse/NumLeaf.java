package analyse;

import basic.Token;
import node.ASTLeaf;
import node.Environment;

public class NumLeaf extends ASTLeaf {

	public NumLeaf(Token t) {
		super(t);
	}

	@Override
	public Object eval(Environment env) {
		return token.getNumber();
	}
	
      
}
