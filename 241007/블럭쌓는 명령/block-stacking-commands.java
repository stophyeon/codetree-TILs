import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int[] map = new int[n];
        int start =0;
        int end=0;
        for(int i=0; i<k; i++){
            st = new StringTokenizer(br.readLine());
            start=Integer.parseInt(st.nextToken())-1;
            end=Integer.parseInt(st.nextToken())-1;
            for(int j=start; j<=end; j++){
                map[j]+=1;
            }
        }
        Arrays.sort(map);
        System.out.print(map[n/2]);
    }
}