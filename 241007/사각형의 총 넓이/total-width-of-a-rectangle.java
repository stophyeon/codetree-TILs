import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int num=500;
        int[][] map =new int[1001][1001];
        int x1,y1,x2,y2=0;
        int total=0;
        for(int i=0; i<n; i++){
            st = new StringTokenizer(br.readLine());
            x1=Integer.parseInt(st.nextToken())+num;
            y1=Integer.parseInt(st.nextToken())+num;
            x2=Integer.parseInt(st.nextToken())+num;
            y2=Integer.parseInt(st.nextToken())+num;
            for(int j=x1; j<x2; j++){
                for(int k=y2; k<y1; k++){
                    if(map[k][j]==1) continue;
                    map[k][j]=1;
                    total++;
                }
            }
        }

        System.out.println(total);
    }
}