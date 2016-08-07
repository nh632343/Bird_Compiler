package analyse_function;

import analyse.BlockList;
import node.Environment;

public class Function {
    protected ParamList paramList;
    protected BlockList blockList;
    protected Environment environment;
	
    public Function(ParamList paramList, BlockList blockList, Environment environment) {
		this.paramList = paramList;
		this.blockList = blockList;
		this.environment = environment;
	}

	public ParamList getParamList() {
		return paramList;
	}

	public BlockList getBlockList() {
		return blockList;
	}
    
    public Environment makeEnv(){
    	return new Environment(environment);
    }
    
}
