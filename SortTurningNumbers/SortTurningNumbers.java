import java.util.Random;


public class SortTurningNumbers {
    public static void main(String[] args){
        int[] numbers = new int[100];
        Random rnd = new Random();
        int key = rnd.nextInt(100);
        int bias = 100 - key;
        for(int i = 0; i < numbers.length; i++){
           numbers[i] = i < key ?  i + bias : i - key ;
        }
        int ans = getNode(27, numbers);
        System.out.println(ans);
    }
    
    
    public static int getNode(int node, int[] numbers){
        if(numbers[0] == node) return 0;
        if(numbers[numbers.length-1] == node) return 0;
        return search(numbers.length - 1, 0, node, numbers);
    }
    
    public static int search(int max, int min, int node, int[] numbers){
        int mid = (max + min) / 2;
        if(numbers[mid] == node) return mid;
        if(max == min) return -1;
        
        if(numbers[mid] > numbers[max]){
            if(numbers[mid] > node && numbers[min] <= node) return search(mid - 1, min, node, numbers);
            else return search(max, mid + 1, node, numbers);
        } else if(numbers[mid] < numbers[min]) {
            if(numbers[mid] < node && numbers[max] >= node) return search(max, mid + 1, node, numbers);
            else return search(mid - 1, min, node, numbers);
        } else {
            if(numbers[mid] < node) return search(max, mid + 1, node, numbers);
            else return search(mid - 1, min, node, numbers);
        }
    }
}
