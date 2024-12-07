import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int l;
    static int n;
    static int[][] map;
    static int[][] loc;
    static int[] dr = {-1,0,1,0};
    static int[] dc = {0,1,0,-1};
    static int total=0;
    public static class Person{
        int r;
        int c;
        int h;
        int w;
        int k;
        int dmg=0;
        public Person(int r,int c,int h,int w,int k){
            this.r=r;
            this.c=c;
            this.h=h;
            this.w=w;
            this.k=k;
        }
    }
    static Person[] ps;
    static boolean[] dead;
    static Set<Integer> res = new HashSet<>();
    static boolean can;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        l=Integer.parseInt(st.nextToken());
        map = new int[l][l];
        loc = new int[l][l];
        n=Integer.parseInt(st.nextToken());
        ps = new Person[n+1];
        dead = new boolean[n+1];
        int q = Integer.parseInt(st.nextToken());
        for(int i=0;i<l;i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<l;j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        for(int i=1;i<=n;i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken())-1;
            int c = Integer.parseInt(st.nextToken())-1;
            int h = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());
            ps[i]=new Person(r,c,h,w,k);
            for(int j=r;j<r+h;j++) {
                for(int g=c; g<c+w; g++){
                    loc[j][g]=i;
                }
            }
        }

        for(int i=0;i<q;i++) {
            can=true;
            st = new StringTokenizer(br.readLine());
            int idx = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            res.clear();

            move(idx,d);
            if(!can) continue;
            res.add(idx);
            //System.out.println(res);
            checkMove(d);
            checkDamage(idx);
//            for(int g=0;g<l;g++) {
//                for(int j=0;j<l;j++) {
//                    System.out.print(loc[g][j]+" ");
//                }
//                System.out.println();
//            }
//            System.out.println();
//            for(int s=1; s<ps.length;s++) {
//                if(dead[s]) continue;
//                System.out.printf("%d - %d\n",s,ps[s].dmg);
//            }
//            System.out.println();
        }

        for(int i=1; i<ps.length;i++) {
            if(dead[i]) continue;
            //System.out.printf("%d - %d\n",i,ps[i].dmg);
            total += ps[i].dmg;
        }
        System.out.println(total);
    }
    public static void checkMove(int d) {
        for(int i : res){
            int h = ps[i].h;
            int w = ps[i].w;
            int r = ps[i].r;
            int c = ps[i].c;
            for(int j=r;j<r+h;j++) {
                for(int k=c;k<c+w;k++) {
                    loc[j][k]=0;
                }
            }
        }

        for(int i : res) {
            int h = ps[i].h;
            int w = ps[i].w;
            int nr = ps[i].r+dr[d];
            int nc = ps[i].c+dc[d];
            ps[i].r=nr;
            ps[i].c=nc;
            for(int j=nr;j<nr+h;j++) {
                for(int g=nc;g<nc+w;g++) {
                    loc[j][g]=i;
                }
            }
        }
    }
    public static void move(int idx, int d) {
        if(dead[idx]) return;
        int nr=ps[idx].r+dr[d];
        int nc=ps[idx].c+dc[d];
        if(!inRange(nr,nc,ps[idx].h-1,ps[idx].w-1)||isWall(nr,nc,ps[idx].h,ps[idx].w)) {res.clear();can=false;return;}
        List<Integer> r = isPerson(nr,nc,ps[idx].h,ps[idx].w,idx);
        if(r.isEmpty()) return;
        //System.out.println(r);
        res.addAll(r);

        for(int index:r) {
            move(index,d);
        }
    }
    public static void checkDamage(int idx) {
        for(int i : res) {
            if(dead[i]||i==idx) continue;
            int r =ps[i].r;
            int c =ps[i].c;
            int h =ps[i].h;
            int w =ps[i].w;
            int cnt=0;
            for(int j=r;j<r+h;j++) {
                for(int g=c;g<c+w;g++) {
                    if(map[j][g]==1) cnt++;
                }
            }
            ps[i].dmg+=cnt;
            if (ps[i].dmg>=ps[i].k){
                dead[i]=true;
                for(int j=r;j<r+h;j++) {
                    for(int g=c;g<c+w;g++) {
                        loc[j][g]=0;
                    }
                }
            }

        }
    }
    public static boolean isWall(int r,int c,int h,int w){
        for(int i=r;i<r+h;i++){
            for(int j=c;j<c+w;j++){
                if(map[i][j]==2) {return true;}
            }
        }
        return false;
    }
    public static List<Integer> isPerson(int r, int c, int h, int w,int idx){
        Set<Integer> rs = new HashSet<>();
        for(int i=r;i<r+h;i++){
            for(int j=c;j<c+w;j++){
                if(loc[i][j]!=0&&loc[i][j]!=idx) rs.add(loc[i][j]);
            }
        }
        return new ArrayList<Integer>(rs);
    }
    public static boolean inRange(int r, int c, int h, int w){
        return r>=0&&r<l&&c>=0&&c<l&&r+h>=0&&r+h<l&&c+w>=0&&c+w<l;
    }
}
