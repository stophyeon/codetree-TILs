

import  java.util.*;
import  java.io.*;

public class Main {
    static int max = Integer.MAX_VALUE;
    static List<Integer>[][] graph;
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
        graph = new ArrayList[n][n];
        for(int i=0; i<n; i++) {
            for (int j = 0; j < n; j++) {
                graph[i][j]=new ArrayList<>();
            }
        }
        for(int i=0; i<m; i++){
            int v= Integer.parseInt(st.nextToken());
            int u= Integer.parseInt(st.nextToken());
            int w= Integer.parseInt(st.nextToken());
            graph[v][u].add(w);
            graph[u][v].add(w);
        }
//        for(int i=0; i<n; i++){
//            for(int j=0; j<n; j++){
//                for(int k : graph[i][j]){
//                    System.out.printf("%d -> %d : %d\n",i,j,k);
//                }
//            }
//        }
        //System.out.printf("%d -> %d : %d",start,3,calculateDistance(3));
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
    public static void producePost(int id, int rev, int dest){
        //start부터 dest까지 최단거리 경로
        int cost = calculateDistance(dest);
        posts.put(id,new Post(id,rev,dest,cost));
    }
    public static int calculateDistance(int d){
        int min=max;
        Queue<Node> q = new LinkedList<>();
        boolean[][][] visited = new boolean[n][n][10000];
        q.add(new Node(start,0));
        while(!q.isEmpty()){
            Node nd = q.poll();
            if(nd.n==d&&min>nd.cost) {min=nd.cost;}
            for(int i=0; i<n; i++){
                for(int j=0; j<graph[nd.n][i].size(); j++){
                    if(graph[nd.n][i].get(j)==0||visited[nd.n][i][j]) continue;
                    q.add(new Node(i,nd.cost+graph[nd.n][i].get(j)));
                    visited[nd.n][i][j]=true;
                }
            }
        }
        return min;
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

    public static void changeStart(int s){
        //존재하는 상품 재 탐색
        HashMap<Integer, Post> pt = new HashMap<>();
        start=s;
        for(int k : posts.keySet()){
            Post p = posts.get(k);
            int c=calculateDistance(posts.get(k).dst);
            pt.put(k,new Post(p.id,p.rev,p.dst,c));
            //System.out.printf("%d %d\n",posts.get(k).id,posts.get(k).cost);
        }
        posts.clear();
        posts.putAll(pt);
    }
}






