import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static class Player {
        int r;
        int c;
        int s;
        int d;
        int gun;
        public Player(int r, int c, int d, int s,int gun) {
            this.r = r;
            this.c = c;
            this.s = s;
            this.d = d;
            this.gun=gun;
        }
        public int score(){
            return this.s+this.gun;
        }
    }
    static int[] point;
    static int n;
    static PriorityQueue<Integer>[][] map;
    static int[][] loc;
    static Player[] players;
    static int[] dr={-1,0,1,0};
    static int[] dc={0,1,0,-1};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        map=new PriorityQueue[n+1][n+1];
        loc=new int [n+1][n+1];
        point = new int[m+1];
        players = new Player[m+1];
        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= n; j++) {
                int gun =Integer.parseInt(st.nextToken());
                map[i][j] = new PriorityQueue<>((g1,g2)->g2-g1);
                map[i][j].add(gun);
            }
        }
        for (int i = 1; i <= m; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            players[i]=new Player(r,c,d,s,0);
            loc[r][c]=i;
        }
//        for (int r = 1; r <= n; r++) {
//            for (int  c= 1; c <= n; c++) {
//                System.out.print(loc[r][c]+" ");
//            }
//            System.out.println();
//        }
//        System.out.println();

        for (int i = 0; i < k; i++) {
        	
            for(int j=1; j<=m; j++){
                move(j,players[j]);
            }
            
            
            
        }

        for (int i = 1; i <= m; i++) {
            System.out.print(point[i]+" ");
        }
    }
    public static void move(int num,Player p) {
        int nr = p.r+dr[p.d];
        int nc = p.c+dc[p.d];
        if (!inRange(nr,nc)){
            p.d=(p.d+2)%4;
            nr = p.r+dr[p.d];
            nc = p.c+dc[p.d];
        }
        loc[p.r][p.c]=0;
        if(loc[nr][nc]!=0) fight(num,loc[nr][nc],nr,nc);
        else{
            getGun(p,nr,nc);
            loc[p.r][p.c]=0;
            loc[nr][nc]=num;
            p.r=nr;
            p.c=nc;
        }
    }

    public static void getGun(Player p,int r, int c){
        if (map[r][c].isEmpty()) return;
        int gun = map[r][c].poll();
        if (p.gun<gun){
            int temp = p.gun;
            p.gun=gun;
            map[r][c].add(temp);
        }
        else{map[r][c].add(gun);}
    }

    public static void fight(int n1, int n2,int r, int c) {

        //p1은 아직 이동하지 않은 상태
        Player p1 = players[n1];
        Player p2 = players[n2];
        //System.out.printf("%d: %d vs %d: %d\n",n1,p1.score(),n2,p2.score());
        //이동한 사람이 이김
        if (p1.score()>p2.score()){
            point[n1]+=p1.score()-p2.score();
            
            lose(p2,r,c,n2);
            win(p1,r,c,n1);
        }
        //원래 있던 사람이 이김
        else if (p1.score()<p2.score()){
            point[n2] += p2.score()-p1.score();
            lose(p1,r,c,n1);
            win(p2,r,c,n2);
        }
        else{
            //이동한 사람이 이김
            if(p1.s>p2.s){
                
                lose(p2,r,c,n2);
                win(p1,r,c,n1);
            }
            //원래 있던 사람이 이김
            else{
                lose(p1,r,c,n1);
                win(p2,r,c,n2);
            }
        }
    }
    public static void win(Player p,int r, int c,int num){
        getGun(p,r,c);
        
        loc[r][c]=num;
        p.r=r;
        p.c=c;
    }
    public static void lose(Player p, int r, int c,int num){
    	map[r][c].add(p.gun);
        p.gun=0;
        int dir = p.d;
        
        for (int i=0; i<4; i++){
            int nr = r+dr[dir];
            int nc = c+dc[dir];
            if(!inRange(nr,nc)||loc[nr][nc]!=0){
                dir=(dir+1)%4;
                continue;
            }
            getGun(p,nr,nc);
            
            loc[nr][nc]=num;
            p.r=nr;
            p.c=nc;
            p.d=dir;
            break;
        }
    }
    public static boolean inRange(int r, int c){
        return r>0&&r<=n&&c>0&&c<=n;
    }
}