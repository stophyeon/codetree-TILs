import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static class Person{
        int r;
        int c;
        public Person(int r, int c) {
            this.r=r;
            this.c=c;
        }
    }
    public static class Node{
        int r;
        int c;
        int d;
        public Node(int r, int c,int d) {
            this.r=r;
            this.c=c;
            this.d=d;
        }
    }
    static int n;
    static int[][] base;
    static int[][] loc;
    static HashMap<Integer,Person> person = new HashMap<>();
    static List<int[]> store=new ArrayList<>();
    static List<int[]> bc=new ArrayList<>();
    static boolean[] arrive;
    static boolean[][] useBc;
    static int[] dr = {-1,0,0,1};
    static int[] dc = {0,-1,1,0};
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        base=new int[n][n];
        loc=new int[n][n];
        arrive = new boolean[m+1];

        for(int i=0; i<n; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<n; j++) {
                base[i][j]=Integer.parseInt(st.nextToken());
                if(base[i][j]==1) bc.add(new int[] {i,j});
            }
        }
        useBc = new boolean[n][n];
        for(int i=0; i<m; i++) {
            st = new StringTokenizer(br.readLine());
            int r=Integer.parseInt(st.nextToken())-1;
            int c=Integer.parseInt(st.nextToken())-1;
            loc[r][c]=i+1;
            store.add(new int[] {r,c});
        }
        int cnt=0;
        int time=1;
//        for(int[] e : bc){
//            System.out.printf("%d,%d\n", e[0],e[1]);
//        }
        while(cnt!=m) {
            //격자안 사람이동

//            for(int[] k : bc){
//                System.out.printf("%d,%d -> %s\n",k[0],k[1],useBc[k[0]][k[1]]);
//            }
//            System.out.println();
            for(int k : person.keySet()) {
                if(arrive[k]) continue;
                Person p = person.get(k);
                //System.out.printf("%d,%d\n",store.get(k-1)[0],store.get(k-1)[1]);
                move(p,store.get(k-1));

            }
            //도착시 처리

            for(int k : person.keySet()) {
                if(arrive[k]) continue;
                Person p = person.get(k);
                if(loc[p.r][p.c]==k) {
                    loc[p.r][p.c] = -1;
                    //store.remove(k - 1);
                    arrive[k] = true;
                    cnt++;
                }
            }
            //base camp선정
            if(time<=m) {
                int[] s=store.get(time-1);
                int min=Integer.MAX_VALUE;
                int minR=n;
                int minC=n;
                //System.out.printf("%d : %d,%d\n",time,s[0],s[1]);
                for(int i=0; i<bc.size(); i++) {
                    if(useBc[bc.get(i)[0]][bc.get(i)[1]]) continue;
                    //System.out.printf("%d,%d -> %d,%d\n",s[0],s[1],bc.get(i)[0],bc.get(i)[1]);
                    int[] k=selectBC(s[0],s[1],bc.get(i)[0],bc.get(i)[1]);
                    if(k[2]==0) continue;
                    //System.out.printf("%d,%d -> %d\n",k[0],k[1],k[2]);
                    if(k[2]<min) {
                        min=k[2];
                        minR=k[0];
                        minC=k[1];
                    }
                    if(k[2]==min) {
                        if(k[0]<minR) {
                            minR=k[0];
                            minC=k[1];
                        }
                        if(k[0]==minR) {
                            if(k[1]<minC) {
                                minC=k[1];
                            }
                        }
                    }
                }
                //System.out.printf("%d : %d, %d\n",time,minR,minC);
                //System.out.printf("%d,%d\n",minR,minC);
                useBc[minR][minC]=true;
                person.put(time, new Person(minR,minC));
                base[minR][minC]=-1;
            }
            time++;
        }
        System.out.println(time-1);
    }
    public static void move(Person p,int[] e) {
        int d=-1;
        boolean[][] visited = new boolean[n][n];
        Queue<Node> q = new LinkedList<>();
        boolean next=true;
        q.add(new Node(p.r,p.c,0));
        visited[p.r][p.c]=true;
        while(!q.isEmpty()) {
            Node nd = q.poll();
            if(nd.r==e[0]&&nd.c==e[1]) {
                d=nd.d;
                break;
            }
            for(int i=0; i<4; i++) {
                int nr=nd.r+dr[i];
                int nc=nd.c+dc[i];
                if(!inRange(nr,nc)||visited[nr][nc]) continue;
                if(base[nr][nc]==-1||loc[nr][nc]==-1) continue;
                if(next) {q.add(new Node(nr,nc,i));}
                else{q.add(new Node(nr,nc,nd.d));}
                visited[nr][nc]=true;
            }
            next=false;
        }
        p.r=p.r+dr[d];
        p.c=p.c+dc[d];

    }
    public static int[] selectBC(int r, int c,int er, int ec) {
        int[] res = new int[3];
        boolean[][] visited = new boolean[n][n];
        Queue<Node> q = new LinkedList<>();
        q.add(new Node(r,c,0));
        visited[r][c]=true;
        while(!q.isEmpty()) {
            Node nd = q.poll();
            if(nd.r==er&&nd.c==ec) {
                res= new int[] {nd.r,nd.c,nd.d};
                break;
            }
            for(int i=0; i<4; i++) {
                int nr=nd.r+dr[i];
                int nc=nd.c+dc[i];
                if(!inRange(nr,nc)||visited[nr][nc]) continue;
                if(base[nr][nc]==-1||loc[nr][nc]==-1) continue;
                q.add(new Node(nr,nc,nd.d+1));
                visited[nr][nc]=true;
            }
        }
        return res;
    }
    public static boolean inRange(int r, int c) {
        return r>=0&& r<n&&c>=0&&c<n;
    }

}
