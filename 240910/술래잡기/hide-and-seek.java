import java.util.*;
import java.io.*;
public class Main {                                     
    static int n;
    static int m;
    static int h;
    static int k;
    static int point=0;
    static int[][] map;
    static int[][] location;
    static Runner[] runners;
    static int[] dr = {0,1,0,-1};
    static int[] dc = {1,0,-1,0};
    public static class Runner{
        int r;
        int c;
        int dir;
        
        public Runner(int r,int c,int dir){
            this.r=r;
            this.c=c;
            this.dir=dir;
        }
    }
    
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        h = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        map= new int[n][n];
        location= new int[n*n*2-1][3];
        location[0] =new int[]{n/2,n/2,0};
        runners = new Runner[m];
        
        for(int i=0; i<m; i++){
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken())-1;
            int c = Integer.parseInt(st.nextToken())-1;
            int d = Integer.parseInt(st.nextToken());
            runners[i] = new Runner(r,c,d);
        }
        for(int i=0; i<h; i++){
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken())-1;
            int y = Integer.parseInt(st.nextToken())-1;
            map[x][y] = 3;
        }
        location[0]=new int[]{n/2,n/2,0};
        moveOne();
        
        //for(int i=0; i<location.length; i++){
        //    System.out.print(location[i][0]);
        //    System.out.print(" ");
        //    System.out.print(location[i][1]);
        //    System.out.print(" ");
        //    System.out.println(location[i][2]);
        //}
        int size = (n*n)-1;
        for(int i=1; i<=k; i++){
            play(i%size);
        }
       
        System.out.println(point);
    }
    public static void play(int turn){
        
        for(int i=0; i<runners.length; i++){
            if(runners[i]==null) continue;
            if(check(runners[i],turn)){moveRunner(runners[i]);}
        }
        calPoint(turn);

    }
    public static void calPoint(int turn){
        int count=0;
        
        for(int i=0; i<runners.length; i++){
            if(runners[i]==null) continue;
            if(inScope(runners[i],turn)&&map[runners[i].r][runners[i].c]!=3){
                count++;
                runners[i]=null;
            }
        }
        point+=count*turn;
    }
    
    public static boolean inScope(Runner r, int turn){
        int[] dy = new int[]{-1, 0, 1,  0};
        int[] dx = new int[]{0 , 1, 0, -1};
        int rscope=location[turn][0]+dy[location[turn][2]]*3;
        int cscope=location[turn][1]+dx[location[turn][2]]*3;
        if(location[turn][2]%2==0){
            return r.r>=location[turn][0]&&r.r<rscope&&r.c==location[turn][1];
        }
        else{
            return r.c>=location[turn][1]&&r.c<cscope&&r.r==location[turn][0];
        }
        
    }
    public static void moveOne(){
        int[] dy = new int[]{-1, 0, 1,  0};
        int[] dx = new int[]{0 , 1, 0, -1};
        int curx = n/2;
        int cury = n/2;
        int dir = 0;
        int count=0;
        int revcount=(n*n*2)-1;
        int moveNum=1;
        while(count<n*n-1){
            for(int i=1; i<=moveNum; i++){
                cury+=dy[dir];
                curx+=dx[dir];
                if(i!=moveNum){
                    location[count+i] = new int[]{cury,curx,dir};
                    location[revcount-i] = new int[]{cury,curx,(dir+2)%4};
                    
                }
                else{
                    location[count+i] = new int[]{cury,curx,(dir+1)%4};
                    location[revcount-i] = new int[]{cury,curx,(dir+2)%4};
                }
                
            }
            dir = (dir+1)%4;
            count+=moveNum;
            revcount-=moveNum;
            //System.out.print("정방향");
            //System.out.println(count);
            if(dir==0 || dir==2) {moveNum++;}
        }
        location[n*n-1]=new int[]{0,0,2};
        location[n*n]=new int[]{1,0,2};
        location[n*n*2-2]=new int[]{n/2,n/2,0};
    }
    public static boolean inRange(int x, int y){
        return x>=0 && x<n && y>=0 && y<n;
    }
    public static boolean check(Runner r,int turn){
        if(location[turn][0]==r.r+dr[r.dir-1] && location[turn][1]==r.c+dc[r.dir-1]){
            return false;
        }
        else{
            if(Math.abs(r.r-location[turn][0])+ Math.abs(r.c-location[turn][1])<=3){
                return true;
            }
            
        }
        return false;
    }
    public static void moveRunner(Runner r){
        if(r.dir==1){
            if(inRange(r.r+dr[0],r.c+dc[0])){
                r.r=r.r+dr[0];
                r.c=r.c+dc[0];
            }
            else{
                r.dir=3;
                moveRunner(r);
            }
        }
        if(r.dir==2){
            if(inRange(r.r+dr[1],r.c+dc[1])){
                r.r=r.r+dr[1];
                r.c=r.c+dc[1];
            }
            else{
                r.dir=4;
                moveRunner(r);
            }
        }
        if(r.dir==3){
            if(inRange(r.r+dr[2],r.c+dc[2])){
                r.r=r.r+dr[2];
                r.c=r.c+dc[2];
            }
            else{
                r.dir=1;
                moveRunner(r);
            }
        }
        if(r.dir==4){
            if(inRange(r.r+dr[3],r.c+dc[3])){
                r.r=r.r+dr[3];
                r.c=r.c+dc[3];
            }
            else{
                r.dir=2;
                moveRunner(r);
            }
        }
    }
}