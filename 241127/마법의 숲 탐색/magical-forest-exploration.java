import  java.util.*;
import  java.io.*;

public class Main {
    public static class Fairy{
        int r;
        int c;
        int id;
        public Fairy(int r, int c, int id){
            this.r=r;
            this.c=c;
            this.id=id;
        }
    }
    public static class Golem{
        int r=0;
        int c;
        int d;
        public Golem(int c,int d){
            this.c=c;
            this.d=d;
        }
    }
    static int R;
    static int C;
    static int k;
    static int[] dr = {-1,0,1,0};
    static int[] dc = {0,1,0,-1};
    static Golem[] gs;
    static int answer=0;
    static int[][] map;
    static boolean[][] escape;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        R=Integer.parseInt(st.nextToken());
        C=Integer.parseInt(st.nextToken());
        k=Integer.parseInt(st.nextToken());
        gs=new Golem[k];
        map=new int[R+1][C+1];
        escape=new boolean[R+1][C+1];
        for(int i=0; i<k; i++){
            st = new StringTokenizer(br.readLine());
            gs[i]=new Golem(Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()));
        }
        int idx=1;
        for(Golem g : gs){
            boolean end = false;
            while(!end){
               end=moveGolem(g);
            }
            if(!check(g)){
                reset();
                for(int i=0; i<=R; i++){
                    Arrays.fill(escape[i],false);
                }
                idx++;
                System.out.println();
                continue;
            }
            map[g.r][g.c]=idx;
            for(int i=0; i<4; i++){
                if(i==g.d) escape[g.r+dr[i]][g.c+dc[i]]=true;
                map[g.r+dr[i]][g.c+dc[i]]=idx;
            }
            //for(int i=0; i<=R; i++){
            //    for(int j=0; j<=C; j++){
            //        if(escape[i][j]) System.out.printf("%d,%d\n",i,j);
            //    }
            //}
            //for(int i=1; i<=R; i++){
            //    for(int j=1; j<=C; j++){
            //        System.out.printf("%d ",map[i][j]);
            //    }
            //    System.out.println();
            //}
            //System.out.println();
            answer+=move(g.r,g.c,idx);
            idx++;
        }
        
        System.out.println(answer);
    }
    public static boolean check(Golem g){
        return inRange(g.r,g.c)&&inRange(g.r+1,g.c)&&inRange(g.r-1,g.c)&&inRange(g.r,g.c+1)&&inRange(g.r,g.c-1);
    }

    public static boolean moveGolem(Golem g){
        if(checkUnder(g.r,g.c)){
            g.r+=1;
            return false;
        }
        else {
            if(checkLeft(g.r,g.c)&&checkUnder(g.r,g.c-1)){
                g.r+=1;
                g.c-=1;
                g.d=(g.d+3)%4;
                return false;
            }
            else{
                if (checkRight(g.r,g.c)&&checkUnder(g.r,g.c+1)){
                    g.r+=1;
                    g.c+=1;
                    g.d=(g.d+1)%4;
                    return false;
                }
            }
            return true;
        }
    }
    public static boolean checkUnder(int r, int c){
        //System.out.printf("D- %d,%d\n",r,c);
        if(!(inRange(r+1,c-1)&&inRange(r+1,c+1)&&inRange(r+2,c))) return false;
        return map[r + 1][c - 1] == 0 && map[r + 1][c + 1] == 0 && map[r + 2][c] == 0;
    }

    public static boolean checkLeft(int r, int c){
        //System.out.printf("L- %d,%d\n",r,c);
        if(r==0&&c>2){
            return map[r+1][c]==0&&map[r+1][c-1]==0&&map[r+1][c-2]==0&&map[r+2][c-1]==0;
        }
        else if(r==1){
            if(!(inRange(r+1,c-1)&&inRange(r,c-2))) return false;
        }
        else{if(!(inRange(r+1,c-1)&&inRange(r-1,c-1)&&inRange(r,c-2))) return false;}
        return map[r + 1][c - 1] == 0 && map[r - 1][c - 1] == 0 && map[r][c - 2] == 0;
    }

    public static boolean checkRight(int r, int c){
        //System.out.printf("R- %d,%d\n",r,c);
        if(r==0&&c<C-1){
            return map[r+1][c]==0&&map[r+1][c+1]==0&&map[r+1][c+2]==0&&map[r+2][c+1]==0;
        }
        if(r==1){
            if(!(inRange(r+1,c+1)&&inRange(r,c+2))) return false;
        }
        if(!(inRange(r+1,c+1)&&inRange(r-1,c+1)&&inRange(r,c+2))) return false;
        return map[r + 1][c + 1] == 0 && map[r - 1][c + 1] == 0 && map[r][c + 2] == 0;
    }

    public static int move(int r, int c, int id){
        int max=0;
        boolean[][] visited = new boolean[R+1][C+1];
        Queue<Fairy> q= new LinkedList<>();
        q.add(new Fairy(r,c,id));
        visited[r][c]=true;
        while(!q.isEmpty()){
            Fairy f = q.poll();
            if(max<f.r) max=f.r;
            for(int i=0; i<4; i++){
                int nr = f.r+dr[i];
                int nc = f.c+dc[i];
                if(!inRange(nr,nc)||visited[nr][nc]||map[nr][nc]==0) continue;
                if(map[nr][nc]!=f.id){
                    if(escape[f.r][f.c]){
                        q.add(new Fairy(nr,nc,map[nr][nc]));
                        visited[nr][nc]=true;
                    }
                }
                else{
                    q.add(new Fairy(nr,nc,f.id));
                    visited[nr][nc]=true;
                }
            }
        }
        //System.out.println(max);
        return max;
    }

    public static void reset(){
        for (int i=0; i<=R; i++){
            Arrays.fill(map[i],0);
        }
    }

    public static boolean inRange(int r, int c){
        return r>0 && r<=R && c>0 && c<=C;
    }
}













