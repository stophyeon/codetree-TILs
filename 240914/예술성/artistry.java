import java.util.*;
import java.io.*;
public class Main {
    static int n;
    static int[][] map;
    static int[][] gp;
    static List<int[]> groups;
    static int[][] line;
    public static class Node{
        int r;
        int c;
        int group;
        
        public Node(int r, int c,int group){
            this.r=r;
            this.c=c;
            this.group=group;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n=Integer.parseInt(st.nextToken());
        map=new int[n][n];
        gp=new int[n][n];
        int score=0;
        for(int i=0; i<n; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<n; j++){
                map[i][j]=Integer.parseInt(st.nextToken());
            }
        }
        groups=makeGroup();
        
        //for(int i=0; i<groups.size(); i++){
        //    System.out.print(groups.get(i)[0]);
        //    System.out.print(",");
        //    System.out.println(groups.get(i)[1]);
        //}
        line=new int[groups.size()][groups.size()];

        for(int i=0; i<line.length; i++){
            for(int j=0; j<n; j++){
                for(int k=0; k<n; k++){
                    if(gp[j][k]==i){
                        line[i]=countLine(i,j,k);
                        i++;
                    }
                }
            }
            
        }
        //for(int i=0; i<line.length; i++){
        //    for(int j=0; j<line.length; j++){
        //        System.out.print(line[i][j]);
        //        System.out.print(",");
        //    }
        //    System.out.println("");
        //}
        //for(int i=0; i<n; i++){
        //    for(int j=0; j<n; j++){
        //        System.out.print(gp[i][j]);
        //    }
        //    System.out.println("");
        //}
        score+=getScore();
        for(int l=0; l<3; l++){
            map=turn(map);
            //for(int i=0; i<n; i++){
            //    for(int j=0; j<n; j++){
            //        System.out.print(map[i][j]);
            //    }
            //    System.out.println("");
            //}
            //System.out.println("---------------");
            groups=makeGroup();
            line= new int[groups.size()][groups.size()];
            for(int i=0; i<line.length; i++){
                for(int j=0; j<n; j++){
                    for(int k=0; k<n; k++){
                        if(gp[j][k]==i){
                            line[i]=countLine(i,j,k);
                            i++;
                        }
                    }
                }
            }
            score+=getScore();
        }
        System.out.println(score);
    }
    public static int[] countLine(int index,int r, int c){
        int[] dr = {-1,1,0,0};
        int[] dc = {0,0,1,-1};
        boolean[][] visited = new boolean[n][n];
        int[] res = new int[groups.size()];
        Queue<Node> q = new LinkedList<>();
        q.add(new Node(r,c,index));
        visited[r][c]=true;
        while(!q.isEmpty()){
            Node n = q.poll();
            for(int i=0; i<4; i++){
                int nr = n.r+dr[i];
                int nc = n.c+dc[i];
                if(!inRange(nr,nc)){continue;}
                if(!visited[nr][nc]){
                    if(n.group==gp[nr][nc]){
                        q.add(new Node(nr,nc,n.group));
                        visited[nr][nc]=true;
                    }
                    else{
                        res[gp[nr][nc]]++;
                    }
                }
            }
        }
        return res;
    }
    public static List<int[]> makeGroup(){
        int[] dr = {-1,1,0,0};
        int[] dc = {0,0,1,-1};
        List<int[]> groups=new ArrayList<>();
        boolean[][] visited = new boolean[n][n];
        Queue<Node> q = new LinkedList<>();
        int gpnum=0;
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                int count=1;
                if(visited[i][j]){continue;}
                q.add(new Node(i,j,map[i][j]));
                visited[i][j]=true;
                gp[i][j]=gpnum;
                while(!q.isEmpty()){
                    Node n = q.poll();
                    for(int k=0; k<4; k++){
                        int nr = n.r+dr[k];
                        int nc = n.c+dc[k];
                        if(!inRange(nr,nc)){continue;}
                        if(visited[nr][nc]||map[nr][nc]!=n.group){continue;}
                        visited[nr][nc]=true;
                        q.add(new Node(nr,nc,n.group));
                        gp[nr][nc]=gpnum;
                        count++;
                    }
                if(q.isEmpty()){groups.add(new int[]{n.group,count});}
                }
                gpnum++;
            }
        }
        return groups;
    }
    public static boolean inRange(int r,int c){
        return r>=0 && r<n && c>=0 && c<n;
    }
    public static int getScore(){
        int point=0;
        boolean[][] visited = new boolean[groups.size()][groups.size()];
        for(int i=0; i<groups.size(); i++){
            for(int j=0; j<line.length; j++){
                if(line[i][j]==0||visited[i][j]==true){continue;}
                int[] g1=groups.get(i);
                int[] g2=groups.get(j);
                point+=(g1[1]+g2[1])*g1[0]*g2[0]*line[i][j];
                visited[i][j]=true;
                visited[j][i]=true;
            }
        }
        return point;
    }
    public static int[][] turn(int[][] map){
        int[][] left=new int[n][n];
        //좌상단
        for(int i=0; i<(n/2); i++){
            for(int j=0; j<(n/2); j++){
                left[i][j]=map[(n/2)-1-j][i];
            }
        }
        //좌하단
        for(int i=0; i<n/2; i++){
            for(int j=(n/2)+1; j<n; j++){
                left[(n/2)+1+i][j-(n/2)-1]=map[j][i];
            }
        }
        //우상단
        for(int i=0; i<n/2; i++){
            for(int j=(n/2)+1; j<n; j++){
                left[i][j]=map[j-(n/2)-1][n-1-i];
            }
        }
        //우하단
        for(int i=(n/2)+1; i<n; i++){
            for(int j=(n/2)+1; j<n; j++){
                left[i][j]=map[j][n+n/2-i];
            }
        }
        //십자모양 위
        for(int i=0; i<n/2; i++){
            left[n/2][i]=map[i][n/2];
        }
        //십자모양 왼쪽
        for(int i=0; i<n/2; i++){
            left[n-1-i][n/2]=map[n/2][i];
        }
        //십자모양 아래
        for(int i=n-1; i>n/2; i--){
            left[n/2][i]=map[i][n/2];
        }
        //십자모양 오른쪽
        for(int i=n-1; i>n/2; i--){
            left[n-1-i][n/2]=map[n/2][i];
        }
        left[n/2][n/2]=map[n/2][n/2];
        return left;
    }
}