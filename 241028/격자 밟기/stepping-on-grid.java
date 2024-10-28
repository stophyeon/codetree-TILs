import  java.util.*;
import  java.io.*;

public class Main {
    static int[][] map =new int[5][5];
    static int[] dr= {-1,1,0,0};
    static int[] dc= {0,0,-1,1};
    static int k=0;
    static int answer=0;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        k = Integer.parseInt(st.nextToken());
        for(int i=0; i<k; i++){
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken())-1;
            int c = Integer.parseInt(st.nextToken())-1;
            map[r][c]=1;
        }
        boolean[][] visited = new boolean[5][5];
        visited[0][0]=true;
        visited[4][4]=true;
        dfsA(visited,0,0,4,4,2);
        dfsB(visited,0,0,4,4,2);
        System.out.println(answer/2);
    }
    public static void dfsA(boolean[][] visited,int r1, int c1,int r2, int c2, int cnt){
        //System.out.printf("A : %d,%d B: %d,%d ",r1,c1,r2,c2);
        //System.out.println(cnt);
        for(int i=0; i<4; i++){
            int nr = r1+dr[i];
            int nc = c1+dc[i];
            if(!inRange(nr,nc)) continue;
            if(map[nr][nc]==1) continue;
            if(visited[nr][nc]){
                if(r2==nr && c2==nc){
                    if(cnt==25-k) answer++;
                    return;
                }
                else{continue;}
            }
            visited[nr][nc]=true;
            dfsB(visited,nr,nc,r2,c2,cnt+1);
            visited[nr][nc]=false;
        }
    }
    public static void dfsB(boolean[][] visited,int r1, int c1,int r2, int c2, int cnt){
        //System.out.printf("A : %d,%d B: %d,%d ",r1,c1,r2,c2);
        //System.out.println(cnt);
        for(int i=0; i<4; i++){
            int nr = r2+dr[i];
            int nc = c2+dc[i];
            if(!inRange(nr,nc)) continue;
            if(map[nr][nc]==1) continue;
            if(visited[nr][nc]){
                if(r1==nr && c1==nc){
                    if(cnt==25-k) answer++;
                    return;
                }
                else{continue;}
            }
            visited[nr][nc]=true;
            dfsA(visited,r1,c1,nr,nc,cnt+1);
            visited[nr][nc]=false;
        }
    }
    public static boolean inRange(int r, int c){
        return r>=0 && r<5&&c>=0 && c<5;
    }
}