import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static class Node{
        int r;
        int c;
        List<Integer> p = new ArrayList<>();
        public Node(int r, int c) {
            this.r = r;
            this.c = c;
        }
        public void addPath(Node n,int num){
            this.p.addAll(n.p);
            this.p.add(num);
        }
    }
    public static class Pts{
        int idx;
        int r;
        int c;
        int turn;
        int s;
        public Pts(int idx,int r, int c, int turn, int s) {
            this.r = r;
            this.c = c;
            this.turn = turn;
            this.s = s;
            this.idx = idx;
        }
    }

    static int n;
    static int m;
    static int[][] map;
    static int[] dr = {0,1,0,-1};
    static int[] dc = {1,0,-1,0};
    static HashMap<Integer, Pts> maps = new HashMap<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m=Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        map = new int[n][m];
        int num=1;
        for(int i=0;i<n;i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<m;j++) {
                int a=Integer.parseInt(st.nextToken());
                if(a!=0){
                    maps.put(num,new Pts(num,i,j,0,a));
                    map[i][j] = num;
                    num++;
                }
            }
        }

        int attack = n+m;
        for(int i=1;i<=k;i++) {
            //공격자 선정
            if(maps.size()<=1) break;
            Pts wp=maps.get(chooseWeak());
            wp.turn=i;
            //강한 포탑 선정
            Pts sp=maps.get(chooseStrong());
            wp.s+=attack;
            //레이저
            List<Integer> pts = razer(wp.r,wp.c,sp.r,sp.c);
            //포탄
            if (pts.isEmpty()){
                pts=attack(wp,sp);
            }

            for(int idx : pts) {
                Pts p=maps.get(idx);
                if(p.idx== sp.idx) p.s-=wp.s;
                else {p.s-=wp.s/2;}
                if(p.s<=0){
                    map[p.r][p.c]=0;
                    maps.remove(idx);
                }
            }

            //정비
            for(int key : maps.keySet()) {
                if(key==wp.idx||pts.contains(key)) continue;
                maps.get(key).s+=1;
            }


        }
        System.out.println(maps.get(chooseStrong()).s);
    }

    public static List<Integer> attack(Pts wp, Pts sp){
        List<Integer> path = new ArrayList<>();
        int[] ndr={-1,-1,-1,0,1,1,1,0};
        int[] ndc={-1,0,1,1,1,0,-1,-1};

        for(int i=0;i<8;i++) {
            int nr=sp.r+ndr[i];
            int nc=sp.c+ndc[i];
            if(!inRange(nr,nc)){
                nr=changeR(nr);
                nc=changeC(nc);
            }
            if(map[nr][nc]==wp.idx) continue;
            if(map[nr][nc]!=0){
                path.add(map[nr][nc]);
            }
        }
        path.add(sp.idx);

        return path;
    }
    public static List<Integer> razer(int sr, int sc, int er, int ec){
        List<Integer> path = new ArrayList<>();
        Queue<Node> q = new LinkedList<>();
        boolean[][] visited = new boolean[n][m];
        q.add(new Node(sr,sc));
        visited[sr][sc] = true;
        while(!q.isEmpty()){
            Node nd = q.poll();
            if (nd.r==er&&nd.c==ec){
                path=nd.p;
                break;
            }
            for (int i=0; i<4; i++){
                int nr=nd.r+dr[i];
                int nc=nd.c+dc[i];
                if (!inRange(nr,nc)){
                    nr = changeR(nr);
                    nc = changeC(nc);
                }
                if(visited[nr][nc]||map[nr][nc]==0) continue;
                Node nn=new Node(nr,nc);
                nn.addPath(nd,map[nr][nc]);
                q.add(nn);
                visited[nr][nc] = true;
            }
        }
        return path;
    }
    public static int chooseStrong(){
        List<Integer> w = new ArrayList<>(maps.keySet());
        w.sort((n1,n2)->{
            Pts p1=maps.get(n1);
            Pts p2=maps.get(n2);
            if (p1.s==p2.s){
                if (p1.turn==p2.turn){
                    if (p1.r+p1.c==p2.r+p2.c){
                        return p1.c-p2.c;
                    }
                    return (p1.r+p1.c)-(p2.r+p2.c);
                }
                return p1.turn-p2.turn;
            }
            return p2.s-p1.s;
        });

        return w.get(0);
    }
    public static int chooseWeak(){
        List<Integer> s = new ArrayList<>(maps.keySet());
        s.sort((n1,n2)->{
            Pts p1=maps.get(n1);
            Pts p2=maps.get(n2);
            if (p1.s==p2.s){
                if (p1.turn==p2.turn){
                    if (p1.r+p1.c==p2.r+p2.c){
                        return p2.c-p1.c;
                    }
                    return (p2.r+p2.c)-(p1.r+p1.c);
                }
                return p2.turn-p1.turn;
            }
            return p1.s-p2.s;
        });
        return s.get(0);
    }
    public static int changeR(int r){
        if (r==-1) return n-1;
        else if(r==n) return 0;
        else return r;
    }
    public static int changeC(int c){
        if (c==-1) return m-1;
        else if(c==m) return 0;
        else return c;
    }
    public static boolean inRange(int x, int y) {
        return x>=0 && x<n && y>=0 && y<m;
    }
}
