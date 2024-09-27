import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static final int MAX_M = 100000;
    public static final int MAX_N = 100000;

    public static Scanner sc;

    public static int n, m, q;
    
    // id에 해당하는 상자의 nxt값과 prv값을 관리합니다.
    // 0이면 없다는 뜻입니다.
    public static int[] prv = new int[MAX_M + 1];
    public static int[] nxt = new int[MAX_M + 1];
    
    // 각 벨트별로 head, tail id, 그리고 총 선물 수를 관리합니다.
    // 0이면 없다는 뜻입니다.
    public static int[] head = new int[MAX_N];
    public static int[] tail = new int[MAX_N];
    public static int[] numGift = new int[MAX_N];
    
    // 공장 설립
    public static void buildFactory() {
        // 공장 정보를 입력받습니다.
        n = sc.nextInt();
        m = sc.nextInt();
        
        // 벨트 정보를 만들어줍니다.
        ArrayList<Integer>[] boxes = new ArrayList[n];
        for(int i = 0; i < n; i++)
            boxes[i] = new ArrayList<>();
        
        for(int id = 1; id <= m; id++) {
            int bNum = sc.nextInt();
            bNum--;
            
            boxes[bNum].add(id);
        }
    
        // 초기 벨트의 head, tail, nxt, prv값을 설정해줍니다.
        for(int i = 0; i < n; i++) {
            // 비어있는 벨트라면 패스합니다.
            if(boxes[i].size() == 0)
                continue;
            
            // head, tail을 설정해줍니다.
            head[i] = boxes[i].get(0);
            tail[i] = boxes[i].get(boxes[i].size() - 1);
    
            // 벨트 내 선물 총 수를 관리해줍니다.
            numGift[i] = boxes[i].size();
    
            // nxt, prv를 설정해줍니다.
            for(int j = 0; j < boxes[i].size() - 1; j++) {
                nxt[boxes[i].get(j)] = boxes[i].get(j + 1);
                prv[boxes[i].get(j + 1)] = boxes[i].get(j);
            }
        }
    }
    
    // 물건을 전부 옮겨줍니다.
    public static void move() {
        int mSrc = sc.nextInt();
        int mDst = sc.nextInt();
        mSrc--; mDst--;
    
        // mSrc에 물건이 없다면
        // 그대로 mDst내 물건 수가 답이 됩니다.
        if(numGift[mSrc] == 0) {
            System.out.println(numGift[mDst]);
            return;
        }
    
        // mDst에 물건이 없다면
        // 그대로 옮겨줍니다.
        if(numGift[mDst] == 0) {
            head[mDst] = head[mSrc];
            tail[mDst] = tail[mSrc];
        }
        else {
            int origHead = head[mDst];
            // mDst의 head를 교체해줍니다.
            head[mDst] = head[mSrc];
            // mSrc의 tail과 기존 mDst의 head를 연결해줍니다.
            int srcTail = tail[mSrc];
            nxt[srcTail] = origHead;
            prv[origHead] = srcTail;
        }
    
        // head, tail을 비워줍니다.
        head[mSrc] = tail[mSrc] = 0;
    
        // 선물 상자 수를 갱신해줍니다.
        numGift[mDst] += numGift[mSrc];
        numGift[mSrc] = 0;
    
        System.out.println(numGift[mDst]);
    }
    
    // 해당 벨트의 head를 제거합니다.
    public static int removeHead(int bNum) {
        // 불가능하면 패스합니다.
        if(numGift[bNum] == 0)
            return 0;
        
        // 노드가 1개라면
        // head, tail 전부 삭제 후
        // 반환합니다.
        if(numGift[bNum] == 1) {
            int id = head[bNum];
            head[bNum] = tail[bNum] = 0;
            numGift[bNum] = 0;
            return id;
        }
    
        // head를 바꿔줍니다.
        int hid = head[bNum];
        int nextHead = nxt[hid];
        nxt[hid] = prv[nextHead] = 0;
        numGift[bNum]--;
        head[bNum] = nextHead;
    
        return hid;
    }
    
    // 해당 밸트에 head를 추가합니다.
    public static void pushHead(int bNum, int hid) {
        // 불가능한 경우는 진행하지 않습니다.
        if(hid == 0)
            return;
    
        // 비어있었다면
        // head, tail이 동시에 추가됩니다.
        if(numGift[bNum] == 0) {
            head[bNum] = tail[bNum] = hid;
            numGift[bNum] = 1;
        }
        // 그렇지 않다면
        // head만 교체됩니다.
        else {
            int origHead = head[bNum];
            nxt[hid] = origHead;
            prv[origHead] = hid;
            head[bNum] = hid;
            numGift[bNum]++;
        }
    }
    
    // 앞 물건을 교체해줍니다.
    public static void change() {
        int mSrc = sc.nextInt();
        int mDst = sc.nextInt();
        mSrc--; mDst--;
        
        int srcHead = removeHead(mSrc);
        int dstHead = removeHead(mDst);
        pushHead(mDst, srcHead);
        pushHead(mSrc, dstHead);
        
        System.out.println(numGift[mDst]);
    }
    
    // 물건을 나눠옮겨줍니다.
    public static void divide() {
        int mSrc = sc.nextInt();
        int mDst = sc.nextInt();
        mSrc--; mDst--;
    
        // 순서대로 src에서 박스들을 빼줍니다.
        int cnt = numGift[mSrc];
        ArrayList<Integer> boxIds = new ArrayList<>();
        for(int i = 0; i < cnt / 2; i++)
            boxIds.add(removeHead(mSrc));
        
        // 거꾸로 뒤집어서 하나씩 dst에 박스들을 넣어줍니다.
        for(int i = boxIds.size() - 1; i >= 0; i--)
            pushHead(mDst, boxIds.get(i));
    
        System.out.println(numGift[mDst]);
    }
    
    // 선물 점수를 얻습니다.
    public static void giftScore() {
        int pNum = sc.nextInt();
    
        int a = prv[pNum] != 0 ? prv[pNum] : -1;
        int b = nxt[pNum] != 0 ? nxt[pNum] : -1;
    
        System.out.println(a + 2 * b);
    }
    
    // 벨트 정보를 얻습니다.
    public static void beltScore() {
        int bNum = sc.nextInt();
        bNum--;
    
        int a = head[bNum] != 0 ? head[bNum] : -1;
        int b = tail[bNum] != 0 ? tail[bNum] : -1;
        int c = numGift[bNum];
    
        System.out.println(a + 2 * b + 3 * c);
    }

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        // 입력:
        q = sc.nextInt();
        while(q-- > 0) {
            int qType = sc.nextInt();
            if(qType == 100)
                buildFactory();
            else if(qType == 200)
                move();
            else if(qType == 300)
                change();
            else if(qType == 400)
                divide();
            else if(qType == 500)
                giftScore();
            else
                beltScore();
        }
    }
}