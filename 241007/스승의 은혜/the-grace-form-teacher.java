import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int money = Integer.parseInt(st.nextToken());
        List<int[]> gift = new ArrayList<>();

        int pi,si=0;
        //1000
        for(int i=0; i<n; i++){
            st = new StringTokenizer(br.readLine());
            pi= Integer.parseInt(st.nextToken());
            si= Integer.parseInt(st.nextToken());
            gift.add(new int[]{pi,si});
        }
        gift.sort((g1, g2) -> {
            if (g1[0] > g1[1]) {
                return g1[0] - g2[0];
            }
            return g1[1] - g2[1];
        });
//        for(int[] g : gift){
//            System.out.print(g[0]);
//            System.out.print(",");
//            System.out.println(g[1]);
//        }
        int total=0;
        for(int i=0; i<n; i++){
            int[] g = gift.get(i);
            if(total+g[0]+g[1]>=money){
                total+=g[0]/2+g[1];
            }
            else{total+=g[0]+g[1];}
            if(total>=money){
                System.out.println(i);
                break;
            }
        }
    }
}