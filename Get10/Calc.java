/**
 * 演算の種類を定義するenum
 * @author kazuma
 */
public enum Calc {
    pu('+'), mi('-'), mu('×'), de('÷');
    private char mark;
    private Calc(char c){
        this.mark = c;
    }
    public char getMark(){
        return this.mark;
    }

}
