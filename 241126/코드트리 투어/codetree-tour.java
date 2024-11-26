import  java.util.*;
import  java.io.*;


public class Main {
    static int max = Integer.MAX_VALUE;
    static int[][] graph;
    public static class Node{
        int cost;
        int n;
        public Node(int n, int cost){
            this.cost=cost;
            this.n=n;
        }

    }
    public static class Post{
        int id;
        int rev;
        int dst;
        int cost;
        List<Integer> path = new ArrayList<>();
        public Post(int id, int rev,int dst,int cost){
            this.id=id;
            this.rev=rev;
            this.dst=dst;
            this.cost=cost;
        }
        public int money(){

            return (this.rev-this.cost);
        }
    }
    static int n;
    static int[] dist;
    static HashMap<Integer, Post> posts = new HashMap<>();
    static int start=0;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int q = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        int type = Integer.parseInt(st.nextToken());
        n=Integer.parseInt(st.nextToken());
        int m=Integer.parseInt(st.nextToken());
        graph = new int[n][n];
        dist=new int[n];
        for(int i=0; i<n; i++) {
            for (int j = 0; j < n; j++) {
                graph[i][j]=max;
            }
        }
        for(int i=0; i<m; i++){
            int v= Integer.parseInt(st.nextToken());
            int u= Integer.parseInt(st.nextToken());
            int w= Integer.parseInt(st.nextToken());
            graph[v][u]=Math.min(graph[v][u],w);
            graph[u][v]=Math.min(graph[u][v],w);
        }

        for(int i=1; i<q; i++){
            st = new StringTokenizer(br.readLine());
            type = Integer.parseInt(st.nextToken());
            if(type==200){
                producePost(Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()));
            }
            else if(type==300){
                posts.remove(Integer.parseInt(st.nextToken()));
            }
            else if(type==400){
                sellPost();
            }
            else{
                changeStart(Integer.parseInt(st.nextToken()));
            }

        }
    }
    public static void dijkstra() {
        boolean[] visit = new boolean[n];
        Arrays.fill(dist, max);
        dist[start] = 0;
        for (int i = 0; i < n-1; i++) {
            int v = 0, minDist = max;
            for (int j = 0; j < n; j++) {
                if (!visit[j] && minDist > dist[j]) {
                    v = j;
                    minDist = dist[j];
                }
            }
            visit[v] = true;
            for (int j = 0; j < n; j++) {
                if (!visit[j] && dist[v] != max && graph[v][j] != max && dist[j] > dist[v] + graph[v][j]) {
                    dist[j] = dist[v] + graph[v][j];
                }
            }
        }
    }
    public static void producePost(int id, int rev, int dest){
        posts.put(id,new Post(id,rev,dest,dist[dest]));
    }

    public static void sellPost(){
        List<Integer> key =  new ArrayList<>(posts.keySet());
        if(key.isEmpty()) {System.out.println(-1);return;}
        key.sort((k1,k2)->{
            if(posts.get(k1).money()==posts.get(k2).money()){
                return posts.get(k1).id-posts.get(k2).id;
            }
            else if(posts.get(k1).money()<0&&posts.get(k2).money()>0) return 1;
            else if(posts.get(k1).money()>0&&posts.get(k2).money()<0) return -1;
            else if(posts.get(k1).money()<0&&posts.get(k2).money()<0) return Math.abs(posts.get(k1).money())-Math.abs(posts.get(k2).money());
            else{return posts.get(k2).money()-posts.get(k1).money();}
        });
//        for(int k : key){
//            System.out.printf("%d : %d\n",k,posts.get(k).money());
//        }
        //System.out.printf("%d : %d\n",key.get(0),posts.get(key.get(0)).money());

        if(posts.get(key.get(0)).rev<posts.get(key.get(0)).cost) System.out.println(-1);
        else{System.out.println(posts.get(key.get(0)).id); posts.remove(key.get(0));}
    }

    public static void changeStart(int s) {
        //존재하는 상품 재 탐색
        start = s;
        dijkstra();
        for(int k : posts.keySet()){
            posts.compute(k, (key, p) -> new Post(p.id, p.rev, p.dst, dist[p.dst]));
        }
    }
}






