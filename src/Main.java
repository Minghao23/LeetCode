import java.util.*;

public class Main {

    //////////Movie Ratings//////////

    public int movieRating(int[] ratings) {
        if(ratings == null || ratings.length == 0) return 0;
        int n = ratings.length;
        int[] skip = new int[n+1];
        int[] take = new int[n+1];
        skip[0] = 0;
        take[0] = 0;
        for(int i = 1; i <= n; i++) {
            skip[i] = take[i-1];
            take[i] = Math.max(take[i-1], skip[i-1]) + ratings[i-1];
        }
        return Math.max(take[n], skip[n]);
    }

    //////////Royal Sort//////////

    public String[] royalSort(String[] names) {
        int i, j;
        String key;
        for (i = 1; i < names.length; i++)
        {
            key = names[i];
            j = i-1;

            while (j >= 0 && compareString(key, names[j]))
            {
                names[j+1] = names[j];
                j = j-1;
            }
            names[j+1] = key;
        }
        return names;
    }

    public boolean compareString(String str1, String str2) { // return true if str1 < str2
        String[] arr1 = str1.split(" ");
        String[] arr2 = str2.split(" ");

        if(arr1[0].compareTo(arr2[0]) < 0) {
            return true;
        }else if (arr1[0].compareTo(arr2[0]) > 0) {
            return false;
        }else {
            return compareNumber(arr1[1], arr2[1]);
        }
    }

    public boolean compareNumber(String num1, String num2) { // return true if num1 < num2
        return(romanToInt(num1) < romanToInt(num2));
    }

    public int romanToInt(String s) {
        char[] arr = s.toCharArray();
        int len = arr.length;
        int res = 0;
        for(int i=0;i<len;i++){
            if(arr[i]=='I'){
                if((i+1)<len&&(arr[i+1]=='V'||arr[i+1]=='X')){
                    res=res-1;
                }else{
                    res=res+1;
                }
            }
            if(arr[i]=='X'){
                if((i+1)<len&&(arr[i+1]=='C'||arr[i+1]=='L')){
                    res=res-10;
                }else{
                    res=res+10;
                }
            }
            if(arr[i]=='C'){
                if((i+1)<len&&(arr[i+1]=='D'||arr[i+1]=='M')){
                    res=res-100;
                }else{
                    res=res+100;
                }
            }
            if(arr[i]=='V') res += 5;
            if(arr[i]=='L') res += 50;
            if(arr[i]=='D') res += 500;
            if(arr[i]=='M') res += 1000;
        }
        return res;
    }


    public String nextTime(String input) {
        char h1 = input.charAt(0);
        char h2 = input.charAt(1);
        char m1 = input.charAt(3);
        char m2 = input.charAt(4);
        char[] arr = new char[]{h1, h2, m1, m2};

        boolean[] visited = new boolean[arr.length];
        StringBuilder temp = new StringBuilder();
        HashSet<String> result = new HashSet<>();
        dfs(arr, visited, temp, result);
        ArrayList<String> list = new ArrayList<>(result);
        Collections.sort(list);
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).equals(input)) {
                return list.get((i + 1) % result.size()).toString();
            }
        }
        return "error";
    }

    public void dfs(char[] arr, boolean[] visited, StringBuilder temp, HashSet<String> result) {
        if (temp.length() == 4) {
            if(Integer.parseInt(temp.substring(0,2)) < 24 &&
                    Integer.parseInt(temp.substring(2)) < 60) {
                temp.insert(2, ':');
                result.add(temp.toString());
                temp.deleteCharAt(2);
            }
            return;
        }
        for(int i = 0; i < arr.length; i++) {
            if(visited[i]) {
                continue;
            }
            temp.append(arr[i]);
            visited[i] = true;
            dfs(arr, visited, temp, result);
            temp.deleteCharAt(temp.length() - 1);
            visited[i] = false;
        }
    }


    public String nextTime1(String input) {
        int h1 = input.charAt(0) - 48;
        int h2 = input.charAt(1) - 48;
        int m1 = input.charAt(3) - 48;
        int m2 = input.charAt(4) - 48;
        int[] arr = new int[]{h1, h2, m1, m2};
        int x;
        int y = helper(arr, -1);
        if((x = helper(arr, m2)) != -1) {
            return "" + h1 + h2 + ":" + m1 + x;
        }
        if((x = helper(arr, m1)) != -1 && x < 6) {
            return "" + h1 + h2 + ":" + x + y;
        }
        if((x = helper(arr, h2)) != -1 && (h1*10+x < 24)) {

            return "" + h1 + x + ":" + y + y;
        }
        if((x = helper(arr, h1)) != -1 && (x*10+y < 24)) {

            return "" + x + y + ":" + y + y;
        }
        return "" + y + y + ":" + y + y;
    }

    private int helper(int[] arr, int n){
        int res = 10;
        for(int i = 0; i < 4; i++) {
            if(arr[i] > n && arr[i] < res) {
                res = arr[i];
            }
        }
        if(res == 10) return -1;
        return res;
    }

    public int flowerQuestion(int[] flowers, int k) {
        if(flowers == null) return -1;
        if(flowers.length == k) return flowers.length;
        TreeSet<Integer> bst = new TreeSet<>();
        bst.add(0);
        bst.add(flowers.length + 1);
        for(int i = flowers.length - 1; i >= 0; i--) {
            bst.add(flowers[i]);
            Integer lo = bst.lower(flowers[i]);
            Integer hi = bst.higher(flowers[i]);
            if((flowers[i] - lo - 1 == k) || (hi - flowers[i] - 1 == k)) {
                return i;
            }
        }
        return -1;
    }

    public int kEmptySlots(int[] flowers, int k) {
        if(flowers == null || flowers.length < 2) {
            return -1;
        }
        TreeSet<Integer> ts = new TreeSet<Integer>();
        for(int i = 0; i < flowers.length; i++) {
            ts.add(flowers[i]);
            Integer lo = ts.lower(flowers[i]);
            Integer hi = ts.higher(flowers[i]);
            if((lo != null && (flowers[i] - lo - 1 == k)) || (hi != null && (hi - flowers[i] - 1 == k))) {
                return i + 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Main main = new Main();

//        int[] input = new int[]{9, -1, -3, 4, 5};
//        int[] input2 = new int[]{-1, -3, 5};
////        System.out.println(main.movieRating(input2));
//        String[] input3 = new String[]{"Abc IV", "Abc X", "Ab III", "Bba I", "Cdd XI", "Cdd V"};
//        main.royalSort(input3);
//        for(int i = 0; i < input3.length; i++) {
//            System.out.println(input3[i]);
//        }
//        main.nextTime("23:45");

//        System.out.println(main.nextTime("23:59"));
//        System.out.println(main.nextTime("14:32"));
//        System.out.println(main.nextTime("19:05"));
//        System.out.println(main.nextTime("22:46"));
//        System.out.println(main.nextTime("07:30"));
//        System.out.println(main.nextTime("08:51"));
//        System.out.println(main.nextTime("12:17"));
//        System.out.println(main.nextTime("12:22"));

        System.out.println(main.flowerQuestion(new int[] {3,1,5,4,2}, 1));
        System.out.println(main.flowerQuestion(new int[] {3,1,5,4,2}, 2));
        System.out.println(main.flowerQuestion(new int[] {3,1,5,4,2}, 3));
        System.out.println(main.flowerQuestion(new int[] {3,1,5,4,2}, 4));
        System.out.println(main.flowerQuestion(new int[] {3,1,5,4,2}, 5));

    }
}
