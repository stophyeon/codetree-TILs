import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static class Runner{
        int idx;
        int r;
        int c;

        public Runner(int idx,int r, int c){
            this.r = r;
            this.c = c;
            this.idx = idx;
        }
    }
    static int n;
    static int m;
    static int er;
    static int ec;
    static int total=0;
    static int[][] map;
    //static int[][] loc;
    static Runner[] runners;
    static boolean[] arrive;
    static int[] dr={-1,1,0,0};
    static int[] dc={0,0,-1,1};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n=Integer.parseInt(st.nextToken());
        m=Integer.parseInt(st.nextToken());
        int k=Integer.parseInt(st.nextToken());
        map= new int[n][n];
        //loc=new int[n][n];
        runners = new Runner[m];
        arrive = new boolean[m];
        for (int i=0; i<n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j=0; j<n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        for (int i=0; i<m; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken())-1;
            int c = Integer.parseInt(st.nextToken())-1;
            runners[i] = new Runner(i,r, c);
            //loc[r][c]=i+1;
        }
        st = new StringTokenizer(br.readLine());
        er = Integer.parseInt(st.nextToken())-1;
        ec = Integer.parseInt(st.nextToken())-1;
        //loc[er][ec]=m+1;
        int cnt=0;

        for(int i=0; i<k; i++) {
            //러너 이동
            move();

            //정사각형 범위 지정
            int[] s=searchSquare();
            //System.out.println(s[0]+","+s[1]+" - "+s[2]);
            //범위에 포함되는 러너 탐색
            List<Integer> rs = searchRunner(s[0],s[1],s[2]);
            //회전
            turnSquare(s[0],s[1],s[2],rs);
            if(cnt==m) break;
//            for(int j=0; j<n; j++) {
//                for(int h=0; h<n; h++) {
//                    System.out.print(map[j][h]+" ");
//                }
//                System.out.println();
//            }
//            for (int j=0; j<m; j++) {
//                if(arrive[j]) continue;
//                System.out.println(runners[j].idx+": "+runners[j].d);
//            }
//            System.out.println(total);
//            System.out.println(er+","+ec);
        }
        System.out.println(total);
        er+=1;
        ec+=1;
        System.out.println(er+" "+ec);
    }
    public static List<Integer> searchRunner(int r, int c, int len){
        List<Integer> list=new ArrayList<>();
        for(int i=0; i< runners.length; i++) {
            if(arrive[i]) continue;
            if(runners[i].r>=r && runners[i].r<r+len&&runners[i].c>=c && runners[i].c<c+len) list.add(runners[i].idx);
        }
        return list;
    }
    public static int[] searchSquare(){
        int[] square = new int[3];
        boolean check = false;
        //길이
        for (int i = 2; i <= n; i++){
            if (check) break;
            for (int j = i-1; j >= 0; j--){
                if (check) break;
                for (int k = i-1; k >= 0; k--){
                    if (check) break;
                    int lr = er-j;
                    int lc = ec-k;
                    if(!inRange(lr,lc)||!inRange(lr+i-1,lc+i-1)) continue;
                    for (Runner r : runners) {
                        if(arrive[r.idx]) continue;
                        if(r.r>=lr&&r.r<lr+i&&r.c>=lc&&r.c<lc+i){
                            square[0]=lr;
                            square[1]=lc;
                            square[2]=i;
                            check=true;
                            break;
                        }
                    }
                }
            }
        }
        return square;
    }
    public static void turnSquare(int r, int c, int len, List<Integer> rs){
        int[][] right=new int[n][n];
        for(int i=0; i<len; i++){
            for(int j=0; j<len; j++){
                right[r+i][c+j]=map[r+len-1-j][c+i];
            }
        }
        for(int i=r; i<r+len; i++){
            for(int j=c; j<c+len; j++){
                if(right[i][j]==0) {map[i][j]=0;}
                else{map[i][j]=right[i][j]-1;}
            }
        }
        for(int k : rs){
            int nr =r-c+runners[k].c;
            int nc =r+c-runners[k].r+len-1;
            runners[k].r=nr;
            runners[k].c=nc;
        }
        int nr =r-c+ec;
        int nc= r+c-er+len-1;
        er=nr;
        ec=nc;
    }

    public static void move() {
        for (int i=0; i<m; i++) {
            if (arrive[i]) continue;
            Runner r = runners[i];
            int dis =Math.abs(r.r-er)+Math.abs(r.c-ec);
            for(int j =0; j<4; j++) {
                int nr = r.r+dr[j];
                int nc = r.c+dc[j];
                if (!inRange(nr,nc)) continue;
                if(nr==er&&nc==ec) {
                    total++;
                    arrive[i]=true;
                    break;
                }
                if(map[nr][nc]==0&&dis>Math.abs(nr-er)+Math.abs(nc-ec)){
                    r.r=nr;
                    r.c=nc;
                    total++;
                    break;
                }
            }
        }
    }
    public static boolean inRange(int r, int c){
        return r>=0&&r<n&&c>=0&&c<n;
    }
}
