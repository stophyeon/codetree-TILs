import  java.util.*;
import  java.io.*;

public class Main {
    static int n;
    static int m;
    static int[][] map;
    static int[][] store;
    static List<int[]> bc;
    static int arrive=0;
    static int total;
    static int[] dr = {-1,0,0,1};
    static int[] dc = {0,-1,1,0};
    static Person[] persons;
    public static class Person{
        int r;
        int c;
        int dis;
        public Person(int r, int c,int dis){
            this.r=r;
            this.c=c;
            this.dis=dis;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n=Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        map = new int[n][n];
        store = new int[m][2];
        bc = new ArrayList<>();
        persons = new Person[m];
        
        for(int i=0; i<n; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<n; j++){
                map[i][j]=Integer.parseInt(st.nextToken());
                if(map[i][j]==1){
                    bc.add(new int[]{i,j});
                }
            }
        }
        for(int i=0; i<m; i++){
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            store[i][0]=r-1;
            store[i][1]=c-1;
        }
        while(arrive!=m){
            total++;
            //최단거리로 1칸 이동
            move();
            //도착 확인
            isArrive();
            //bc 찾기
            if(total<=m){
                int[] loc=findBC(total);
                persons[total-1] = new Person(loc[0],loc[1],loc[2]);
                map[loc[0]][loc[1]]=-1;
            }
//            System.out.println(total);
//            for(int i=0; i<m; i++) {
//            	if(persons[i]==null) continue;
//            	System.out.print(i+1);
//            	System.out.print(":");
//            	System.out.print(persons[i].r);
//            	System.out.print(",");
//            	System.out.print(persons[i].c);
//            	System.out.print(",");
//            	System.out.println(persons[i].dis);
//            }
        }
        System.out.println(total);
    }

    //최단거리 이동
    public static void move(){
        for(int i=0; i< persons.length; i++){
            if(persons[i]==null) continue;
            for(int j=0; j<4; j++){
                int nr = persons[i].r+dr[j];
                int nc = persons[i].c+dc[j];
                if(!inRange(nr,nc)) continue;
                if(map[nr][nc]==-1) continue;
                int d = Math.abs(nr-store[i][0])+Math.abs(nc-store[i][1]);
                if(persons[i].dis>d){
                    persons[i].r=nr;
                    persons[i].c=nc;
                    persons[i].dis=d;
                }
            }
        }
    }
    //도착 여부 확인
    public static void isArrive(){
        for(int i=0; i< persons.length; i++){
            if(persons[i]==null) continue;
            if(persons[i].r==store[i][0] && persons[i].c==store[i][1]){
                map[store[i][0]][store[i][1]]=-1;
                persons[i]=null;
                arrive++;
            }
        }
    }
    //최단거리 베이스 캠프
    public static int[] findBC(int num){
        int min = Integer.MAX_VALUE;
        int[] mLoc = new int[3];
        for(int[] camp : bc){
        	if(map[camp[0]][camp[1]]==-1) continue;
            int dis = Math.abs(camp[0]-store[num-1][0])+Math.abs(camp[1]-store[num-1][1]);
            if(min>dis){
                min=dis;
                mLoc = new int[]{camp[0],camp[1],dis};
            }
            if(min==dis){
                if(camp[0]<mLoc[0]) mLoc = new int[]{camp[0],camp[1],dis};
                if(camp[0]==mLoc[0]){
                    if(camp[1]<mLoc[1]) mLoc = new int[]{camp[0],camp[1],dis};
                }
            }
        }
        return mLoc;
    }


    public static boolean inRange(int r, int c){
        return r>=0&&r<n&&c>=0&&c<n;
    }
}