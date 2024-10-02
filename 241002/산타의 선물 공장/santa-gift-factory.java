import  java.util.*;
import  java.io.*;

public class Main {
    static int q;
    static int m;
    
    public static class Box{
        int id;
        int w;
        public Box(int id, int w){
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
        belt= new List[m+1];
        int[] ids = new int[n];
        int[] ws = new int[n];

        for (int i=0; i<n; i++){
            ids[i]=Integer.parseInt(st.nextToken());
        }
        for (int i=0; i<n; i++){
            ws[i] = Integer.parseInt(st.nextToken());
        }

        for (int i=1; i<=m; i++){
            belt[i] = new ArrayList<>();
            for(int j=0; j<n/m; j++){
                belt[i].add(new Box(ids[(i-1)*(n/m)+j],ws[(i-1)*(n/m)+j]));
            }
        }

        for(int i=1; i<q; i++){
            st = new StringTokenizer(br.readLine());
            int type = Integer.parseInt(st.nextToken());
            if(type==200){
                int max = Integer.parseInt(st.nextToken());
                put(max);
            }
            if(type==300){
                int id = Integer.parseInt(st.nextToken());
                remove(id);
            }
            if(type==400){
                int id = Integer.parseInt(st.nextToken());
                check(id);
            }
            if(type==500){
                int num = Integer.parseInt(st.nextToken());
                error(num);
            }
        }
    }
    public static void put(int w){
        int total=0;
        for(int i=1; i<=m; i++){
            if(belt[i]==null) continue;
            if(belt[i].get(0).w<=w){
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
        for(int i=1; i<=m; i++){
            if(belt[i]==null) continue;
            for(int j=0; j<belt[i].size(); j++){
                if(belt[i].get(j).id==id) {
                    belt[i].remove(j);
                    System.out.println(id);
                    return;
                }
            }
        }
        System.out.println(-1);
    }
    public static void check(int id) {
        for (int i = 1; i <= m; i++) {
            if(belt[i]==null) continue;
            for (int j = 0; j < belt[i].size(); j++) {
                if (belt[i].get(j).id == id) {
                    List<Box> back = belt[i].subList(0,j);
                    List<Box> front = belt[i].subList(j,belt[i].size());
                    front.addAll(back);
                    belt[i] = front;
                    System.out.println(i);
                    return;
                }
            }
        }
        System.out.println(-1);
    }
    public static void error(int num){
        if(belt[num]==null) {System.out.println(-1);return;}
        int next = (num+1)%m;
        while(true){
            if(belt[next]==null) {next=(next+1)%m; continue;}
            belt[next].addAll(belt[num]);
            belt[num]=null;
            System.out.println(num);
            break;
        }
    }
}