import java.util.Random;

class HumanPyramid {
    
    public static void main(String[] args){
        //サーカスの団員の身長と体重を作る
        int[] w = new int[15];
        int[] h = new int[15];
        Random rnd = new Random();
        System.out.println("\t(体重,身長)");
        for(int i = 0; i < w.length; i++){
            w[i] = rnd.nextInt(80) + 100;
            h[i] = w[i] / 10 + (rnd.nextInt(2) + 2) * 20;
            System.out.print("団員" + (i+1) + " =\t(" + w[i] + ", " + h[i] + ")\n");
        }
        //求める
        System.out.println(createTower(w, h));
    }
    
    /**
     * 身長と体重を受け取り、身長・体重ともに小さくないとその人の上に登れないという制約の元での
     * 人間ピラミッドの最大の構成員を返す
     * @param wt    団員の体重
     * @param ht    団員の身長
     * @return      ピラミッドを構成する団員の最大数
     */
    public static Result createTower(int[] wt, int[] ht){
        /**体重の『インデックス』を昇順にソート=>wt[index[0]]=最小の体重になる、ということ**/
        //体重に昇順にアクセスするインデックスを保持
        int[] index = new int[wt.length];
        //その団員がすでにソート済みかどうか
        boolean[] flags = new boolean[wt.length];
        for(int i = 0; i < index.length; i++){
            int min = Integer.MAX_VALUE;
            int ind = -1;
            for(int j = 0; j < wt.length; j++){
                boolean flag = !flags[j] && min > wt[j];
                if(flag){
                    ind = j;
                    min = wt[j];
                }
            }
            index[i] = ind;
            flags[ind] = true;
        }
		
        /**動的計画法によりタワーの最大値を求める**/
        //index[x]の団員が一番下の場合の最大値をtower[index[i]]に格納する配列
        int[] tower = new int[wt.length];
        String[] result = new String[wt.length];
        for(int i = 0; i < tower.length; i++){
            tower[index[i]] = 1;
            result[index[i]] = index[i] + "";
            for(int j = 0; j < i; j++){
                //団員index[i]が団員index[j]の上に乗れる && 今までの結果よりそちらの方が大きい
                boolean flag = ht[index[i]] > ht[index[j]] && wt[index[i]] > wt[index[j]] 
                        && tower[index[i]] < tower[index[j]] + 1;
                if(flag){
                    tower[index[i]] = tower[index[j]] + 1;
                    result[index[i]] = result[index[j]] + "," + index[i]; 
                }
            }
        }
        //結果から最大値を求める
        int ans = -1;
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < tower.length; i++){
            if(max < tower[i]){
                ans = i;
                max = tower[i];
            }
        }
        Result rslt = new Result(tower[ans], result[ans]);
        return rslt;
    }
}
