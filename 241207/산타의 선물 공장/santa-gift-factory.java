import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class Main{
    static int n;
    static int m;
    static int[] ids;
    static int[] ws;
    static HashMap<Integer, Boolean> out=new HashMap<>();
    static boolean[] broken;
    static HashMap<Integer, Boolean> exists=new HashMap<>();
    public static class Box{
        int id;
        int w;
        public Box(int id, int w){
            this.id = id;
            this.w = w;
        }
    }
    static List<Box>[] belt;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int  q = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        int type= Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        ids = new int[n];
        ws = new int[n];

        broken = new boolean[m];
        belt = new List[m];
        for (int i = 0; i < n; i++) {
            ids[i] = Integer.parseInt(st.nextToken());
            exists.put(ids[i],true);
        }
        for (int i = 0; i < n; i++) {
            ws[i] = Integer.parseInt(st.nextToken());
        }
        for (int i = 0; i < m; i++) {
            belt[i] = new ArrayList<Box>();
            for (int j = (n/m)*i; j < (n/m)*(i+1); j++) {
                belt[i].add(new Box(ids[j], ws[j]));
            }
        }

        for (int i = 1; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            type = Integer.parseInt(st.nextToken());
            if(type==200){
                outBox(Integer.parseInt(st.nextToken()));
            }
            else if(type==300){
                removeBox(Integer.parseInt(st.nextToken()));
            }
            else if(type==400){
                findBox(Integer.parseInt(st.nextToken()));
            }
            else{
                breakBelt(Integer.parseInt(st.nextToken())-1);

            }
        }
    }
    public static void outBox(int max){
        int total=0;
        for (int i = 0; i < m; i++) {
            if(broken[i]||belt[i].isEmpty()) continue;
            Box b=belt[i].get(0);
            //System.out.println(b.id+" :"+b.w);
            if(b.w<=max) {
                total+=b.w;
                out.put(b.id,true);
                belt[i].remove(0);
            }
            else{
                belt[i].remove(0);
                belt[i].add(b);
            }
        }
        System.out.println(total);
    }

    public static void removeBox(int id){
        if(out.containsKey(id)||!exists.containsKey(id)) {System.out.println(-1); return;}
        for(int i=0; i<m; i++){
            if(broken[i]||belt[i].isEmpty()) continue;
            for(int j=0; j<belt[i].size(); j++){
                if(belt[i].get(j).id==id){
                    belt[i].remove(j);
                    out.put(id,true);
                    System.out.println(id);
                    return;
                }
            }
        }
    }

    public static void findBox(int id){
        if(out.containsKey(id)||!exists.containsKey(id)) {System.out.println(-1); return;}
        for(int i=0; i<m; i++){
            if(broken[i]||belt[i].isEmpty()) continue;
            for(int j=0; j<belt[i].size(); j++){
                if(belt[i].get(j).id==id){
                    System.out.println(i+1);
                    List<Box> rest = new ArrayList<>(belt[i].subList(0, j));
                    belt[i].subList(0, j).clear();
                    belt[i].addAll(rest);
                    return;
                }
            }
        }
    }

    public static void breakBelt(int id){
        if(broken[id]) {System.out.println(-1); return;}
        broken[id]=true;
        List<Box> rest = new ArrayList<>(belt[id]);
        belt[id].clear();
        for(int i=1; i<m; i++){
            int idx=(id+i)%m;
            if(broken[idx]) continue;
            belt[idx].addAll(rest);
            System.out.println(id+1);
            return;
        }
    }
}
