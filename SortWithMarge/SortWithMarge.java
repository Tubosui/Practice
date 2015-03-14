public class SortWithMarge {
    int[] a;
    
    public SortWithMarge(int[] a){
        this.a = a;
    }
    
    /**
     * aとbをソートしながらマージするメソッド
     * @param b　ソートされた配列
     */
    public void marge(int[] b){
        int ar = 0; //this.aのランナー
        int br = 0; //bのランナー
        boolean flag = true;
        
        while(flag){
            //a[ar]とb[br]を比べ、a[ar]がb[br]より大きくなるまでarを加算
            while(a[ar] <= b[br]) ar++;
            
            //最初のbrの値(=開始位置)を保持し,a[ar]がb[br]より小さくなるまでbrを加算
            int bf = br;
            while(br < b.length && a[ar] > b[br]) br++;
            
            //aの要素を、bから挿入する分だけ、右にシフトさせ(=挿入できるスペースを作る)、bから挿入
            shift(ar, br-bf);
            for(int i = bf; i<br; i++){
                if(i >= b.length) break;
                a[ar] = b[i];
                ar++;
            }
            if(br >= b.length)flag = false;//break;
        }
    }
    
    /**
     * this.aの要素を、beginの位置からcount分だけ右にシフトさせる
     * int[] a ={1,2,3,4,5,6,7,8,9};
     * swap(2, 3); => 1 2 [3 4 5] 3 4 5 6
     * [ ]の部分をスペースとして使う
     * @param begin　開始位置
     * @param count　シフトする数
     */
    public void shift(int begin, int count){
        if(begin >= a.length) return;
        for(int i = a.length - count - 1; i >= begin; i--){
            a[i + count] = a[i];  
        }
    }
}