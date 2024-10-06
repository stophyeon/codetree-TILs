import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[] arr;
    static int n;
    static int cnt=0;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        arr= new int[n];

        for(int i=0; i<n; i++){
            st = new StringTokenizer(br.readLine());
            arr[i]=Integer.parseInt(st.nextToken());
        }

        for(int i=0; i<n; i++){
            boolean[] visited = new boolean[n];
            visited[i]=true;
            dfs(1,arr[i],visited);
        }
        System.out.println(cnt);
    }
    public static void dfs(int depth,int total, boolean[] visited){
        if(cnt<depth) cnt=depth;
        if(depth==n) return;
        for(int i=0; i<n; i++){
            if(visited[i]) continue;
            if(isCarry(total,arr[i])) continue;
            visited[i]=true;
            dfs(depth+1,total+arr[i],visited);
            visited[i]=false;
        }
    }

     public static boolean isCarry(int sum, int target){
        while (sum > 0 || target > 0) {
            int num1 = sum % 10;
            int num2 = target % 10;
            if (num1 + num2 >= 10) return true;
            sum /= 10;
            target /= 10;
        }
        return false;
    }
}