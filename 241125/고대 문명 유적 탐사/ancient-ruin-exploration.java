

import  java.util.*;
import  java.io.*;

public class Main {
    public static class Node{
        int r;
        int c;
        int n;

        public Node(int r, int c, int n){
            this.r=r;
            this.c=c;
            this.n=n;

        }
    }
    public static class Rotate{
        int r;
        int c;
        int a;
        int cnt;

        public Rotate(int r, int c, int a, int cnt){
            this.r=r;
            this.c=c;
            this.a=a;
            this.cnt=cnt;
        }
    }
    static int[][] map = new int[5][5];
    static List<Integer> refill = new ArrayList<>();

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int k = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        for(int i=0; i<5; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<5; j++){
                map[i][j]=Integer.parseInt(st.nextToken());
            }
        }
        st = new StringTokenizer(br.readLine());
        for(int i=0; i<m; i++){
            refill.add(Integer.parseInt(st.nextToken()));
        }


        int idx=0;
        for(int i=0; i<k; i++){
            int answer=0;
            //회전 위치, 각도 선택
            Rotate r = chooseRotate();
            if(r.cnt==0) break;
            RotateMap(r.r,r.c,r.a);
            while(true){
                List<int[]> del = remove();
                if(del.isEmpty()) break;
                answer+=del.size();
                //System.out.println(answer);
                del.sort((d1,d2)->{
                    if(d1[1]==d2[1]){
                        return d2[0]-d1[0];
                    }
                    return d1[1]-d2[1];
                });
                for(int[] d : del){
                    map[d[0]][d[1]]=refill.get(idx);
                    idx++;
                }
//                for(int h=0; h<5; h++){
//                    for(int j=0; j<5; j++){
//                        System.out.printf("%d ",map[h][j]);
//                    }
//                    System.out.println();
//                }
            }
            System.out.print(answer);
            System.out.print(" ");
        }
    }

    public static List<int[]> remove(){
        int[] dr = {-1,1,0,0};
        int[] dc = {0,0,-1,1};
        List<int[]> del = new ArrayList<>();
        boolean[][] visited = new boolean[5][5];
        Queue<Node> q = new LinkedList<>();
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                if(visited[i][j]) continue;
                q.add(new Node(i,j,map[i][j]));
                visited[i][j]=true;
                List<int[]> tr = new ArrayList<>();
                tr.add(new int[]{i,j});
                while(!q.isEmpty()){
                    Node n = q.poll();
                    for(int h=0; h<4; h++){
                        int nr = n.r+dr[h];
                        int nc = n.c+dc[h];
                        if (!inRange(nr,nc)||visited[nr][nc]) continue;
                        if (n.n!=map[nr][nc]) continue;
                        q.add(new Node(nr,nc,n.n));
                        visited[nr][nc]=true;
                        tr.add(new int[]{nr,nc});
                    }
                }
                if(tr.size()>=3) del.addAll(tr);
            }
        }
        return del;
    }
    public static void RotateMap(int r, int c, int a){
        int[][] turn = new int[5][5];
        int[][] m = new int[5][5];
        for(int i=0; i<5; i++){
            turn[i]=map[i].clone();
        }
        while(a!=0){
            for(int i=0; i<5; i++){
                m[i]=turn[i].clone();
            }
            for(int i=0; i<3; i++){
                for(int j=0; j<3; j++){
                    turn[r+j][c+2-i]=m[r+i][c+j];
                }
            }
            a--;
        }
        map=turn;
    }
    public static Rotate chooseRotate(){
        PriorityQueue<Rotate> pq = new PriorityQueue<>((r1,r2)->{
            if(r1.cnt==r2.cnt){
                if (r1.a==r2.a){
                    if(r1.c==r2.c){
                        return r1.r-r2.r;
                    }
                    return r1.c-r2.c;
                }
                return r1.a-r2.a;
            }
            return r2.cnt-r1.cnt;
        });

        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                for(int a=1; a<4; a++){
                    pq.add(new Rotate(i,j,a,rotate(i,j,a)));
                }
            }
        }
        return pq.poll();
    }

    public static int rotate(int r, int c, int a){
        int[][] turn = new int[5][5];
        int[][] m = new int[5][5];
        for(int i=0; i<5; i++){
            turn[i]=map[i].clone();
        }
        while(a!=0){
            for(int i=0; i<5; i++){
                m[i]=turn[i].clone();
            }
            for(int i=0; i<3; i++){
                for(int j=0; j<3; j++){
                    turn[r+j][c+2-i]=m[r+i][c+j];
                }
            }
            a--;
        }
        return check(turn);
    }

    public static int check(int[][] m){
        int[] dr = {-1,1,0,0};
        int[] dc = {0,0,-1,1};
        int total=0;
        boolean[][] visited = new boolean[5][5];
        Queue<Node> q = new LinkedList<>();
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                if(visited[i][j]) continue;
                q.add(new Node(i,j,m[i][j]));
                visited[i][j]=true;
                int cnt=1;
                while(!q.isEmpty()){
                    Node n = q.poll();
                    for(int h=0; h<4; h++){
                        int nr = n.r+dr[h];
                        int nc = n.c+dc[h];
                        if (!inRange(nr,nc)||visited[nr][nc]) continue;
                        if (n.n!=m[nr][nc]) continue;
                        q.add(new Node(nr,nc,n.n));
                        visited[nr][nc]=true;
                        cnt++;
                    }
                }
                if(cnt>=3) total+=cnt;
            }
        }
        return total;
    }
    public static boolean inRange(int r, int c){
        return r>=0&&r<5&&c>=0&&c<5;
    }
}






