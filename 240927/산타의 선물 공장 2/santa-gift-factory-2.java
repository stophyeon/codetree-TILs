import  java.util.*;
import  java.io.*;

public class Main {
    static int q;
    static int n;
    static int m;
    static int[] size;
    static List<Integer>[] belt;
    static int max=100000;
    static int[] head=new int[max+1];
    static int[] tail=new int[max+1];
    static int[] prev=new int[max+1];
    static int[] next=new int[max+1];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        q=Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        st.nextToken();
        n=Integer.parseInt(st.nextToken());
        m=Integer.parseInt(st.nextToken());
        belt = new ArrayList[n];
        size= new int[n];
        for(int i=0; i<n; i++){
            belt[i]=new ArrayList<>();
        }
        for(int j=0; j<m; j++){
            int index = Integer.parseInt(st.nextToken());
            belt[index-1].add(j+1);
        }
        for(int i=0; i<n; i++){
            if(belt[i].isEmpty()) continue;
            head[i]=belt[i].get(0);
            tail[i]=belt[i].get(belt[i].size()-1);
            size[i]=belt[i].size();
            if(size[i]==1) continue;
            for(int j=0; j<size[i]-1; j++){
                prev[belt[i].get(j+1)]=belt[i].get(j);
                next[belt[i].get(j)]=belt[i].get(j+1);
            }
        }

        for(int i=1; i<q; i++){
            st = new StringTokenizer(br.readLine());
            int type = Integer.parseInt(st.nextToken());

            if(type==200){
                int src = Integer.parseInt(st.nextToken());
                int dst = Integer.parseInt(st.nextToken());
                moveAll(src-1,dst-1);
            }
            if(type==300){
                int src = Integer.parseInt(st.nextToken());
                int dst = Integer.parseInt(st.nextToken());
                moveFront(src-1,dst-1);
            }
            if(type==400){
                int src = Integer.parseInt(st.nextToken());
                int dst = Integer.parseInt(st.nextToken());
                divide(src-1,dst-1);
            }
            if(type==500){
                int pnum= Integer.parseInt(st.nextToken());
                getBoxInfo(pnum);
            }
            if(type==600){
                int bnum= Integer.parseInt(st.nextToken());
                getBeltInfo(bnum-1);
            }
            
        }
    }

    public static void moveAll(int src, int dst){
        if(size[src]==0) {System.out.println(size[dst]);return;}
        int sh = head[src];
        int st = tail[src];
        int dh = head[dst];
        head[src]=0;
        tail[src]=0;
        head[dst]=sh;
        next[st]=dh;
        prev[dh]=st;


        size[dst]+=size[src];
        size[src]=0;
        System.out.println(size[dst]);
    }

    public static void moveFront(int src, int dst){
        if(size[src]==0&&size[dst]==0) {System.out.println(0);return;}
        else if(size[src]==0){
            head[src]=head[dst];
            tail[src]=head[dst];
            head[dst]=next[head[dst]];
            next[head[src]]=0;
            prev[head[dst]]=0;
            size[src]+=1;
            size[dst]-=1;
        }
        else if(size[dst]==0){
            head[dst]=head[src];
            tail[dst]=head[src];
            head[src]=next[head[src]];
            next[head[dst]]=0;
            prev[head[src]]=0;
            size[dst]+=1;
            size[src]-=1;
        }else{
            int sh = head[src];
            int dh = head[dst];
            int nsh = next[sh];
            int ndh = next[dh];
            head[dst]=sh;
            head[src]=dh;
            prev[nsh]=dh;
            prev[ndh]=sh;
            next[sh]=ndh;
            next[dh]=nsh;
            if(size[src]==1) tail[src]=head[src];
            if(size[dst]==1) tail[dst]=head[dst];
        }
        System.out.println(size[dst]);
    }

    public static void divide(int src, int dst){
        if(size[src]==0) {System.out.println(size[dst]);return;}
        if(size[src]==1) System.out.println(size[dst]);
        else{
            int len = size[src]/2;
            int s =head[src];
            for(int i=1; i<len; i++){
                s=next[s];
            }
            head[src]=next[s];
            next[s]=head[dst];
            prev[head[src]]=s;
            head[dst]=s;
            prev[head[src]]=0;
            if(size[dst]==1) prev[tail[dst]]=s;
            size[src]-=len;
            size[dst]+=len;
            System.out.println(size[dst]);
        }
    }

    public static void getBoxInfo(int num){
        if(prev[num]==0&&next[num]==0) {System.out.println(-3);return;}
        if(prev[num]==0) System.out.println(2*next[num]-1);
        else if(next[num]==0) System.out.println(prev[num]-2);
        else {System.out.println(prev[num]+2*next[num]);}
    }

    public static void getBeltInfo(int num){
        if(size[num]==0) System.out.println(-3);
        if(size[num]==1) System.out.println(0);
        else{
            int a=head[num];
            int b=tail[num];
            int c=size[num];
            if(a==0) a=-1;
            if(b==0) b=-1;
            System.out.println(a+2*b+3*c);

    }

}