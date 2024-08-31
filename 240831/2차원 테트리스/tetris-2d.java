import java.util.*;
import java.io.*;

public class Main {
    static int n;
    static int res=0;
    static int[][] block;
    static boolean[][] red=new boolean[4][6];
    static boolean[][] yellow=new boolean[6][4];
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        block= new int[n][3];
        for(int i=0; i<n; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<3; j++){
                block[i][j]= Integer.parseInt(st.nextToken());
            }
        }
        for(int i=0; i<n; i++){
            moveRed(block[i]);
            moveYellow(block[i]);
        }
        System.out.println(res);
        int blockNum=0;
        for(int i=2; i<6; i++){
            for(int j=0; j<4; j++){
                if(yellow[i][j]==true){
                    blockNum++;
                    //System.out.print("1");
                }
                //else{System.out.print("0");}
            }
            //System.out.println("");
        }
        //System.out.println("------------");
        for(int i=0; i<4; i++){
            for(int j=2; j<6; j++){
                if(red[i][j]==true){
                    blockNum++;
                    //System.out.print("1");
                }
                //else{System.out.print("0");}
            }
            //System.out.println("");
        }
        
        System.out.println(blockNum);
    }

    public static void moveRed(int[] block){
        int loc=-1;
        if(block[0]==1){
            for(int i=5; i>=0; i--){
                if(red[block[1]][i]==true){
                    loc=i;
                }
            }
            if(loc==-1){
                red[block[1]][5]=true;    
            }
            else{red[block[1]][loc-1]=true;}
            pointRed();
            for(int i=0; i<4; i++){
                for(int j=0; j<2; j++){
                    if(red[i][j]&&loc<=2&&loc!=-1){
                        removeRed(1,5);
                    }
                }
            }
        }
        if(block[0]==3){
            for(int i=5; i>=0; i--){
                if(red[block[1]][i]==true||red[block[1]+1][i]==true){
                    loc=i;
                }
            }
            if(loc==-1){
                red[block[1]][5]=true;
                red[block[1]+1][5]=true;
                
            }
            else{
                red[block[1]][loc-1]=true;
                red[block[1]+1][loc-1]=true;
            }
            pointRed();
            for(int i=0; i<4; i++){
                for(int j=0; j<2; j++){
                    if(red[i][j]&&loc<=2&&loc!=-1){
                        removeRed(1,5);
                    }
                }
            }
        }
        if(block[0]==2){
            for(int i=5; i>0; i--){
                if(red[block[1]][i]==true){
                    loc=i;
                }
            }
            if(loc==-1){
                red[block[1]][5]=true;
                red[block[1]][4]=true;
                
            }
            else{
                red[block[1]][loc-1]=true;
                red[block[1]][loc-2]=true;
            }
            pointRed();
            for(int i=0; i<4; i++){
                for(int j=0; j<2; j++){
                    if(red[i][j]&&loc<=3&&loc!=-1){
                        removeRed(4-loc,5);
                    }
                }
            }
            
        }
    }

    public static void moveYellow(int[] block){
        int loc=-1;
        if(block[0]==1){
            for(int i=5; i>=0; i--){
                if(yellow[i][block[2]]==true){
                    loc=i;
                }
            }
            if(loc==-1){
                yellow[5][block[2]]=true;    
            }
            else{yellow[loc-1][block[2]]=true;}
            pointYellow();
             for(int i=0; i<4; i++){
                for(int j=0; j<2; j++){
                    if(yellow[i][j]&&loc<=2&&loc!=-1){
                        removeYellow(1,5);
                    }
                }
            }
        }
        if(block[0]==2){
            for(int i=5; i>=0; i--){
                if(yellow[i][block[2]]==true||yellow[i][block[2]+1]==true){
                    loc=i;
                }
            }
            if(loc==-1){
                yellow[5][block[2]]=true;
                yellow[5][block[2]+1]=true;    
            }
            else{
                yellow[loc-1][block[2]]=true;
                yellow[loc-1][block[2]+1]=true;
            }
            pointYellow();
             for(int i=0; i<4; i++){
                for(int j=0; j<2; j++){
                    if(yellow[i][j]&&loc<=2&&loc!=-1){
                        removeYellow(1,5);
                    }
                }
            }
        }
        if(block[0]==3){
            for(int i=5; i>0; i--){
                if(yellow[i][block[2]]==true){
                   loc=i;
                }
            }
            if(loc==-1){
                yellow[5][block[2]]=true;
                yellow[4][block[2]]=true;    
            }
            else{
                yellow[loc-1][block[2]]=true;
                yellow[loc-2][block[2]]=true;
            }
            pointYellow();
             for(int i=0; i<4; i++){
                for(int j=0; j<2; j++){
                    if(yellow[i][j]&&loc<=3&&loc!=-1){
                        removeYellow(4-loc,5);
                    }
                }
            }
        }
    }

    public static void removeRed(int c,int s){
        for(int i=s; i>s-c; i--){
            for(int j=0; j<4; j++){
                red[j][i]=false;
            }
        }
        for(int i=0; i<4; i++){
            for(int j=s; j>=0; j--){
                if(red[i][j]==true){
                    if(j+c>=n){
                        red[i][5]=true;
                        red[i][j]=false;
                    }
                    else{
                        red[i][j+c]=true;
                        red[i][j]=false;
                    }
                    
                }
            }
        }
    }

    public static void removeYellow(int r,int s){
        for(int i=s; i>s-r; i--){
            for(int j=0; j<4; j++){
                yellow[i][j]=false;
            }
        }
        for(int i=0; i<4; i++){
            for(int j=s; j>=0; j--){
                if(yellow[j][i]==true){
                    if(j+r>=n){
                       yellow[5][i]=true;
                       yellow[j][i]=false; 
                    }
                    else{
                        yellow[j+r][i]=true;
                        yellow[j][i]=false;
                    }
                    
                }
            }
        }
    }

    public static void pointRed(){
        for(int i=5; i>=0; i--){
            int count=0;
            for(int j=0; j<4; j++){
                if(red[j][i]==true){count++;}
            }
            if(count==4){
                removeRed(1,i);
                res++;
            }
        }
    }
    public static void pointYellow(){
        for(int i=5; i>=0; i--){
            int count=0;
            for(int j=0; j<4; j++){
                if(yellow[i][j]==true){count++;}
            }
            if(count==4){
                removeYellow(1,i);
                res++;
            }
        }
    }
}