public class TorF {
    
    public static void main(String[] args){
        TorF tf = new TorF();
        System.out.println(tf.getX("1^0|0|1", false));//2
    }
     /**
      * 内部クラス　0、1のそれぞれのパターンを保持するラッパークラス
      */
     class Memo{
         int T = 0;//tになるパターン数
         int F = 0;//fになるパターン数
         int S = 0;//全てのパターン
         
         Memo(int T, int F){
             this.T = T;
             this.F = F;
             this.S = T + F;
         }
         /**
          * 渡されたMemoオブジェクトのT,Fをそれぞれ合計し、
          * それを格納した新たなMemoオブジェクトを返す
          * @param m　マージするMemoオブジェクト
          * @return　　それぞれの合計を保持したMemoオブジェクト
          */
         Memo marge(Memo m){
             return new Memo(this.T + m.T, this.F + m.F);
         }
     }
     
     /**
      * 与えられた文字列からtfになるパターン数を返す。
      * @param expression　0,1,^,|,&からなる文字列
      * @param tf　true or false　期待する演算結果
      * @return　パターン数
      */
     public int getX(String expression, boolean tf){
         Memo ans = getN(expression);
         if(tf) return ans.T;
         else return ans.F;
     }
     
     /**
      * 論理演算子で分割していき、パターン数を数え上げる再帰関数
      * @param exp　0,1,&,^,|からなる文字列
      * @return　0または1になるパターン数を保持したMemoオブジェクト
      */
     public Memo getN(String exp){
         int length = exp.length();
         //expが1文字の時
         if(length == 1){
             if(exp.equals("1")) return new Memo(1, 0);
             else return new Memo(0, 1);
         }
         //expが3文字以上の時
         /**
          * iは論理演算子の文字列内のindexを示す
          * 論理演算子と0,1は交互に並んでいる(11&0、または&^1||1ということはない)
          * =>論理演算子のindexは1、3、5、7・・・であるためそのインデックスで文字列を分解する
          * (=カッコを入れる)ようにしていく
          */
         Memo ans = new Memo(0, 0);
         for(int i = 1; i < exp.length(); i+=2){
             Memo left = getN(exp.substring(0, i));
             Memo right = getN(exp.substring(i+1, length));
             Memo subans = check(left, exp.charAt(i), right);
             ans = ans.marge(subans);
         }
         return ans;
     }
     
     /**
      * 論理演算を擬似的に行い、それぞれのパターン数を求める　(left 演算子 right)という感じ
      * @param left     左項
      * @param c        論理演算子
      * @param right    右項
      * @return         1,0のそれぞれのパターン数を格納したMemoオブジェクト
      */
     public Memo check(Memo left, char c, Memo right){
         switch(c){
         case '&' : 
             int T1 = left.T * right.T;
             int F1 = (left.S * right.S) - T1;
             return new Memo(T1,F1);
             
         case '^' :
             int T2 = (left.T * right.S) + (right.T * left.S) - 2 * (left.T * right.T);
             int F2 = (left.S * right.S) - T2;
             return new Memo(T2, F2);
         
         case '|' :
             int F3 = left.F * right.F;
             int T3 = (left.S * right.S) - F3;
             return new Memo(T3, F3);
         
         default:
             return new Memo(0, 0);
         }
     }
    
}
