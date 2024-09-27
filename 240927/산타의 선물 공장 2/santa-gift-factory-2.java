import  java.util.*;
import  java.io.*;

public class Main {
    static int q;
    static int n;
    static int m;
    static List<Integer>[] belt;
    static HashMap<Integer,Integer> loc=new HashMap<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        q=Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        st.nextToken();
        n=Integer.parseInt(st.nextToken());
        m=Integer.parseInt(st.nextToken());
        belt = new ArrayList[n];
        for(int i=0; i<n; i++){
            belt[i] = new ArrayList<>();
        }
        for(int i=0; i<m; i++){
            int index = Integer.parseInt(st.nextToken());
            belt[index-1].add(i+1);
            loc.put(i+1,index-1);
        }
        //System.out.println();
        for(int i=1; i<q; i++){
            st = new StringTokenizer(br.readLine());
            int type = Integer.parseInt(st.nextToken());
            //System.out.println(type);
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
//            for(int j=0; j<n; j++){
//                System.out.print(j+1);
//                System.out.print(" - ");
//                for(int s : belt[j]){
//                    System.out.print(s);
//                    System.out.print(",");
//                }
//                System.out.println();
//            }
        }
    }

    public static void moveAll(int src, int dst){
        if(belt[src].isEmpty()) {System.out.println(belt[dst].size());return;}
        List<Integer> sr = belt[src];
        sr.addAll(belt[dst]);
        belt[dst].clear();
        for(int k : sr){
            belt[dst].add(k);
            loc.put(k,dst);
        }
        belt[src].clear();
        System.out.println(belt[dst].size());
    }

    public static void moveFront(int src, int dst){
        if(belt[src].isEmpty()&&belt[dst].isEmpty()) {System.out.println(0);return;}
        else if(belt[src].isEmpty()){
            loc.put(belt[dst].get(0),src);
            belt[src].add(belt[dst].get(0));
            belt[dst].remove(0);
        }
        else if(belt[dst].isEmpty()){
            loc.put(belt[src].get(0),dst);
            belt[dst].add(belt[src].get(0));
            belt[src].remove(0);
        }else{
            loc.put(belt[src].get(0),dst);
            loc.put(belt[dst].get(0),src);
            int sr =belt[src].get(0);
            belt[src].remove(0);
            belt[src].add(0,belt[dst].get(0));
            belt[dst].remove(0);
            belt[dst].add(0,sr);
        }
        System.out.println(belt[dst].size());
    }
    public static void divide(int src, int dst){
        if(belt[src].isEmpty()) {System.out.println(belt[dst].size());return;}
        if(belt[src].size()==1) System.out.println(belt[dst].size());
        else{
            List<Integer> sr = new ArrayList<>();

            for(int i=0; i<belt[src].size()/2; i++){
                sr.add(belt[src].get(i));
            }
            for(int i=0; i<sr.size(); i++){
                belt[src].remove(0);
                loc.put(sr.get(i),dst);
            }
            sr.addAll(belt[dst]);
            belt[dst].clear();
            belt[dst]=sr;
            System.out.println(belt[dst].size());
        }

    }

    public static void getBoxInfo(int num){
        int index = loc.get(num);
        int lc=belt[index].indexOf(num);
        if(belt[index].size()<=1) {System.out.println(-3);return;}
        if(lc-1<0){
            System.out.println(2*belt[index].get(lc+1)-1);
        }
        else if(lc+1>=belt[index].size()){
            System.out.println(belt[index].get(lc-1)-2);
        }
        else{
            System.out.println(belt[index].get(lc-1)+2*belt[index].get(lc+1));
        }
    }

    public static void getBeltInfo(int num){
        if(belt[num].isEmpty()) System.out.println(-3);
        else{
            int a=belt[num].get(0);
            int b=belt[num].get(belt[num].size()-1);
            int c = belt[num].size();
            System.out.println(a+2*b+3*c);
        }

    }

}