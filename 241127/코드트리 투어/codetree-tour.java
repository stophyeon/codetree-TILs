

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

    public static class Post implements Comparable<Post>{
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
            return this.rev-this.cost;
        }
        @Override
        public int compareTo(Post other) {
            if (this.money() == other.money()) {
                return Integer.compare(this.id, other.id);
            }
            return Integer.compare(other.money(), this.money());
        }
    }
    static int n;
    static int[] dist;
    static int start=0;
    static PriorityQueue<Post> pq = new PriorityQueue<>();
    static boolean[] re;
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
        re=new boolean[30001];
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
        dijkstra();
        for(int i=1; i<q; i++){
            st = new StringTokenizer(br.readLine());
            type = Integer.parseInt(st.nextToken());
            if(type==200){
                int id=Integer.parseInt(st.nextToken());
                int rev=Integer.parseInt(st.nextToken());
                int dest=Integer.parseInt(st.nextToken());
                pq.add(new Post(id,rev,dest,dist[dest]));
                if(re[id]) re[id]=false;
            }
            else if(type==300){
                re[Integer.parseInt(st.nextToken())]=true;
            }
            else if(type==400){
                sellPost();
            }
            else{
                start = Integer.parseInt(st.nextToken());
                dijkstra();
                List<Post> list =new ArrayList<>();
                while(!pq.isEmpty()){
                    Post p = pq.poll();
                    list.add(new Post(p.id,p.rev,p.dst,dist[p.dst]));
                }
                pq.addAll(list);
            }
//            int idx=0;
//            for(int d: dist){
//                if(dist[idx]!=max)
//                    System.out.printf("%d -> %d : %d\n",start, idx, dist[idx]);
//                idx++;
//            }
        }
    }
    static void dijkstra() {
        boolean[] visit = new boolean[n];
        Arrays.fill(dist, max);
        dist[start] = 0;
        for (int i = 0; i < n - 1; i++) {
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

    public static void sellPost(){
        while (!pq.isEmpty()) {
            Post p = pq.peek();
            if (p.money() < 0) {
                break;
            }
            pq.poll();
            if (!re[p.id]) {
                System.out.println(p.id);
                return;
            }
        }
        System.out.println(-1);
    }
}













