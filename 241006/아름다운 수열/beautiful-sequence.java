import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static int n;
    static int m;
    static int[]  a;
    static int[]  b;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        List<Integer> ans = new ArrayList<>();
        a=new int[n];
        for(int i=0 ;i<n; i++){
            st = new StringTokenizer(br.readLine());
            a[i] = Integer.parseInt(st.nextToken());
        }
        st = new StringTokenizer(br.readLine());
        m = Integer.parseInt(st.nextToken());
        b=new int[m];
        for(int i=0 ;i<m; i++){
            st = new StringTokenizer(br.readLine());
            b[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(b);
        int[] beautiful = new int[m-1];
        for (int i=0; i<m-1; i++){
            beautiful[i]=b[i+1]-b[0];

        }
        int[] arr = new int[m];
        for(int i=0; i<=n-m; i++){
            int cnt=0;
            for(int j=0; j<m; j++){
                arr[j]= a[i+j];
            }
            Arrays.sort(arr);
            //Arrays.stream(arr).forEach(System.out::println);
            for(int k=0; k<m-1; k++){
                if(arr[k+1]!=arr[0]+beautiful[k]) break;
                cnt++;
            }
            if(cnt==m-1) ans.add(i+1);
        }
        System.out.println(ans.size());
        for(int n : ans){
            System.out.println(n);
        }
    }
}