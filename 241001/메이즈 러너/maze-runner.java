import  java.util.*;
import  java.io.*;

public class Main {
    static int n;
    static int m;
    static int k;
    static int escapeR;
    static int escapeC;
    static int total;
    static int[][] map;
    static int[] dr = {-1,1,0,0};
    static int[] dc = {0,0,-1,1};
    public static class Runner{
        int r;
        int c;
        int d;

        public Runner(int r, int c, int d){
            this.r=r;
            this.c=c;
            this.d=d;

        }
    }
    static Runner[] runners;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        map = new int[n][n];
        runners=new Runner[m];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        for(int i=0; i<m; i++){
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken())-1;
            int c = Integer.parseInt(st.nextToken())-1;
            runners[i]=new Runner(r,c,0);
        }
        st = new StringTokenizer(br.readLine());
        escapeR=Integer.parseInt(st.nextToken())-1;
        escapeC=Integer.parseInt(st.nextToken())-1;
//        System.out.print(escapeR);
//        System.out.print(" ");
//        System.out.println(escapeC);
        for (int i=0; i<k; i++){
            move();
            moveWall();
            if(check()) break;

//            for(int j=0; j<n; j++){
//                for(int h=0; h<n; h++){
//                    System.out.print(map[j][h]);
//                }
//                System.out.println();
//            }
//            System.out.print(escapeR);
//            System.out.print(" ");
//            System.out.println(escapeC);
        }
        System.out.println(total);
        System.out.print(escapeR+1);
        System.out.print(" ");
        System.out.print(escapeC+1);

    }
    public static void move(){
        for(int i=0; i<m; i++){
            if(runners[i]==null) continue;
            for (int j=0; j<4; j++) {
                int nr = runners[i].r+dr[j];
                int nc = runners[i].c+dc[j];
                if(!inRange(nr,nc)) continue;
                if(map[nr][nc]>=1) continue;
                int dis = Math.abs(escapeR-runners[i].r)+Math.abs(escapeC-runners[i].c);
                if(dis>Math.abs(escapeR-nr)+Math.abs(escapeC-nc)){
                    runners[i].r=nr;
                    runners[i].c=nc;
                    total+=1;
                    if(runners[i].r==escapeR&&runners[i].c==escapeC) {
                        runners[i]=null;
                    }
                    break;
                }
            }
        }
//        for(int j=0; j<m; j++){
//            if(runners[j]==null) continue;
//            System.out.print(j+1);
//            System.out.print(" - ");
//            System.out.print(runners[j].r);
//            System.out.print(",");
//            System.out.print(runners[j].c);
//            System.out.print(",");
//            System.out.println(runners[j].d);
//        }
    }

    public static void moveWall(){
        int[] square = getSquare();
//        System.out.print(square[0]);
//        System.out.print(",");
//        System.out.print(square[1]);
//        System.out.print(", len : ");
//        System.out.println(square[2]);
        int[][] turn = new int[n][n];
        for(int i=0; i<n; i++){
            turn[i]=Arrays.copyOf(map[i],n);
        }
        for(int i=0; i<square[2]; i++){
            for(int j=0; j<square[2]; j++){
//                System.out.print(turn[square[0]+i][square[1]+j]);
//                System.out.print(" - ");
//                System.out.print(square[0]+i);
//                System.out.print(",");
//                System.out.print(square[1]+j);
//                System.out.print(" ->");
//                System.out.print(square[0]+j);
//                System.out.print(",");
//                System.out.println(square[2]+square[1]-1-i);
                if(turn[square[0]+i][square[1]+j]>=1)  map[square[0]+j][square[1]+square[2]-1-i]=turn[square[0]+i][square[1]+j]-1;
                else{map[square[0]+j][square[1]+square[2]-1-i]=turn[square[0]+i][square[1]+j];}
            }
        }

        for(int i=0; i<m; i++){
            if(runners[i]==null) continue;
            if(runners[i].r>=square[0]&&runners[i].r<square[0]+square[2]&&runners[i].c>=square[1]&&runners[i].c<square[1]+square[2]){
                int r = runners[i].r;
                runners[i].r = square[0]+runners[i].c-square[1];
                runners[i].c = square[0]+square[1]+square[2]-1-r;
            }
        }
    }
    public static int[] getSquare(){
        for(int i=2; i<n; i++){
            for(int j=0; j<n-i+1; j++){
                for(int h=0; h<n-i+1; h++){
                    int r = j+i-1;
                    int c = h+i-1;
                    if(escapeR>=j&&escapeR<=r&&escapeC>=h&&escapeC<=c){
                        for(int g=0; g<m; g++){
                            if(runners[g]==null) continue;
                            if(runners[g].r>=j&&runners[g].r<=r&&runners[g].c>=h&&runners[g].c<=c){
                                int er = escapeR;
                                int ec = escapeC;
                                escapeR=j-h+ec;
                                escapeC=j+h+i-1-er;
                                return new int[]{j,h,i};
                            }
                        }
                    }
                }
            }
        }
        return new int[]{0,0,n};
    }
    public static boolean check(){
        int cnt=0;
        for(int i=0; i<m; i++){
            if(runners[i]==null) cnt++;
        }
        return cnt == m;
    }
    public static boolean inRange(int r, int c){
        return r>=0&&r<n&&c>=0&&c<n;
    }
}