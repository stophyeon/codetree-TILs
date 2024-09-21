import  java.util.*;
import  java.io.*;

public class Main {

    static int n;
    static int m;
    static int k;
    static int c;
    static int[][] map;
    static int[][] cant;
    static int count=0;
    static int[] dr = {-1,1,0,0};
    static int[] dc = {0,0,-1,1};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n=Integer.parseInt(st.nextToken());
        m=Integer.parseInt(st.nextToken());
        k=Integer.parseInt(st.nextToken());
        c=Integer.parseInt(st.nextToken());
        map=new int[n][n];
        cant = new int[n][n];
        //1-100 나무, 0-빈칸, -1 벽
        for(int i=0; i<n; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<n; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        for(int i=0; i<m; i++) {
            grow();
            makeTree();
            int[] max = findMax();
            setCant(max[0], max[1]);
            for (int j = 0; j < n; j++) {
                for (int l = 0; l < n; l++) {
                    if (cant[j][l] > 0) {
                        cant[j][l] -= 1;
                    }
                }
            }
//            for(int h=0; h<n; h++) {
//                for (int j = 0; j < n; j++) {
//                    System.out.print(map[h][j]);
//                    System.out.print(" ");
//                }
//                System.out.println();
//            }
//            System.out.println("===============");
//            for(int h=0; h<n; h++) {
//                for (int j = 0; j < n; j++) {
//                    System.out.print(cant[h][j]);
//                    System.out.print(" ");
//                }
//                System.out.println();
//            }
//            System.out.println(count);
        }

        //System.out.println(max[0]);
        //System.out.println(max[1]);


        System.out.println(count);
    }
    //설장
    public static void grow(){
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(map[i][j]<=0) continue;
                int num=0;
                for(int l=0; l<4; l++){
                    int row = i+dr[l];
                    int col = j+dc[l];
                    if(!inRange(row,col)) continue;
                    if(map[row][col]>0) num++;
                }
                map[i][j]+=num;
            }
        }
    }
    //번식
    public static void makeTree(){
        List<int[]> loc = new ArrayList<>();
        for(int i=0; i<n; i++) {
            for (int j = 0; j < n; j++) {
                if(map[i][j]>0){
                    int num=0;
                    List<int[]> trees = new ArrayList<>();
                    for(int l=0; l<4; l++) {
                        int row = i + dr[l];
                        int col = j + dc[l];
                        if (!inRange(row, col)) continue;
                        if(cant[row][col]>0) continue;
                        if (map[row][col] == 0) {
                            num++;
                            trees.add(new int[]{row,col,map[i][j]});
                        }
                    }
                    for(int[] tree : trees){
                        loc.add(new int[]{tree[0],tree[1],tree[2]/num});
                    }
                }
            }
        }
        for (int[] tree : loc) {
            map[tree[0]][tree[1]] += tree[2];
        }
    }
    //제초제 위치 탐색 k
    public static int[] findMax(){
        int[] tr = {-1,1,-1,1};
        int[] tc = {1,1,-1,-1};
        int max=-1;
        int[] maxLoc = new int[2];
        for(int i=0; i<n; i++){
            for (int j = 0; j < n; j++) {
                if(map[i][j]<=0) continue;
                int num=map[i][j];
                for(int l=0; l<4; l++){
                    int row = i+tr[l];
                    int col = j+tc[l];
                    if (!inRange(row, col)) continue;
                    if(map[row][col]>0) num+=map[row][col];
                    if(map[row][col]<=0) continue;
                    for(int h=0; h<k; h++) {
                        row += tr[l];
                        col += tc[l];
                        if (!inRange(row, col)) break;
                        if (map[row][col] > 0) num += map[row][col];
                        if (map[row][col] <= 0) break;
                    }
                }
                if(max<num){
                    max=num;
                    maxLoc = new int[]{i,j};
                }
                if(max==num){
                    if(maxLoc[0]>i){
                        maxLoc = new int[]{i,j};
                    }
                    if(maxLoc[0]==i){
                        if(maxLoc[1]>j){
                            maxLoc = new int[]{i,j};
                        }
                    }
                }
            }
        }
        count+=max;
        return maxLoc;
    }

    public static void setCant(int row, int col){
        int[] tr = {-1,1,-1,1};
        int[] tc = {1,1,-1,-1};
        cant[row][col]=c+1;
        map[row][col]=0;
        for(int l=0; l<4; l++){
            int nr = row+tr[l];
            int nc = col+tc[l];
            if (!inRange(nr, nc)) continue;
            cant[nr][nc]=c+1;
            if(map[nr][nc]<=0) continue;
            map[nr][nc]=0;
            for(int h=0; h<k; h++) {
                nr += tr[l];
                nc += tc[l];
                if (!inRange(nr, nc)) break;
                cant[nr][nc]=c+1;
                map[nr][nc]=0;
                if(map[nr][nc]<=0) {break;}
            }
        }
    }

    public static boolean inRange(int r, int c){
        return r>=0&&r<n&&c>=0&&c<n;
    }
}