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
        dfs(0,0,0);
        System.out.println(cnt);
    }
    public static void dfs(int depth,int total, int count){
        if(cnt<count) cnt=count;
        if(depth==n) return;
        for(int i=depth; i<n; i++){
            if(isCarry(total,arr[i])) continue;
            dfs(i+1,total+arr[i],count+1);
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