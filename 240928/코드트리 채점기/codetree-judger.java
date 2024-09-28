import  java.util.*;
import  java.io.*;

public class Main {
    static int q;
    static int n;
    public static class Problem{
        int time;
        String url;
        int p;
        public Problem(int time, int p, String url){
            this.p=p;
            this.url=url;
            this.time=time;
        }
    }
    static PriorityQueue<Problem> pq = new PriorityQueue<>((p1,p2)->{
        if(p1.p==p2.p){
            return p1.time-p2.time;
        }
        return p1.p-p2.p;
    });
    static int[] solved=new int[2];

    static List<String> urls = new ArrayList<>();
    static String[] calculator;
    static int[] start;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        q=Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        st.nextToken();
        n=Integer.parseInt(st.nextToken());
        calculator = new String[n];
        Arrays.fill(calculator,"");
        start=new int[n];

        String firstUrl = st.nextToken();
        pq.add(new Problem(0,1,firstUrl));
        urls.add(firstUrl);
        for(int i=1; i<q; i++){
            st = new StringTokenizer(br.readLine());
            int type = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());
            if(type==200){
                int p = Integer.parseInt(st.nextToken());
                String u = st.nextToken();
                request(t,p,u);
            }
            if(type==300) tryOne(t);
            if(type==400){
                int id = Integer.parseInt(st.nextToken());
                end(t,id);
            }
            if(type==500) System.out.println(pq.size());
        }
    }

    public static void request(int t, int p, String u){
        if(urls.contains(u)) return;
        pq.add(new Problem(t,p,u));
        urls.add(u);
    }

    public static void tryOne(int t){
        for(Problem p: pq){
            if(possible(p,t)){
                for (int i=0; i<n; i++){
                    if(calculator[i].isEmpty()) {
                        calculator[i]=p.url;
                        start[i]=t;
                        pq.poll();
                        urls.remove(p.url);
                        return;
                    }
                }
            }
        }
    }

    public static void end(int t, int id){
        solved[0]=start[id-1];
        solved[1]=t-start[id-1];
        calculator[id-1]="";
        start[id-1]=0;
    }
    public static boolean possible(Problem p,int t){
        if(3*solved[1]+solved[0]>t) return false;
        for(String s: calculator){
            if(p.url.equals(s)) return false;
        }
        return true;
    }
}