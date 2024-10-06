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
        for(int i=0; i<n; i++){
            if(visited[i]) continue;
            if(isCarry(total,arr[i])){
                visited[i]=true;
                dfs(depth+1,total+arr[i],visited);
                visited[i]=false;
            }
        }
    }

    public static boolean isCarry(int n1, int n2){
        String[] num1 = String.valueOf(n1).split("");
        String[] num2 = String.valueOf(n2).split("");
        int min = Math.min(num1.length,num2.length);
        for(int i=0; i<min; i++){
            if(Integer.parseInt(num1[num1.length-i-1])+Integer.parseInt(num2[num2.length-i-1])>=10) return false;
        }
        return true;
    }
}