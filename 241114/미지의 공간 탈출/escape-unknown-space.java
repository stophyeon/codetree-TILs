
import  java.util.*;
import  java.io.*;

public class Main {
    static int[] dr= {0,0,1,-1};
    static int[] dc= {1,-1,0,0};
    public static class Node{
        int r;
        int c;
        int cost;
        public Node(int r, int c, int cost){
            this.r=r;
            this.c=c;
            this.cost=cost;
        }
    }
    public static class Cube{
        int r;
        int c;
        int d;
        int turn;
        public Cube(int r, int c,int turn,int d){
            this.r=r;
            this.c=c;
            this.d=d;
            this.turn=turn;
        }
    }
    static int[][] map;
    static int[][][] wall;
    static int[][] point;
    static int n;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int f = Integer.parseInt(st.nextToken());
        map = new int[n][n];
        wall = new int[5][m][m];
        point = new int[f][4];
        int wr =0;
        int wc =0;
        boolean first=true;
        for(int i=0;i<n; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<n; j++){
                map[i][j]=Integer.parseInt(st.nextToken());
                if(map[i][j]==3&&first){
                    wr=i;
                    wc=j;
                    first=false;
                }
            }
        }
        int sr=0;
        int sc=0;
        for(int i=0; i<5; i++){
            for(int j=0; j<m; j++){
                st = new StringTokenizer(br.readLine());
                for(int k=0; k<m; k++){
                    wall[i][j][k]=Integer.parseInt(st.nextToken());
                    if(wall[4][j][k]==2){
                        sr=j;
                        sc=k;
                    }
                }
            }
        }

        for(int i=0; i<f; i++){
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            point[i]=new int[]{r,c,d,v};
        }
        //System.out.println(sr);
        //System.out.println(sc);
        //정육면체 공간 탈출
        int er =0;
        int ec =0;
        int ed =0;
        int ssr=0;
        int ssc=0;
        for(int i=0;i<n; i++){
            for(int j=0;j<n; j++){
                if(map[i][j]==3){
                    for(int k=0; k<4; k++){
                        if(map[i+dr[k]][j+dc[k]]==0){
                            er=i;
                            ec=j;
                            ed=k;
                            ssr=i+dr[k];
                            ssc=j+dc[k];
                            break;
                        }
                    }
                }
            }
        }
        int ans=-1;
        //System.out.println(ed);
        if(ed==0) wall[ed][m-1][wr+m-1-er]=4;
        else if(ed==1) wall[ed][m-1][er-wr]=4;
        else if(ed==2) wall[ed][m-1][ec-wc]=4;
        else{ wall[ed][m-1][wc+m-1-ec]=4;}
        for(int[] p:point){
            map[p[0]][p[1]]=1;
        }
        //정육면체 탈출
        int answer=1;
        boolean[][][] visited = new boolean[5][m][m];
        Queue<Cube> q = new LinkedList<>();
        q.add(new Cube(sr,sc,0,4));
        visited[4][sr][sc]=true;

        //int[] loc = moveCube(2,0,0,3,3);
        //System.out.printf("%d : %d, %d",loc[0],loc[1],loc[2]);
        while(!q.isEmpty()){
            Cube c = q.poll();
            //System.out.printf("%d : %d, %d , %d\n",c.d,c.r,c.c,c.turn);
            if(wall[c.d][c.r][c.c]==4) {answer=c.turn; break;}
            for(int i=0; i<4; i++){
                int nr = c.r+dr[i];
                int nc = c.c+dc[i];
                int nd=c.d;
                if(!inRange(nr,nc,m)){
                    int[] loc = moveCube(c.d,c.r,c.c,i,m);
                    if(loc[0]==-1) continue;
                    nd=loc[0];
                    nr=loc[1];
                    nc=loc[2];
                }
                if(visited[nd][nr][nc]) continue;
                if (wall[nd][nr][nc]==1) continue;
                q.add(new Cube(nr,nc,c.turn+1,nd));
                visited[nd][nr][nc]=true;
            }
        }
        for(int i=1; i<=answer; i++){
            movePoint(i);
        }

        Queue<Node> q1 = new LinkedList<>();
        boolean[][] visit = new boolean[n][n];
        q1.add(new Node(ssr,ssc,answer));
        visit[ssr][ssc]=true;
        boolean[] vs = new boolean[1000];
        while(!q1.isEmpty()) {
            Node nd = q1.poll();
            if (map[nd.r][nd.c] == 4) {
                ans=nd.cost+1;
                break;
            }
            if(!vs[nd.cost+1]) movePoint(nd.cost+1);
            //if(nd.cost+1==14) System.out.println(map[0][n-2]);
            vs[nd.cost+1]=true;
            if(map[nd.r][nd.c]==1) continue;
            for (int i = 0; i < 4; i++) {
                int nr = nd.r + dr[i];
                int nc = nd.c + dc[i];
                if (!inRange(nr, nc, n)) continue;
                if (visit[nr][nc]) continue;
                if (map[nr][nc] == 1 || map[nr][nc] == 3) continue;
                q1.add(new Node(nr, nc, nd.cost + 1));
                visit[nr][nc] = true;
            }
        }
        System.out.println(ans);
//        for(int i=0; i<n; i++){
//            for(int j=0; j<n; j++){
//                System.out.print(map[i][j]);
//                System.out.print(" ");
//            }
//            System.out.println();
//        }
    }

    public static void movePoint(int t){
        for(int[] p : point){
            if(t%p[3]==0){
                if(!inRange(p[0]+dr[p[2]],p[1]+dc[p[2]],n)) continue;
                if(map[p[0]+dr[p[2]]][p[1]+dc[p[2]]]==1||map[p[0]+dr[p[2]]][p[1]+dc[p[2]]]==4) continue;
                map[p[0]+dr[p[2]]][p[1]+dc[p[2]]]=1;
                p[0]=p[0]+dr[p[2]];
                p[1]=p[1]+dc[p[2]];

            }
        }
    }
    public static int[] moveCube(int nd, int r,int c, int d,int m){
        int[] loc = new int[3];
        if(nd==4) {
            loc[0]=d;
            if(d==0) loc[2]=m-1-r;
            if(d==1) loc[2]=r;
            if(d==2) loc[2]=c;
            if(d==3) loc[2]=m-1-c;

        }
        if(nd!=4 && d==2) loc[0]=-1;
        if(nd==0){
            if(d==0) {loc[0]=3;loc[1]=r;}
            if(d==1) {loc[0]=2;loc[1]=r;loc[2]=m-1;}
            if(d==3) {loc[0]=4;loc[1]=m-1-c;loc[2]=m-1;}
        }
        if(nd==1){
            if(d==0) {loc[0]=2;loc[1]=r;}
            if(d==1) {loc[0]=3;loc[1]=r;loc[2]=m-1;}
            if(d==3) {loc[0]=4;loc[1]=m-1-c;}
        }
        if(nd==2){
            if(d==0) {loc[1]=r;}
            if(d==1) {loc[0]=1;loc[1]=r;loc[2]=m-1;}
            if(d==3) {loc[0]=4;loc[1]=m-1;loc[2]=c;}
        }
        if(nd==3){
            if(d==0) {loc[0]=1;loc[1]=r;}
            if(d==1) {loc[1]=r;loc[2]=m-1;}
            if(d==3) {loc[0]=4;loc[2]=m-1-c;}
        }
        return loc;
    }

    public static boolean inRange(int r, int c, int len){
        return r>=0&& r<len&&c>=0&&c<len;
    }

}