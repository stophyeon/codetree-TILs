import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

public class Main {
    public static class Snake{
        int r;
        int c;
        List<int[]> path = new ArrayList<>();
        public Snake(int r, int c, List<int[]> path) {
            this.r = r;
            this.c = c;
            this.path = path;
        }
    }
    public static class Soldier{
        int r;
        int c;
        boolean stun=false;
        boolean dead=false;
        public Soldier(int r, int c){
            this.r = r;
            this.c = c;

        }
    }
    static int n;
    static int[][] map;
    static Soldier[] soldiers;
    static int[] dr={-1,1,0,0};
    static int[] dc={0,0,-1,1};
    static int[][][] map4;
    static int[][] sight;
    static int a=0,s=0,d=0;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        int sr = Integer.parseInt(st.nextToken());
        int sc = Integer.parseInt(st.nextToken());
        int er = Integer.parseInt(st.nextToken());
        int ec = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        soldiers = new Soldier[m];
        map4=new int[4][n][n];
        sight=new int[n][n];
        for(int i = 0; i < m; i++) {
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            soldiers[i] = new Soldier(r, c);

        }
        map=new int[n][n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        //메두사 이동 좌표 저장
        List<int[]> turn = new ArrayList<>();
        Queue<Snake> q = new LinkedList<>();
        boolean[][] visited = new boolean[n][n];
        visited[sr][sc]=true;
        List<int[]> p=new ArrayList<>();
        p.add(new int[]{sr,sc});
        q.add(new Snake(sr, sc,p));
        while(!q.isEmpty()) {
            Snake s = q.poll();
            if(s.r==er&&s.c==ec){
                turn=s.path;
                break;
            }
            for (int i = 0; i < 4; i++) {
                int nr = s.r + dr[i];
                int nc = s.c + dc[i];
                if (!inRange(nr, nc)||visited[nr][nc]) continue;
                if(map[nr][nc]!=0) continue;
                List<int[]> path=new ArrayList<>(s.path);
                path.add(new int[]{nr,nc});
                q.add(new Snake(nr, nc,path));
                visited[nr][nc]=true;
            }
        }
        if (turn.isEmpty()) {
            System.out.println(-1);
            return;
        }
        for (int i = 1; i < turn.size()-1; i++) {
            a=0;s=0;d=0;
            int[] loc = turn.get(i);
            for (Soldier s : soldiers) {
                if (s.r==loc[0]&&s.c==loc[1]) s.dead=true;
            }
            //메두사 시선
            sight=makeStun(loc[0],loc[1]);
            //전사 이동

            move1(loc[0],loc[1]);
            move2(loc[0],loc[1]);
//            for (int j=0; j<m; j++) {
//                if(soldiers[j].dead)
//                    System.out.println(j+" ");
//            }
//            for (int j=0; j<n; j++){
//                for (int k=0; k<n; k++){
//                    System.out.print(sight[j][k]+" ");
//                }
//                System.out.println();
//            }
            System.out.printf("%d %d %d\n",d,s,a);
            for (Soldier s : soldiers) {
                if(s.dead) continue;
                s.stun=false;
            }
            for(int j=0; j<4; j++){
                for(int k=0; k<n; k++)
                    Arrays.fill(map4[j][k], 0);
            }
        }
        System.out.println(0);
    }
    public static void move1(int r, int c) {
        //System.out.println(soldiers[3].r+","+soldiers[3].c);
        for(int i=0; i<soldiers.length; i++) {
            //System.out.printf("%d\n",soldiers.length);
            if(soldiers[i].stun||soldiers[i].dead) continue;
            int dis= Math.abs(r-soldiers[i].r)+Math.abs(c-soldiers[i].c);
            int ndr=-1,ndc=-1;

            for(int j=0; j<4; j++){
                int nr = soldiers[i].r + dr[j];
                int nc = soldiers[i].c + dc[j];

                if (!inRange(nr, nc)||sight[nr][nc]==1) continue;
                if(dis>Math.abs(r-nr)+Math.abs(c-nc)) {
                    d++;
                    ndr=nr;
                    ndc=nc;
                    break;
                }
            }
            if (ndr == -1){continue;}
            if (ndr==r&&ndc==c){
                a++;
                soldiers[i].dead=true;
            }
            soldiers[i].r=ndr;
            soldiers[i].c=ndc;
        }
    }
    public static void move2(int r, int c) {
        for(int i=0; i<soldiers.length; i++) {
            if(soldiers[i].stun||soldiers[i].dead) continue;
            int dis= Math.abs(r-soldiers[i].r)+Math.abs(c-soldiers[i].c);
            int ndr=-1,ndc=-1;

            for(int j=0; j<4; j++){
                int nr = soldiers[i].r + dr[(j+2)%4];
                int nc = soldiers[i].c + dc[(j+2)%4];
                if (!inRange(nr, nc)||sight[nr][nc]==1) continue;
                if(dis>Math.abs(r-nr)+Math.abs(c-nc)) {
                    d++;
                    ndr=nr;
                    ndc=nc;
                    break;
                }
            }
            if (ndr == -1){continue;}
            if (ndr==r&&ndc==c){
                a++;
                soldiers[i].dead=true;
            }
            soldiers[i].r=ndr;
            soldiers[i].c=ndc;
        }
    }
    public static int[][] makeStun(int r, int c){
        List<Integer> right = seeRight(r,c);
        List<Integer> left = seeLeft(r,c);
        List<Integer> up = seeUp(r,c);
        List<Integer> down = seeDown(r,c);
        //System.out.println(r+","+c+": up-"+up+" down-"+down+" left-"+left+" right-"+right);
        List<Integer>[] p = new List[]{up,down,left,right};
        int maxIdx=0;
        int max=0;
        for (int i=0; i<4; i++) {
            if(max<p[i].size()) {max=p[i].size();maxIdx=i;}
        }
        s=max;
        stun(p[maxIdx]);
        return map4[maxIdx];
    }
    public static void stun(List<Integer> d){
        for (Integer i : d) {
            //System.out.println(i);
            soldiers[i].stun=true;
        }
    }
    public static List<Integer> seeRight(int r, int c) {
        List<Integer> sd = new ArrayList<>();
        if(c==n-1) return sd;
        int[][] map2 = new int[n][n];
        int[][] map3 = new int[n][n];

        int idx=c+1;
        for (int j=1; j<n; j++) {
            if (idx>=n) break;
            for (int k=r-j; k<=r+j; k++) {
                if(k>=0&&k<n)
                    map4[3][k][idx]=1;
            }
            idx++;
        }

        for (int i = 0; i < soldiers.length; i++) {
            if (soldiers[i].stun||soldiers[i].dead) continue;
            if (map3[soldiers[i].r][soldiers[i].c]<0) continue;
            if(map4[3][soldiers[i].r][soldiers[i].c]==0) continue;
            map2[soldiers[i].r][soldiers[i].c] +=1;
            if(r==soldiers[i].r&&c<soldiers[i].c) {
                for (int j = soldiers[i].c+1; j < n; j++) {
                    map3[r][j]=-301;
                }
            }
            else if(r<soldiers[i].r&&c<soldiers[i].c) {
                for (int j = 1; j < n-soldiers[i].c; j++) {
                    for (int k=0; k<=j; k++){
                        if(soldiers[i].r+k>=n) break;
                        map3[soldiers[i].r+k][soldiers[i].c+j]=-301;
                    }
                }
            }
            else if(r>soldiers[i].r&&c<soldiers[i].c) {
                for (int j = 1; j < n-soldiers[i].c; j++) {
                    for (int k=0; k<=j; k++){
                        if(soldiers[i].r-k<0) break;
                        map3[soldiers[i].r-k][soldiers[i].c+j]=-301;
                    }
                }
            }
        }
//        for (int i=0; i<n; i++) {
//            for (int j=0; j<n; j++) {
//                System.out.print(map2[i][j]+" ");
//            }
//            System.out.println();
//        }
//        System.out.println();
//
//        for (int i=0; i<n; i++) {
//            for (int j=0; j<n; j++) {
//                System.out.print(map3[i][j]+" ");
//            }
//            System.out.println();
//        }
//        System.out.println();
//
//        for (int i=0; i<n; i++) {
//            for (int j=0; j<n; j++) {
//                System.out.print(map4[3][i][j]+" ");
//            }
//            System.out.println();
//        }
        for (int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if (map3[i][j]<0&&map4[3][i][j]==1) map4[3][i][j]=0;
            }
        }
        for (int i=0; i<soldiers.length; i++) {
            if (soldiers[i].dead) continue;
            int nr=soldiers[i].r;
            int nc=soldiers[i].c;
            //System.out.println(nr+" "+nc+" ");
            if (map3[nr][nc]>=0&&map2[nr][nc]>0&&map4[3][nr][nc]==1) sd.add(i);
        }

        return sd;
    }
    public static List<Integer> seeLeft(int r, int c) {
        List<Integer> sd = new ArrayList<>();
        if(c==0) return sd;
        int[][] map2 = new int[n][n];
        int[][] map3 = new int[n][n];

        int idx=c-1;
        for (int j=1; j<n; j++) {
            if (idx<0) break;
            for (int k=r-j; k<=r+j; k++) {
                if(k>=0&&k<n)
                    map4[2][k][idx]=1;
            }
            idx--;
        }
        for (int i = 0; i < soldiers.length; i++) {
            if (soldiers[i].stun||soldiers[i].dead) continue;
            if (map3[soldiers[i].r][soldiers[i].c]<0) continue;
            if (map4[2][soldiers[i].r][soldiers[i].c]==0) continue;
            map2[soldiers[i].r][soldiers[i].c] +=1;
            if(r==soldiers[i].r&&c>soldiers[i].c) {
                for (int j = soldiers[i].c-1; j >=0; j--) {
                    map3[r][j]=-301;
                }
            }
            else if(r<soldiers[i].r&&c>soldiers[i].c) {
                for (int j = 1; j <=soldiers[i].c; j++) {
                    for (int k=0; k<=j; k++){
                        if(soldiers[i].r+k>=n) break;
                        map3[soldiers[i].r+k][soldiers[i].c-j]=-301;
                    }
                }
            }
            else if(r>soldiers[i].r&&c>soldiers[i].c) {
                for (int j = 1; j <= soldiers[i].c; j++) {
                    for (int k=0; k<=j; k++){
                        if(soldiers[i].r-k<0) break;
                        map3[soldiers[i].r-k][soldiers[i].c-j]=-301;
                    }
                }
            }
        }
        for (int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if (map3[i][j]<0&&map4[2][i][j]==1) map4[2][i][j]=0;
            }
        }
        for (int i=0; i<soldiers.length; i++) {
            if (soldiers[i].dead) continue;
            int nr=soldiers[i].r;
            int nc=soldiers[i].c;
            if (map3[nr][nc]>=0&&map2[nr][nc]>0&&map4[2][nr][nc]==1) sd.add(i);
        }

        return sd;
    }
    public static List<Integer> seeUp(int r, int c) {
        List<Integer> sd = new ArrayList<>();
        if(r==0) return sd;
        int[][] map2 = new int[n][n];
        int[][] map3 = new int[n][n];
        //int[][] map4 = new int[n][n];


        int idx=r-1;
        for (int j=1; j<n; j++) {
            if (idx<0) break;
            for (int k=c-j; k<=c+j; k++) {
                if(k>=0&&k<n)
                    map4[0][idx][k]=1;
            }
            idx--;
        }
        for (int i = 0; i < soldiers.length; i++) {
            if (soldiers[i].stun||soldiers[i].dead) continue;
            if (map3[soldiers[i].r][soldiers[i].c]<0) continue;
            if (map4[0][soldiers[i].r][soldiers[i].c]==0) continue;
            map2[soldiers[i].r][soldiers[i].c] +=1;
            if(c==soldiers[i].c&&r>soldiers[i].r) {
                for (int j = soldiers[i].r-1; j >=0; j--) {
                    map3[j][c]=-301;
                }
            }
            else if(c<soldiers[i].c&&r>soldiers[i].r) {
                for (int j = 1; j <= soldiers[i].r; j++) {
                    for (int k=0; k<=j; k++){
                        if(soldiers[i].c+k>=n) break;
                        map3[soldiers[i].r-j][soldiers[i].c+k]=-301;
                    }
                }
            }
            else if(c>soldiers[i].c&&r>soldiers[i].r) {
                for (int j = 1; j <= soldiers[i].r; j++) {
                    for (int k=0; k<=j; k++){
                        if(soldiers[i].c-k<0) break;
                        map3[soldiers[i].r-j][soldiers[i].c-k]=-301;
                    }
                }
            }
        }
//        for (int i=0; i<n; i++) {
//            for (int j=0; j<n; j++) {
//                System.out.print(map2[i][j]+" ");
//            }
//            System.out.println();
//        }
//        System.out.println();
//
//        for (int i=0; i<n; i++) {
//            for (int j=0; j<n; j++) {
//                System.out.print(map3[i][j]+" ");
//            }
//            System.out.println();
//        }
//        System.out.println();
//
//        for (int i=0; i<n; i++) {
//            for (int j=0; j<n; j++) {
//                System.out.print(map4[3][i][j]+" ");
//            }
//            System.out.println();
//        }
        for (int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if (map3[i][j]<0&&map4[0][i][j]==1) map4[0][i][j]=0;
            }
        }
        for (int i=0; i<soldiers.length; i++) {
            if (soldiers[i].dead) continue;
            int nr=soldiers[i].r;
            int nc=soldiers[i].c;
            if (map3[nr][nc]>=0&&map2[nr][nc]>0&&map4[0][nr][nc]==1) sd.add(i);
        }

        return sd;
    }
    public static List<Integer> seeDown(int r, int c) {
        List<Integer> sd = new ArrayList<>();
        if(r==n-1) return sd;
        int[][] map2 = new int[n][n];
        int[][] map3 = new int[n][n];
        //int[][] map4 = new int[n][n];

        int idx=r+1;
        for (int j=1; j<n; j++) {
            if (idx>=n) break;
            for (int k=c-j; k<=c+j; k++) {
                if(k>=0&&k<n)
                    map4[1][idx][k]=1;
            }
            idx++;
        }
        for (int i = 0; i < soldiers.length; i++) {
            if (soldiers[i].stun||soldiers[i].dead) continue;
            if (map3[soldiers[i].r][soldiers[i].c]<0) continue;
            if (map4[1][soldiers[i].r][soldiers[i].c]==0) continue;
            map2[soldiers[i].r][soldiers[i].c] +=1;
            if(c==soldiers[i].c&&r<soldiers[i].r) {
                for (int j = soldiers[i].r+1; j < n; j++) {
                    map3[j][c]=-301;
                }
            }
            else if(c<soldiers[i].c&&r<soldiers[i].r) {
                for (int j = 1; j < n-soldiers[i].r; j++) {
                    for (int k=0; k<=j; k++){
                        if(soldiers[i].c+k>=n) break;
                        map3[soldiers[i].r+j][soldiers[i].c+k]=-301;
                    }
                }
            }
            else if(c>soldiers[i].c&&r<soldiers[i].r) {
                for (int j = 1; j < n-soldiers[i].r; j++) {
                    for (int k=0; k<=j; k++){
                        if(soldiers[i].c-k<0) break;
                        map3[soldiers[i].r+j][soldiers[i].c-k]=-301;
                    }
                }
            }
        }
        for (int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if (map3[i][j]<0&&map4[1][i][j]==1) map4[1][i][j]=0;
            }
        }
        for (int i=0; i<soldiers.length; i++) {
            if (soldiers[i].dead) continue;
            int nr=soldiers[i].r;
            int nc=soldiers[i].c;
            if (map3[nr][nc]>=0&&map2[nr][nc]>0&&map4[1][nr][nc]==1) sd.add(i);
        }

        return sd;
    }
    public static boolean inRange(int r, int c){
        return r >= 0 && r<n&&c>=0&&c<n;
    }
}