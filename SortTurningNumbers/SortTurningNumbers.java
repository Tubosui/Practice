
import java.util.Random;


public class SortTurningNumbers {
    public static void main(String[] args){
        //回転する配列を作る
        int[] numbers = new int[100];
        Random rnd = new Random();
        int key = rnd.nextInt(100);
        int bias = 100 - key;
        for(int i = 0; i < numbers.length; i++){
           numbers[i] = i < key ?  i + bias : i - key ;
        }
        
        //配列をgetNodeメソッドに渡して解を求める
        int ans = getNode(27, numbers);
        System.out.println(ans + ":" + numbers[ans]);
    }
    
    /**
     * 回転している配列から特定の値を探し出すメソッド
     * @param node      探す値
     * @param numbers   回転している配列
     * @return          見つけた値のIndex
     */
    public static int getNode(int node, int[] numbers){
        if(numbers[0] == node) return 0;
        if(numbers[numbers.length-1] == node) return 0;
        return search(numbers.length - 1, 0, node, numbers);
    }
    
    /**
     * 2分探索で配列を探索するメソッド
     * @param max   範囲の最大値
     * @param min   範囲の最小値
     * @param node  探す値 
     * @param numbers　回転している配列
     * @return      見つけた値のIndex
     */
    public static int search(int max, int min, int node, int[] numbers){
        //範囲の中央の値を求める
        int mid = (max + min) / 2;
        
        //もし探している値と一致したら返す
        if(numbers[mid] == node) return mid;
        
        //範囲が1、つまり1つの値になっても見つからない場合は-1をかえす
        if(max == min) return -1;
        
        //探している値がどこの範囲にあるか調べて次の段階へ
        if(numbers[mid] > numbers[max]){ 
            /* mid < x <= max の範囲内で回転してた場合 */
            //回転してない範囲に値がある場合と、回転している範囲に値がある場合とで処理を分ける
            if(numbers[mid] > node && numbers[min] <= node) return search(mid - 1, min, node, numbers);
            else return search(max, mid + 1, node, numbers);
            
        } else if(numbers[mid] < numbers[min]) { 
            /* min <= x < mid　の範囲内で回転してた時 */
            //回転してない範囲に値がある場合と、回転している範囲に値がある場合とで処理を分ける
            if(numbers[mid] < node && numbers[max] >= node) return search(max, mid + 1, node, numbers);
            else return search(mid - 1, min, node, numbers);
        
        } else { 
            /* 探索する範囲が回転していない場合*/
            if(numbers[mid] < node) return search(max, mid + 1, node, numbers);
            else return search(mid - 1, min, node, numbers);
        }
    }
}
