import  java.util.*;
import  java.io.*;

public class Main {
    static int q;
    static int n;
    static int m;
    static int[] size;
    static LinkedList<Integer>[] belt;
    static HashMap<Integer,Integer> loc=new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        q=Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        st.nextToken();
        n=Integer.parseInt(st.nextToken());
        m=Integer.parseInt(st.nextToken());
        belt = new LinkedList[n];
        size= new int[n];

        for(int i=0; i<n; i++){
            belt[i] = new LinkedList<>();
        }
        for(int i=0; i<m; i++){
            int index = Integer.parseInt(st.nextToken());
            belt[index-1].add(i+1);
            loc.put(i+1,index-1);
        }
        for(int i=0; i<n; i++){
            size[i]=belt[i].size();
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
        int len = size[src];
        for(int i=0; i<len; i++){
            belt[dst].add(i,belt[src].get(0));
            loc.replace(belt[src].get(0),dst);
            belt[src].remove(0);
        }
        size[dst]+=size[src];
        size[src]=0;
        System.out.println(size[dst]);
    }

    public static void moveFront(int src, int dst){
        if(size[src]==0&&size[dst]==0) {System.out.println(0);return;}
        else if(size[src]==0){
            loc.replace(belt[dst].get(0),src);
            belt[src].add(belt[dst].get(0));
            belt[dst].remove(0);
            size[src]+=1;
            size[dst]-=1;
        }
        else if(size[dst]==0){
            loc.replace(belt[src].get(0),dst);
            belt[dst].add(belt[src].get(0));
            belt[src].remove(0);
            size[dst]+=1;
            size[src]-=1;
        }else{
            loc.replace(belt[src].get(0),dst);
            loc.replace(belt[dst].get(0),src);
            int sr =belt[src].get(0);
            belt[src].remove(0);
            belt[src].add(0,belt[dst].get(0));
            belt[dst].remove(0);
            belt[dst].add(0,sr);
        }
        System.out.println(size[dst]);
    }
    public static void divide(int src, int dst){
        if(size[src]==0) {System.out.println(size[dst]);return;}
        if(size[src]==1) System.out.println(size[dst]);
        else{
            int len = size[src]/2;
            for(int i=0; i<len; i++){
                belt[dst].add(i,belt[src].get(0));
                loc.replace(belt[src].get(0),dst);
                belt[src].remove(0);
            }
            size[src]-=len;
            size[dst]+=len;
            System.out.println(size[dst]);
        }
    }

    public static void getBoxInfo(int num){
        int index = loc.get(num);
        if(size[index]<=1) {System.out.println(-3);return;}
        List<Integer> l = belt[index];
        int lc=l.indexOf(num);
        if(lc-1<0){
            System.out.println(2*l.get(lc+1)-1);
        }
        else if(lc+1>=size[index]){
            System.out.println(l.get(lc-1)-2);
        }
        else{
            System.out.println(l.get(lc-1)+2*l.get(lc+1));
        }
    }

    public static void getBeltInfo(int num){
        if(size[num]==0) System.out.println(-3);
        else{
            int a=belt[num].get(0);
            int b=belt[num].get(size[num]-1);
            int c = size[num];
            System.out.println(a+2*b+3*c);
        }
    }
}