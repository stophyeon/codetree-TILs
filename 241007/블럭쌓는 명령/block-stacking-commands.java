import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.invoke.CallSite;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int[] m = new int[n+1];
        int start =0;
        int end=0;
        for(int i=0; i<k; i++){
            st = new StringTokenizer(br.readLine());
            start=Integer.parseInt(st.nextToken())-1;
            end=Integer.parseInt(st.nextToken())-1;
            m[start]+=1;
            m[end+1]-=1;
        }
        int total=0;
        for(int i=0; i<n; i++){
            total += m[i];
            m[i]= total;
        }
        Arrays.sort(m);
        System.out.print(m[(n/2)+1]);
    }
}