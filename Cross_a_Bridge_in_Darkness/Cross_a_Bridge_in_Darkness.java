
import java.util.LinkedList;
import java.util.Queue;
import Lib.ByteBit;

//渡らなければいけない人数が8人以下の場合のみ動く(フラグとしてbyteを使っているため)。
public class Cross_a_Bridge_in_Darkness {
    String result = "" ;
    public static void main(String[] args){
        Cross_a_Bridge_in_Darkness cbd = new Cross_a_Bridge_in_Darkness();
        int[] menber = {1, 2, 5, 10};
        System.out.println("time:" + cbd.getTime(menber));
        System.out.println(cbd.result);
    }
    
    /**
     * 最速タイムを求めるメソッド
     * @param times それぞれの人が橋を渡りきるのに要する時間を保持する配列
     * @return　最速タイム
     */
    public int getTime(int[] times){
        int ans = Integer.MAX_VALUE;
        int number = times.length;
        Queue<Byte> pattern = new LinkedList<Byte>();           //誰が右にいて誰が左にいるか(1:右　0:左)
        Queue<Boolean> direction = new LinkedList<Boolean>();   //右に向かう(=true)か左に戻るか(=false)
        Queue<Integer> time = new LinkedList<Integer>();        //タイム
        Queue<String> result = new LinkedList<String>();        //移動の行程を保持する文字列
        pattern.add((byte)0);
        direction.add(true);
        time.add(0);
        result.add("process:\n");
        
        //幅探索
        while(!pattern.isEmpty()){
            byte isRight = pattern.poll();
            boolean toRight = direction.poll();
            int nowTime = time.poll();
            String process = result.poll();
            
            //全員右(=isRightの右number桁が1)の時、記録を比較・更新
            if(isRight == (byte) ((1 << number) - 1)){
                if(ans > nowTime){
                    this.result = process;
                    ans = nowTime;
                }
                continue;
            }
            
            //右に向かう時
            if(toRight){
                for(int i = 0; i < number; i++){
                    for(int j = i+1; j < number; j++){
                        //移動させようとしている人(i,j)が左にいるかどうか
                        boolean flag = !ByteBit.get(isRight, i) && !ByteBit.get(isRight, j);
                        if(flag){
                            int p = ByteBit.reverse(ByteBit.reverse(isRight, i), j);//i,jを移動させる
                            int nt = nowTime + Math.max(times[i], times[j]);
                            pattern.add((byte)p);
                            direction.add(!toRight);
                            time.add(nt);
                            result.add(process + "toRight:" + i + " " + j + " (" +  nt + "m)"+"\n");
                        }
                    }
                }
            }else{
                //左に戻るとき
                int minTime = Integer.MAX_VALUE;
                int index = -1;
                //懐中電灯をもっていく人物はすでに右にいる人で、一番タイムが早い人(貪欲・・？)
                for(int i = 0; i < number; i++){
                    if(ByteBit.get(isRight, i) && minTime > times[i]){
                        minTime = times[i];
                        index = i;
                    }
                }
                int p = ByteBit.reverse(isRight, index);//移動させる
                int nt = nowTime + times[index];
                pattern.add((byte)p);
                direction.add(!toRight);
                time.add(nt);
                result.add(process + "toLeft: " + index + "   (" + nt + "m)" + "\n");
            }
        }
        return ans;
    }
    
}
