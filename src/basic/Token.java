package basic;

public abstract class Token {
    public static final Token EOF = new Token(-1,0){}; // end of file
    public static final String EOL = "\\n";          // end of line 
    
    public static final int EMPTY=0;
    public static final int NUMBER=1;
    public static final int STRING=2;
    public static final int ID=3;
    
    public  final int TYPE;
    private int lineNumber;

    protected Token(int line,int type) {
        lineNumber = line;
        TYPE=type;
    }
    public int getLineNumber() { return lineNumber; }
   
    public Object getNumber() { throw new StoneException("not number token"); }
    public String getText() { return ""; }
}
