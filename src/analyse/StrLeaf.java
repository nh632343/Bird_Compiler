package analyse;

import basic.Token;
import node.ASTLeaf;
import node.Environment;

public class StrLeaf extends ASTLeaf{

	public StrLeaf(Token t) {
		super(t);
	}

	@Override
	public Object eval(Environment env) {
		return token.getText();
	}

}
