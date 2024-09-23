import  java.util.*;
import  java.io.*;

public class Main {
    static int n;
    static int m;
    static int k;
    static PriorityQueue<Integer>[][] gun;
    static int[][] map;
    static int[] score;
    static Player[] player;
    static int[] dr = {-1,0,1,0};
    static int[] dc = {0,1,0,-1};

    public static class Player{
        int r;
        int c;
        int d;
        int s;
        int w;
        public Player( int r,int c,int w,int d, int s){
            this.r=r;
            this.c=c;
            this.w=w;
            this.d=d;
            this.s=s;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        gun = new PriorityQueue[n][n];
        map = new int[n][n];
        score = new int[m];
        player = new Player[m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
                pq.add(Integer.parseInt(st.nextToken()));
                gun[i][j] = pq;
            }
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken())-1;
            int c = Integer.parseInt(st.nextToken())-1;
            int d = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            map[r][c] = i+1;
            player[i] = new Player(r,c,0,d,s);
        }
        
        for(int i=0; i<k; i++){
            move();
            //for(int j=0; j<n; j++){
            //    for(int l=0; l<n; l++){
            //        System.out.print(map[j][l]);
            //        System.out.print(" ");
            //    }
            //    System.out.println();
            //}
        }

        //for(int i=0; i<m; i++){
        //    System.out.print(player[i].r);
//            System.out.print(",");
//            System.out.print(player[i].c);
//            System.out.print(",");
//            System.out.print(player[i].w);
//            System.out.print(",");
//            System.out.println(player[i].d);
//        }


        for(int i=0; i<m; i++){
            //System.out.println(player[i].w);
            System.out.print(score[i]);
            System.out.print(" ");
        }
    }

    public static void move(){
        for(int i=0; i<m; i++){
            moveOne(player[i],i);
        }
    }
    public static void moveOne(Player p,int index){
        int dir = p.d;
        int row = p.r+dr[dir];
        int col = p.c+dc[dir];
        if(!inRange(row,col)){
            dir = (p.d+2)%4;
            row=p.r+dr[dir];
            col=p.c+dc[dir];
            p.d=dir;
        }
        int g=0;
        map[p.r][p.c]=0;
        //총 갈기

        if(map[row][col]==0){
            changeGun(row,col,p);
            p.r=row;
            p.c=col;
            map[row][col]=index+1;
            return;
        }
        //player
        if(map[row][col]!=0){
            int s1 = player[map[row][col]-1].w+player[map[row][col]-1].s;
            int s2 = p.w+p.s;
            p.r=row;
            p.c=col;
            if(s1>s2){
                score[map[row][col]-1]+=s1-s2;
                loser(p,index,row,col);
                changeGun(row,col,player[map[row][col]-1]);

            }
            else if(s1<s2){
                score[index]+=s2-s1;
                loser(player[map[row][col]-1],map[row][col]-1,p.r,p.c);
                changeGun(row,col,p);
                map[row][col]=index+1;
            }
            else{
                if(player[map[row][col]-1].s>p.s){
                    loser(p,index,row,col);
                    changeGun(row,col,player[map[row][col]-1]);
                }
                else{
                    loser(player[map[row][col]-1],map[row][col]-1,p.r,p.c);
                    changeGun(row,col,p);
                    map[row][col]=index+1;
                }
            }
        }

    }

    public static void changeGun(int row, int col,Player p){
        int g=0;
        if(!gun[row][col].isEmpty()){
            g= gun[row][col].poll();
        }
        if(g>p.w){
            int temp = g;
            g = p.w;
            p.w=temp;
        }
        gun[row][col].add(g);
    }

    public static void loser(Player p,int index, int r, int c){
        //총 놓기
        gun[r][c].add(p.w);
        p.w=0;
        //오른쪽 90도 회전후 이동
        int dir = p.d;
        int row = r+dr[dir];
        int col = c+dc[dir];
        for(int i=0; i<4; i++){
            if(inRange(row,col)){
               if(map[row][col]==0){
                   changeGun(row,col,p);
                   p.r=row;
                   p.c=col;
                   p.d=dir;
                   map[row][col]=index+1;
                   break;
               }
            }
            dir = (dir+1)%4;
            row=r+dr[dir];
            col=c+dc[dir];

        }
    }

    public static boolean inRange(int r, int c){
        return r>=0&&r<n&&c>=0&&c<n;
    }
}