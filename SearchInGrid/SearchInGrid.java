public class Sample {
    public static boolean flag = false;//要素がもう見つかった時、trueになる
    public static void main(String[] args){
        //対象の配列を作り、出力
        int[][] numbers = new int[25][7];
        for(int i = 0; i < numbers.length; i++) {
            for(int j = 0; j < numbers[i].length; j++){
                if(i == 0){
                    numbers[i][j] = j ;
                }else{
                    numbers[i][j] = numbers[i-1][j] + (j+1) * (j * (j-1) /5 ) * 3 + 2;
                }
                System.out.print(numbers[i][j] + "\t");
            }
            System.out.println();
        }
        //探す
        int[] ans = search(numbers, 213);
        
        //もし結果が配列の範囲内にある(-1ではない)時は結果を出力し、そうでない場合は"No Answer!"を出力する
        boolean ansFlag = checkRange(ans[0], 0, numbers.length - 1)
                 && checkRange(ans[1], 0, numbers[0].length - 1);
        String out = ansFlag? "(x, y) = (" + ans[1] + ", " + ans[0]+ ")" : "No Answer!"; 
        System.out.println("search 213: " + out);
    }
    
    /**
     * 二次元配列からある要素を探す
     * @param map   二次元配列
     * @param node  探す要素
     * @return      探した要素のindex(無い場合は(-1,-1))を返す
     */
    public static int[] search(int[][] map, int node){
        //見つかった時のフラグをひっくり返す
        flag = false;
        
        //答え
        int[] ans = {-1, -1};
        
        //範囲を定める
        int col_max = map.length - 1;
        int row_max = map[0].length - 1;
        int[] col_range = {0, row_max};
        int[] row_range = {0, col_max};
        
        /**
         *実際の探索:2分探索でnodeの境界を探す
         *境界を探し、探索範囲を狭める
         */
        while(!flag){
            //範囲が1かどうかのフラグ
            boolean isMinRange_row = row_range[1] == row_range[0];
            boolean isMinRange_col = col_range[1] == col_range[0];
            
            //両方の範囲が1でnodeと同じならそのIndex,そうでないなら{-1, -1}を返す
            if(isMinRange_row && isMinRange_col){
                if(map[row_range[0]][col_range[0]] == node){
                    ans[0] = row_range[0];
                    ans[1] = col_range[0];
                    flag = true;
                }
                break;
            }
            
            //最大値が最小値よりも小さくなったら(=その要素はない){-1, -1}を返す
            if(col_range[0] > col_range[1] || row_range[0] > row_range[1]) break;
            
            //横の範囲の最大値を求める
            while(!isMinRange_col && row_range[0] < map.length){
                //もし探索した列にnodeの境界がなかった時、1行右にずらす
                int border = findBorder(map[row_range[0]], node, col_range[0], col_range[1]);
                if(border == -1){
                    row_range[0]++;//右にずらす
                    continue;
                }
                col_range[1] = border;
                break;
            }
            //見つかっていたら返す
            if(flag){
                return new int[]{row_range[0], col_range[1]};
            }
            
            //横の範囲の最小値を求める
            while(!isMinRange_col && row_range[1] >= 0){
                int border = findBorder(map[row_range[1]], node, col_range[0], col_range[1]);
                //その行で境界が見つからなかったら1行左へずらす
                if(border == -1){
                    row_range[1]--;//左にずらす
                    continue;
                }
                col_range[0] = border + 1;
                break;
            }
            //見つかっていたら返す
            if(flag){
                return new int[]{row_range[1], col_range[0] - 1};
            }
            
            //縦の範囲の最大値を求める
            while(!isMinRange_row && col_range[0] < map[0].length ){
                int border = findBorder(map, node, col_range[0], row_range[0], row_range[1]);
                //もし探索した行にnodeの境界がなかった時、1行下にずらす
                if(border == -1){
                    col_range[0]++;//1行下にずらす
                    continue;
                }
                row_range[1] = border;
                break;
            }
            
            //見つかっていたら返す
            if(flag){
                return new int[]{row_range[1], col_range[0]};
            }
            
            //たての範囲の最大値を求める
            while(!isMinRange_row && col_range[1] >= 0){
                int border = findBorder(map, node, col_range[1], row_range[0], row_range[1]);
                //もし探索した行にnodeの境界がなかった時、1行上にずらす
                if(border == -1){
                    col_range[1]--;//上にずらす
                    continue;
                }
                row_range[0] = border + 1;
                break;
            }
            
            //見つかっていたら返す
            if(flag){
                return new int[]{row_range[0] - 1, col_range[1]};
            }
        }
        return ans;
    }
    
    /**
     * 渡された配列で、nodeで別れる境界を見つける
     * @param map   探索対象の配列
     * @param node  探索する要素
     * @param start 探索の開始位置
     * @param end   探索の終了位置
     * @return      map[index] <= node <= map[index + 1]を満たすindex
     */
    public static int findBorder(int[] map, int node, int start, int end){
        int ans = -1;
        //渡された開始位置と終了位置が配列の範囲内かチェックする
        boolean checkRange = checkRange(start, 0, map.length - 1) && checkRange(end, 0, map.length - 1); 
        
        //もし探索のindexが配列のサイズと不適合だったとき、またはもしその配列内に境界がない時は返す
        if(!checkRange || !checkRange(node, map[start], map[end])) return ans;
        
        //探索の開始位置と終了位置とその中間位置を定義
        int s = start;
        int e = end;
        int mid ;
        
        //2分探索
        while(true){
            mid = (s + e) / 2;
            //map[mid]が境界かどうか調べる
            boolean flag1 = mid > 0 && checkRange(node, map[mid], map[mid - 1]);
            boolean flag2 = mid < map.length - 1 && checkRange(node, map[mid], map[mid + 1]);
            
            /* map[mid]が境界になっていた場合、mid[index] <= node < mid[index + 1]を満たす
             * indexを返す
             * また、nodeと一致したら、flagをtrueにし、探索を終了するようにする*/
            if(flag1 || flag2){
                if(map[mid] < node) {
                    if(mid < map.length - 1 && map[mid + 1] == node){
                        flag = true;
                        ans = mid + 1;
                    }
                    else ans = mid;
                } else if(map[mid] == node){
                    flag = true;
                    ans = mid;
                } else {
                    if(mid > 0 && map[mid - 1] == node) flag = true;
                    ans = mid - 1;
                }
                break;
            }
            //範囲が1になっても見つからなかったら-1を返す
            if(s == e) break;
            
            if(map[mid] < node) s = mid + 1;
            else e = mid - 1;
        }
        return ans;
    }
    
    /**
     * 2次元配列を縦に探索し、境界をみつかるメソッド
     * @param map   検索をする対象の二次元配列
     * @param node  検索する要素
     * @param index 何列目を探索するか示すIndex
     * @param start 探索の開始位置を示すIndex(何行目か)
     * @param end   探索の終了位置を示すIndex(何行目か)
     * @return  map[ind][index] <= node <= map[ind + 1][index]を満たすind
     */
    public static int findBorder(int[][] map, int node, int index, int start, int end){
        int ans = -1;
        //渡された開始位置と終了位置が配列の範囲内かチェックする
        boolean checkRange = checkRange(start, 0, map.length - 1) && checkRange(end, 0, map.length - 1)
                 && checkRange(index, 0, map[0].length - 1);

        //もし探索のindexが配列のサイズと不適合だったとき、またはもしその配列内に境界がない時は返す
        if(!checkRange || !checkRange(node, map[start][index], map[end][index])) return ans;
        
        //探索の開始位置と終了位置とその中間位置を定義
        int s = start;
        int e = end;
        int mid;
        
        //2分探索
        while(true){
            mid = (s + e) / 2;
            
            //map[mid]が境界かどうか調べる
            boolean flag1 = mid > 0 && checkRange(node, map[mid][index], map[mid - 1][index]);
            boolean flag2 = mid < map.length - 1 && checkRange(node, map[mid][index], map[mid + 1][index]);

            /* map[mid]が境界になっていた場合、mid[index] <= node < mid[index + 1]を満たす
             * indexを返す
             * また、nodeと一致したら、flagをtrueにし、探索を終了するようにする*/
            if(flag1 || flag2){
                if(map[mid][index] < node){
                    if(mid < map.length - 1 && map[mid + 1][index] == node){
                        flag = true;
                        ans = mid + 1;
                    }
                    ans = mid;
                }else if(map[mid][index] == node){
                    ans = mid;
                    flag = true;
                }else{
                    if(mid > 0 && map[mid][index] == node) flag = true;
                    ans = mid - 1;
                }
                break;
            }
            if(s == e) break;
            
            if(map[mid][index] < node) s = mid + 1;
            else e = mid - 1;
        }
        return ans;
    }
    
    /**
     * xがaとbの間にあるかどうか調べるメソッド
     * @param x
     * @param a
     * @param b
     * @return
     */
    public static boolean checkRange(int x, int a, int b){
       return (x - a) * (x - b) <= 0; 
    }
}
