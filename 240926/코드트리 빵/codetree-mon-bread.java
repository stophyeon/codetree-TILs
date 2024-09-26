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
    static int[][] visit;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n=Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        map = new int[n][n];
        store = new int[m][2];
        bc = new ArrayList<>();
        persons = new Person[m];
        visit=new int[n][n];
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
            isArrive(total);
            //bc 찾기
            if(total<=m){
                persons[total-1] = findBC(total);
//                System.out.print(persons[total-1].r);
//                System.out.print(",");
//                System.out.println(persons[total-1].c);
                map[persons[total-1].r][persons[total-1].c]=-1;
            }

            for(int i=0; i<persons.length; i++){
                if(persons[i]==null) continue;
                visit[persons[i].r][persons[i].c]=i+1;
            }
//            System.out.println(total);
//            for(int i=0; i<m; i++) {
//                if(persons[i]==null) continue;
//                System.out.print(i+1);
//                System.out.print(":");
//                System.out.print(persons[i].r);
//                System.out.print(",");
//                System.out.print(persons[i].c);
//                System.out.print(",");
//                System.out.println(persons[i].dis);
//            }
        }
        System.out.println(total);
    }

    //최단거리 이동
    public static void move(){
        for(int i=0; i< persons.length; i++){
            if(persons[i]==null) continue;
            PriorityQueue<Person> pq = new PriorityQueue<>((p1,p2)->{
                if(p1.dis==p2.dis) {
                    if(p1.r==p2.r) {
                        return p1.c-p2.c;
                    }
                    return p1.r-p2.r;
                }
                return p1.dis-p2.dis;
            });
            for(int j=0; j<4; j++){
                int nr = persons[i].r+dr[j];
                int nc = persons[i].c+dc[j];
                if(!inRange(nr,nc)) continue;
                if(map[nr][nc]==-1) continue;
                if(visit[nr][nc]==i+1) continue;
                int d = Math.abs(nr-store[i][0])+Math.abs(nc-store[i][1]);
                pq.add(new Person(nr,nc,d));
            }
            persons[i] = pq.poll();
        }
    }
    //도착 여부 확인
    public static void isArrive(int total){
        for(int i=0; i< persons.length; i++){
            if(persons[i]==null) continue;
            if(persons[i].r==store[i][0] && persons[i].c==store[i][1]){
                map[store[i][0]][store[i][1]]=-1;
//                System.out.print(total);
//                System.out.print(" : ");
//                System.out.print(store[i][0]);
//                System.out.print(",");
//                System.out.println(store[i][1]);
                persons[i]=null;
                arrive++;
            }
        }
    }
    //최단거리 베이스 캠프
    public static Person findBC(int num){
        int min = Integer.MAX_VALUE;
        List<Person> p = new ArrayList<>();
        for(int[] camp : bc){
            if(map[camp[0]][camp[1]]==-1) continue;
            Queue<Person> q = new LinkedList<>();
            boolean[][] visited = new boolean[n][n];
            q.add(new Person(camp[0],camp[1],0));
            visited[camp[0]][camp[1]]=true;
            while(!q.isEmpty()){
                Person per = q.poll();
//                if(camp[0]==3&&camp[1]==1&&num==2){
//                    System.out.print(per.r);
//                    System.out.print(",");
//                    System.out.print(per.c);
//                    System.out.print(",");
//                    System.out.println(per.dis);
//                }
                if(per.r==store[num-1][0]&&per.c==store[num-1][1]){p.add(new Person(camp[0],camp[1],per.dis));break;}
                int r = per.r;
                int c = per.c;

                for(int i=0; i<4; i++){
                    int nr = r+dr[i];
                    int nc = c+dc[i];
                    if(!inRange(nr,nc)) continue;
                    if(map[nr][nc]==-1) continue;
                    if(visited[nr][nc]) continue;
                    q.add(new Person(nr,nc,per.dis+1));
                    visited[nr][nc]=true;

                }
            }
        }


//        for(Person ps : p){
//            System.out.print(num);
//            System.out.print("-");
//            System.out.print(ps.r);
//            System.out.print(",");
//            System.out.print(ps.c);
//            System.out.print(",");
//            System.out.println(ps.dis);
//        }
        Person ps=Collections.min(p,(p1,p2)->{
            if(p1.dis==p2.dis) {
                if(p1.r==p2.r) {
                    return p1.c-p2.c;
                }
                else{return p1.r-p2.r;}
            }
            return p1.dis-p2.dis;
        });


        return new Person(ps.r, ps.c,Math.abs(ps.r-store[num-1][0])+Math.abs(ps.c-store[num-1][1]));
    }


    public static boolean inRange(int r, int c){
        return r>=0&&r<n&&c>=0&&c<n;
    }
}