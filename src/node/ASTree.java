package node;
import java.util.Iterator;

import basic.StoneException;
import basic.Token;

public abstract class ASTree implements Iterable<ASTree> {
    public abstract ASTree child(int i);
    public abstract int numChildren();
    public abstract Iterator<ASTree> children();
    public abstract String location();
    public Iterator<ASTree> iterator() { return children(); }
    public abstract Object eval(Environment env);
    public void display(int space) {
		System.out.print(this.getClass().getName()+" ");
		if(this instanceof ASTLeaf){
			Token token=((ASTLeaf)this).token();
			System.out.println(token.getText());
		}
		else{
			System.out.println("numC: "+numChildren());
			for(int i=0;i<numChildren();++i){
				for(int j=0;j<space;++j){
					System.out.print(" ");
				}
				System.out.print("child "+i+": ");
				child(i).display(space+9);
			}
		}
	}
    
    public Object fuzhi(Environment environment,ASTree asTree) {
		throw new StoneException("must be override");
	}
}
