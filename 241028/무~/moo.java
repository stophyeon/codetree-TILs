import  java.util.*;
import  java.io.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long N = scanner.nextLong();  // N번째 문자 입력
        scanner.close();

        System.out.println(findMooChar(N));
    }

    // N번째 문자가 무엇인지 찾는 함수
    private static char findMooChar(long n) {
        long length = 3;
        int t=0;
        while (n > length) {
            t++;
            length = length * 2 + (t + 3);  // S(t)의 길이 공식
        } // 적절한 길이의 수열을 찾음
        while (true) {
            long prevLength = (length - (t + 3)) / 2;  // 이전 단계 S(t-1)의 길이
            long midStart = prevLength + 1;  // 중간 "m"의 위치

            if (n <= prevLength) {  // n이 첫 번째 S(t-1)에 속할 때
                length = prevLength;
                t--;
            } else if (n >= midStart && n < midStart + (t + 3)) {  // n이 중간 부분에 속할 때
                return (n == midStart) ? 'm' : 'o';  // 중간 첫 번째 문자면 'm', 아니면 'o'
            } else {  // n이 두 번째 S(t-1)에 속할 때
                n -= (prevLength + (t + 3));
                length = prevLength;
                t--;
            }
        }
    }


}