public class Num {
    private String process_L;   //左辺のプロセスを保持する
    private String process_R;   //右辺のプロセスを保持する
    private float left;     //左辺の計算結果を保持する
    private float right;    //右辺の計算結果を保持する
    private int runner;     //アクセスする要素のインデックスのインデックスを保持(使う順番を保持しているindex[]の何番目まで使ったか)
    static int[] index;     //アクセスする要素のインデックスを保持(使う順番を保持)
    static int[] num;       //要素を保持
    
    /**
     * 通常の初期化
     * @param nums  四則演算に使う数を保持した配列
     * @param ind   numsにアクセスする順番を保持した配列
     */
    Num (int[] nums, int[] ind){
        this.left = nums[ind[0]];
        this.right = nums[ind[1]];
        this.runner = 2;
        this.process_L = nums[ind[0]] + "";
        this.process_R = nums[ind[1]] + "";
        index = ind;
        num = nums;
    }
    
    /**
     * Nums自身がが次の段階の要素を作り出すためのコンストラクタ
     * @param l     左辺の計算結果
     * @param r     右辺の計算結果
     * @param runner　現段階で(このオブジェクトで)使う要素のインデックス
     * @param pl    左辺のプロセス
     * @param pr    右辺のプロセス
     */
    private Num (float l, float r, int runner, String pl, String pr){
        this.process_L = pl;
        this.process_R = pr;
        this.left = l;
        this.right = r;
        this.runner = runner;
    }
    
    /**
     * 最後に左辺と右辺を計算した時に利用するコンストラクタ
     * @param result   ここまでのプロセス 
     * @param sum      計算結果
     */
    private Num (String result, float sum){
        this(sum, 0, index.length+1, result, "");
    }
    
    /**
     * 次の要素を右の追加するメソッド
     * @param c 演算の種類を示す
     * @return  次の段階のNum
     */
    Num append(Calc c){
        if(this.runner == index.length) return getEnd(c);
        float f = 0;
        int n = num[index[this.runner]];
        boolean[] flag ={true, true};
        switch(c){
            case pl:
                f = this.right + n;
                flag[0] = false;
                flag[1] = false;
                break;
            case mi:
                f = this.right - n;
                flag[0] = false;
                break;
            case mu:
                f = this.right * n;
                break;
            case de:
                f = this.right / n;
                break;
        }
        String pro = getFormat(this.process_R, flag[0], c.getMark(), Integer.toString(n), flag[1]);
        int next = this.runner+1;
        return new Num(this.left, f, next, this.process_L, pro);
    }
    
    /**
     * 左辺に右辺をマージ(演算)し、次の要素を右辺として保持する
     * @param c 演算の種類
     * @return  次の段階のNum
     */
    Num flushAppend(Calc c){
        if(this.runner == index.length ) return getEnd(c);
        float f = 0;
        boolean[] flag = {true, true};
        switch(c){
            case pl:
                f = this.left + this.right;
                flag[0] = false;
                flag[1] = false;
                break;
            case mi:
                f = this.left - this.right;
                flag[0] = false;
                break;
            case mu:
                f = this.left * this.right;
                break;
            case de:
                f = this.left / this.right;
                break;
        }
        String pro = getFormat(this.process_L, flag[0], c.getMark(), this.process_R, flag[1]);
        int N = num[index[this.runner]];
        int next = this.runner+1;
        return new Num(f, N, next, pro, N+"");
    }
    
    /**
     * 最後の処理をするメソッド(全ての要素が使い終わったときに呼び出す)
     * @param c 演算の種類
     * @return  最終結果を保持するNum
     */
    Num getEnd(Calc c){
        float n = 0;
        boolean[] flag = {true, true};
        switch(c){
        case pl:
            n = this.left + this.right;
            flag[0] = false;
            flag[1] = false;
            break;
        case mi:
            n = this.left - this.right;
            flag[0] = false;
            break;
        case mu:
            n = this.left * this.right;
            break;
        case de:
            n = this.left / this.right;
            break;
        }
        return new Num(getFormat(this.process_L, flag[0], c.getMark(), this.process_R, flag[1]), n);
    }
    
    /**
     * 演算のプロセスをマージする時に使うメソッド
     * @param left      左辺のプロセス
     * @param flag_L    左辺のプロセスを括弧でくくる必要があるか
     * @param c         演算の記号(+, -, *, /)
     * @param right     右辺のプロセス
     * @param flag_R    右辺のプロセスを括弧でくくる必要があるか
     * @return          左辺と右辺を演算記号でマージした文字列
     */
    String getFormat(String left, boolean flag_L ,char c, String right, boolean flag_R){
        String l = flag_L && left.length() != 1? '(' + left + ')' : left;
        String r = flag_R && right.length() != 1? '(' + right + ')' : right;
        return l + c + r;
    }
    
    /**
     * 終了しているかどうか
     * @return　最終段階に達しているかどうか
     */
    boolean isOver(){
        return this.runner > index.length;
    }
    
    /**
     * 演算結果を取得するメソッド
     * @return　演算結果
     */
    float getF(){
        return this.left;
    }
    /**
     * 演算のプロセスを取得するメソッド
     * @return　演算のプロセス
     */
    String getResult(){
        return this.process_L;
    }
}
