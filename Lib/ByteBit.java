package Lib;
/**
*byteをフラグとして使うときに利用するメソッドを定義
*
*/
public class ByteBit {

    /**
     * nビット目を0にする
     * @param b　対象のbyte
     * @param n　index
     * @return　　nビット目が0になった配列を返す
     */
    public static byte clear(byte b, int n){
        if(n < 0 || n > 7) return b;
        byte mask = (byte) ~(1 << n);
        return (byte) (b & mask);
    }
    
    /**
     * byteの　begin から　end　まで　を0にする
     * @param b     対象のbyte 
     * @param begin beginIndex
     * @param end   endIndex
     * @return      begin~endまで0にしたbyte
     */
    public static byte clear(byte b, int begin, int end){
        if(begin > end){
            int bf = end;
            end = begin;
            begin = bf;
        }
        byte mask = (byte) ~((1 << end - begin + 1) - 1 << begin);
        return (byte)(b & mask);
    }
    
    /**
     * nビットを取得する
     * @param b　対象のbyte
     * @param n　index
     * @return 　nビット目が1ならtrue,0ならfalse
     */
    public static boolean get(byte b, int n){
        byte mask = (byte)(1 << n);
        return (mask & b) != 0;
    }
    
    /**
     * 立っているフラグの数を取得する
     * @param b　対象のbyte
     * @return 立っているフラグの数
     */
    public static int get(byte b){
        int ans = 0;
        for(int i = 0; i<8; i++){
            if(get(b, i)) ans++;
        }
        return ans;
    }
    /**
     * bのbegin~endまでを取得する
     * @param b     対象のbyte 
     * @param begin beginIndex
     * @param end   endIndex
     * @return      begin~endまでを取得し、右に詰めたbyte
     */
    public static byte get(byte b, int begin, int end){
        if(begin > end){
            int bf = end;
            end = begin;
            begin = bf;
        }
        byte ans = (byte)(b >> begin);
        byte mask = (byte)((1 << end - begin +1) - 1);
        return (byte)(ans & mask);
    }
    
    /**
     * nビット目に0か1をセットする
     * @param b  対象のbyte
     * @param tf セットするbit(1 or 0)
     * @param n　index
     * @return  nビット目にtfをセットしたbyte
     */
    public static byte set(byte b, int tf, int n){
        byte ans = clear(b, n);
        byte mask = (byte)(tf << n);
        return (byte)(mask | ans);
    }
    
    /**
     * beginからendビット目まで反転するメソッド
     * @param b　　　対象のbyte
     * @param begin beginIndex
     * @param end   endIndex
     * @return      beginからendビット目まで反転したbyte
     */
    public static byte reverse(byte b, int begin, int end){
        if(begin > end){
            int bf = end;
            end = begin;
            begin = bf;
        }
        byte mask = (byte) ((1 << (end - begin + 1))- 1 << begin);
        return (byte) (b ^ mask);
        
    }
    /**
     * nビット目を反転させる
     * @param b 対象のbyte
     * @param n index
     * @return  nビット目を反転させたbyte
     */
    public static byte reverse(byte b, int n){
        byte mask = (byte)(1 << n);
        return (byte)(b ^ mask);
    }
    
    /**
     * byte配列を出力させる
     * @param b チェックするbyte配列
     */
    public static void test(byte b){
        for(int i = 7; i>=0; i--){
            if(get(b, i)) System.out.print(1);
            else System.out.print(0);
        }
        System.out.println();
    }
}
