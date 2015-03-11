import Lib.ByteBit;

// N=8の時のクイーン問題をByteBitクラスを使って実装
public class Queen {
    public static byte[] boad ={0,0,0,0,0,0,0,0};       //クイーンを置ける場所を保持する
    public static boolean[][] Queen = new boolean[8][8];//クイーンが実際おいてある場所を保持する
    public static int c = 0;            //何通りあるか
    static int[] movex = {-1,-1,1,1};   //斜めに塗るときのｘの動き
    static int[] movey = {-1,1,-1,1};   //斜めに塗るときのｙの動き
    
    public static void main(String[] args){
        c = 0;
        putQueen(8);
        System.out.println(c + " END");
    }
    
    /**
     * クイーンを置くメソッド
     * @param n　残りのクイーンの数
     */
    public static void putQueen(int n){
        if(n == 0){
            c++;
            out();
            return;
        }
        for(int i = 0; i<Queen.length; i++){
            if(!ByteBit.get(boad[n-1], 7-i) && !Queen[n-1][i]){
                Queen[n-1][i] = true;
                paint(i, n-1);
                putQueen(n-1);
                Queen[n-1][i] = false; 
                restrate();
            }
        }
    }
    
    /**
     * クイーンが置けない場所を反映するメソッド
     * @param x　クイーンを置いた場所のｘ座標
     * @param y　クイーンを置いた場所のｙ座標
     */
    public static void paint(int x, int y){
        row(y);
        column(x);
        diagonal(x,y);
    }
    
    /**
     * 横一列　bitを1にする＝Queenが置けない状態にする
     * @param i　横一列を示すｙ座標
     */
    public static void row(int i){
        boad[i] = (byte) ~0;
    }
    
    /**
     * 縦一列bitを1にする＝Queenが置けない状態にする
     * @param i
     */
    public static void column(int i){
        for(int j = 0; j<boad.length; j++){
            boad[j] = ByteBit.set(boad[j], 1, 7-i);
        }
    }
    
    /**
     * 両斜め（45度）のbitを1にする＝Queenが置けない状態にする
     * @param x　Queenを置いたx座標
     * @param y　Queenを置いたｙ座標
     */
    public static void diagonal(int x, int y){
        for(int i = 0; i<4; i++){
            diagonal(7-x, y, i);
        }
    }
    
    /**
     * 両斜め（45度）のbitを1にする＝Queenが置けない状態にする
     * @param x　7からQueenを置いたｘ座標を引いた数(bit操作では「0」が右端であるため)
     * @param y　Queenを置いたｙ座表
     * @param i　movex,moveyのどの動きをするか決めるindex
     */
    public static void diagonal(int x, int y, int i){
        if(x < 0 || x > 7) return;
        if(y < 0 || y > 7) return;
        boad[y] = ByteBit.set(boad[y], 1, x);
        diagonal(x + movex[i], y + movey[i], i);
    }
    
    /**
     * Queenが置かれている状態から置けない場所を作り直す
     */
    public static void restrate(){
        byte[] b = {0,0,0,0,0,0,0,0};
        boad = b;
        for(int i = 0; i<Queen.length; i++){
            for(int j = 0; j < Queen.length; j++){
                if(Queen[i][j]) paint(j, i);
            }
        }
    }
    /**
     * 結果を出力するメソッド
     */
    public static void out(){
        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){
                if(Queen[i][j])System.out.print("Q");
                else System.out.print("x");
            }
            System.out.println();
        }
        System.out.println("\n******************");
        
    }
}
