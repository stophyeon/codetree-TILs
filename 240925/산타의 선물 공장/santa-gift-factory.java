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
    //위치 저장
    static HashMap<Integer,Integer>[] loc;
    static HashMap<Integer,Integer> beltLoc;

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
        loc = new HashMap[m];
        beltLoc =  new HashMap<>();
        for(int i=0; i<m; i++){
            belt[i] = new ArrayList<>();
            loc[i] = new HashMap<>();
        }
        for(int i=0; i<n; i++){
           ids[i] = Integer.parseInt(st.nextToken());
        }
        for(int i=0; i<n; i++){
            ws[i] = Integer.parseInt(st.nextToken());
        }
        for(int i=0; i<n; i++){
            belt[i/(n/m)].add(new Box(ids[i],ws[i]));
            loc[i/(n/m)].put(ids[i],i%(n/m));
            beltLoc.put(ids[i],i/(n/m));
        }

        for(int i =1; i<q; i++){
            st = new StringTokenizer(br.readLine());
            int type = Integer.parseInt(st.nextToken());
            int num = Integer.parseInt(st.nextToken());
            if(type==200) move(num);
            if(type==300) {if(beltLoc.get(num)==null){System.out.println(-1);continue;}remove(num,beltLoc.get(num));}
            if(type==400) {if(beltLoc.get(num)==null){System.out.println(-1);continue;}check(num,beltLoc.get(num));}
            if(type==500) error(num-1);
        }
    }

    public static void move(int max){
        int total=0;
        for(int i=0; i<m; i++){
            if(belt[i]==null) continue;
            if(belt[i].get(0).w<=max){
                total+=belt[i].get(0).w;
                loc[i].remove(belt[i].get(0).id);
                belt[i].remove(0);
                //위치 변경
                for(int j=0; j<belt[i].size(); j++){
                    loc[i].put(belt[i].get(j).id,j);
                }
                //beltLoc.remove(belt[i].get(0).id);
            }
            else{
                Box b = belt[i].get(0);
                belt[i].remove(0);
                belt[i].add(b);
                //위치 변경
                for(int j=0; j<belt[i].size(); j++){
                    loc[i].put(belt[i].get(j).id,j);
                }
            }
        }
        System.out.println(total);

    }
    public static void remove(int id,int i){
        if(loc[i].containsKey(id)){
            int index = loc[i].get(id);
            belt[i].remove(index);
            System.out.println(id);
            loc[i].remove(id);
            for(int j=index; j<belt[i].size(); j++){
                loc[i].put(belt[i].get(j).id,j);
            }
            return;
        }
        System.out.println(-1);
    }

    public static void check(int id,int i){
        if(loc[i].containsKey(id)){
            System.out.println(i+1);
            int index = loc[i].get(id);
            Box b = belt[i].get(index);
            belt[i].remove(index);
            belt[i].add(0,b);
            for(int j=index; j<belt[i].size(); j++){
                loc[i].put(belt[i].get(j).id,j);
            }
            return;
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
        loc[bn].clear();
        for(int j=0; j<belt[index].size(); j++){
            loc[index].put(belt[index].get(j).id,j);
            beltLoc.put(belt[index].get(j).id,index);
        }

        System.out.println(bn+1);
    }
}