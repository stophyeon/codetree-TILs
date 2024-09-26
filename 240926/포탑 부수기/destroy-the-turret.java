import  java.util.*;
import  java.io.*;

public class Main {
    static int n;
    static int m;
    static int k;
    static int[][] map;
    static int[][] turn;
    static int[] dr = {0,1,0,-1};
    static int[] dc = {1,0,-1,0};
    public static class Node{
        int r;
        int c;
        List<int[]> path;
        public Node(int r, int c, List<int[]> path) {
            this.r=r;
            this.c=c;
            this.path=path;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n=Integer.parseInt(st.nextToken());
        m=Integer.parseInt(st.nextToken());
        k=Integer.parseInt(st.nextToken());
        map=new int[n][m];
        turn=new int[n][m];
        for(int i=0; i<n; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<m; j++) {
                map[i][j]=Integer.parseInt(st.nextToken());
            }
        }

        for(int i=0; i<k; i++) {
            int[] w = pickWeak(i);
            int[] s = pickStrong();
            map[w[0]][w[1]]+=n+m;
            List<int[]> port=attackLazer(w,s);
            if(port.isEmpty()) attackBomb(w,s,port);
            else{
                int level = map[w[0]][w[1]];
                for(int j=0; j<port.size(); j++){
                    if(j==port.size()-1) map[port.get(j)[0]][port.get(j)[1]]-=level;
                    else{map[port.get(j)[0]][port.get(j)[1]]-=level/2;}
                }
            }
            if(check()==1){break;}
            port.add(new int[]{w[0],w[1]});
            health(port);
//            for(int[] p : path){
//                System.out.print(p[0]);
//                System.out.print(",");
//                System.out.println(p[1]);
//            }
//            for(int k=0; k<n; k++) {
//                for (int j = 0; j < m; j++) {
//                    System.out.print(map[k][j]);
//                    System.out.print(" ");
//                }
//                System.out.println();
//            }
        }
        System.out.println(pickStrong()[2]);
    }
    //공격자 선정
    public static int[] pickWeak(int t){
        List<int[]> res = new ArrayList<>();
        int min = Integer.MAX_VALUE;
        for(int i=0; i<n; i++) {
            for(int j=0; j<m; j++) {
                if(map[i][j]==0) continue;
                if(min>=map[i][j]) {
                    min=map[i][j];
                    res.add(new int[] {i,j,map[i][j]});
                }
            }
        }
        Collections.sort(res,((r1,r2)->{
            if(r1[2]==r2[2]) {
                if(turn[r1[0]][r1[1]]==turn[r2[0]][r2[1]]) {
                    if(r1[0]+r1[1]==r2[0]+r2[1]) {
                        return r2[1]-r1[1];
                    }
                    return (r2[0]+r2[1])-(r1[0]+r1[1]);
                }
                return turn[r2[0]][r2[1]]-turn[r1[0]][r1[1]];
            }
            return r1[2]-r2[2];
        }));
        turn[res.get(0)[0]][res.get(0)[1]]=t+1;
        return res.get(0);
    }
    //공격 대상 설정
    public static int[] pickStrong(){
        List<int[]> res = new ArrayList<>();
        int max = 0;
        for(int i=0; i<n; i++) {
            for(int j=0; j<m; j++) {
                if(map[i][j]==0) continue;
                if(max<=map[i][j]) {
                    max=map[i][j];
                    res.add(new int[] {i,j,map[i][j]});
                }
            }
        }
        Collections.sort(res,((r1,r2)->{
            if(r1[2]==r2[2]) {
                if(turn[r1[0]][r1[1]]==turn[r2[0]][r2[1]]) {
                    if(r1[0]+r1[1]==r2[0]+r2[1]) {
                        return r1[1]-r2[1];
                    }
                    return (r1[0]+r1[1])-(r2[0]+r2[1]);
                }
                return turn[r1[0]][r1[1]]-turn[r2[0]][r2[1]];
            }
            return r2[2]-r1[2];
        }));

        return res.get(0);
    }
    //lazer 경로 설정
    public static List<int[]> attackLazer(int[] w, int[] s){
        Queue<Node> q = new LinkedList<>();
        List<int[]> res = new ArrayList<>();
        q.add(new Node(w[0],w[1],new ArrayList<>()));
        boolean[][] visited = new boolean[n][m];
        visited[w[0]][w[1]]=true;
        while(!q.isEmpty()) {
            Node n = q.poll();
            if(n.r==s[0]&&n.c==s[1]){res=n.path;break;}
            for(int i=0; i<4; i++) {
                int nr = n.r+dr[i];
                int nc = n.c+dc[i];
                if(!inRange(nr,nc)) {
                    nr = reverse(nr,0);
                    nc = reverse(nc,1);
                }
                if(visited[nr][nc]) continue;
                if(map[nr][nc]==0) continue;
                List<int[]> p = new ArrayList<>(n.path);
                p.add(new int[]{nr,nc});
                q.add(new Node(nr,nc,p));
                visited[nr][nc]=true;

            }
        }
        return res;
    }
    public static int reverse(int r,int type) {
        if(type==0){
            if(r==-1) return n-1;
            if(r==n) return 0;
            else {
                return r;
            }
        }
        else{
            if(r==-1) return m-1;
            if(r==m) return 0;
            else {
                return r;
            }
        }
        
    }
    public static void attackBomb(int[] w, int[] s,List<int[]> port){
        int level = map[w[0]][w[1]];
        int[] dr ={-1,-1,0,1,1,1,0,-1};
        int[] dc ={0,1,1,1,0,-1,-1,-1};
        map[s[0]][s[1]]-=level;
        port.add(new int[]{s[0],s[1]});
        for(int i=0; i<8; i++){
            int nr = s[0]+dr[i];
            int nc = s[1]+dc[i];
            if(!inRange(nr,nc)){
                nr=reverse(nr,0);
                nc=reverse(nc,1);
            }
            if(map[nr][nc]==0) continue;
            if(nr==w[0]&&nc==w[1]) continue;
            map[nr][nc]-=level/2;
            port.add(new int[]{nr,nc});
        }
    }

    public static int check(){
        int count=0;
        for(int i=0; i<n; i++) {
            for(int j=0; j<m; j++) {
                if(map[i][j]==0) continue;
                if(map[i][j]<0) {
                    map[i][j]=0;
                }
                if(map[i][j]>0) count++;
            }
        }
        return count;
    }
    public static void health(List<int[]> port){
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                if(map[i][j]==0) continue;
                boolean check =true;
                for(int[] p: port) {
                    if(p[0]==i&&p[1]==j) check=false;
                }
                if(check) map[i][j]+=1;
            }
        }
    }

    public static boolean inRange(int r, int c) {
        return r>=0&&r<n&&c>=0&&c<m;
    }
}