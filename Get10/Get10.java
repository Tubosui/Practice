import java.util.LinkedList;
import java.util.Queue;
import Lib.ByteBit;

/**
 * ある数列の各桁の数を四則演算して10にするプログラム
 * @author kazuma
 * 他でもない「全探索」をしているため効率は悪いかも
 */
public class Test {
    long count = 0; //計算量を保持
    long time = 0;  //実行時間を保持
    boolean tf = false;	//答えが見つかっているかどうか
    String result;//答え
    int[] numbers;  //計算に使う数の配列
    Calc[] m = {Calc.pu, Calc.mi, Calc.mu, Calc.de};//演算方法(+, -, *, /)
    
    /**
     * 初期化するメソッド
     * @param s 四則演算に使う数列
     * @throws NumberFormatException
     */
    public void init(String s) throws NumberFormatException, Exception{
        int length = s.length();
		//1桁では無理
        if (length == 1) throw new Exception("1桁では無理っす(^q^)！");
        //byteを使っているため8桁以内でないと正常に数列を並び替える事ができない。
        if(length > 8) throw new Exception("8桁以内の数字を入力してください(^q^)!");
        
        //文字列の数列を分解、配列に代入する。
        this.numbers = new int[length];
        int n = Integer.parseInt(s);
        int r = (int) Math.pow(10, length-1);
        int count = 0;
        while(r > 0){
            numbers[count] = (int)(n/r);
            n  -= numbers[count++] * r;
            r /= 10;
        }
        this.result = "No Answer!";
        this.tf = false;
    }
    
    /**
     * 10にする方法を求めるメソッド(これを呼び出した答えを出す)
     * @param s 四則演算に使う数列
     * @return  結果
     */
    public String getTen(String s){
        try{
            //初期化
            init(s);
            
            //インデックスを保持する数列を定義
            int[] index = new int[this.numbers.length];
            
            //実行
            long start = System.currentTimeMillis();
            getInd(index, (byte)0);
            long end = System.currentTimeMillis();
            
            //結果をまとめて返す
            this.time = end - start;
            String ans = "数列: "+ s +"\n解答: " + this.result + "\n計算量: " + count
                + "\n時間: " + this.time + "ミリ秒";
            return ans;
            
        }catch(NumberFormatException e){
            return "数字を正しく入力してください^q^!";
        }catch(Exception e){
            return e.getMessage();
        }
    }
    
    /**
     * インデックスを並べ替えてアクセスする順番を定義(=数の順番を決定)
     * 並び変えたらgetAnsを呼び、今度は挿入する演算子、括弧の挿入場所を全て探索
     * @param index アクセスする順番を保持する配列
     * @param b     アクセス済みはどの要素か保持するbyte(フラグとして利用)
     * @throws Exception
     */
    public void getInd(int[] index, byte b){
        //もう見つかっている場合は探索終了
        if(this.tf) return;
        //全てアクセス済みならgetAnsを呼び次の段階へ。
        if(b == (byte) ((1 << index.length) - 1)){
            getAns(index);
            return;
        }
        //フラグが何本立っているか調べ(=アクセス済みの要素はいくつあるか)、次のインデックスを代入し、getIndを呼び出す。
        int fn = ByteBit.get(b);
        for(int i = 0; i<index.length; i++){
            if(!ByteBit.get(b, i)){
                index[fn] = i;
                getInd(index, ByteBit.reverse(b, i));
            }
        }
    }
    
    /**
     * 演算子と括弧をいれる場所を定義、計算していき、最終的な結果が10ならフィールドのresultに結果を代入、break
     * (幅探索)
     * ここでは終了条件になるまで次の段階に進めているだけなので、詳細はNumクラスに！
     * @param index 数列にアクセスする順番を保持する数列(数の順番)
     */
    public void getAns(int[] index){
        Queue<Num> q = new LinkedList<Num>();
        Num n = new Num(this.numbers, index);
        q.add(n);
        while(!q.isEmpty()){
            count++;
            Num N = q.poll();
            if(N.isOver()){
                float ans = N.getF();
                if(ans < 10 + 10e-3 && ans > 10 - 10e-3){
                    this.result = N.getResult();
                    this.tf = true;
                    break;
                }
            }else{
                for(int i = 0; i < this.m.length; i++){
                    q.add(N.append(this.m[i]));
                    q.add(N.flushAppend(this.m[i]));
                }
            }
        }
    }
}
