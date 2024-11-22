

import  java.util.*;
import  java.io.*;

public class Main {
    public static class Rabbit{
        int r=0;
        int c=0;
        int num;
        int d;
        int cnt=0;
        int score=0;
        public Rabbit(int num,int d){
            this.num=num;
            this.d=d;
        }
    }
    static int n;
    static int m;
    static PriorityQueue<Rabbit> jump = new PriorityQueue<>((r1,r2)->{
        if (r1.cnt==r2.cnt){
            if(r1.r+r1.c==r2.r+r2.c){
                if(r1.r==r2.r){
                    if(r1.c==r2.c){
                        return r1.num-r2.num;
                    }
                    return r1.c-r2.c;
                }
                return r1.r-r2.r;
            }
            return (r1.r+r1.c)-(r2.r+r2.c);
        }
        return r1.cnt-r2.cnt;
    });

    static HashMap<Integer, Rabbit> map = new HashMap<>();
    static int[] dr = {-1,1,0,0};
    static int[] dc = {0,0,-1,1};
    static int[] d= new int[2];
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int q = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        int type=Integer.parseInt(st.nextToken());
        n=Integer.parseInt(st.nextToken());
        m=Integer.parseInt(st.nextToken());
        int p=Integer.parseInt(st.nextToken());

        for(int i=0; i<p; i++){
            int num = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            map.put(num,new Rabbit(num,d));
        }

        for(int k : map.keySet()){
            jump.add(map.get(k));
        }

        for(int i=1; i<q; i++){
            st = new StringTokenizer(br.readLine());
            type = Integer.parseInt(st.nextToken());
            if(type==200){
                int k = Integer.parseInt(st.nextToken());
                int s = Integer.parseInt(st.nextToken());
                PriorityQueue<Rabbit> mr = new PriorityQueue<>((r1,r2)->{
                    if(r1.r+r1.c==r2.r+r2.c){
                        if(r1.r==r2.r){
                            if(r1.c==r2.c){
                                return r2.num-r1.num;
                            }
                            return r2.c-r1.c;
                        }
                        return r2.r-r1.r;
                    }
                    return (r2.r+r2.c)-(r1.r+r1.c);
                });
                for(int j=0; j<k; j++){
                    Rabbit rabbit = jump.poll();
                    move(rabbit);
                    mr.add(rabbit);
                    jump.add(rabbit);
                    afterJump(rabbit.num, rabbit.r+ rabbit.c+2);
                }
                mr.poll().score+=s;
                mr.clear();
            }
            else if(type==300){
                int t = Integer.parseInt(st.nextToken());
                int l = Integer.parseInt(st.nextToken());
                map.get(t).d*=l;
            }
            else{
                int max=0;
                for(int k : map.keySet()){
                    if(max<map.get(k).score) max=map.get(k).score;
                }
                System.out.println(max);
            }
        }

    }

    public static void afterJump(int num,int rc){
        for(int k : map.keySet()){
            if(k==num) continue;
            map.get(k).score+=rc;
        }
    }

    public static void move(Rabbit rabbit){
        int[][] dis = new int[4][2];
        int[] max = new int[4];
        for(int i=0; i<4; i++){
            int nr = rabbit.r+dr[i]*rabbit.d;
            int nc = rabbit.c+dc[i]*rabbit.d;
            if (!inRange(nr,nc)){

                reverse(rabbit.r, rabbit.c,rabbit.d,i);
                dis[i]=d;
                max[i]=dis[i][0]+dis[i][1];
            }
            else{dis[i]=new int[]{nr,nc};max[i]=nr+nc;}
        }
        int maxR =-1;
        int maxC =-1;
        for(int i=0; i<4; i++){
            if(max[i]>maxR+maxC){
                maxR=dis[i][0];
                maxC=dis[i][1];

            }
            else if(max[i]==maxR+maxC){
                if(maxR==dis[i][0]){
                    if(maxC<dis[i][1]){
                        maxR=dis[i][0];
                        maxC=dis[i][1];
                    }

                }
                else if(maxR<dis[i][0]){
                    maxR=dis[i][0];
                    maxC=dis[i][1];
                }
            }
        }
        rabbit.r=maxR;
        rabbit.c=maxC;
        rabbit.cnt++;
    }

    public static void reverse(int r, int c, int dis,int dir){

        // 상(r<0) 하(r>=n) 좌(c<0) 우(c>=m) 순
        if(dir==0){
            dis%=2*(n-1);
            if(r-dis<0) reverse(0,c,dis-r,1);
            else{d= new int[]{r-dis,c};}
        }
        else if(dir==1){
            dis%=2*(n-1);
            if(r+dis>=n) reverse(n-1,c,dis-((n-1)-r),0);
            else{d= new int[]{r+dis,c};}
        }
        else if(dir==2){
            dis%=2*(m-1);
            if(c-dis<0) reverse(r,0,dis-c,3);
            else{d= new int[]{r,c-dis};}
        }
        else{
            dis%=2*(m-1);
            if(c+dis>=m) reverse(r,m-1,dis-((m-1)-c),2);
            else{d= new int[]{r,c+dis};}
        }

    }

    public static boolean inRange(int r, int c){
        return r>=0&&r<n&&c>=0&&c<m;
    }
}