public class Box {
    /**
     * 積み上げられる箱の最大個数を求める(動的計画法)
     * @param w　長さ
     * @param h　高さ
     * @param d　奥行き
     * @return 　積み上げられる箱の最大個数
     */
    public static int getMax (int[] w, int[] h, int[] d){
        //num[i] = i番目の箱を使って積み上げることのできる箱の最大値
        int[] num = new int[w.length];
        num[0] = 1;
        
        //インデックスをソートする(詳細は下)
        int[] sbw = new int[w.length];
        for(int i = 0; i<sbw.length; i++) sbw[i] = i;
        sbw = sort(w, sbw);
        
        //i番目の箱とそれよりw(長さ)が小さい箱を使って積み上げる事ができる個数の最大値を求めていく。
        for(int i = 0; i<w.length; i++){
            for(int j = 0; j<i; j++){
                //長さ、高さ、奥行き全て大きくないと積み上げる事が出来ない
                boolean flag = w[sbw[i]] > w[sbw[j]] && h[sbw[i]] > h[sbw[j]] && d[sbw[i]] > d[sbw[j]];
                if (flag) num[i] = Math.max(num[i], num[j] + 1);
            }
            //num[i] = 0 の時(つまりどの箱も積み上げれなかった時)、その箱自身で1つ
            num[i] = Math.max(num[i], 1);
        }
        //最大値を求めて返す。
        int ans = 0;
        for(int i = 0; i < num.length; i++){
            ans = Math.max(ans, num[i]);
        }
        return ans;
    }
    
    /**
     * 配列aをもとに、bをソートする(インデックスをソートする)
     * @param a　元の配列
     * @param b　b[i] = i　の配列
     * @return　 aを昇順にソートした配列に対応するインデックスを格納した配列
     * 
     * 例：int[] a = {0, 6, 7, 1, 5};
     *    int[] b = {0, 1, 2, 3, 4};
     *    
     *    return  : {0, 3, 4, 1, 2};
     *    →返された配列の順番にaにアクセスすれば小さいものから順にアクセスすることになる！
     *    ★なぜわざわざそうするのか？
     *    この問題はd,h,wとそれぞれ独立しているため、wでソートするとそれに対応させるためh,gも並べ替えなければいけない。
     *    →インデックスをソートすることで並べ替えるのは一つで済む！
     */
    public static int[] sort (int[] a, int[] b){
        int[] b2 = new int[a.length];
        boolean[] map = new boolean[a.length];
        int c = 0;
        boolean flag = true;
        while (flag){
            int min = Integer.MAX_VALUE;
            int index = -1;
            for (int i = 0; i<a.length; i++){
                if (!map[i] && a[i] < min){
                    min = a[i];
                    index = i;
                }
            }
            if (index < 0){
                flag = false;
            } else {
                b2[c++] = b[index];
                map[index] = true;
            }
        }
        return b2;
    }
}