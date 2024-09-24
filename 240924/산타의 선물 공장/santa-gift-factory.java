import  java.util.*;
import  java.io.*;

public class Main {
    static int q;
    static int m;
    public static class Box{
        int id;
        int w;
        public Box(int id,int w){
            this.id=id;
            this.w=w;
        }
    }
    static List<Box>[] belt;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        q = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        st.nextToken();
        int n=Integer.parseInt(st.nextToken());
        m=Integer.parseInt(st.nextToken());
        int[] ids = new int[n];
        int[] ws = new int[n];
        belt = new ArrayList[m];
        for(int i=0; i<m; i++){
            belt[i] = new ArrayList<>();
        }
        for(int i=0; i<n; i++){
           ids[i] = Integer.parseInt(st.nextToken());
        }
        for(int i=0; i<n; i++){
            ws[i] = Integer.parseInt(st.nextToken());
        }
        for(int i=0; i<n; i++){
            belt[i/(n/m)].add(new Box(ids[i],ws[i]));
        }

        for(int i =1; i<q; i++){
            st = new StringTokenizer(br.readLine());
            int type = Integer.parseInt(st.nextToken());
            int num = Integer.parseInt(st.nextToken());
            if(type==200) move(num);
            if(type==300) remove(num);
            if(type==400) check(num);
            if(type==500) error(num-1);
        }
    }

    public static void move(int max){
        int total=0;
        for(int i=0; i<m; i++){
            if(belt[i]==null) continue;
            if(belt[i].get(0).w<=max){
                total+=belt[i].get(0).w;
                belt[i].remove(0);
            }
            else{
                Box b = belt[i].get(0);
                belt[i].remove(0);
                belt[i].add(b);
            }
        }
        System.out.println(total);
    }
    public static void remove(int id){
        for(int i=0; i<m; i++){
            if(belt[i]==null) continue;
            for(int j=0; j<belt[i].size(); j++) {
                if (belt[i].get(j).id == id) {
                    belt[i].remove(j);
                    System.out.println(id);
                    return;
                }
            }
        }
        System.out.println(-1);
    }

    public static void check(int id){
        int index=0;

        for(int i=0; i<m; i++){
            if(belt[i]==null) continue;

            for(int j=0; j<belt[i].size(); j++) {
                if (belt[i].get(j).id == id) {
                    System.out.println(i+1);
                    Box b = belt[i].get(j);
                    belt[i].remove(j);
                    belt[i].add(0,b);
                    return;
                }

            }
        }

        System.out.println(-1);
    }

    public static void error(int bn){
        if(belt[bn]==null){
            System.out.println(-1);
            return;
        }
        List<Box> list = belt[bn];
        belt[bn]=null;
        int index = (bn+1)%m;
        while(belt[index]==null){

            index=(index+1)%m;
        }
        for (Box box : list) {
            belt[index].add(box);
        }
        System.out.println(bn+1);
    }
}