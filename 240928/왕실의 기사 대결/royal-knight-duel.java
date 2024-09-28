import  java.util.*;
import  java.io.*;

public class Main {
    static int l;
    static int n;
    static int q;
    static int[][] map;
    static int[][] chess;
    public static class Knight{
        int r;
        int c;
        int h;
        int w;
        int k;
        int damage;
        public Knight(int r, int c, int h, int w, int k,int damage){
            this.r=r;
            this.c=c;
            this.h=h;
            this.w=w;
            this.k=k;
            this.damage=damage;
        }
    }
    static Knight[] kn;
    static int[] dr = {-1,0,1,0};
    static int[] dc = {0,1,0,-1};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        l=Integer.parseInt(st.nextToken());
        n=Integer.parseInt(st.nextToken());
        q=Integer.parseInt(st.nextToken());
        map=new int[l][l];
        chess= new int[l][l];
        kn = new Knight[n];
        for(int i=0; i<l; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<l; j++){
                map[i][j]=Integer.parseInt(st.nextToken());
            }
        }
        for(int i=0; i<n; i++){
            st = new StringTokenizer(br.readLine());
            int r=Integer.parseInt(st.nextToken())-1;
            int c=Integer.parseInt(st.nextToken())-1;
            int h=Integer.parseInt(st.nextToken());
            int w=Integer.parseInt(st.nextToken());
            int k=Integer.parseInt(st.nextToken());
            kn[i]=new Knight(r,c,h,w,k,0);
        }

        for(int i=0; i<n; i++){
            int r= kn[i].r;
            int c= kn[i].c;
            for(int j=0; j<kn[i].h; j++){
                for(int w=0; w<kn[i].w; w++){
                    chess[r+j][c+w]=i+1;
                }
            }
        }

        for(int i=0; i<q; i++){
            st = new StringTokenizer(br.readLine());
            int index=Integer.parseInt(st.nextToken())-1;
            int dir = Integer.parseInt(st.nextToken());
            List<Integer> moveKnight = new ArrayList<>();
            moveKnight.add(index+1);
            boolean poss = moveCheck(index,dir, moveKnight);
            if(poss) {
                move(moveKnight,dir);
                calDamage(moveKnight,index+1);
            }
            healthCheck();
        }

        int total=0;
        for(int i=0; i<n; i++){
            if(kn[i]==null) continue;
            total+=kn[i].damage;
        }
        System.out.println(total);
    }
    public static boolean moveCheck(int index, int dir,List<Integer> moveKnight){
        int nr = kn[index].r+dr[dir];
        int nc = kn[index].c+dc[dir];
        for(int j=0; j<kn[index].h; j++) {
            for (int w = 0; w < kn[index].w; w++) {
                if(!possible(nr+j,nc+w)) return false;
                if(chess[nr+j][nc+w]!=index+1&&chess[nr+j][nc+w]!=0){
                    if(!moveCheck(chess[nr+j][nc+w]-1,dir,moveKnight)) return false;
                    if(!moveKnight.contains(chess[nr+j][nc+w])) moveKnight.add(chess[nr+j][nc+w]);
                }
            }
        }
        return true;
    }

    public static void move(List<Integer> moveKnight,int dir){
        for(int i: moveKnight){
            for(int j=0; j<kn[i-1].h; j++){
                for(int w=0; w<kn[i-1].w; w++){
                    chess[kn[i-1].r+j][kn[i-1].c+w]=0;
                }
            }
        }
        for(int i : moveKnight){
            kn[i-1].r=kn[i-1].r+dr[dir];
            kn[i-1].c=kn[i-1].c+dc[dir];
            for(int j=0; j<kn[i-1].h; j++){
                for(int w=0; w<kn[i-1].w; w++){
                    chess[kn[i-1].r+j][kn[i-1].c+w]=i;
                }
            }
        }
    }
    public static void calDamage(List<Integer> moveKnight,int index){
        for(int i : moveKnight){
            if(i==index) continue;
            for(int j=0; j<kn[i-1].h; j++){
                for(int w=0; w<kn[i-1].w; w++){
                    if(map[kn[i-1].r+j][kn[i-1].c+w]==1){
                        kn[i-1].k-=1;
                        kn[i-1].damage+=1;
                    }
                }
            }
        }
    }
    public static void healthCheck(){
        for(int i=0; i<n; i++){
            if(kn[i]==null) continue;
            if(kn[i].k<=0){
                for(int j=0; j<kn[i].h; j++){
                    for(int w=0; w<kn[i].w; w++){
                        chess[kn[i].r+j][kn[i].c+w]=0;
                    }
                }
                kn[i]=null;
            }
        }
    }
    public static boolean possible(int r, int c){
        if(!inRange(r,c)) return false;
        return map[r][c] != 2;
    }

    public static boolean inRange(int r, int c){
        return r>=0&&r<l&&c>=0&&c<l;
    }
}