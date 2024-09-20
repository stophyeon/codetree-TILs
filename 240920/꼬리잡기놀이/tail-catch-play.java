import java.util.*;
import java.io.*;

public class Main {
    static int n;
    static int m;
    static int k;
    static int point;
    static int[][] map;
    static int[][] head;
    static int[][] line;
    static int[][] tail;
    //시계방향 아래,왼,위,오른
    static int[] dr = {1,0,-1,0};
    static int[] dc = {0,-1,0,1};
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n=Integer.parseInt(st.nextToken());
        m=Integer.parseInt(st.nextToken());
        k=Integer.parseInt(st.nextToken());

        map = new int[n][n];
        tail=new int[m][2];
        line= new int[n][n];
        head = new int[m][2];
        int c=0;

        //0-빈칸,1-머리사람,2-일반인,3-꼬리사람,4-이동선
        for(int i=0; i<n; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<n; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
                if(map[i][j]==1){head[c]=new int[]{i,j};c++;}
                if(map[i][j]!=0){line[i][j]=4;}
            }
        }
        //팀 인원 세기, 꼬리사람 
        for(int i=1; i<=m; i++){
            bfs(i,head[i-1]);
        }
        for(int j=0; j<n; j++){
            for(int k=0; k<n; k++){
                if(map[j][k]==3){
                    tail[line[j][k]-1]=new int[]{j,k};
                }
            }
        }
        
        
        //라운드 진행
        for(int i=1; i<=k; i++){
            move(i);
            getScore(i-1);
        }
        //for(int i=0; i<m; i++){
        //    System.out.print("head : ");
        //    System.out.print(head[i][0]);
        //    System.out.println(head[i][1]);
        //    System.out.print("tail : ");
        //    System.out.print(tail[i][0]);
        //   System.out.println(tail[i][1]);
        //}
        System.out.println(point);
    }
    //line 팀 번호
    public static void bfs(int team,int[] h){
        Queue<int[]> q = new LinkedList<>();
        boolean[][] visit = new boolean[n][n];
        q.add(new int[]{h[0],h[1]});
        visit[h[0]][h[1]]=true;
        line[h[0]][h[1]]=team;
        while(!q.isEmpty()){
            int[] node = q.poll();
            for(int i=0; i<4; i++){
                int nr = node[0]+dr[i];
                int nc = node[1]+dc[i];
                if(nr>=0&&nr<n&&nc>=0&&nc<n){
                    if(!visit[nr][nc] && line[nr][nc]==4){
                        line[nr][nc]=team;
                        q.add(new int[]{nr,nc});
                        visit[nr][nc]=true;
                    }
                }
            }
        }
    } 

    public static void move(int round){
        for(int i=0; i<m; i++){
            int r=head[i][0];
            int c=head[i][1];
            for(int j=0; j<4; j++){
                int nr =r+dr[j];
                int nc =c+dc[j];
                if(inRange(nr,nc,i)&&map[nr][nc]==4){
                    moveOne(i,nr,nc);
                    break;
                }
            }
        }
    }
    public static void moveOne(int i,int nr,int nc){
        int num = Math.abs(head[i][0]-tail[i][0])+Math.abs(head[i][1]-tail[i][1]);
        //System.out.print(nr);
        boolean[][] visited = new boolean[n][n];
        head[i] = new int[]{nr,nc};
        map[nr][nc]=1;
        visited[nr][nc]=true;
        for(int k=0; k<=num; k++){
            for(int j=0; j<4; j++){
                int r = nr+dr[j];
                int c = nc+dc[j];
                if(r<0||r>=n||c<0||c>=n){continue;}
                if(visited[r][c]){continue;}
                if(k==num && line[r][c]==i+1 && map[r][c]==3){map[r][c]=4;break;}
                if(map[r][c]==2 || map[r][c]==1){
                    if(line[r][c]==i+1){
                        if(k==num-1){
                            map[r][c]=3;
                            visited[r][c]=true;
                            nr=r;
                            nc=c;
                            tail[i]=new int[]{r,c};
                        }
                        else{
                            map[r][c]=2;
                            visited[r][c]=true;
                            nr=r;
                            nc=c;
                        }
                        break;
                    }
                }
            }
            
            //for(int j=0; j<n; j++){
            //    for(int l=0; l<n; l++){
            //        System.out.print(map[j][l]);
            //    }
            //    System.out.println("");
            //}
            //System.out.println("---------------");
        }
        
    }
    
    public static boolean inRange(int r, int c,int i){
        if(r>=0 && r<n && c>=0 && c<n){
            if(line[r][c]==i+1){
                return true;
            }
        }

        return false;
    }

    public static void changeDir(int k){
        int[] temp = tail[k-1];
        tail[k-1]=head[k-1];
        head[k-1]=temp;
        map[head[k-1][0]][head[k-1][1]]=1;
        map[tail[k-1][0]][tail[k-1][1]]=3;
    }
    public static void getScore(int r){
        if((r/n)%4==0){
            int round=(r%n);
            for(int i=0; i<n; i++){
                if(map[round][i]==1 || map[round][i]==2 || map[round][i]==3){
                    point+=calScore(round,i);
                    break;
                }
            }
        }
        else if((r/n)%4==1){
            int round=(r%n);
            //System.out.println(r+1);
            for(int i=n-1; i>=0; i--){
                //System.out.println(i);
                if(map[i][round]==1 || map[i][round]==2 || map[i][round]==3){
                    point+=calScore(i,round);
                    break;
                }
            }
        }
        else if((r/n)%4==2){
            int round=n-1-(r%n);
            for(int i=n-1; i>=0; i--){
                if(map[round][i]==1 || map[round][i]==2 || map[round][i]==3){
                    point+=calScore(round,i);
                    break;
                }
            }
        }
        else{
            int round=n-1-(r%n);
            for(int i=0; i<n; i++){
                if(map[i][round]==1 || map[i][round]==2 || map[i][round]==3){
                    point+=calScore(i,round);
                    break;
                }
            }
        }
    }
    public static int calScore(int r, int i){
        int k = line[r][i];
        //System.out.println(head[k-1][0]);
        //System.out.println(r);
        //System.out.println(head[k-1][1]);
        //System.out.println(i);
        int score = Math.abs(head[k-1][0]-r)+Math.abs(head[k-1][1]-i)+1;
        changeDir(k);
        return score*score;            
    }
    
}