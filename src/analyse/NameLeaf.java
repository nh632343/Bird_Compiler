package analyse;



import basic.StoneException;
import basic.Token;
import node.ASTLeaf;
import node.ASTree;
import node.Environment;

public class NameLeaf extends ASTLeaf {

	public NameLeaf(Token t) {
		super(t);
	}

	@Override
	public Object eval(Environment env) {
		Object result=env.get(token.getText());
		if(result==null){
			throw new StoneException("variation "+token.getText()+"doesn't exist");
		}
		return result;
	}
	
	public String name(){
		return token.getText();
	}

	@Override
	public Object fuzhi(Environment environment, ASTree asTree) {
		return environment.put(name(), asTree.eval(environment));
	}

}
