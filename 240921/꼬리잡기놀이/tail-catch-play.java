import java.util.*;
import java.io.*;

public class Main{
    static int n;
    static int m;
    static int k;
    static int point;
    static int[][] map;
    static int[][] head;
    static int[][] line;
    static int[][] tail;
    static int[] teamNum;
    static int[] lineNum;
    //시계방향 아래,왼,위,오른
    static int[] dr = {1,0,-1,0};
    static int[] dc = {0,-1,0,1};
    //반시계방향 왼,아래,오른,위
    static int[] revdr = {0,1,0,-1};
    static int[] revdc = {-1,0,1,0};
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
        teamNum = new int[m];
        lineNum = new int[m];
        Arrays.fill(lineNum,1);
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

        for(int l=1; l<=m; l++){
            for(int i=0; i<n; i++){
                for(int j=0; j<n; j++){
                    if(line[i][j]==l && map[i][j]!=4){
                        teamNum[l-1]+=1;
                    }
                }
            }
        }
        //라운드 진행
        for(int i=1; i<=k; i++){
            //System.out.println(i);
            move(i);
            getScore(i-1);
        }
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
                        lineNum[team-1]+=1;
                        q.add(new int[]{nr,nc});
                        visit[nr][nc]=true;
                    }
                }
            }
        }
    }

    public static void move(int round){
        for(int i=0; i<m; i++){
            int hr=head[i][0];
            int hc=head[i][1];
            int tr=tail[i][0];
            int tc=tail[i][1];
            if(teamNum[i]==lineNum[i]){
                for(int j=0; j<4; j++){
                    int nr =hr+dr[j];
                    int nc =hc+dc[j];
                    if(inRange(nr,nc,i)&&map[nr][nc]==3){
                        map[hr][hc]=2;
                        moveOne(i,nr,nc,j);
                        break;
                    }
                }
            }
            else{
                for(int j=0; j<4; j++){
                    int nr =hr+dr[j];
                    int nc =hc+dc[j];
                    if(inRange(nr,nc,i)&&map[nr][nc]==4){
                        moveOne(i,nr,nc,j);
                        break;
                    }
                }
            }
        }
    }

    public static void moveOne(int i,int nr,int nc,int index){
        int num = teamNum[i];
        boolean[][] visited = new boolean[n][n];
        head[i] = new int[]{nr,nc};
        if(num==lineNum[i]){
            map[nr][nc]=1;
            visited[nr-dr[index]][nc-dc[index]]=true;
            nr-=dr[index];
            nc-=dc[index];
            for(int j=0; j<4; j++){
                int r = nr+dr[j];
                int c = nc+dc[j];
                if(r<0||r>=n||c<0||c>=n){continue;}
                if(visited[r][c]){continue;}
                if(line[r][c]!=i+1){continue;}

                if(map[r][c]==1){
                        for(int l=0; l<4; l++){
                        int tr=r+dr[l];
                        int tc=c+dc[l];
                        if(tr<0||tr>=n||tc<0||tc>=n){continue;}
                        if(map[tr][tc]==2&&!visited[tr][tc]){
                            map[tr][tc]=3;
                            tail[i]=new int[]{tr,tc};
                            break;
                        }
                    }
                    if(map[r][c]==2){
                        visited[r][c]=true;
                        nr=r;
                        nc=c;
                        j=-1;
                    }
                    break;
                }

            }
        }
        else{
            map[nr][nc]=1;
            visited[nr][nc]=true;
            for(int j=0; j<4; j++){
                int r = nr+dr[j];
                int c = nc+dc[j];
                if(r<0||r>=n||c<0||c>=n){continue;}
                if(visited[r][c]){continue;}
                if(line[r][c]!=i+1){continue;}

                if(map[r][c]==2){
                    visited[r][c]=true;
                    nr=r;
                    nc=c;
                    j=-1;
                }
                if(map[r][c]==3){map[r][c]=4;map[nr][nc]=3;tail[i]=new int[]{nr,nc};break;}
                if(map[r][c]==1){map[r][c]=2;nr=r;nc=c;visited[r][c]=true;j=-1;}

            }
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
                if(map[round][i]==2){
                    point+=calScore(round,i);
                    break;
                }
                if(map[round][i]==1){
                    point+=1;
                    changeDir(line[round][i]);
                    break;
                }
                if(map[round][i]==3){
                    point+=teamNum[line[round][i]-1]*teamNum[line[round][i]-1];
                    changeDir(line[round][i]);
                    break;
                }
            }
        }
        else if((r/n)%4==1){
            int round=(r%n);
            for(int i=n-1; i>=0; i--){
                if(map[i][round]==2){
                    point+=calScore(i,round);
                    break;
                }
                if(map[i][round]==1){
                    point+=1;
                    changeDir(line[i][round]);
                    break;
                }
                if(map[i][round]==3){
                    point+=teamNum[line[i][round]-1]*teamNum[line[i][round]-1];
                    changeDir(line[i][round]);
                    break;
                }
            }
        }
        else if((r/n)%4==2){
            int round=n-1-(r%n);
            for(int i=n-1; i>=0; i--){
                if(map[round][i]==2){
                    point+=calScore(round,i);
                    break;
                }
                if(map[round][i]==1){
                    point+=1;
                    changeDir(line[round][i]);
                    break;
                }
                if(map[round][i]==3){
                    point+=teamNum[line[round][i]-1]*teamNum[line[round][i]-1];
                    changeDir(line[round][i]);
                    break;
                }
            }
        }
        else{
            int round=n-1-(r%n);
            for(int i=0; i<n; i++){
                if(map[i][round]==2){
                    point+=calScore(i,round);
                    break;
                }
                if(map[i][round]==1){
                    point+=1;
                    changeDir(line[i][round]);
                    break;
                }
                if(map[i][round]==3){
                    point+=teamNum[line[i][round]-1]*teamNum[line[i][round]-1];
                    changeDir(line[i][round]);
                    break;
                }
            }
        }
    }
    public static int calScore(int round, int i){
        int k = line[round][i];
        int score = 1;
        int r = head[k-1][0];
        int c = head[k-1][1];
        boolean[][] visited = new boolean[n][n];
        visited[r][c]=true;

        for(int l=0; l<4; l++){
            int nr= r+dr[l];
            int nc= c+dc[l];
            if(nr<0||nr>=n||nc<0||nc>=n){continue;}
            if(visited[nr][nc]){continue;}
            if(line[nr][nc]!=k){continue;}
            if(map[nr][nc]==3||map[nr][nc]==4){continue;}

            if(map[nr][nc]==2){
                visited[nr][nc]=true;
                score++;
                r=nr;
                c=nc;
                l=-1;
            }
            if(nr==round&&nc==i){break;}
        }

        //System.out.println(score);
        changeDir(k);
        return score*score;
    }
}