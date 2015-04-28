class StreamNumber {
    Node first;
    Node last;
    
    public static void main(String[] args){
        //挿入するデータを作成
        StreamNumber smn = new StreamNumber();
        int[] numbers = new int[15];
        Random rnd = new Random();
        for(int i = 0; i < numbers.length; i++){
            numbers[i] = rnd.nextInt(50);
        }
        for(int i = 0; i < numbers.length; i++){
            smp.track(numbers[i]);
        }
        //データを出力
        System.out.println("StreamNumber:" + smp);
        System.out.println("ans:" + smp.getRankOfNumber(27));
    }
    
    /**
     * ｘを追加するメソッド
     * @param x 追加する数値
     * @return  
     */
    public void track(int x){
        Node now = new Node(x);
        //firstがnull(=初期状態)のとき
        if(first == null){
            this.first = now;
            this.last = now;
        } else {
           Node n = this.first;
           //追加しようとする数値が最小のとき
           if(n.moreThan(x)){
               now.next = this.first;
               this.first = now;
           }else{
               //追加する場所まで移動
               while(n.next != null && now.moreThan(n.next.data)) n = n.next;
               //nがlastのとき、フィールドのlastを更新
               if(n.next == null){
                   this.last.next = now;
                   this.last = now;
               }else{
                   now.next = n.next;
                   n.next = now;
               }
           }
        }
        
    }
    
    /**
     * xより小さい値の数を見つけ出すメソッド
     * @param x 見つけ出す要素
     * @return  ｘより小さい数の総数
     */
    public int getRankOfNumber(int x){
        int ans = 0;
        Node n = this.first;
        while(n != null && n.lessThan(x)){
            n = n.next;
            ans++;
        }
        return ans;
    }
    
    @Override
    public String toString(){
        Node n = this.first;
        StringBuffer ans = new StringBuffer(50);
        while(n != null){
            ans.append(n.data + " ");
            n = n.next;
        }
        return ans.toString();
    }
}