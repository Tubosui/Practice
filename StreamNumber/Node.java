class Node{
    int data;
    Node next;
    
    Node(int data){
        this.data = data;
    }
    
    /**
     * this.dataがxより大きいかどうか調べる
     * @param x 比較対象の数
     * @return  x < this.data　が成り立つかどうか
     */
    public boolean moreThan(int x){
        return this.data > x;
    }
    
    /**
     * this.dataがxより小さいかどうか調べる
     * @param x 比較対象の数
     * @return  this.data < x が成り立つがどうか
     */
    public boolean lessThan(int x){
        return this.data < x;
    }
}