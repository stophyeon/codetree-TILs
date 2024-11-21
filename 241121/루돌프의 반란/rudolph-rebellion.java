import  java.util.*;
import  java.io.*;

public class Main {
    public static class Santa{

        int r;
        int c;
        boolean alive=true;
        int stun=0;
        int score=0;
        public Santa(int r, int c){
            this.r=r;
            this.c=c;
        }
    }
    static int n;
    static int m;
    static int p;
    static int cs;
    static int ds;
    static HashMap<Integer, Santa> santas = new HashMap<>();
    static int[][] map;
    static int[] dr = {-1,0,1,0};
    static int[] dc = {0,1,0,-1};
    static int[] rdr = {-1,-1,0,1,1,1,0,-1};
    static int[] rdc = {0,1,1,1,0,-1,-1,-1};
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        p = Integer.parseInt(st.nextToken());
        cs = Integer.parseInt(st.nextToken());
        ds = Integer.parseInt(st.nextToken());

        map=new int[n+1][n+1];
        st = new StringTokenizer(br.readLine());
        int sr = Integer.parseInt(st.nextToken());
        int sc = Integer.parseInt(st.nextToken());

        for(int i=0; i<p; i++){
            st = new StringTokenizer(br.readLine());
            int num = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            santas.put(num,new Santa(r,c));
            map[r][c]=num;
        }
        List<Integer> keys = new ArrayList<>(santas.keySet());
        keys.sort((k1,k2)->k1-k2);
        //게임 시작
//        System.out.printf("%d,%d\n",sr,sc);
//        for(int k : keys){
//            System.out.printf("%d : %d,%d ,%d점\n",k,santas.get(k).r,santas.get(k).c,santas.get(k).score);
//        }
        for(int i=0; i<m;i++){
            //루돌프 이동
            //산타 선택
            int[] res = minSanta(sr,sc);
            Santa s = santas.get(res[0]);

            //이동방향 선택
            int dir = 0;
            int dis = res[1];
            for(int j=0; j<8; j++){
                int nr = sr+rdr[j];
                int nc = sc+rdc[j];
                if(!inRange(nr,nc)) continue;
                if(dis>(nr-s.r)*(nr-s.r)+(nc-s.c)*(nc-s.c)){
                    dis=(nr-s.r)*(nr-s.r)+(nc-s.c)*(nc-s.c);
                    dir=j;
                }
            }
            sr+=rdr[dir];
            sc+=rdc[dir];
            //충돌,상호 작용
            if(map[sr][sc]!=0){
                s.stun=2;
                collapse(res[0],dir);
            }
            //산타 이동
            for(int k : keys){
                if(santas.get(k).stun!=0 ||!santas.get(k).alive) {continue;}
                Santa san = santas.get(k);
                int dst = (san.r-sr)*(san.r-sr)+(san.c-sc)*(san.c-sc);
                int nxr=san.r;
                int nxc=san.c;
                int drt=0;
                for(int j=0;j<4;j++){
                    int nr = san.r+dr[j];
                    int nc = san.c+dc[j];
                    if(!inRange(nr,nc)) continue;
                    if(map[nr][nc]!=0) continue;
                    if(dst>(nr-sr)*(nr-sr)+(nc-sc)*(nc-sc)){
                        nxr=nr;
                        nxc=nc;
                        dst=(nr-sr)*(nr-sr)+(nc-sc)*(nc-sc);
                        drt=j;
                    }
                    if(dst==(nr-sr)*(nr-sr)+(nc-sc)*(nc-sc)){

                    }
                }
                map[san.r][san.c]=0;
                san.r=nxr;san.c=nxc;


                if(nxr==sr&&nxc==sc){
                    san.stun=2;
                    collapse2(k,drt);
                }
                else{
                    map[nxr][nxc]=k;
                }
            }
            int death=0;
            for(int k: keys){
                if(!santas.get(k).alive) {death++;continue;}
                if(santas.get(k).stun!=0) santas.get(k).stun--;
                santas.get(k).score++;
            }
            if(death==p) break;
//            System.out.printf("%d - %d,%d\n",i,sr,sc);
//            for(int k : keys){
//                System.out.printf("%d : %d,%d ,%d점\n",k,santas.get(k).r,santas.get(k).c,santas.get(k).score);
//            }
        }
        for(int k : keys){
            System.out.printf("%d ",santas.get(k).score);
        }

    }
    public static void collapse2(int num,int d){
        Santa s = santas.get(num);
        s.score+=ds;
        d=(d+2)%4;
        s.r+=dr[d]*ds;
        s.c+=dc[d]*ds;
        if(!inRange(s.r,s.c)) {s.alive=false;return;}
        if(map[s.r][s.c]!=0){
            sameCell2(num,map[s.r][s.c],d);
        }
        else{
            map[s.r][s.c]=num;
        }
    }
    public static void collapse(int num,int d){
        Santa s = santas.get(num);
        s.score+=cs;
        map[s.r][s.c]=0;
        s.r+=rdr[d]*cs;
        s.c+=rdc[d]*cs;
        if(!inRange(s.r,s.c)) {s.alive=false;return;}
        if(map[s.r][s.c]!=0){
            sameCell(num,map[s.r][s.c],d);
        }
        else{
            map[s.r][s.c]=num;
        }
    }
    public static void sameCell(int n1, int n2,int d){
        Santa s1 = santas.get(n1);
        Santa s2 = santas.get(n2);
        map[s1.r][s1.c]=n1;
        s2.r+=rdr[d];
        s2.c+=rdc[d];
        if(!inRange(s2.r,s2.c)) {s2.alive=false;return;}
        if(map[s2.r][s2.c]!=0){
            sameCell(n2,map[s2.r][s2.c],d);
        }
        if(map[s2.r][s2.c]==0){
            map[s2.r][s2.c]=n2;
        }
    }
    public static void sameCell2(int n1, int n2,int d){
        Santa s1 = santas.get(n1);
        Santa s2 = santas.get(n2);
        map[s1.r][s1.c]=n1;
        s2.r+=dr[d];
        s2.c+=dc[d];
        if(!inRange(s2.r,s2.c)) {s2.alive=false;return;}
        if(map[s2.r][s2.c]!=0){
            sameCell2(n2,map[s2.r][s2.c],d);
        }
        if(map[s2.r][s2.c]==0){
            map[s2.r][s2.c]=n2;
        }
    }
    public static int[] minSanta(int r, int c){
        int minNum=0;
        int min=Integer.MAX_VALUE;
        for(int k : santas.keySet()){
            if(!santas.get(k).alive) continue;
            Santa s = santas.get(k);
            int dis = (r-s.r)*(r-s.r)+(c-s.c)*(c-s.c);
            if(dis<min){
                minNum=k;
                min=dis;
            }
            if(dis==min){
                if(santas.get(minNum).r<santas.get(k).r){
                    minNum=k;
                }
                if(santas.get(minNum).r==santas.get(k).r){
                    if(santas.get(minNum).c<santas.get(k).c){
                        minNum=k;
                    }
                }
            }
        }
        return new int[]{minNum,min};
    }
    public static boolean inRange(int r, int c){
        return r>0&&r<=n&&c>0&&c<=n;
    }
}